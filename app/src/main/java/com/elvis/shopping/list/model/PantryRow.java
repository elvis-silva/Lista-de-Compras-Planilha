package com.elvis.shopping.list.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.elvis.shopping.list.CartScreen;
import com.elvis.shopping.list.MainActivity;
import com.elvis.shopping.list.PantryScreen;
import com.elvis.shopping.list.R;
import com.elvis.shopping.list.manager.AppManager;
import com.elvis.shopping.list.utils.CustomDialog;

public class PantryRow extends TableRow  implements View.OnTouchListener{

    private int currentBg;
    private Context context;
    public TextView productQuantTextView, productUnitTextView, productNameTextView;
    public String productName, productUnit, productQuant, productCategory;
    private int id = 0;

    public PantryRow(Context pContext) {
        super(pContext);
    }

    public PantryRow(Context pContext, String pProductName, String pProductQuant, String pProductUnit, String pProductCategory) {
        super(pContext);

        context = pContext;

        productName = pProductName;
        productUnit = pProductUnit;
        productQuant = pProductQuant;
        productCategory = pProductCategory;

//        CartScreen cartScreen = AppManager.getInstance().getCartScreen();
//        float textSize = cartScreen.textSize;
//        float subTextSize = cartScreen.subTextSize;

        TableRow.LayoutParams containerData2layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        TableRow.LayoutParams containerDatalayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f);

        LinearLayout containerData = new LinearLayout(pContext);
        containerData.setPadding(16,16,16,16);
        containerData.setLayoutParams(containerDatalayoutParams);
        containerData.setOrientation(LinearLayout.VERTICAL);
        addView(containerData);
        productNameTextView = new TextView(pContext);
//        productNameTextView.setTextSize(textSize);
        productNameTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        productNameTextView.setText(pProductName);
        productNameTextView.setTextColor(AppManager.getInstance().getCartScreen().currentProductAddedFontColor);
        productNameTextView.setTextSize(18f);
        containerData.addView(productNameTextView);
        productUnitTextView = new TextView(pContext);
        productUnitTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
//        productUnitTextView.setTextSize(subTextSize);
        productUnitTextView.setText(pProductUnit);
        productUnitTextView.setTextColor(AppManager.getInstance().getCartScreen().currentTitlesFontColor);
        productUnitTextView.setTextSize(12f);
        containerData.addView(productUnitTextView);

        LinearLayout containerData2 = new LinearLayout(pContext);
        containerData2.setPadding(16,16,16,16);
        containerData2.setLayoutParams(containerData2layoutParams);
        containerData2.setOrientation(LinearLayout.VERTICAL);
        addView(containerData2);
        TextView quantText = new TextView(context);
        quantText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        quantText.setTextSize(12f);
        quantText.setTextColor(AppManager.getInstance().getCartScreen().currentTitlesFontColor);
        quantText.setText("Quant.");
        quantText.setGravity(Gravity.END);
        containerData2.addView(quantText);
        productQuantTextView = new TextView(pContext);
        productQuantTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        productQuantTextView.setText(pProductQuant);

//        productQuantTextView.setTextColor(AppManager.getInstance().getCartScreen().currentProductAddedFontColor);
        float currentQuant = Float.valueOf(getProductQuant().
                        replace(".", "").replace(",", "."));
        int textColor = currentQuant <= 0 ? Color.RED :
                AppManager.getInstance().getCartScreen().currentProductAddedFontColor;
        if(currentQuant <= 0) {
            productQuantTextView.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            productQuantTextView.setTypeface(Typeface.DEFAULT);
        }
        productQuantTextView.setTextColor(textColor);
        productQuantTextView.setTextSize(18f);
        productQuantTextView.setGravity(Gravity.END);
        containerData2.addView(productQuantTextView);
/*
        float currentQuant = Float.valueOf(pProductQuant.replace(".", "").replace(",", "."));
        int bg = currentQuant <= 0 ? R.drawable.btn_negative : R.drawable.btn;

//        setBackgroundResource(bg);

        OnClickListener touchListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionsDialog();
            }
        };
        setOnClickListener(touchListener);*/

        setOnTouchListener(this);

        setBackgroundColor(AppManager.getInstance().getCartScreen().currentBgColor);
    }

    public long getID() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductQuant() {
        return productQuant;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setID(int pID) {
        id = pID;
    }

    public void setProductName(String pProductName) {
        productName = pProductName;
        productNameTextView.setText(productName);
    }

    public void setProductQuant(String pProductQuant) {
        productQuant = pProductQuant;
        productQuantTextView.setText(productQuant);
        float currentQuant = Float.valueOf(productQuant.
                        replace(".", "").replace(",", "."));
        int textColor = currentQuant <= 0 ? Color.RED :
                AppManager.getInstance().getCartScreen().currentProductAddedFontColor;
        productQuantTextView.setTextColor(textColor);
        if(currentQuant <= 0) {
            productQuantTextView.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            productQuantTextView.setTypeface(Typeface.DEFAULT);
        }
    }

    public void setProductUnit(String pProductUnit) {
        productUnit = pProductUnit;
        productUnitTextView.setText(productUnit);
    }

    public void setProductCategory(String pProductCategory) {
        productCategory = pProductCategory;
    }

    public void updateData(String pProductName, String pProductQuant, String pProductUnit, String pProductCategory) {
        setProductName(pProductName);
        setProductQuant(pProductQuant);
        setProductUnit(pProductUnit);
        setProductCategory(pProductCategory);

        ((MainActivity)AppManager.getInstance().getActivity()).dataHandler.updateProductOnPantry(
                this, pProductName, pProductQuant, pProductUnit, pProductCategory);
    }

    private int getColorRes(int resColor) {
        return context.getResources().getColor(resColor);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            setBackgroundColor(getColorRes(R.color.custom_color));
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            setBackgroundColor(AppManager.getInstance().getCartScreen().currentBgColor);
            showOptionsDialog();
        }
        if(event.getAction() == MotionEvent.ACTION_CANCEL) {
            setBackgroundColor(AppManager.getInstance().getCartScreen().currentBgColor);
        }
        return v.performClick();
    }

    @Override
    public boolean performClick() {
        super.performClick();

        return true;
    }

    private void showOptionsDialog() {
        PantryScreen pantryScreen = AppManager.getInstance().getPantryScreen();
        if(pantryScreen.currentPantryRow != null) {
            float currentQuant = Float.valueOf(
                    pantryScreen.currentPantryRow.getProductQuant().
                            replace(".", "").replace(",", "."));
            int textColor = currentQuant <= 0 ? Color.RED :
                    AppManager.getInstance().getCartScreen().currentProductAddedFontColor;
            pantryScreen.currentPantryRow.productQuantTextView.setTextColor(textColor);
//            int bg = currentQuant <= 0 ? R.drawable.btn_negative : R.drawable.btn;
//            pantryScreen.currentPantryRow.setBackgroundResource(bg);
        }
        pantryScreen.currentPantryRow = this;

//        setBackgroundResource(R.drawable.btn_pressed);

        CustomDialog customDialog = new CustomDialog(context, getProductName(), "", CustomDialog.TYPE_SHOW_OPTIONS);
        customDialog.show();
    }
}
