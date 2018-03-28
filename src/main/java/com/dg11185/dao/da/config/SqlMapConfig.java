package com.dg11185.dao.da.config;

import com.dg11185.dao.da.SqlMap;
import com.dg11185.util.filemonitor.FileMonitorMgr;
import com.dg11185.util.filemonitor.callback.FileChangeCallback;
import com.dg11185.util.filemonitor.vo.FileChangeObject;
import com.dg11185.util.filemonitor.vo.MonitorEvent;
import com.dg11185.util.filemonitor.vo.MonitorKey;

import java.io.File;

/**
 * Auto-Detect sql files change
 * @author xiesp
 * @description
 * @date 4:51 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SqlMapConfig implements FileChangeCallback {

    //need an instance for FileChangeCallBack
    private static final SqlMapConfig instance = new SqlMapConfig();
    //private constructor
    private  SqlMapConfig(){}

    /**
     * After
     */
    public static void loadSqlMap(){
        //add sql files to watcher
        String path = SqlMapConfig.class.getResource("/").getFile();
        path = path+ DaoConfig.getSqlMapDir();
        File file = new File(path);
        MonitorKey monitorKey = new MonitorKey(file, MonitorEvent.UPDATE);
        FileMonitorMgr.addFileToMonitor(monitorKey, instance);
        //load sql files
        SqlMap.load(path);
    }


    @Override
    public void fileChanged(FileChangeObject fco) {
        SqlMap.reload();
    }
}
