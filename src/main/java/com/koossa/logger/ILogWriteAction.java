package com.koossa.logger;

public interface ILogWriteAction {
	
	void onEvent(String newEntry, String log);

}
