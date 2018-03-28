package com.dg11185.util.filemonitor.vo;

import java.io.File;
import java.io.Serializable;
import java.nio.file.WatchKey;


public class MonitorKey implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final File file;
	private final MonitorEvent event;

	private volatile WatchKey watchKey;
	
	public MonitorKey(File path,MonitorEvent event){
		this.file = path;
		this.event = event;
	}
	
	public File getFile() {
		return file;
	}
	public MonitorEvent getEvent() {
		return event;
	}


	public WatchKey getWatchKey() {
		return watchKey;
	}

	public void setWatchKey(WatchKey watchKey) {
		this.watchKey = watchKey;
	}
}
