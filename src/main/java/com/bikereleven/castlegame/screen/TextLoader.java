package com.bikereleven.castlegame.screen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.bikereleven.castlegame.utility.Reference;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * This class is designed to load and cache game text from the various text
 * files. The text will remain in the cache unless the cache requires more space
 * or all references to the text's key are released.
 * 
 * Dialog is found in the /assets/castlegame/text folder under the localization and groupname
 * see screen-config.txt for examples
 * 
 * @author Biker
 *
 */
public abstract class TextLoader {

	private static String loadDialog(String key) throws Exception {

		Iterator<String> itt = Splitter.on(".").limit(3).split(key).iterator();
		String fileURL = String.format("/assets/castlegame/text/%s/%s-%s.txt", Reference.localization, itt.next(), itt.next());
		String dialogKey = itt.next();
		
		Reference.logger.traceEntry("Dialog loader attempting to load File:({}) Key:({})", fileURL, dialogKey);

		BufferedReader in = new BufferedReader(new InputStreamReader(TextLoader.class.getResourceAsStream(fileURL)));
		try {
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.trim().startsWith("#")) continue;
				
				itt = Splitter.on("=").limit(2).trimResults().split(line).iterator();
				if (dialogKey.equals(itt.next())) {
					return itt.next();
				}
			}
		} finally {
			in.close();
			Reference.logger.traceExit();
		}

		throw new Exception();

	}

	private static LoadingCache<String, String> dialogs = CacheBuilder.newBuilder().maximumSize(100)
			.expireAfterWrite(10, TimeUnit.MINUTES).weakKeys().build(new CacheLoader<String, String>() {

				@Override
				public String load(String key) throws Exception {
					Reference.logger.info("Dialog loader loaded {}", key);
					return loadDialog(key);
				}

			});

	public static String request(String key) {
		try {
			return dialogs.get(key);
		} catch (Exception e) {
			Reference.logger.error("Dialog loader could not find {}", key);
			return "<NO-TEXT>";
		}
	}

}
