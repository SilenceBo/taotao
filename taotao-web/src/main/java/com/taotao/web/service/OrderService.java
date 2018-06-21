package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.httpclient.HttpResult;
import com.taotao.common.service.ApiService;
import com.taotao.web.bean.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by zb on 2017/12/17.
 */
@Service
public class OrderService {

    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_ORDER_URL}")
    private String TAOTAO_ORDER_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String submitOrder(Order order) {
        try {
            String url = TAOTAO_ORDER_URL + "/order/create";
            HttpResult httpResult = this.apiService.doPostJson(url, MAPPER.writeValueAsString(order));
            if (httpResult.getCode().intValue() == 200){
                String jsonData = httpResult.getData();
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                if (jsonNode.get("status").intValue() == 200){
                    //订单提交成功
                    return jsonNode.get("data").asText();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Order queryOrderById(String orderId) {
        String url = TAOTAO_ORDER_URL + "/order/query/" + orderId;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData, Order.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
