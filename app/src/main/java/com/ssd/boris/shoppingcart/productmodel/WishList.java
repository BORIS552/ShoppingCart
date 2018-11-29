package com.ssd.boris.shoppingcart.productmodel;

import com.ssd.boris.shoppingcart.IConstants;

public class WishList {
    private String prod_id;
    private String prod_name;
    private String prod_image;
    private Float prod_price;
    private int prod_qty;
    private String prod_category;

    //setters..

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public void setProd_name(String prod_name){
        this.prod_name = prod_name;
    }

    public void setProd_image(String prod_image){
        this.prod_image = prod_image;
    }

    public void setProd_price(Float prod_price){
        this.prod_price = prod_price;
    }

    public void setProd_qty(int prod_qty) {
        this.prod_qty = prod_qty;
    }

    public void setProd_category(String prod_category) {
        this.prod_category = prod_category;
    }

    //getters...
    public Float getProd_price() {
        return prod_price;
    }

    public int getProd_qty() {
        return prod_qty;
    }

    public String getProd_id() {
        return prod_id;
    }

    public String getProd_image() {
        String image_url = IConstants.image_url+ this.prod_image +".jpg";
        return image_url;
    }

    public String getProd_name() {
        return prod_name;
    }

    public String getProd_category() {
        return prod_category;
    }
}
