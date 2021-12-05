package com.elvis.shopping.list.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by elvis on 05/02/18.
 */

public class MyButton extends AppCompatImageButton {

    boolean touchOn;
    boolean mDownTouch = false;

    public MyButton(Context context) {
        super(context);
        init();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        touchOn = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        // Listening for the down and up touch events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                touchOn = !touchOn;
                invalidate();

                mDownTouch = true;
                return true;

            case MotionEvent.ACTION_UP:
                if (mDownTouch) {
                    mDownTouch = false;
                    performClick(); // Call this method to handle the response, and
                    // thereby enable accessibility services to
                    // perform this action for a user who cannot
                    // click the touchscreen.
                    return true;
                }
        }
        return false; // Return false for other touch events
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
