package com.taotao.cart.threadlocal;


import com.taotao.sso.query.bean.User;

/**
 * Created by zb on 2017/12/19.
 */
public class UserThreadLocal {

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<User>();

    public static void set(User user){
        LOCAL.set(user);
    }

    public static User get(){
        return LOCAL.get();
    }
}
