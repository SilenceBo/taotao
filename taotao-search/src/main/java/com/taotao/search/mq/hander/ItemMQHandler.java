package com.taotao.search.mq.hander;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import com.taotao.search.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zb on 2018/1/1.
 */
public class ItemMQHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private HttpSolrServer httpSolrServer;

    @Autowired
    private ItemService itemService;

    public void execute(String msg){
        try{
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long itemId = jsonNode.get("itemId").asLong();
            String type = jsonNode.get("type").asText();
            if (StringUtils.equals(type, "insert") || StringUtils.equals(type, "update")){
                System.out.println(itemId);
                //从后台查询
                Item item = this.itemService.queryItemById(itemId);
                System.out.println(item);
                if (item != null){
                    this.httpSolrServer.addBean(item);
                    this.httpSolrServer.commit();
                }
            }else if (StringUtils.equals(type, "delete")){
                this.httpSolrServer.deleteById(String.valueOf(itemId));
                this.httpSolrServer.commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
