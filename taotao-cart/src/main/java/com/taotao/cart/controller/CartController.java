package com.taotao.cart.controller;

import com.github.abel533.entity.Example;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartCookieService;
import com.taotao.cart.service.CartService;
import com.taotao.cart.threadlocal.UserThreadLocal;
import com.taotao.sso.query.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zb on 2018/1/2.
 */
@RequestMapping("cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartCookieService cartCookieService;

    /**
     * 加入商品到购物车
     * @param itemId
     * @return
     */
    @GetMapping(value= "{itemId}")
    public String addItemToCart(@PathVariable("itemId")Long itemId, HttpServletRequest request, HttpServletResponse response){
        User user = UserThreadLocal.get();
        if (null == user){
        //未登录
            this.cartCookieService.addItemToCart(itemId, request, response);
        }else {
        //登录
            this.cartService.addItemToCart(itemId);
        }
        //重定向到购物车列表
        return "redirect:/cart/list.html";
    }

    /**
     * 对外提供接口，根据用户id查询购物车列表（数据库）
     * @param userId
     * @return
     */
    @GetMapping(params = "userId")
    public ResponseEntity<List<Cart>> queryCartListByUserId(@RequestParam("userId") Long userId){
        try {
            List<Cart> carts = this.cartService.queryCartList(userId);
            if (null == carts || carts.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(carts);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 显示购物车列表
     * @return
     */
    @GetMapping(value= "list")
    public ModelAndView showCartList(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("cart");
        List<Cart> cartList = null;
        User user = UserThreadLocal.get();
        if (null == user){
            //未登录
            cartList = this.cartCookieService.queryCartList(request);
        }else {
            //登录
            cartList = this.cartService.queryCartList();
        }
        mv.addObject("cartList", cartList);
        return mv;
    }

    /**
     * 修改商品的数量
     * @param itemId
     * @param num 最终购买的数量
     * @return
     */
    @PostMapping(value = "update/num/{itemId}/{num}")
    public ResponseEntity<Void> updateNum(@PathVariable("itemId")Long itemId,
            @PathVariable("num")Integer num, HttpServletRequest request, HttpServletResponse response){
        User user = UserThreadLocal.get();
        if (null == user){
            //未登录
            this.cartCookieService.updateNum(itemId, num, request, response);
        }else {
            //登录
            this.cartService.updateNum(itemId, num);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除购物车中的商品
     * @param itemId
     * @return
     */
    @GetMapping(value = "delete/{itemId}")
    public String deleteCartItem(@PathVariable("itemId")Long itemId,
            HttpServletRequest request, HttpServletResponse response){
        User user = UserThreadLocal.get();
        if (null == user){
            //未登录
            this.cartCookieService.deleteItem(itemId, request, response);
        }else {
            //登录
            this.cartService.deleteCartItem(itemId);
        }
        return "redirect:/cart/list.html";
    }
}
