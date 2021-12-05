package com.elvis.shopping.list.model;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elvis.shopping.list.CartScreen;
import com.elvis.shopping.list.manager.AppManager;

public class Category extends LinearLayout {

    private String categoryName;
    public TextView title;
    private FrameLayout mask;

    public Category(Context pContext) {
        super(pContext);
    }

    public Category(Context pContext, String pCategoryName) {
        super(pContext);

        CartScreen cartScreen = AppManager.getInstance().getCartScreen();

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);

        categoryName = pCategoryName;

        mask = new FrameLayout(pContext);
        mask.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mask);

        title = new TextView(pContext);
        title.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//        title.setTextSize(25f);
        title.setText(categoryName);
        if(categoryName.equals("")) {
            title.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
        } else {
            title.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        title.setTextColor(cartScreen.currentCategoryTitleFontColor);
        mask.setBackgroundColor(cartScreen.currentCategoryTitleBgColor);
        title.setBackgroundColor(Color.TRANSPARENT);
        title.setPadding(5,5,5,5);

        mask.addView(title);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public FrameLayout getMask() {
        return mask;
    }
}
