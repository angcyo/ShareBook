package com.angcyo.sharebook.http;

import android.os.Build;
import android.text.TextUtils;

import com.angcyo.sharebook.App;
import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.net.rsa.RSA;
import com.angcyo.uiview.utils.RUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/14 14:13
 * 修改人员：Robi
 * 修改时间：2016/12/14 14:13
 * 修改备注：
 * Version: 1.0.0
 */
public class P {

    /**
     * app_version	是	string	APP版本号
     * time_stamp	是	int	请求时间戳【精确到秒，10位】
     * client_type	是	String	终端类型【android/ios】
     * lang	否	int	接口使用的语言【目前支持三种：1-中文简体，2-中文繁体，3-英文】
     */
    public static Map<String, String> map(Map<String, String> map) {
        return map(map, false);
    }

    public static Map<String, String> map(Map<String, String> map, boolean isInfo) {
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if (!TextUtils.isEmpty(value)) {
                result.put(key, value);
            }
        }

        long time = System.currentTimeMillis() / 1000;

            /*版本信息*/
        String versionName = RUtils.getAppVersionName(App.getApp());
        result.put("app_version", versionName);

            /*时间戳*/
        result.put("time_stamp", String.valueOf(time));

            /*终端类型*/
        result.put("devtype", "android");
        result.put("devtoken", RApplication.getIMEI());
        result.put("os", Build.VERSION.RELEASE);
        result.put("cpu", Build.CPU_ABI);
        result.put("model", Build.MODEL);

        return result;
    }

    /**
     * 安全的去掉字符串的最后一个字符
     */
    public static String safe(StringBuilder stringBuilder) {
        return stringBuilder.substring(0, Math.max(0, stringBuilder.length() - 1));
    }

    public static <T> String connect(List<T> list) {
        if (list == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (T bean : list) {
            builder.append(bean);
            builder.append(",");
        }

        return safe(builder);
    }

    /**
     * 组装参数之后, 并签名
     */
    public static Map<String, String> b(String... args) {
        return map(build(args), false);
    }

    /**
     * 组装参数之后, 并签名 (资讯API)
     */
    public static Map<String, String> buildInfoMap(String... args) {
        return map(build(false, args), true);
    }

    public static Map<String, String> buildPayMap(String... args) {
        return mapPay(build(false, args));
    }

    public static Map<String, String> mapPay(Map<String, String> map) {
        Map<String, String> result = new HashMap<>();
        List<String> signList = new ArrayList<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if (!TextUtils.isEmpty(value)) {
                result.put(key, value);
                signList.add(key + "=" + value);
            }
        }
            /*资讯API支持*/
        signList.add("e_type=RSA");
        result.put("e_type", "RSA");
        Collections.sort(signList);

        StringBuilder builder = new StringBuilder();
        for (String s : signList) {
            builder.append(s);
            builder.append("&");
        }

        String signString;

        signString = RSA.encodeInfo(safe(builder)).replaceAll("/", "_a").replaceAll("\\+", "_b").replaceAll("=", "_c");

        result.put("sign", signString);

        return result;
    }


    /**
     * 组装参数
     */
    public static Map<String, String> build(String... args) {
        return build(true, args);
    }

    public static Map<String, String> build(boolean uid, String... args) {
        final Map<String, String> map = new HashMap<>();
        if (uid) {
            //map.put("uid", UserCache.getUserAccount());//默认传输uid参数
            //map.put("limit", Constant.DEFAULT_PAGE_DATA_COUNT + "");
        }
        foreach(new OnPutValue() {
            @Override
            public void onValue(String key, String value) {
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            }
        }, args);
        return map;
    }

    private static void foreach(OnPutValue onPutValue, String... args) {
        if (onPutValue == null || args == null) {
            return;
        }
        for (String str : args) {
            String[] split = str.split(":");
            if (split.length >= 2) {
                String first = split[0];
                onPutValue.onValue(first, str.substring(first.length() + 1));
            }
        }
    }

    interface OnPutValue {
        void onValue(String key, String value);
    }
}
