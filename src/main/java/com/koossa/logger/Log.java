package com.koossa.logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple logger library. <br>
 * Log should be initialised and disposed individually for each {@link Thread}. <br>
 * Call init() to start and dispose() to stop and save file. <br>
 * info(), debug() and error() can be called between init() and dispose(). <br>
 * @author KoosSA
 *
 */
public class Log {
	
	private static Map<String, LogInstance> instances = new HashMap<String, LogInstance>();
	private static File folder;
	private static boolean debug;
	private static int maxFiles = 10;
	
	/**
	 * Initialises the logger for the current {@link Thread}.
	 * @param folder
	 * @param debug
	 */
	public static void init(File folder, boolean debug) {
		Log.folder = folder;
		Log.debug = debug;
		String name = Thread.currentThread().getName();
		if (instances.containsKey(name)) {
			return;
		}
		LogInstance instance = new LogInstance();
		instance.init(new File(folder, name), debug);
		instance.setMaxFiles(maxFiles);
		instances.put(name, instance);
	}
	
	/**
	 * Sets the maximum amount of log files to keep per folder (aka per {@link LogInstance}.
	 * @param maxFiles
	 */
	public static void setMaxLogFiles(int maxFiles) {
		Log.maxFiles = maxFiles;
		instances.values().forEach(i -> {
			i.setMaxFiles(maxFiles);
		});
	}
	
	/**
	 * Prints an information message on the logger of the current {@link Thread}.
	 * @param obj
	 * @param message
	 */
	public static void info(Object obj, String message) {
		String name = Thread.currentThread().getName();
		if (!instances.containsKey(name)) {
			Log.init(folder, debug);
		}
		instances.get(name).info(obj, message);
	}
	
	/**
	 * Prints an information message on the logger of the current {@link Thread}.
	 * @param clazz
	 * @param message
	 */
	public static void info(Class<?> clazz, String message) {
		String name = Thread.currentThread().getName();
		if (!instances.containsKey(name)) {
			Log.init(folder, debug);
		}
		instances.get(name).info(clazz, message);
	}
	
	/**
	 * Prints an error message on the logger of the current {@link Thread}.
	 * @param obj
	 * @param message
	 */
	public static void error(Object obj, String message) {
		String name = Thread.currentThread().getName();
		if (!instances.containsKey(name)) {
			Log.init(folder, debug);
		}
		instances.get(name).error(obj, message);
	}
	
	/**
	 * Prints an error message on the logger of the current {@link Thread}.
	 * @param clazz
	 * @param message
	 */
	public static void error(Class<?> clazz, String message) {
		String name = Thread.currentThread().getName();
		if (!instances.containsKey(name)) {
			Log.init(folder, debug);
		}
		instances.get(name).error(clazz, message);
	}
	
	/**
	 * Prints an debug message on the logger of the current {@link Thread}. <br>
	 * Debugging should be enabled on the {@link Thread}, otherwise nothing will be printed.
	 * @param obj
	 * @param message
	 */
	public static void debug(Object obj, String message) {
		String name = Thread.currentThread().getName();
		if (!instances.containsKey(name)) {
			Log.init(folder, debug);
		}
		instances.get(name).debug(obj, message);
	}
	
	/**
	 * Prints an debug message on the logger of the current {@link Thread}. <br>
	 * Debugging should be enabled on the {@link Thread}, otherwise nothing will be printed.
	 * @param clazz
	 * @param message
	 */
	public static void debug(Class<?> clazz, String message) {
		String name = Thread.currentThread().getName();
		if (!instances.containsKey(name)) {
			Log.init(folder, debug);
		}
		instances.get(name).debug(clazz, message);
	}
	
	/**
	 * Disposes the {@link Log} on the current {@link Thread}. <br>
	 * Saves the log file. <br>
	 * Should be done once per {@link Thread}.
	 */
	public static void dispose() {
		String name = Thread.currentThread().getName();
		if (!instances.containsKey(name)) {
			return;
		}
		instances.get(name).dispose();
		instances.remove(name);
	}

}
