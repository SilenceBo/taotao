package com.taotao.manage.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zb on 2017/11/17.
 */
@RequestMapping("api/item/cat")
@Controller
public class ApiItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    //private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 对外提供接口查询数据
     * @return
     */
    @GetMapping
    public ResponseEntity<ItemCatResult> queryItemCat(){
        try{
            ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
            return ResponseEntity.ok(itemCatResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 对外提供接口查询数据
     * @return
     */
   /* @GetMapping
    public ResponseEntity<String> queryItemCat(@RequestParam("callback")String callback){
        try{
            ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
            String json = MAPPER.writeValueAsString(itemCatResult);
            if (StringUtils.isEmpty(callback)){
                return ResponseEntity.ok(json);
            }
            return ResponseEntity.ok(callback + "("+ json +");");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }*/
}
