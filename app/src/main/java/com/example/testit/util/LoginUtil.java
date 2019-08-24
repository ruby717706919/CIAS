package com.example.testit.util;


import java.util.HashMap;
import java.util.Map;

public class LoginUtil {
    public LoginUtil(){
        map.put("张三","123");
        map.put("李四","456");
    }
    private Map<String,String>map=new HashMap<>();
    public boolean login(String userName,String userPassword){
        if (map.get(userName).equals(userPassword))
            return true;
        else return false;
    }
    /*public LoginUtil(){

    }*///该类的初始化本应该从数据库中获得数据，但目前无法实现，故作罢
    /*public boolean login(String userName,String userPassword){

    }*///该方法实现从数据库中查找对比来进行登录验证，但目前无法与数据库完成连接，故空
}
