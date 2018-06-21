package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.bean.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zb on 2017/12/2.
 */
@Service
public class ItemService {
    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_MANAGE_URL}")
    private String TAOTAO_MANAGE_URL;

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final String REDIS_KEY = "TAOTAO_WEB_ITEM_DETAIL_";

    private static final Integer REDIS_TIME = 60 * 60 * 24;

    public Item queryItemById(Long itemId) {

        // 从缓存中命中
        String key = REDIS_KEY + itemId;
        try {
            String cacheData = this.redisService.get(key);
            if (StringUtils.isNotEmpty(cacheData)) {
                return MAPPER.readValue(cacheData, Item.class);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try{
            String url = TAOTAO_MANAGE_URL + "/rest/item/" + itemId;
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }

            try {
                // 将数据写入到缓存中
                this.redisService.set(key, jsonData, REDIS_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return MAPPER.readValue(jsonData, Item.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ItemDesc queryItemDescByItemId(Long itemId) {
        try{
            String url = TAOTAO_MANAGE_URL + "/rest/item/desc/" + itemId;
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }
            return MAPPER.readValue(jsonData, ItemDesc.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String queryItemParamItemByItemId(Long itemId) {
        try{
            String url = TAOTAO_MANAGE_URL + "/rest/item/param/item/" + itemId;
            String jsonData = this.apiService.doGet(url);
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            String paramData = jsonNode.get("paramData").asText();
            ArrayNode arrayNode = (ArrayNode) MAPPER.readTree(paramData);
            StringBuilder sb = new StringBuilder();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
            for (JsonNode node : arrayNode){
                sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + node.get("group").asText()+"</th></tr>");
                ArrayNode params = (ArrayNode)node.get("params");
                for (JsonNode param : params){
                    sb.append("<tr><td class=\"tdTitle\">"+param.get("k").asText()+"</td><td>"+param.get("v").asText()+"</td></tr>");
                }
            }
            sb.append("</tbody></table>");
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
