package com.taotao.manage.controller;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zb on 2017/11/29.
 */
@RequestMapping("content")
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 保存内容
     * @param content
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> savecontent(Content content){
        try {
            content.setId(null);
            this.contentService.save(content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 查询内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public ResponseEntity<EasyUIResult> queryList(@RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows){
        try {
            EasyUIResult easyUIResult = this.contentService.queryList(categoryId, page, rows);
            return ResponseEntity.ok(easyUIResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
