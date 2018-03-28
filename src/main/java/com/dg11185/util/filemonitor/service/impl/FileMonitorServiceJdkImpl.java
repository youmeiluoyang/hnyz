package com.dg11185.util.filemonitor.service.impl;

import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.util.filemonitor.callback.FileChangeCallback;
import com.dg11185.util.filemonitor.service.FileMonitorService;
import com.dg11185.util.filemonitor.vo.FileChangeObject;
import com.dg11185.util.filemonitor.vo.MonitorEvent;
import com.dg11185.util.filemonitor.vo.MonitorKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class FileMonitorServiceJdkImpl implements FileMonitorService {
	private static Logger log = LoggerFactory.getLogger(FileMonitorServiceJdkImpl.class);
	private static final FileMonitorServiceJdkImpl instance = new FileMonitorServiceJdkImpl();
	private static final Map<MonitorKey,FileChangeCallback> keyCallableMap = new ConcurrentHashMap<MonitorKey,FileChangeCallback>();
	private static final Map<String,MonitorKey> nameToKeyMap= new ConcurrentHashMap<String,MonitorKey>();
	private volatile boolean monitorStopFlag = false;
	private volatile Thread thread;
	
	private volatile WatchService ws = null;
	
	private FileMonitorServiceJdkImpl(){
		try{
			ws = FileSystems.getDefault().newWatchService();
		}catch(Exception e){
			log.error("[文件监控]初始化发生异常:", LogUtil.getTrace(e));
		}
	}
	
	public static FileMonitorServiceJdkImpl getInstance(){
		return instance;
	}
	
	private class MonitorTask implements Runnable{
		public void run(){
			while(!Thread.interrupted() && !monitorStopFlag){
				WatchKey key = null;
				try {
					//FIXME why trigger twice?
					key = ws.take();
					for (WatchEvent<?> event : key.pollEvents()) {
						//wait a little time
						TimeUnit.MILLISECONDS.sleep(1000);
		                Kind<?> kind = event.kind();
		                @SuppressWarnings("unchecked")
						WatchEvent<Path> ev = (WatchEvent<Path>)event;
		                Path dir = (Path)key.watchable();
		                //这里不应该为空
		                MonitorKey watchKey = nameToKeyMap.get(dir.toString());
						Path fullPath = dir.resolve(ev.context());
		                FileChangeCallback callback = keyCallableMap.get(watchKey);
		                if(callback == null){
							log.warn("[文件监控]目录==>{},文件==>{},发生变化但是没找到对应处理类,是否是监控设置不对?",dir.toString(),fullPath.toString());
						}
						else{
		                	log.info("[文件监控]检测到文件==>{}发生变化,调用==>{}进行处理..",fullPath.toString(),callback.getClass().getSimpleName());
							FileChangeObject fileChangeObject = new FileChangeObject(fullPath.toFile(),watchKey);
							callback.fileChanged(fileChangeObject);
						}
		            }
				} catch (InterruptedException e) {
					//恢复中断标志
					Thread.currentThread().interrupt();
					monitorStopFlag = true;
					log.warn("[文件监控]收到中断请求,强制退出");
				} catch(Exception e){
					log.error("[文件监控]发生异常:{}", LogUtil.getTrace(e));
				}finally{
					if(key != null){
						key.reset();
					}
				}
			}
		}
	}

	//visibility not ensured,code should  make this visibility promise
	@Override
	public  void startMonitor(){
		if(thread == null){
			log.info("[文件监控]文件变动监控服务启动...");
			thread = new Thread(new MonitorTask());
			thread.start();
		}
	}
	
	
	private Kind<Path> convert2WatchEventKind(MonitorEvent event){
		if(event == MonitorEvent.CREATE){
			return StandardWatchEventKinds.ENTRY_CREATE;
		}else if(event == MonitorEvent.UPDATE){
			return StandardWatchEventKinds.ENTRY_MODIFY;
		}else if(event == MonitorEvent.DELETE){
			return StandardWatchEventKinds.ENTRY_DELETE;
		}
		return null;
	}

	@Override
	public void addFileToMonitor(MonitorKey monitorKey, FileChangeCallback callback) {
		if(!monitorKey.getFile().isDirectory()){
			throw new RuntimeException("监控对象必须是目录.");
		}
		try {
			Path path = Paths.get(monitorKey.getFile().getAbsolutePath());
			WatchKey key = path.register(ws, convert2WatchEventKind(monitorKey.getEvent()));
			monitorKey.setWatchKey(key);
			nameToKeyMap.put(monitorKey.getFile().getAbsolutePath(),monitorKey);
			keyCallableMap.put(monitorKey, callback);
			log.info("添加==>" + monitorKey.getFile().getAbsolutePath() +" 作为监控目录!");
		} catch (IOException e) {
			log.error("[文件监控]：文件==>{}监控失败...,{}",monitorKey.getFile().getAbsolutePath(), e);
		}
	}

	@Override
	public void removeMonitor(MonitorKey key) {
		if(key.getWatchKey()!=null){
			log.info("[文件监控]目录==>{}取消监控...",key.getFile().getAbsolutePath());
			key.getWatchKey().cancel();
		}
	}

	@Override
	public void stop() {
		log.info("[文件监控]终结文件监控服务");
		monitorStopFlag = true;
		thread.interrupt();
	}
}
