package com.eat.service;

import com.eat.dao.Dao;
import com.eat.entity.Menu;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    Dao dao;
    Logger logger=Logger.getLogger(MenuServiceImpl.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Override
    public boolean insertMenu(Menu menu,Map map,Map m) {
        logger.debug("map:"+map);
        int zysellid=dao.count("select zyid from zyseller where zyname='"+map.get("sellzyname")+"'");
        logger.debug("zysellid:"+zysellid);
//        String sql="insert into zymenu(zyid,zyname,zyprice,zyremark,zypic,zysellid) values(zymenu_id.nextval,'"+menu.getZyname()+"','"+menu.getZyprice()+"','"
//                +menu.getZyremark()+"','"+pic +"','"+zysellid+"')";
//        return dao.exeUpdate(sql);
//        java.sql.Connection
//        org.apache.ibatis.session.SqlSession
        map.put("zysellid",zysellid);
        logger.debug("map:"+map);
        boolean b=sqlSessionFactory.openSession().insert("com.zy.mapper.MenuMapper.insert",map)>0;
        logger.debug(b);
        logger.debug("mune:"+menu);
        return b;

    }

    @Override
    public boolean checkZyname(String zyname,int zysellid) {
        String sql = "select * from zymenu where zyname='" + zyname + "' and zysellid="+zysellid;
        return dao.isExist(sql);
    }

    @Override
    public boolean delete(Map map) {
        logger.debug("map:"+map);
        boolean b=sqlSessionFactory.openSession().delete("com.zy.mapper.MenuMapper.delete",map)>0;
        logger.debug(b);
        return b;
    }

    @Override
    public List selectMune(Map map,Map m) {
//        String sql=null;
//        if(map.get("zyname")==null) {
//            sql="select zyid,zyname,zyprice,zypic,zyremark from zymenu where zysellid="+zysellid;
//        }else {
//            sql="select zyid,zyname,zyprice,zypic,zyremark from zymenu where zyname like '%"+map.get("zyname")+"%' and zysellid='"+zysellid+"'";
//        }
//        logger.debug(sql);
        logger.debug(m);
        map.put("sellzyname",m.get("zyname"));
        logger.debug("map:"+map);
        List l=sqlSessionFactory.openSession().selectList("com.zy.mapper.MenuMapper.getList",map);
//        return dao.getData(l);
        return l;
    }

}
