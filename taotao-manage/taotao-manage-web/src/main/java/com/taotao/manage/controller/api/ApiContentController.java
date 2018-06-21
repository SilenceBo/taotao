package com.taotao.manage.controller.api;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zb on 2017/11/29.
 */
@RequestMapping("api/content")
@Controller
public class ApiContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 根据内容分类id查询分类列表
     *
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public ResponseEntity<EasyUIResult> queryListByCategoryId(@RequestParam("categoryId") Long categoryId,
              @RequestParam(value = "page", defaultValue = "1") Integer page,
              @RequestParam(value = "rows", defaultValue = "6") Integer rows) {
        try {
            EasyUIResult easyUIResult = this.contentService.queryList(categoryId, page, rows);
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
