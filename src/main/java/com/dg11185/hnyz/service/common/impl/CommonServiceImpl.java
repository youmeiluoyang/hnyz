package com.dg11185.hnyz.service.common.impl;

import com.dg11185.hnyz.bean.common.Menu;
import com.dg11185.hnyz.dao.common.CommonDao;
import com.dg11185.hnyz.service.common.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiesp
 * @description
 * @date 3:09 PM  3/29/2018
 */
@Service
public class CommonServiceImpl implements CommonService{

    private final static Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
    @Autowired
    private CommonDao commonDao;


    @Override
    public Map<String, List<Menu>> getMenu() {
        List<Menu> list = commonDao.getMenu();
        Map<String,String> tmpMap = new HashMap<>();
        for(Menu menu:list){
            tmpMap.put(menu.getIds()+"",menu.getNames());
        }

        Map<String, List<Menu>> map = new HashMap<>();
        List<Menu> subMenus;
        for(Menu menu:list){
            int level = menu.getLevel();
            //父菜单
            if(level == 1){
                String key =tmpMap.get(menu.getIds()+"");
                if(map.get(key) ==null){
                    subMenus = new ArrayList<>();
                    map.put(key,subMenus);
                }
            }else{
                String key =tmpMap.get(menu.getParentId()+"");
                subMenus = map.get(key);
                subMenus.add(menu);
            }
        }
        return map;
    }
}
