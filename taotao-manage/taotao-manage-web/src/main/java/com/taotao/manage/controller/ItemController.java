package com.taotao.manage.controller;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zb on 2017/10/24.
 */
@RequestMapping("item")
@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    /**
     * 新增商品
     * @param item
     * @param desc
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc,
                                         @RequestParam("itemParams") String itemParams){
        try {
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("新增商品，item = { }, desc = { }", item, desc);
            }

            if (StringUtils.isEmpty(item.getTitle())){
                //400,参数有误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            this.itemService.saveItem(item, desc, itemParams);

            if (LOGGER.isInfoEnabled()){
                LOGGER.info("新增商品成功，itemId = { }", item.getId());
            }
            //201
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            LOGGER.error("新增商品失败！title={ }, cid = { }, e={ }", item.getTitle(), item.getCid(), e);
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 查询商品列表
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows){
        try{
            PageInfo<Item> pageInfo = this.itemService.queryPageList(page, rows);
            EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
            return ResponseEntity.ok(easyUIResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    /**
     * 修改商品信息
     * @param item
     * @param desc
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc,
                                           @RequestParam("itemParams") String itemParams){
        try {
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("修改商品，item = { }, desc = { }", item, desc);
            }

            if (StringUtils.isEmpty(item.getTitle())){
                //400,参数有误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            this.itemService.updateItem(item, desc, itemParams);

            if (LOGGER.isInfoEnabled()){
                LOGGER.info("修改商品成功，itemId = { }", item.getId());
            }
            //204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            LOGGER.error("修改商品失败！title={ }, cid = { }, e={ }", item.getTitle(), item.getCid(), e);
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = "{itemId}")
    public ResponseEntity<Item> queryById(@PathVariable("itemId") Long itemId){
        try{
            Item item = this.itemService.queryById(itemId);
            if (null == item){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(item);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
