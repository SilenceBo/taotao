package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zb on 2017/10/30.
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem>{

    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    public void updateSelective(Long itemId, String itemParams) {
        //更新对象
        ItemParamItem record = new ItemParamItem();
        record.setParamData(itemParams);

        //更新条件
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        this.itemParamItemMapper.updateByExampleSelective(record, example);
    }
}
