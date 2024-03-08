package com.eat.service;

import java.util.List;
import java.util.Map;

public interface CartService {
    boolean insertCart(Map m);
    List getcart(Map map,String zyname);
    boolean deleteCart(Map map,String zymenuid, String zymemid);
    boolean settlement(Map m);
    boolean addcart(Map m);
}
