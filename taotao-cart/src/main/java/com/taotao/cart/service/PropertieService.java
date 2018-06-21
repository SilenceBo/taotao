package com.taotao.cart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zb on 2017/10/24.
 */
@Service
public class PropertieService {

    @Value("${TAOTAO_SSO_URL}")
    public String TAOTAO_SSO_URL;

}
