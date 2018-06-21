package com.taotao.web.controller;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.web.bean.Item;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zb on 2017/12/2.
 */
@RequestMapping("item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping( value = "{itemId}")
    public ModelAndView showDetail(@PathVariable("itemId") Long itemId){
        ModelAndView mv = new ModelAndView("item");
        //基本数据
        Item item = this.itemService.queryItemById(itemId);
        mv.addObject("item", item);

        //描述数据
        ItemDesc itemDesc = this.itemService.queryItemDescByItemId(itemId);
        mv.addObject("itemDesc", itemDesc);

        //规格参数
        String itemParam = this.itemService.queryItemParamItemByItemId(itemId);
        mv.addObject("itemParam", itemParam);
        return mv;
    }
}
