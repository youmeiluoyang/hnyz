package com.dg11185.util.filemonitor.service;

import com.dg11185.util.filemonitor.callback.FileChangeCallback;
import com.dg11185.util.filemonitor.vo.MonitorKey;


/**
 * 监控接口,只能是目录,只会监控目录下面的文件,忽略目录
 */
public interface FileMonitorService {
	
	void startMonitor();
	
	void addFileToMonitor(MonitorKey key, FileChangeCallback callback);
	
	void removeMonitor(MonitorKey key);
	
	void stop();
	
}
