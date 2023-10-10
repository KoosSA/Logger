package com.koossa.logger;

/**
 * 
 * @author KoosSA
 *
 */
public interface ILogWriteAction {
	
	/**
	 * This event is called every time a message is written to a log.
	 * @param newEntry - Message to be added to the log.
	 * @param log - The current log entries.
	 */
	void onEvent(String newEntry, String log);

}
