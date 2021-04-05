package com.koossa.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Simple logger library.
 * Call init() to start and dispose() to stop and save file.
 * info(), debug() and error() called between init() and dispose().
 * @author KoosSA
 *
 */
public class Log {
	
	private static File logFolder;
	private static int maxFiles = 50;
	private static String fileExt = ".log";
	private static FileWriter writer;
	private static File logFile;
	private static String name;
	private static boolean debug = false;
	private static StringBuilder builder;
	private static SimpleDateFormat format = new SimpleDateFormat("[HH_mm_ss]");
	
	/**
	 * Initialises the Logger and prepare for receiving log commands.
	 * @param folder
	 * @param debug
	 */
	public static void init(File folder, boolean debug) {
		logFolder = folder;
		Log.debug = debug;
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (folder.list().length >= maxFiles) {
			int numToDelete = folder.list().length - maxFiles + 1;
			for (int i = 0; i < numToDelete; i++) {
				File file = folder.listFiles()[i];
				file.deleteOnExit();
			}
		}
		builder = new StringBuilder();
		generateFile();
	}
	
	/**
	 * Disposes the Logger and save the log file.
	 */
	public static void dispose() {
		try {
			writer = new FileWriter(logFile);
			writer.write(builder.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Sets the max number of log files to keep on device. Default 50.
	 * Should be called before init().
	 * @param maxFiles
	 */
	public static void setMaxFiles(int maxFiles) {
		Log.maxFiles = maxFiles;
	}

	/**
	 * Sets the file extension of log files. Defaults to "example.log".
	 * Should be called before init().
	 * @param fileExt
	 */
	public static void setFileExt(String fileExt) {
		Log.fileExt = fileExt;
	}
	
	private static void generateFile() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd [HH_mm_ss]");
		name = sdf.format(Calendar.getInstance().getTime()) + fileExt;
		logFile = new File(logFolder, name);
	}
	
	public static void debug(Class<?> clazz, String message) {
		if (!debug) {
			return;
		}
		message = format.format(Calendar.getInstance().getTime()) + " DEBUG: {" + clazz.getName() + "} >>> " + message;
		System.out.println(message);
		builder.append(message + "\n");
	}
	
	public static void debug(Object obj, String message) {
		if (!debug) {
			return;
		}
		message = format.format(Calendar.getInstance().getTime()) + " DEBUG: {" + obj.getClass().getName() + "} >>> " + message;
		System.out.println(message);
		builder.append(message + "\n");
	}
	
	public static void info(Class<?> clazz, String message) {
		message = format.format(Calendar.getInstance().getTime()) + " INFO: {" + clazz.getName() + "} >>> " + message;
		System.out.println(message);
		builder.append(message + "\n");
	}
	
	public static void info(Object obj, String message) {
		message = format.format(Calendar.getInstance().getTime()) + " INFO: {" + obj.getClass().getName() + "} >>> " + message;
		System.out.println(message);
		builder.append(message + "\n");
	}
	
	public static void error(Class<?> clazz, String message) {
		message = format.format(Calendar.getInstance().getTime()) + " ERROR: {" + clazz.getName() + "} >>> " + message;
		System.err.println(message);
		builder.append(message + "\n");
	}
	
	public static void error(Object obj, String message) {
		message = format.format(Calendar.getInstance().getTime()) + " ERROR: {" + obj.getClass().getName() + "} >>> " + message;
		System.err.println(message);
		builder.append(message + "\n");
	}
	
	
	
}
