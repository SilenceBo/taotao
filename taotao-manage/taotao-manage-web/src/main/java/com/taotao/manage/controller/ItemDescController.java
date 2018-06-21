package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zb on 2017/10/24.
 */
@RequestMapping("item/desc")
@Controller
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

    /**
     * 根据商品id查询商品描述
     * @param itemId
     * @return
     */
    @GetMapping(value = "{itemId}")
    public ResponseEntity<ItemDesc> queryByItemId(@PathVariable("itemId")Long itemId){
        try {
            ItemDesc itemDesc = this.itemDescService.queryById(itemId);
            if (null == itemDesc){
                //资源不存在404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //200
            return ResponseEntity.ok(itemDesc);
        }catch (Exception e){
            e.printStackTrace();
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
