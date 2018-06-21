package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品类目
 * Created by zb on 2017/9/29.
 */
@RequestMapping("item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 根据父节点id查询商品列表
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ItemCat>> queryItemCat(@RequestParam(value = "id", defaultValue = "0") Long parentId){
        try {
            //List<ItemCat> list = this.itemCatService.queryItemCatListByParentId(parentId);
            ItemCat record = new ItemCat();
            record.setParentId(parentId);
            List<ItemCat> list = this.itemCatService.queryListByWhere(record);
            if (list == null || list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
