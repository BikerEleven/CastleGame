package com.bikereleven.castlegame.world;

import java.util.Date;
import static com.google.common.base.Preconditions.*;

public abstract class TimePiece {

	private static String tFormat = "%02d:%02d:%02d";

	public static long getTime() {
		return new Date().getTime();
	}

	public static String diff(long time1, long time2) {
		checkArgument(time1 > 0, "The first time must be over 0");
		checkArgument(time2 > 0, "The second time must be over 0");

		long diff = time2 - time1;

		int s = (int) (diff / 1000) % 60 ;
		int m = (int) ((diff / (1000*60)) % 60);
		int h = (int) ((diff / (1000*60*60)) % 24);
		
		return String.format(tFormat, h, m, s);
	}

}
