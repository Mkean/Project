package com.bwie.project.cart.bean;

/**
 * 作者：王庆
 * 时间：2017/12/16
 */

public class CountPriceBean {
    private Double price;
    private int count;

    public CountPriceBean(Double price, int count) {
        this.price = price;
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
