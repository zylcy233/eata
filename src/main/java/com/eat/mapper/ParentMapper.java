package com.eat.mapper;

import java.util.List;
import java.util.Map;

public interface ParentMapper {
    Map getOne(Map map);
    boolean insert(Object object);
    boolean delete(Object object);
    boolean update(Object object);
    List getList(Object ojb);
    boolean settlement(Object object);
}
