package com.elvis.shopping.list.impl;

import android.view.KeyEvent;
import android.view.View;

public interface IActivity {

    void createView(View pView);

    void onResume();

    void onPause();

    void onDestroy();

    boolean onKeyUp(int keyCode, KeyEvent event);

    void onBackPressed();
}
