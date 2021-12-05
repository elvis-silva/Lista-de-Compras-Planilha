package com.elvis.shopping.list.model;

import java.util.ArrayList;

public class Cart{

    private  ArrayList<Product> cartProducts = new ArrayList<>();


    public Product getProduct(int pPosition) {

        return cartProducts.get(pPosition);
    }

    public void setProduct(Product pProduct) {

        cartProducts.add(pProduct);

    }

    public int getCartSize() {

        return cartProducts.size();

    }

    public boolean checkProductInCart(Product pProduct) {

        return cartProducts.contains(pProduct);

    }

}
