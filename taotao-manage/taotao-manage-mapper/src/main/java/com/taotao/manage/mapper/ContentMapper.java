package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;

import java.util.List;

/**
 * Created by zb on 2017/11/29.
 */
public interface ContentMapper extends Mapper<Content>{

    List<Content> queryList(Long categoryId);
}
