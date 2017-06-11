package com.angcyo.sharebook.http;

/**
 * Created by angcyo on 2017-05-16.
 */

public class Action {
    public static final String LOGIN = "action:10001";
    public static final String REGISTER = "action:10000";
    public static final String ADD_BOOK = "action:10200";
    public static final String HOME = "action:10401";
    public static final String SEARCH_BOOK = "action:10202";//搜索, 详情书
    public static final String FAV_BOOK = "action:10100";//收藏
    public static final String UNFAV_BOOK = "action:10101";//取消收藏
    public static final String GET_ALL_FAV_BOOK = "action:10102";//遍历所有收藏
    public static final String ADD_CART = "action:10206";//添加购物车
    public static final String DEL_CART = "action:10207";//删除购物车
    public static final String ALL_CARTS = "action:10208";//所有购物车

    public static final String ALL_ADDRESS = "action:10005";//所有地址
    public static final String ADD_ADDRESS = "action:10004";//add地址
    public static final String DEL_ADDRESS = "action:10006";//del地址
    public static final String DEFAULT_ADDRESS = "action:10008";//默认地址

}
