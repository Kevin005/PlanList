package com.future.bigblack.untils;


import com.xsfuture.xsfuture2.config.Configuration;

public class Log {
	public static boolean D;
	public static boolean E;
	public static boolean I;
	public static boolean V;
	public static boolean W;
	public static boolean T;
	private static boolean printLog = Boolean.parseBoolean(Configuration.getProperty("printLog", "false"));

	static {
		if (printLog) {

			D = Boolean.parseBoolean(Configuration.getProperty("debugLog", "false"));
			E = Boolean.parseBoolean(Configuration.getProperty("errorLog", "false"));
			I = Boolean.parseBoolean(Configuration.getProperty("infoLog", "false"));
			V = Boolean.parseBoolean(Configuration.getProperty("viewLog", "false"));
			W = Boolean.parseBoolean(Configuration.getProperty("warnLog", "false"));
			T = Boolean.parseBoolean(Configuration.getProperty("testLog", "false"));
		}
	}

	public static void t(String tag, String msg) {
		if (printLog) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (printLog) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (printLog) {
			android.util.Log.d(tag, msg, tr);
		}
	}

	public static void e(String tag, String msg) {
		if (printLog) {
			android.util.Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (printLog) {
			android.util.Log.e(tag, msg, tr);
		}
	}

	public static void i(String tag, String msg) {
		if (printLog) {
			android.util.Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (printLog) {
			android.util.Log.i(tag, msg, tr);
		}
	}

	public static void v(String tag, String msg) {
		if (printLog) {
			android.util.Log.v(tag, msg);
		}
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (printLog) {
			android.util.Log.v(tag, msg, tr);
		}
	}

	public static void w(String tag, String msg) {
		if (printLog) {
			android.util.Log.w(tag, msg);
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (printLog) {
			android.util.Log.w(tag, msg, tr);
		}
	}

	public static void w(String tag, Throwable tr) {
		if (printLog) {
			android.util.Log.w(tag, tr);
		}
	}
}
