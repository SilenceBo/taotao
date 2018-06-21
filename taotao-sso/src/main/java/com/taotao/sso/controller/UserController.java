package com.taotao.sso.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zb on 2017/12/3.
 */
@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public static final String COOKIE_NAME = "TT_TOKEN";

    @GetMapping("register")
    public String toRegister(){
        return "register";
    }

    @GetMapping("login")
    public String toLogin(){
        return "login";
    }

    /**
     * 检测数据是否可用
     * @param param
     * @param type
     * @return
     */
    @GetMapping("check/{param}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
             @PathVariable("type") Integer type){
        try {
            Boolean bool = this.userService.check(param, type);
            if (null == bool){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            //为了兼容前端的业务逻辑，做出妥协处理。
            return ResponseEntity.ok(!bool);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 注册
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("doRegister")
    @ResponseBody
    public Map<String, Object> doRegister(@Validated User user, BindingResult bindingResult){
        Map<String, Object> result = new HashMap<String, Object>();

        if (bindingResult.hasErrors()){
            //校验有错误
            List<String> msgs = new ArrayList<String>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors){
                String msg = objectError.getDefaultMessage();
            }
            result.put("status", "400");
            result.put("data", StringUtils.join(msgs, "|"));
            return result;
        }

        Boolean bool = this.userService.saveUser(user);
        if (bool){
            //注册成功
            result.put("status", "200");
        }else{
            result.put("status", "300");
            result.put("data", "是的！");
        }
        return result;
    }

    @PostMapping("doLogin")
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam("username") String username,
           @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> result = new HashMap<String, Object>();

        try{
            String token = this.userService.doLogin(username, password);
            if (null == token){
                //登陆失败
                result.put("status", 400);
            }else{
                //登录成功，token写入cookie中
                result.put("status", "200");
                CookieUtils.setCookie(request, response, COOKIE_NAME, token);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("status", 500);
        }
        return result;
    }

    @GetMapping("{token}")
    public ResponseEntity<User> queryByToken(@PathVariable("token")String token){
        /*try {
            User user = this.userService.queryByToken(token);
            if (null == user){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);*/
        User user = new User();
        user.setUsername("该服务已经停止了，请访问ssoquery.taotao.com或dubbo中的服务！");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
    }
}
