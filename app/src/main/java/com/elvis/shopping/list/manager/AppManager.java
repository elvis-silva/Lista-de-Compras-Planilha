package com.elvis.shopping.list.manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.elvis.shopping.list.CartScreen;
import com.elvis.shopping.list.ConfigScreen;
import com.elvis.shopping.list.MainActivity;
import com.elvis.shopping.list.PantryScreen;
import com.elvis.shopping.list.ShoppingLists;
import com.elvis.shopping.list.impl.IActivity;

public class AppManager {
    private static AppManager instance;
    private Context context;
    private Activity activity, lastActivity;
    private IActivity currentActivity;
    private CartScreen cartScreen;
    private PantryScreen pantryScreen;
    private ConfigScreen configScreen;
    private ShoppingLists shoppingLists;
    private boolean isKeyboardShow = false;

    private AppManager(){}

    public static AppManager getInstance() {
        if(instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    public void initActivity(Activity pActivity) {
        if(context == null) {
            initApp(pActivity);
        }
    }

    private synchronized void initApp(Activity pActivity) {
        context = pActivity.getApplicationContext();
        activity = pActivity;
    }

    public Activity getActivity() {
        return activity;
    }

    public Context getContext() {
        return context;
    }

    public IActivity getCurrentScreen() {
        return currentActivity;
    }

    public void setCurrentScreen(IActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public void setCartScreen(CartScreen cartScreen) {
        this.cartScreen = cartScreen;
    }

    public CartScreen getCartScreen() {
        return cartScreen;
    }

    public void setConfigScreen(ConfigScreen configScreen) {
        this.configScreen = configScreen;
    }

    public ConfigScreen getConfigScreen() {
        return configScreen;
    }

    public void setPantryScreen(PantryScreen pantryScreen) {
        this.pantryScreen = pantryScreen;
    }

    public PantryScreen getPantryScreen() {
        return pantryScreen;
    }

    public void setShoppingLists(ShoppingLists shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public ShoppingLists getShoppingListsScreen() {
        return shoppingLists;
    }

    public void showSoftKeyboard(View pView) {
        openSoftKeyboard();
        pView.requestFocus();
        /*if (pView.requestFocus()) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert im != null;
            im.showSoftInput(pView, InputMethodManager.SHOW_IMPLICIT);
        }*/
    }

    public void openSoftKeyboard() {
        if(!((MainActivity) activity).getSoftKeyboard().isKeyboardShow) {
            ((MainActivity) activity).getSoftKeyboard().openSoftKeyboard(true);
            ((MainActivity) activity).getSoftKeyboard().isKeyboardShow = true;
            isKeyboardShow = true;
        }
    }

    public void closeSoftKeyboard() {
        if(((MainActivity) activity).getSoftKeyboard().isKeyboardShow) {
            ((MainActivity) activity).getSoftKeyboard().openSoftKeyboard(false);
            ((MainActivity) activity).getSoftKeyboard().isKeyboardShow = false;
            isKeyboardShow = false;
        }
    }
}
