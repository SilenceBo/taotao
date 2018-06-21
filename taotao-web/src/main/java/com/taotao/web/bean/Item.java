package com.taotao.web.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zb on 2017/12/2.
 */
public class Item extends com.taotao.manage.pojo.Item{

    public String[] getImages(){
        return StringUtils.split(super.getImage(), ",");
    }
}
