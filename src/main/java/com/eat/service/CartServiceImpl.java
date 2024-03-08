package com.eat.service;

import com.eat.dao.Dao;
import com.eat.mapper.CartMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class CartServiceImpl implements CartService {
    @Autowired
    Dao dao;
    Logger logger= Logger.getLogger(CartServiceImpl.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    CartMapper cartMapper;
    @Override
    public boolean insertCart(Map map) {
//        String sql = "insert into zycart(zymenuid,zymemid) values('" + map.get("zymenuid") + "', '" + zymemid+ "')";
//        logger.debug(sql);
//        logger.debug("map:"+map);
//        map.put("zymemid",zymemid);
        logger.debug("map:"+map);
        boolean b=sqlSessionFactory.openSession().insert("com.eat.mapper.CartMapper.insert",map)>0;
        return b;
}

    @Override
    public List getcart(Map map,String zyname) {
//        String sql = "select zymenu.zyid,zypic,zyname,zyprice,zyremark " + " from zymenu,zycart" + " where zymenu.zyid=zycart.zymenuid"
//                + " and zymemid='" + zymemid + "'" + " and zyname like '%" + zyname + "%'";
//        logger.debug(sql);
//        return dao.getData(sql);
        map.put("zyname",zyname);
        logger.debug("map:"+map);
        List l=cartMapper.getList(map);
        return l;
    }

    @Override
    public boolean deleteCart(Map map,String zymenuid, String zymemid) {
//       String sql = "delete from zycart where zymenuid=" + zymenuid+" and zymemid='"+zymemid+"'";
//        logger.debug(sql);
//        return dao.exeUpdate(sql);
        logger.debug("map:"+map);
        boolean b=cartMapper.delete(map);
        return b;
    }

    @Override
    public boolean settlement(Map m) {

        return false;
    }

    @Override
    public boolean addcart(Map m) {
        return false;
    }
}
