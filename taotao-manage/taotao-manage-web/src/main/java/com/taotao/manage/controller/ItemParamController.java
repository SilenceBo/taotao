package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zb on 2017/10/30.
 */
@RequestMapping("item/param")
@Controller
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @GetMapping(value = "{itemCatId}")
    public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") Long itemCatId){
       try {
           ItemParam record = new ItemParam();
           record.setItemCatId(itemCatId);
           ItemParam itemParam = this.itemParamService.queryOne(record);

           if (null == itemParam){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
           }
           return ResponseEntity.ok(itemParam);
       }catch (Exception e){
           e.printStackTrace();
       }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增规格参数模板
     * @param paramData
     * @param itemCatId
     * @return
     */
    @PostMapping(value = "{itemCatId}")
    public ResponseEntity<Void> saveItemParam(@RequestParam("paramData") String paramData,
                                              @PathVariable("itemCatId") Long itemCatId){
       try{
           ItemParam itemParam = new ItemParam();
           itemParam.setId(null);
           itemParam.setItemCatId(itemCatId);
           itemParam.setParamData(paramData);
           this.itemParamService.save(itemParam);
           return ResponseEntity.status(HttpStatus.CREATED).build();
       }catch (Exception e){
            e.printStackTrace();
       }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
