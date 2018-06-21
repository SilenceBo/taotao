package com.taotao.web.controller;

import com.taotao.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zb on 2017/11/17.
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping(value = "index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");

        //大广告位数据
        String indexAd1 = this.indexService.queryIndexAD1();
        mv.addObject("indexAD1", indexAd1);

        //右上角小广告位数据
        String indexAd2 = this.indexService.queryIndexAD2();
        mv.addObject("indexAD2", indexAd2);
        return mv;
    }
}
