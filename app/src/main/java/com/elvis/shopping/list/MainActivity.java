package com.elvis.shopping.list;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.elvis.shopping.list.data.DataHandler;
import com.elvis.shopping.list.impl.IActivity;
import com.elvis.shopping.list.manager.AppManager;
import com.elvis.shopping.list.model.ConfigData;
import com.elvis.shopping.list.utils.CustomDialog;
import com.elvis.shopping.list.utils.LOG;
import com.elvis.shopping.list.utils.SoftKeyboard;
import com.elvis.shopping.list.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener,
        TextView.OnEditorActionListener {//,
//        KeyboardWatcher.OnKeyboardToggleListener {

//      private KeyboardWatcher keyboardWatcher;

    private SoftKeyboard softKeyboard;

    private static final String TAG = MainActivity.class.getSimpleName();
    public DataHandler dataHandler;
    public String currentListName, currentCustomDialogType;
    public String currentCategoryOnPantryEdit = "";
    public String currentProductNameOnPantryEdit = "";
    public String currentQuantOnPantryEdit = "";
    public String currentUnitOnPantryEdit = "";
    public TableRow tadRowAdView;
    LayoutInflater inflater;
    View rootView = null;
    FrameLayout container;
    ProgressBar progressBar;

    public static int []DEFAULT_COLORS = CartScreen.DEFAULT_COLORS;
    public int currentBgColor = DEFAULT_COLORS[0];
    public int currentFontColor = DEFAULT_COLORS[1];
    public int currentFontInsertColor = DEFAULT_COLORS[2];
    public int currentTitlesFontColor = DEFAULT_COLORS[3];
    public int currentMessageFontColor = DEFAULT_COLORS[4];
    public int currentProductAddedFontColor = DEFAULT_COLORS[5];
    public int currentProductCanceledFontColor = DEFAULT_COLORS[6];
    public int currentProductScratchedFontColor = DEFAULT_COLORS[7];
    public int currentCategoryTitleFontColor = DEFAULT_COLORS[8];
    public int currentCategoryTitleBgColor = DEFAULT_COLORS[9];

    public CharSequence mTitle;

    public IActivity currentActivity;
    public CustomDialog dialog;

    private MenuItem lastItem;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        keyboardWatcher = new KeyboardWatcher(this);
//        keyboardWatcher.setListener(this);

        LOG.getInstance().setDebugMode(true);
        AppManager.getInstance().initActivity(this);

        initDataHandler();
        initViews();
        getInputMethodManager();
    }

    private void initViews() {
        inflater = this.getLayoutInflater();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lastItem = navigationView.getMenu().findItem(R.id.nav_section_current);
        lastItem.setChecked(true);

        container = findViewById(R.id.container_view);

        mTitle = getTitle();

        AppManager.getInstance().setCartScreen(new CartScreen(this));
        AppManager.getInstance().setShoppingLists(new ShoppingLists(this));
        AppManager.getInstance().setPantryScreen(new PantryScreen(this));
        AppManager.getInstance().setConfigScreen(new ConfigScreen(this));
        AppManager.getInstance().setCurrentScreen(AppManager.getInstance().getCartScreen());
        LOG.i(TAG, String.valueOf("CURRENT SCREEN : " + AppManager.getInstance().getCartScreen()));

        currentListName = drawer.isDrawerOpen(GravityCompat.START) ?
                getString(R.string.app_name) : dataHandler.getListLoaded();

        rootView = inflater.inflate(R.layout.cart_screen, container);
        restoreActionBar();
        if(rootView != null) AppManager.getInstance().getCurrentScreen().createView(rootView);

        onSectionAttached(R.id.nav_section_current);
    }

    private void initDataHandler() {
        dataHandler = new DataHandler(this);
        dataHandler.open();
        Cursor cursor = dataHandler.returnConfigData();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            ConfigData configData = dataHandler.parseConfigData(cursor);
            currentBgColor = configData.getCurrentBgColor();
            currentFontColor = configData.getCurrentFontColor();
            currentFontInsertColor = configData.getCurrentInsertFontColor();
            currentTitlesFontColor = configData.getCurrentTitlesFontColor();
            currentMessageFontColor = configData.getCurrentMessageFontColor();
            currentProductAddedFontColor = configData.getCurrentProductAddedFontColor();
            currentProductCanceledFontColor = configData.getCurrentProductCanceledFontColor();
            currentProductScratchedFontColor = configData.getCurrentProductScratchedFontColor();
            currentCategoryTitleFontColor = configData.getCurrentCategoryTitleFontColor();
            currentCategoryTitleBgColor = configData.getCurrentCategoryTitleBgColor();
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppManager.getInstance().getCurrentScreen().onResume();
    }

    @Override
    protected void onDestroy() {
//        keyboardWatcher.destroy();
        AppManager.getInstance().getCurrentScreen().onDestroy();
        dataHandler.close();
        super.onDestroy();
        softKeyboard.unRegisterSoftKeyboardCallback();
        System.exit(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppManager.getInstance().getCurrentScreen().onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AppManager.getInstance().getCurrentScreen().onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.getMenu().clear();
        if(AppManager.getInstance().getCurrentScreen().equals(AppManager.getInstance().getCartScreen())) {
            getMenuInflater().inflate(R.menu.cart_screen, menu);
        }  else if (AppManager.getInstance().getCurrentScreen().equals(AppManager.getInstance().getPantryScreen())) {
            getMenuInflater().inflate(R.menu.pantry_screen, menu);
        }
        restoreActionBar();

        return true;
    }
/*
    @Override
    public void onKeyboardShown(int keyboardSize) {
        LOG.i(TAG, "KEYBOARD SHOWN");
    }

    @Override
    public void onKeyboardClosed() {
        LOG.i(TAG, "KEYBOARD CLOSED");
    }
*/
    public InputMethodManager getInputMethodManager() {
        ViewGroup mainLayout = findViewById(R.id.drawer_layout); // You must use the layout root
        InputMethodManager im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        softKeyboard = new SoftKeyboard(mainLayout, im);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged() {

            @Override
            public void onSoftKeyboardHide() {
                // Code here
            }

            @Override
            public void onSoftKeyboardShow() {
                // Code here
            }
        });
        return im; //((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
    }


    public InputMethodManager getInputMethodManager(ViewGroup pViewGroup) {
        InputMethodManager im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        softKeyboard = new SoftKeyboard(pViewGroup, im);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged() {

            @Override
            public void onSoftKeyboardHide() {
                // Code here
            }

            @Override
            public void onSoftKeyboardShow() {
                // Code here
            }
        });
        return im; //((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    public SoftKeyboard getSoftKeyboard() {
        return softKeyboard;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        LOG.i(TAG, String.valueOf(keyCode));
        return AppManager.getInstance().getCurrentScreen().onKeyUp(keyCode, event);
/*        AppManager.getInstance().getCartScreen().onKeyUp(keyCode, event);
        switch (keyCode) {
            case 66:
                return AppManager.getInstance().getCurrentScreen().onKeyUp(keyCode, event);
            default:
                return super.onKeyUp(keyCode, event);
        }*/
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.i(TAG, "[ ==========> ITEM SELECTED : "+item.getTitle().toString()+" <========== ]");
       // CustomDialog dialog;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            dialog = new CustomDialog(MainActivity.this, "Salvar?",
                    "", CustomDialog.TYPE_SAVE_OPTIONS);
            dialog.show();
            return true;
        } else if(id == R.id.action_zero) {
            dialog = new CustomDialog(MainActivity.this, "Opções",
                    "", CustomDialog.TYPE_RESET_VALUES);
            dialog.show();
            return true;
        } else if(id == R.id.action_add_new_item) {
            dialog = new CustomDialog(MainActivity.this, "Novo Item",
                    "", CustomDialog.TYPE_ADD_PRODUCT_ON_PANTRY);
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        currentActivity = AppManager.getInstance().getCurrentScreen();

        switch (id) {
            case R.id.nav_section_current:
                if(currentActivity != AppManager.getInstance().getCartScreen()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_view, PlaceholderFragment.newInstance(id))
                            .commit();
                    AppManager.getInstance().setCurrentScreen(
                            AppManager.getInstance().getCartScreen()
                    );
                    onCreateOptionsMenu(toolbar.getMenu());
                }
                break;
            case R.id.nav_section_my_lists:
                if(currentActivity != AppManager.getInstance().getShoppingListsScreen()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_view, PlaceholderFragment.newInstance(id))
                            .commit();
                    AppManager.getInstance().setCurrentScreen(
                            AppManager.getInstance().getShoppingListsScreen()
                    );
                }
                break;
            case R.id.nav_section_my_pantry:
                if(currentActivity != AppManager.getInstance().getPantryScreen()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_view, PlaceholderFragment.newInstance(id))
                            .commit();
                    AppManager.getInstance().setCurrentScreen(
                            AppManager.getInstance().getPantryScreen()
                    );
                }
                break;
            case R.id.nav_section_setup:
                if(currentActivity != AppManager.getInstance().getConfigScreen()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_view, PlaceholderFragment.newInstance(id))
                            .commit();
                    AppManager.getInstance().setCurrentScreen(
                            AppManager.getInstance().getConfigScreen()
                    );
                }
                break;
        }

        onCreateOptionsMenu(toolbar.getMenu());

        lastItem.setChecked(false);
        item.setChecked(true);
        lastItem = item;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void showOfferWall() {
        onDestroy();
    }

    public String getActionBarTitle() {
        return getSupportActionBar().getTitle() == null ? "" : getSupportActionBar().getTitle().toString();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case R.id.nav_section_current:
                mTitle = dataHandler.getListLoaded();//getString(R.string.title_section1);
                break;
            case R.id.nav_section_my_lists:
                mTitle = getString(R.string.title_section_my_lists);
                break;
            case R.id.nav_section_setup:
                mTitle = getString(R.string.title_section_setup);
                break;
            case R.id.nav_section_my_pantry:
                mTitle = getString(R.string.title_section_my_pantry);
                break;
        }
        restoreActionBar();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return v.performClick();
    }

    public void openDevPage(MenuItem item) {
        Utils.openDevPage(MainActivity.this);
    }

    public void rateMe(MenuItem item) {
        Utils.rateApp(MainActivity.this);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
//            container.setVisibility(View.INVISIBLE);
            View rootView;

            int currentSectionNumber = 0;
            if(getArguments() != null) {
                currentSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            }

            if(currentSectionNumber == R.id.nav_section_current) {
                rootView = inflater.inflate(R.layout.cart_screen, container, false);
                AppManager.getInstance().setCurrentScreen(AppManager.getInstance().getCartScreen());
                ((MainActivity)AppManager.getInstance().getActivity()).restoreActionBar();
            } else if (currentSectionNumber == R.id.nav_section_my_lists) {
                rootView = inflater.inflate(R.layout.shopping_lists_view, container, false);
                AppManager.getInstance().setCurrentScreen(AppManager.getInstance().getShoppingListsScreen());
            } else if (currentSectionNumber == R.id.nav_section_my_pantry) {
                rootView = inflater.inflate(R.layout.pantry_screen, container, false);
                AppManager.getInstance().setCurrentScreen(AppManager.getInstance().getPantryScreen());
            } else {
                rootView = inflater.inflate(R.layout.configure_screen, container, false);
                AppManager.getInstance().setCurrentScreen(AppManager.getInstance().getConfigScreen());
            }

            AppManager.getInstance().getCurrentScreen().createView(rootView);
            Utils.setAnimation(container, R.anim.move_right_to_left);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if(getArguments() != null) {
                ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
            }
        }
    }
}
