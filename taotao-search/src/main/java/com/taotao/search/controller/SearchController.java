package com.taotao.search.controller;


import com.taotao.search.bean.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zb on 2017/12/29.
 */

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(value = "search")
    public ModelAndView search(@RequestParam(value = "q")String keyWords, @RequestParam(value = "page", defaultValue = "1")Integer page) throws SolrServerException {
        ModelAndView mv = new ModelAndView("search");

        try {
            //解决get中文乱码
            keyWords = new String(keyWords.getBytes("ISO-8859-1"), "UTF-8");

            SearchResult searchResult = this.searchService.search(keyWords, page);

            mv.addObject("query", keyWords);//搜索关键字
            mv.addObject("itemList", searchResult.getData());//商品列表
            mv.addObject("page", page);//页数
            int total = searchResult.getTotal().intValue();
            int pages = total%searchService.ROWS == 0 ? total/searchService.ROWS :
                    total/searchService.ROWS + 1;
            mv.addObject("pages", pages);//总页数
        }catch (Exception e){
            e.printStackTrace();
        }
        return mv;
    }
}
