package com.taotao.web.controller;

import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zb on 2017/12/3.
 */
@RequestMapping("item/cache")
@Controller
public class ItemCacheController {

    @Autowired
    private RedisService redisService;

    /**
     * 删除redis中的商品的缓存
     * @param itemId
     * @return
     */
    @PostMapping(value = "{itemId}")
    public ResponseEntity<Void> deletecache(@PathVariable("itemId") Long itemId){
        try {
            String key = ItemService.REDIS_KEY + itemId;
            this.redisService.del(key);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
