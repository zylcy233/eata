package com.eat.service;

import com.eat.dao.Dao;
import com.eat.mapper.MemberMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    Dao dao;
    Logger logger=Logger.getLogger(MemberServiceImpl.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    MemberMapper memberMapper;
    @Override
    public boolean checkMemid(String zymemid) {
//        String sql = "select * from zyMember where zymemid='" + zymemid + "'";
//        Map m=sqlSessionFactory.openSession().selectOne("com.zy.mapper.MemberMapper.check",zymemid);
//        logger.debug(sql);
//        return dao.isExist(sql);
        Map m=new Hashtable();
        m.put("zymemid",zymemid);
        Map result=memberMapper.getOne(m);
        logger.debug(result);
        return (result!=null);
    }

    @Override
    public boolean register(Map map) {
//        String sql = "insert into zyMember(zymemid,zymempass,zymemalias) values('" + map.get("zymemid") + "','" + map.get("zymempass") + "','" + map.get("zymemalias")
//                + "')";
//        logger.debug(sql);
//        return dao.exeUpdate(sql);
        boolean b=memberMapper.insert(map);
        logger.debug(b);
        return b;
    }

    @Override
    public boolean login(Map map) {
//        String sql = "select * from zyMember where zymemid='" + map.get("zymemid") + "'" + " and zymempass='" + map.get("zymempass") + "'";
//        logger.debug(sql);
//        Map m=sqlSessionFactory.openSession().selectOne("com.zy.mapper.MemberMapper.getOne",map);
        if (map.get("zypass")==null) map.put("zypass","");
        Map result=memberMapper.getOne(map);
        logger.debug(result);
        return result!=null;

    }
}
