package com.taotao.common.service;

/**
 * Created by zb on 2017/12/2.
 */
public interface Function<T, E> {

    public T callBack(E e);
}
