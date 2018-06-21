package com.taotao.cart.service;

import com.github.abel533.entity.Example;
import com.taotao.cart.bean.Item;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.threadlocal.UserThreadLocal;
import com.taotao.sso.query.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zb on 2018/1/2.
 */
@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    public void addItemToCart(Long itemId) {
        // 判断该商品在购物车中是否存在
        User user = UserThreadLocal.get();

        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(user.getId());
        Cart cart = this.cartMapper.selectOne(record);

        if (null == cart) {
            // 购物车中不存在该商品
            Item item = this.itemService.queryItemById(itemId);
            if (null == item){
                //TODO 给出用户提示
                return;
            }

            cart = new Cart();
            cart.setItemId(itemId);
            cart.setUserId(user.getId());
            cart.setNum(1); // TODO 先默认为1
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setItemTitle(item.getTitle());

            // 将Cart保存到数据库
            this.cartMapper.insert(cart);
        } else {
            // 该商品已经存在购物车中
            cart.setNum(cart.getNum() + 1); // TODO 先默认为1
            cart.setUpdated(new Date());
            this.cartMapper.updateByPrimaryKey(cart);
        }
    }

    public List<Cart> queryCartList() {
        User user = UserThreadLocal.get();
        return this.queryCartList(user.getId());
    }

    public List<Cart> queryCartList(Long userId) {
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("userId", userId);
        example.setOrderByClause("created DESC");//根据创建时间倒序
        return this.cartMapper.selectByExample(example);
    }

    public void updateNum(Long itemId, Integer num) {
        User user = UserThreadLocal.get();

        //更新的对象
        Cart record = new Cart();
        record.setNum(num);
        record.setUpdated(new Date());

        //更新的条件
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("userId", user.getId());
        this.cartMapper.updateByExampleSelective(record, example);
    }

    public void deleteCartItem(Long itemId) {
        User user = UserThreadLocal.get();

        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(user.getId());
        this.cartMapper.delete(record);
    }
}
