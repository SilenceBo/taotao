package com.taotao.web.controller;

import com.taotao.manage.pojo.Item;
import com.taotao.sso.query.bean.User;
import com.taotao.web.bean.Cart;
import com.taotao.web.bean.Order;
import com.taotao.web.service.CartService;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zb on 2017/12/17.
 */

@RequestMapping("order")
@Controller
public class OrderController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    /**
     * 去订单确认页面
     * @return
     */
    @GetMapping("{itemId}")
    public ModelAndView toOrder(@PathVariable("itemId") Long itemId){
        ModelAndView mv = new ModelAndView("order");
        Item item = this.itemService.queryItemById(itemId);
        mv.addObject("item", item);
        return mv;
    }

    @GetMapping("create")
    public ModelAndView toCartOrder(){
        ModelAndView mv = new ModelAndView("order-cart");
        User user = UserThreadLocal.get();
        List<Cart> carts = this.cartService.queryCartListByUserId(user.getId());
        mv.addObject("carts", carts);
        return mv;
    }

    @PostMapping(value = "submit")
    @ResponseBody
    public Map<String, Object> submitOrder(Order order){
        Map<String, Object> result = new HashMap<String, Object>();
        User user = UserThreadLocal.get();
        System.out.println(user);
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        String orderId = this.orderService.submitOrder(order);
        if (StringUtils.isEmpty(orderId)){
            result.put("status", 300);
        }else{
            result.put("status", 200);
            result.put("data", orderId);
        }
        return result;
    }

    @GetMapping(value = "success" )
    public ModelAndView success(@RequestParam("id") String orderId){
        ModelAndView mv = new ModelAndView("success");
        Order order = this.orderService.queryOrderById(orderId);
        mv.addObject("order", order);
        //当前时间向后推俩天
        mv.addObject("date", new DateTime().plusDays(2).toString("MM月dd日"));
        return mv;
    }
}
