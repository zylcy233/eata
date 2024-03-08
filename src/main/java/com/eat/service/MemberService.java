package com.eat.service;

import com.eat.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MemberService {
    boolean checkMemid(String zymemid);
    boolean register(Map map);
    boolean login(Map map);
}
