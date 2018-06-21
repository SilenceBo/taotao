package com.taotao.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.sso.query.api.UserQueryService;
import com.taotao.sso.query.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zb on 2017/12/17.
 */
@Service
public class UserService {

    /*@Autowired
    private PropertieService propertieService;

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public User queryUserByToken(String token){
        String url = propertieService.TAOTAO_SSO_URL + "/service/user/" + token;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData, User.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/

    @Autowired
    private UserQueryService userQueryService;

    public User queryUserByToken(String token){
        return this.userQueryService.queryUserByToken(token);
    }
}
