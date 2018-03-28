package com.dg11185.util.filemonitor;

import com.dg11185.util.filemonitor.callback.FileChangeCallback;
import com.dg11185.util.filemonitor.service.FileMonitorService;
import com.dg11185.util.filemonitor.service.impl.FileMonitorServiceJdkImpl;
import com.dg11185.util.filemonitor.vo.MonitorKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileMonitorMgr {
	private static final FileMonitorService service = FileMonitorServiceJdkImpl.getInstance();
	private static final Logger log = LoggerFactory.getLogger(FileMonitorMgr.class);
	private FileMonitorMgr(){}
	
	public static void startService(){
		service.startMonitor();
	}
	
	public static void addFileToMonitor(MonitorKey monitorKey, FileChangeCallback callback){
		service.addFileToMonitor(monitorKey,callback);
	}
	
	public static void removeMonitor(MonitorKey key){
		service.removeMonitor(key);
	}
	
	public static void stop(){
		service.stop();
	}
}
