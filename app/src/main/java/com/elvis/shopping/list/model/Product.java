package com.elvis.shopping.list.model;

public class Product {
    public static final String SCRATCHED_TRUE = "true";
    public static final String SCRATCHED_FALSE = "false";

    private int id = 0;
    private int productIndex;
    private String productName;
    private String productDescr;
    private String productQuant;
    private String productPrice;
    private String productCategory = "";
    private String productUnit = "Unidade";
    private String scratched = SCRATCHED_FALSE;

    public Product() {
    }

    public Product(int pProductIndex, String pProductName, String pProductQuant, String pProductPrice,
                   String pScratched, String pProductCategory, String pProductUnit) {
        productIndex = pProductIndex;
        productName = pProductName;
        productQuant = pProductQuant;
        productPrice = pProductPrice;
        scratched = pScratched;
        productCategory = pProductCategory;
        productUnit = pProductUnit;// != null ? pProductUnit : productUnit;
    }

    public long getID() {
        return id;
    }

    public void setID(int pID) {
        id = pID;
    }

    public int getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(int pProductIndex) {
        productIndex = pProductIndex;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName (String pProductName) {
        productName = pProductName;
    }

    public String getProductDescr() {
        return productDescr;
    }

    public void setProductDescr (String pProductDesc) {
        productDescr = pProductDesc;
    }

    public String getProductQuant() {
        return productQuant;
    }

    public void setProductQuant(String pProductQuant) {
        productQuant = pProductQuant;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String pProductPrice) {
        productPrice = pProductPrice;
    }

    public String getScratched () {
        return scratched;
    }

    public void setScratched (String pScratched) {
        scratched = pScratched;
    }

    public String getProductCategory () {
        return productCategory;
    }

    public void setProductCategory (String pProductCategory) {
        productCategory = pProductCategory;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String pProductUnit) {
        productUnit = pProductUnit;// != null ? pProductUnit : "Unidade";
    }

}
