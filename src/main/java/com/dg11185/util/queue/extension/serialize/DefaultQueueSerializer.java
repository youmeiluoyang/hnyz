package com.dg11185.util.queue.extension.serialize;

import com.dg11185.util.common.DateUtil;
import com.dg11185.util.queue.queueobj.Queueable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author xiesp
 * @description
 * @date 9:40 AM  10/13/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class DefaultQueueSerializer<T extends Queueable> implements QueueSerializer<T> {


    private final static Logger log = LoggerFactory.getLogger(DefaultQueueSerializer.class);

    private  final String storePath;

    public DefaultQueueSerializer(String storePath){
        this.storePath = storePath;
    }


    /**
     * file name pattern is yyyy-MM-dd---HH-mm-ss-UUID   with .queue extension
     * @param queueableList
     */
    @Override
    public void serialize(List<T> queueableList){
        String name = DateUtil.formatDate(new Date(), "yyyy-MM-dd---HH-mm-ss") + "-"+ UUID.randomUUID().toString() +  ".queue";
        ObjectOutputStream objectOutputStream = null;
        try{
            File targetFile = new File(storePath+ File.separator);
            if(!targetFile.exists())
                targetFile.mkdirs();
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(targetFile.getAbsoluteFile()+  File.separator + name));
            objectOutputStream.writeObject(queueableList);
        }catch (Exception e){
            log.error("[QueueSerializer]Error occur during serialize operation", e);
        }finally {
            if(objectOutputStream!=null){
                try{
                    objectOutputStream.close();
                }catch (Exception e){
                    log.error("[QueueSerializer]Error close ObjectOutputStream during serialize", e);
                }
            }
        }
    }

    /**
     * scan all the files in the storePath,read all the file with the .queue extension
     */
    @Override
    public List<T> deserialize() {
        File file = new File(this.storePath);
        File[] files = file.listFiles();
        List<T> allQueueableList = new ArrayList<>();
        if(files == null)
            return allQueueableList;
        for(File f:files){
            File queueableFile = null;
            ObjectInputStream objectInputStream = null;
            if((!f.isDirectory()) && f.getName().endsWith(".queue")){
                try{
                    queueableFile = new File(storePath+ File.separator + f.getName());
                    objectInputStream = new ObjectInputStream(new FileInputStream(queueableFile));
                    List<T> partList = (List<T>) objectInputStream.readObject();
                    allQueueableList.addAll(partList);
                }catch (Exception e){
                    log.error("[QueueSerializer]Error occur during deserialize", e);
                }finally {
                    if(objectInputStream!=null){
                        try{
                            objectInputStream.close();
                            //delete the file
                            if(!queueableFile.delete()){
                                queueableFile.renameTo(new File(queueableFile.getAbsolutePath()+"(used)"));
                            }
                        }catch (Exception e){
                            log.error("[QueueSerializer]Error close ObjectInputStream during deserialize", e);
                        }
                    }
                }
            }
        }
        return allQueueableList;
    }
}
