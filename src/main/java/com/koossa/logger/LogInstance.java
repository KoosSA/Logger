package com.koossa.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Simple instance of a logger. <br>
 * There exists one instance per {@link Thread}.
 * @author KoosSA
 *
 */
public class LogInstance {
	
	private File logFolder;
	private int maxFiles = 50;
	private String fileExt = ".log";
	private FileWriter writer;
	private File logFile;
	private String name;
	private boolean debug = false;
	private StringBuilder builder;
	private SimpleDateFormat format = new SimpleDateFormat("[HH_mm_ss]");
	
	/**
	 * Initialises the Logger and prepare for receiving log commands.
	 * @param folder
	 * @param debug
	 */
	protected void init(File folder, boolean debug) {
		logFolder = folder;
		
		this.debug = debug;
		if (!logFolder.exists()) {
			logFolder.mkdirs();
		}
		if (logFolder.list().length >= maxFiles) {
			int numToDelete = logFolder.list().length - maxFiles + 1;
			for (int i = 0; i < numToDelete; i++) {
				File file = logFolder.listFiles()[i];
				file.deleteOnExit();
			}
		}
		builder = new StringBuilder();
		generateFile();
	}
	
	/**
	 * Disposes the Logger and save the log file.
	 */
	protected void dispose() {
		try {
			writer = new FileWriter(logFile);
			writer.write(builder.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Sets the max number of log files to keep on device. <br> Default 50. <br>
	 * Should be called before init().
	 * @param maxFiles
	 */
	protected void setMaxFiles(int maxFiles) {
		this.maxFiles = maxFiles;
	}

	/**
	 * Sets the file extension of log files.<br> Defaults to "example.log". <br>
	 * Should be called before init().
	 * @param fileExt
	 */
	protected void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
	protected void generateFile() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd [HH_mm_ss]");
		name = sdf.format(Calendar.getInstance().getTime()) + fileExt;
		logFile = new File(logFolder, name);
	}
	
	protected void debug(Class<?> clazz, String message) {
		if (!debug) {
			return;
		}
		message = format.format(Calendar.getInstance().getTime()) + " DEBUG: {" + clazz.getName() + "} >>> " + message;
		System.out.println(message);
		builder.append(message + "\n");
		for ( ILogWriteAction action : Log.logWriteActions) {
			action.onEvent(message, builder.toString());
		}
		
	}
	
	protected void debug(Object obj, String message) {
		if (!debug) {
			return;
		}
		message = format.format(Calendar.getInstance().getTime()) + " DEBUG: {" + obj.getClass().getName() + "} >>> " + message;
		System.out.println(message);
		builder.append(message + "\n");
		for ( ILogWriteAction action : Log.logWriteActions) {
			action.onEvent(message, builder.toString());
		}
	}
	
	protected void info(Class<?> clazz, String message) {
		message = format.format(Calendar.getInstance().getTime()) + " INFO: {" + clazz.getName() + "} >>> " + message;
		System.out.println(message);
		builder.append(message + "\n");
		for ( ILogWriteAction action : Log.logWriteActions) {
			action.onEvent(message, builder.toString());
		}
	}
	
	protected void info(Object obj, String message) {
		message = format.format(Calendar.getInstance().getTime()) + " INFO: {" + obj.getClass().getName() + "} >>> " + message;
		System.out.println(message);
		builder.append(message + "\n");
		for ( ILogWriteAction action : Log.logWriteActions) {
			action.onEvent(message, builder.toString());
		}
	}
	
	protected void error(Class<?> clazz, String message) {
		message = format.format(Calendar.getInstance().getTime()) + " ERROR: {" + clazz.getName() + "} >>> " + message;
		System.err.println(message);
		builder.append(message + "\n");
		for ( ILogWriteAction action : Log.logWriteActions) {
			action.onEvent(message, builder.toString());
		}
	}
	
	protected void error(Object obj, String message) {
		message = format.format(Calendar.getInstance().getTime()) + " ERROR: {" + obj.getClass().getName() + "} >>> " + message;
		System.err.println(message);
		builder.append(message + "\n");
		for ( ILogWriteAction action : Log.logWriteActions) {
			action.onEvent(message, builder.toString());
		}
	}
	
	
	
}
