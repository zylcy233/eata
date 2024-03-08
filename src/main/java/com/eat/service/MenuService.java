package com.eat.service;

import com.eat.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    boolean insertMenu(Menu menu,Map map,Map m);
    boolean checkZyname(String zyname,int zysellid);
    boolean delete(Map map);
    List selectMune(Map map,Map m);
}
