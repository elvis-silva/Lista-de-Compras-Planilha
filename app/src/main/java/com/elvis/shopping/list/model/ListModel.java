package com.elvis.shopping.list.model;

public class ListModel {

    private int listId = 0;
    private String listName;
    private String productName;
    private String productQuant;
    private String productPrice;
    private String productScratched;
    private String productCategory;
    private String productUnit;

    public ListModel(){
    }

    public ListModel(String pListName, String pProductName, String pProductQuant, String pProductPrice,
                     String pProductScratched, String pProductCategory, String pProductUnit) {

        listName = pListName;
        productName = pProductName;
        productQuant = pProductQuant;
        productPrice = pProductPrice;
        productScratched = pProductScratched;
        productCategory = pProductCategory;
        productUnit = pProductUnit;// != null ? pProductUnit : "Unidade";
    }

    public void setListId(int pListId) {
        listId = pListId;
    }

    public void setListName(String pListName) {
        listName = pListName;
    }

    public void setProductName(String pProductName) {
        productName = pProductName;
    }

    public void setProductQuant(String pProductQuant) {
        productQuant = pProductQuant;
    }

    public void setProductPrice(String pProductPrice){
        productPrice = pProductPrice;
    }

    public void setProductScratched(String pProductScratched) {
        productScratched = pProductScratched;
    }

    public void setProductUnit(String pProductUnit) {
        productUnit = pProductUnit;// != null ? pProductUnit : "Unidade";
    }

    public void setProductCategory(String pProductCategory) {
        productCategory = pProductCategory;
    }

    public long getId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductQuant() {
        return productQuant;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductScratched() {
        return productScratched;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductUnit() {
        return productUnit;
    }
}
