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
    private List<RecommandBean> recommand;

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

    public List<RecommandBean> getRecommand() {
        return recommand;
    }

    public void setRecommand(List<RecommandBean> recommand) {
        this.recommand = recommand;
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

    public static class RecommandBean {

        /**
         * isbn : 9787040064773
         * id : 1562739
         * title : 大学英语自学教程（上册）
         * subtitle :
         * origin_title :
         * author : 高远
         * author_intro :
         * translator :
         * publisher : 高等教育
         * pubdate : 1998-11
         * pages :
         * catalog :
         * summary : 《大学英语自学教程(上册)》作为我国高等教育组成部分的自学考试，其职责就是在高等教育这个水平上倡导自学、鼓励自学、帮助自学、推动自学，为每一个自学者铺就成才之路。组织编写供读者学习的教材就是履行这个职责的重要环节。毫无疑问，这种教材应当适合自学，应当有利于学习者掌握、了解新知识、新信息，有利于学习者增强创新意识，培养实践能力，形成自学能力，也有利于学习者学以致用，解决实际工作中所遇到的问题。
         * price : 30.9
         * tags : 自考,英语,教材,自考教材,大学英语自学教程(上册),学习,实体书,用途：学习
         * amount : 4
         * loan : 3
         * pic : 1495804500.jpg
         */

        private String isbn;
        private String id;
        private String title;
        private String subtitle;
        private String origin_title;
        private String author;
        private String author_intro;
        private String translator;
        private String publisher;
        private String pubdate;
        private String pages;
        private String catalog;
        private String summary;
        private String price;
        private String tags;
        private String amount;
        private String loan;
        private String pic;

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getOrigin_title() {
            return origin_title;
        }

        public void setOrigin_title(String origin_title) {
            this.origin_title = origin_title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor_intro() {
            return author_intro;
        }

        public void setAuthor_intro(String author_intro) {
            this.author_intro = author_intro;
        }

        public String getTranslator() {
            return translator;
        }

        public void setTranslator(String translator) {
            this.translator = translator;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getLoan() {
            return loan;
        }

        public void setLoan(String loan) {
            this.loan = loan;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getBookPic() {
            return BASE_IMG_PATH + getPic();
        }
    }
}
