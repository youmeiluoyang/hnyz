package com.dg11185.util.filemonitor.vo;

import java.io.File;
import java.io.Serializable;


public class FileChangeObject implements Serializable {
	private static final long serialVersionUID = 1L;

	//这个是实际发生变化的文件
	private final  File file;
	private final  MonitorKey key;

	public FileChangeObject(File file,MonitorKey key){
		this.file = file;
		this.key = key;
	}

	public File getFile() {
		return file;
	}
	public MonitorKey getKey() {
		return key;
	}
}
