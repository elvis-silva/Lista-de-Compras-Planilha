package com.elvis.shopping.list.model;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.elvis.shopping.list.CartScreen;
import com.elvis.shopping.list.manager.AppManager;
import com.elvis.shopping.list.utils.CalculeUtils;

public class ProductTableRow extends TableRow {

    public int id = 0;
    private String productCategory, productName, productQuant, productPrice, productTotalValue, productUnit;
    private TextView tvProductName, tvProductQuant, tvProductPrice, tvProductValTotal, tvProductUnit, tvPricetTitle,
                        tvQuantTitle, tvTotalTitle;
    private boolean scratched = false;
    private CartScreen cartScreen;
    public boolean computed = false;
    private Product product;

    public ProductTableRow(Context pContext) {
        super(pContext);
    }

    public ProductTableRow(Context pContext, String pProductName, String pProductQuant, String pProductPrice,
                           String pProductCategory, String pProductUnit) {
        super(pContext);

        cartScreen = AppManager.getInstance().getCartScreen();

        productCategory = pProductCategory;

        productName = pProductName;
        productUnit = pProductUnit == null || pProductUnit.equals("") ? "Unidade" : pProductUnit;
        productQuant = pProductQuant.equals("") ? "0" : pProductQuant;
        productPrice = pProductPrice.equals("") ? "0" : pProductPrice;

        float priceUnit = Float.valueOf(String.valueOf(productPrice));
        float quant = Float.valueOf(String.valueOf(productQuant));
        productTotalValue = String.valueOf(priceUnit * quant);

        int textColor = Float.valueOf(productTotalValue) != 0 ? cartScreen.currentProductAddedFontColor : cartScreen.currentProductCanceledFontColor;

        // COLUMN 1
        LinearLayout linearLayout = new LinearLayout(pContext);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.65f));
        addView(linearLayout);

        tvProductName = new TextView(pContext);
        tvProductName.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvProductName.setText(productName);
        tvProductName.setTextColor(textColor);
        tvProductName.setGravity(Gravity.BOTTOM);
        tvProductName.setPadding(5,0,5,0);
        float productNameSize = tvProductName.getTextSize() * 0.45f;
        tvProductName.setTextSize(productNameSize);
        linearLayout.addView(tvProductName);

        tvProductUnit = new TextView(pContext);
        tvProductUnit.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvProductUnit.setGravity(Gravity.TOP);
        tvProductUnit.setText(productUnit);
        tvProductUnit.setTextColor(cartScreen.currentTitlesFontColor);
        tvProductUnit.setPadding(5,0,5,0);
        tvProductUnit.setTextSize(productNameSize * 0.6f);
        linearLayout.addView(tvProductUnit);

        // COLUMN 2
        linearLayout = new LinearLayout(pContext);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        addView(linearLayout);

        tvQuantTitle = new TextView(pContext);
        tvQuantTitle.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvQuantTitle.setGravity(Gravity.TOP | Gravity.END);
        tvQuantTitle.setText("Quant");
        tvQuantTitle.setTextColor(cartScreen.currentTitlesFontColor);
        tvQuantTitle.setPadding(5,0,5,0);
        tvQuantTitle.setTextSize(productNameSize * 0.6f);
        linearLayout.addView(tvQuantTitle);

        tvProductQuant = new TextView(pContext);
        tvProductQuant.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvProductQuant.setText(CalculeUtils.kiloFormat(Float.valueOf(String.valueOf(productQuant))));
        tvProductQuant.setTextColor(textColor);
        tvProductQuant.setGravity(Gravity.TOP | Gravity.END);
        tvProductQuant.setTextSize(productNameSize * 0.85f);
        tvProductQuant.setPadding(5,0,5,0);
        linearLayout.addView(tvProductQuant);

        // COLUMN 3
        linearLayout = new LinearLayout(pContext);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        addView(linearLayout);

        tvPricetTitle = new TextView(pContext);
        tvPricetTitle.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvPricetTitle.setGravity(Gravity.TOP | Gravity.END);
        tvPricetTitle.setText("$Unit");
        tvPricetTitle.setTextColor(cartScreen.currentTitlesFontColor);
        tvPricetTitle.setPadding(5,0,5,0);
        tvPricetTitle.setTextSize(productNameSize * 0.6f);
        linearLayout.addView(tvPricetTitle);

        tvProductPrice = new TextView(pContext);
        tvProductPrice.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvProductPrice.setText(CalculeUtils.moneyFormat(Float.valueOf(String.valueOf(productPrice))));
        tvProductPrice.setTextColor(textColor);
        tvProductPrice.setGravity(Gravity.TOP | Gravity.END);
        tvProductPrice.setTextSize(productNameSize * 0.85f);
        tvProductPrice.setPadding(5,0,5,0);
        linearLayout.addView(tvProductPrice);

        // COLUMN 4
        linearLayout = new LinearLayout(pContext);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        addView(linearLayout);

        tvTotalTitle = new TextView(pContext);
        tvTotalTitle.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvTotalTitle.setGravity(Gravity.TOP | Gravity.END);
        tvTotalTitle.setText("$Total");
        tvTotalTitle.setTextColor(cartScreen.currentTitlesFontColor);
        tvTotalTitle.setPadding(5,0,5,0);
        tvTotalTitle.setTextSize(productNameSize * 0.6f);
        linearLayout.addView(tvTotalTitle);

        tvProductValTotal = new TextView(pContext);
        tvProductValTotal.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvProductValTotal.setText(CalculeUtils.moneyFormat(Float.valueOf(productTotalValue)));
        tvProductValTotal.setTextColor(textColor);
        tvProductValTotal.setGravity(Gravity.TOP | Gravity.END);
        tvProductValTotal.setTextSize(productNameSize * 0.85f);
        tvProductValTotal.setPadding(5,0,5,0);
        linearLayout.addView(tvProductValTotal);

        setPadding(3,3,3,3);

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                AppManager.getInstance().openSoftKeyboard();
                if (cartScreen.currentState.equals(CartScreen.STATE.CART_READY)) {
                    cartScreen.showEditing(getProductName());
                    cartScreen.openProductEdit(getInstance(), getProductName(), getProductQuant(), getProductPrice(), getProductCategory(), getProductUnit());
                    cartScreen.showEditSceneButtons();
                } else if (cartScreen.currentState.equals(CartScreen.STATE.EDIT_PRODUCT)) {
                    cartScreen.showEditing(getProductName());
                    cartScreen.updateProductList();
                    cartScreen.changeProduct(getInstance(), getProductName(), getProductQuant(), getProductPrice(), getProductCategory(), getProductUnit());
                    cartScreen.deleteProduct(getProduct());

                    if (scratched) {
                        removeScratchedBackground();
                        cartScreen.removeScratch(getProduct());
                    }
                    cartScreen.updateTotaldaCompra();
                }
            }
        });
    }

    public void setTextColor(int pColor) {
        tvProductQuant.setTextColor(pColor);
        tvProductName.setTextColor(pColor);
        tvProductPrice.setTextColor(pColor);
        tvProductValTotal.setTextColor(pColor);
    }

    public void setTitlesColor(int pColor) {
        tvProductUnit.setTextColor(pColor);
        tvQuantTitle.setTextColor(pColor);
        tvPricetTitle.setTextColor(pColor);
        tvTotalTitle.setTextColor(pColor);
    }

    public void setProduct (Product pProduct) {
        product = pProduct;
    }

    public Product getProduct() {
        return product;
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

    public String getProductTotalValue() {
        return productTotalValue;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductCategory(String pProductCategory) {
        productCategory = pProductCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public ProductTableRow getInstance() {
        return this;
    }

    public void updateData(String pProductName, String pProductQuant, String pProductPrice, String pProductUnit) {
        productName = pProductName;
        productQuant = pProductQuant;
        productPrice = pProductPrice;
        productUnit = pProductUnit;

        float price = Float.valueOf(productPrice);
        float quant = Float.valueOf(productQuant);
        productTotalValue = String.valueOf(price * quant);

        tvProductName.setText(productName);
        tvProductUnit.setText(productUnit);
        tvProductQuant.setText(CalculeUtils.kiloFormat(Float.valueOf(productQuant)));
        tvProductPrice.setText(CalculeUtils.moneyFormat(Float.valueOf(productPrice)));
        tvProductValTotal.setText(CalculeUtils.moneyFormat(Float.valueOf(productTotalValue)));

        int textColor = Float.valueOf(productTotalValue) != 0 ? cartScreen.currentProductAddedFontColor :
                cartScreen.currentProductCanceledFontColor;
        textColor = scratched ? cartScreen.currentProductScratchedFontColor : textColor;

        tvProductName.setTextColor(textColor);
        tvProductQuant.setTextColor(textColor);
        tvProductPrice.setTextColor(textColor);
        tvProductValTotal.setTextColor(textColor);

        int titlesColor = scratched ? cartScreen.currentProductScratchedFontColor : cartScreen.currentTitlesFontColor;

        tvQuantTitle.setTextColor(titlesColor);
        tvPricetTitle.setTextColor(titlesColor);
        tvTotalTitle.setTextColor(titlesColor);
        tvProductUnit.setTextColor(titlesColor);

    }

    public void setScratchedBackground() {
        scratched = true;

        tvProductName.setPaintFlags(tvProductName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvProductQuant.setPaintFlags(tvProductQuant.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvProductPrice.setPaintFlags(tvProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvProductValTotal.setPaintFlags(tvProductValTotal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvProductUnit.setPaintFlags(tvProductUnit.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvQuantTitle.setPaintFlags(tvQuantTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvPricetTitle.setPaintFlags(tvPricetTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvTotalTitle.setPaintFlags(tvTotalTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        tvProductName.setTextColor(cartScreen.currentProductScratchedFontColor);
        tvProductQuant.setTextColor(cartScreen.currentProductScratchedFontColor);
        tvProductPrice.setTextColor(cartScreen.currentProductScratchedFontColor);
        tvProductValTotal.setTextColor(cartScreen.currentProductScratchedFontColor);
        tvProductUnit.setTextColor(cartScreen.currentProductScratchedFontColor);
        tvQuantTitle.setTextColor(cartScreen.currentProductScratchedFontColor);
        tvPricetTitle.setTextColor(cartScreen.currentProductScratchedFontColor);
        tvTotalTitle.setTextColor(cartScreen.currentProductScratchedFontColor);
    }

    public void removeScratchedBackground () {
        scratched = false;

        int textColor = Float.valueOf(productTotalValue) != 0 ? cartScreen.currentProductAddedFontColor : cartScreen.currentProductCanceledFontColor;

        tvProductName.setPaintFlags(tvProductName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        tvProductQuant.setPaintFlags(tvProductQuant.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        tvProductPrice.setPaintFlags(tvProductPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        tvProductValTotal.setPaintFlags(tvProductValTotal.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        tvProductUnit.setPaintFlags(tvProductUnit.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        tvQuantTitle.setPaintFlags(tvQuantTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        tvPricetTitle.setPaintFlags(tvPricetTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        tvTotalTitle.setPaintFlags(tvTotalTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

        tvProductName.setTextColor(textColor);
        tvProductQuant.setTextColor(textColor);
        tvProductPrice.setTextColor(textColor);
        tvProductValTotal.setTextColor(textColor);

        int titleColor = cartScreen.currentTitlesFontColor;

        tvQuantTitle.setTextColor(titleColor);
        tvPricetTitle.setTextColor(titleColor);
        tvTotalTitle.setTextColor(titleColor);
        tvProductUnit.setTextColor(titleColor);
    }

    public View[] getTitles() {
        return new View[]{tvProductUnit, tvQuantTitle, tvPricetTitle, tvTotalTitle};
    }

    public View[] getSubtitles() {
        return new View[]{tvProductName, tvProductQuant, tvProductPrice, tvProductValTotal};
    }

    public boolean isScratched() {
        return scratched;
    }
}
