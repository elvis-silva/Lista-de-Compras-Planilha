package com.elvis.shopping.list.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class MySingleButton extends AppCompatButton {
    public MySingleButton(Context context) {
        super(context);
    }

    public MySingleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySingleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
