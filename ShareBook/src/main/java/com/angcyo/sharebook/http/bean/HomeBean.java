package com.angcyo.sharebook.http.bean;

import com.angcyo.sharebook.adapter.BookAdapter;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/26 15:05
 * 修改人员：Robi
 * 修改时间：2017/05/26 15:05
 * 修改备注：
 * Version: 1.0.0
 */
public class HomeBean {

    public static String BASE_IMG_PATH = "";

    /**
     * path : http://119.23.221.242/res/pic/zpic
     * latern : [{"ibsn":"123456789","pic":"l1.png"},{"ibsn":"234567890","pic":"l2.png"},{"ibsn":"345678901","pic":"l3.png"}]
     * topical : [{"title":"网络文学","pic":"t1.png"},{"title":"童书","pic":"t2.png"},{"title":"计算机","pic":"t3.png"},{"title":"人文社科","pic":"t4.png"},{"title":"经管","pic":"t5.png"},{"title":"励志成功","pic":"t6.png"},{"title":"科技","pic":"t7.png"},{"title":"杂志","pic":"t8.png"}]
     * special : [{"title":"琅琊榜","subtitle":"琅琊榜","pic":"s1.png"},{"title":"教辅","subtitle":"考试培训","pic":"s2.png"},{"title":"新人活动","subtitle":"充多少送多少","pic":"t3.png"},{"title":"零食","subtitle":"吃点啥","pic":"s1.png"}]
     */

    private String path;
    private List<LaternBean> latern;
    private List<TopicalBean> topical;
    private List<SpecialBean> special;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<LaternBean> getLatern() {
        return latern;
    }

    public void setLatern(List<LaternBean> latern) {
        this.latern = latern;
    }

    public List<TopicalBean> getTopical() {
        return topical;
    }

    public void setTopical(List<TopicalBean> topical) {
        this.topical = topical;
    }

    public List<SpecialBean> getSpecial() {
        return special;
    }

    public void setSpecial(List<SpecialBean> special) {
        this.special = special;
    }

    public static class LaternBean implements BookAdapter.IBook {
        /**
         * ibsn : 123456789
         * pic : l1.png
         */

        private String ibsn;
        private String pic;

        public String getIbsn() {
            return ibsn;
        }

        public void setIbsn(String ibsn) {
            this.ibsn = ibsn;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        @Override
        public String getBookTitle() {
            return getIbsn();
        }

        @Override
        public String getBookPic() {
            return BASE_IMG_PATH + getPic();
        }
    }

    public static class TopicalBean implements BookAdapter.IBook {
        /**
         * title : 网络文学
         * pic : t1.png
         */

        private String title;
        private String pic;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        @Override
        public String getBookTitle() {
            return getTitle();
        }

        @Override
        public String getBookPic() {
            return BASE_IMG_PATH + getPic();
        }
    }

    public static class SpecialBean implements BookAdapter.IBook {
        /**
         * title : 琅琊榜
         * subtitle : 琅琊榜
         * pic : s1.png
         */

        private String title;
        private String subtitle;
        private String pic;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        @Override
        public String getBookTitle() {
            return getTitle();
        }

        @Override
        public String getBookPic() {
            return BASE_IMG_PATH + getPic();
        }
    }
}
