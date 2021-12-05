package com.elvis.shopping.list;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.elvis.shopping.list.data.DataHandler;
import com.elvis.shopping.list.impl.IActivity;
import com.elvis.shopping.list.CartScreen.CONFIG;
import com.elvis.shopping.list.manager.AppManager;
import com.elvis.shopping.list.model.Category;
import com.elvis.shopping.list.model.ProductTableRow;
import com.elvis.shopping.list.utils.Utils;
import com.elvis.shopping.list.view.MyEditText;
import com.elvis.shopping.list.view.MySingleButton;
import com.elvis.shopping.list.view.MyTextView;

import java.util.ArrayList;


public class ConfigScreen implements IActivity{

    private Context context;

    private int [] DEFAULT_COLORS = CartScreen.DEFAULT_COLORS;
    private View lastConfigBtn, view, frameBg;
    private CONFIG currentConfig = CONFIG.BACKGROUND;
    private ArrayList<MyTextView> tvTitles = new ArrayList<>();
    private RelativeLayout principal;
    private TableRow tabShowCartContent;
    private MyTextView showCartContent, productQuantText, productPriceText, productNameText, productCategoryText;
    private MyEditText productQuantInsert, productPriceInsert, productNameInsert, productCategoryInsert;
    private int changeFontColor, changeFontInsertColor, changeTitlesFontColor, changeMessageFontColor,
                changeProductAddedFontColor, changeBgColor, changeProductCanceledFontColor,
                changeProductScratchedFontColor, changeCategoryTitleFontColor, changeCategoryTitleBgColor;
    private ProductTableRow productScratched, productCanceled, productAdded;
    private Category categoryExample;
    private ArrayList<View> currentViews = new ArrayList<>();

    private CartScreen cartScreen;
    ArrayList<Category> categoriesList;
    private int backControl = 0;

    public ConfigScreen(Context pContext) {
        context = pContext;
        cartScreen = AppManager.getInstance().getCartScreen();//MainActivity.getInstance().cartScreen;
        categoriesList = cartScreen.categoriesList;
    }

    @Override
    public void createView(View pView) {
        backControl = 0;

        view = pView;

        showExampleScreen();
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if(backControl == 0) {
            Toast.makeText(context, "Pressione outra vez para sair", Toast.LENGTH_SHORT).show();
            backControl++;
        } else {
            ((MainActivity)AppManager.getInstance().getActivity()).showOfferWall();
        }
    }

    private void showExampleScreen() {
        currentViews.clear();

        final MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity();
        changeBgColor = mainActivity.currentBgColor;
        changeFontColor = mainActivity.currentFontColor;
        changeFontInsertColor = mainActivity.currentFontInsertColor;
        changeTitlesFontColor = mainActivity.currentTitlesFontColor;
        changeMessageFontColor = mainActivity.currentMessageFontColor;
        changeProductAddedFontColor = mainActivity.currentProductAddedFontColor;
        changeProductCanceledFontColor = mainActivity.currentProductCanceledFontColor;
        changeProductScratchedFontColor = mainActivity.currentProductScratchedFontColor;
        changeCategoryTitleFontColor = mainActivity.currentCategoryTitleFontColor;
        changeCategoryTitleBgColor = mainActivity.currentCategoryTitleBgColor;

        principal = view.findViewById(R.id.principal_config);
        frameBg = view.findViewById(R.id.principal_mask);
        updateCurrentViews(new View[]{frameBg});
        principal.setBackgroundColor(changeBgColor);
        tabShowCartContent = view.findViewById(R.id.tabShowCartContent);
        tabShowCartContent.setBackgroundColor(changeBgColor);
        showCartContent = view.findViewById(R.id.showCart);
        showCartContent.setBackgroundColor(changeBgColor);
        showCartContent.setTextColor(changeMessageFontColor);
/*        TableRow titlesTableRow = view.findViewById(R.id.titlesTableRow);
        int i = 0;
        while (i < titlesTableRow.getChildCount()) {
            MyTextView textView = (MyTextView) titlesTableRow.getChildAt(i);
            textView.setTextColor(changeTitlesFontColor);
            tvTitles.add(textView);
            i++;
        }
*/
        final TableRow tableRowTotal = view.findViewById(R.id.tableRowTotal);
        int i = 0;
        while (i < tableRowTotal.getChildCount()) {
            MyTextView textView = (MyTextView) tableRowTotal.getChildAt(i);
            textView.setTextColor(changeTitlesFontColor);
            tvTitles.add(textView);
            i++;
        }

        TableRow tbAddProductCategory = view.findViewById(R.id.tableRowAddCategory);
        TableRow tbAddProduct = view.findViewById(R.id.tableRowAddProduct);
        TableRow tbAddProductData = view.findViewById(R.id.tableRowAddProductData);
        LinearLayout container = view.findViewById(R.id.container);
        i = 0;
        while (i < container.getChildCount()) {
            MySingleButton btn = (MySingleButton) container.getChildAt(i);
            if(i == 0) btn.setBackgroundColor(DEFAULT_COLORS[0]);
            btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                    if (pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                        MySingleButton btn = (MySingleButton) pView;
                        if(currentConfig.equals(CONFIG.BACKGROUND)) {
                            changeBgColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.FONT)) {
                            changeFontColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.FONT_INSERT)) {
                            changeFontInsertColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.FONT_TITLES)) {
                            changeTitlesFontColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.MESSAGE_FONT)) {
                            changeMessageFontColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.PRODUCT_ADDED_FONT)) {
                            changeProductAddedFontColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.PRODUCT_CANCELED_FONT)) {
                            changeProductCanceledFontColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.PRODUCT_SCRACHED_FONT)) {
                            changeProductScratchedFontColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.CATEGORY_TITLE_FONT)) {
                            changeCategoryTitleFontColor(btn.getCurrentTextColor());
                        } else if (currentConfig.equals(CONFIG.CATEGORY_TITLE_BG)) {
                            changeCategoryTitleBgColor(btn.getCurrentTextColor());
                        }
                    }
                    return pView.performClick();
                }
            });
            i++;
        }

        MySingleButton changeBgBtn = view.findViewById(R.id.changeBgBtn);
        changeBgBtn.setBackgroundColor(DEFAULT_COLORS[11]);
        changeBgBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if (pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.BACKGROUND;
                    frameBg.setBackgroundDrawable(Utils.getBorderDrawable());

                    updateCurrentViews(new View[]{frameBg});
                }
                return pView.performClick();
            }
        });
        lastConfigBtn = changeBgBtn;
        currentConfig = CONFIG.BACKGROUND;
        frameBg.setBackgroundDrawable(Utils.getBorderDrawable());
        MySingleButton changeFontBtn = view.findViewById(R.id.changeFontBtn);
        changeFontBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeFontBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.FONT;
                    removeBg(frameBg);

                    updateCurrentViews(new View[]{
                            productCategoryText, productNameText, productQuantText, productPriceText});
                }
                return pView.performClick();
            }
        });
        MySingleButton changeFontInsertBtn = view.findViewById(R.id.changeFontInsertBtn);
        changeFontInsertBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeFontInsertBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.FONT_INSERT;

                    updateCurrentViews(new View[]{
                            productCategoryInsert, productNameInsert, productQuantInsert, productPriceInsert});
                }
                return pView.performClick();
            }
        });
        MySingleButton changeFontTitleBtn = view.findViewById(R.id.changeFontTitleBtn);
        changeFontTitleBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeFontTitleBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.FONT_TITLES;

                    int viewsLength = productCanceled.getTitles().length +
                            productAdded.getTitles().length + 1;

                    View[] views = new View[viewsLength];

                    int viewIndex = 0;

                    for(View v : productCanceled.getTitles()) {
                        views[viewIndex] = v;
                        viewIndex++;
                    }

                    for(View v : productAdded.getTitles()) {
                        views[viewIndex] = v;
                        viewIndex++;
                    }

                    views[viewIndex] = tableRowTotal;

                    updateCurrentViews(views);
                }
                return pView.performClick();
            }
        });
        MySingleButton changeMessageFontBtn = view.findViewById(R.id.changeMessageFontBtn);
        changeMessageFontBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeMessageFontBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.MESSAGE_FONT;

                    updateCurrentViews(new View[]{showCartContent});
                }
                return pView.performClick();
            }
        });
        MySingleButton changeProductAddFontBtn = view.findViewById(R.id.changeProductAddFontBtn);
        changeProductAddFontBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeProductAddFontBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.PRODUCT_ADDED_FONT;

                    int viewsLength = productAdded.getSubtitles().length;

                    View[] views = new View[viewsLength];

                    int viewIndex = 0;
                    for(View v : productAdded.getSubtitles()) {
                        views[viewIndex] = v;
                        viewIndex++;
                    }

                    updateCurrentViews(views);
                }
                return pView.performClick();
            }
        });
        MySingleButton changeProductCancelFontBtn = view.findViewById(R.id.changeProductCancelFontBtn);
        changeProductCancelFontBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeProductCancelFontBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.PRODUCT_CANCELED_FONT;

                    int viewsLength = productCanceled.getSubtitles().length;

                    View[] views = new View[viewsLength];

                    int viewIndex = 0;
                    for(View v : productCanceled.getSubtitles()) {
                        views[viewIndex] = v;
                        viewIndex++;
                    }

                    updateCurrentViews(views);
                }
                return pView.performClick();
            }
        });
        MySingleButton changeProductScratchedFontBtn = view.findViewById(R.id.changeProductScrachedFontBtn);
        changeProductScratchedFontBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeProductScratchedFontBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if (pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.PRODUCT_SCRACHED_FONT;

                    updateCurrentViews(new View[]{productScratched});
                }
                return pView.performClick();
            }
        });
        MySingleButton changeCategoryTitleFontBtn = view.findViewById(R.id.changeCategoryTitleFontBtn);
        changeCategoryTitleFontBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeCategoryTitleFontBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.CATEGORY_TITLE_FONT;

                    updateCurrentViews(new View[]{categoryExample.title});
                }
                return pView.performClick();
            }
        });
        MySingleButton changeCategoryTitleBgBtn = view.findViewById(R.id.changeCategoryTitleBgBtn);
        changeCategoryTitleBgBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeCategoryTitleBgBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.CATEGORY_TITLE_BG;

                    updateCurrentViews(new View[]{categoryExample.title});
                }
                return pView.performClick();
            }
        });
        MySingleButton changeDefaultBtn = view.findViewById(R.id.changeDefaultBtn);
        changeDefaultBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        changeDefaultBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    changeBgResource();
                    lastConfigBtn = pView;
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                    currentConfig = CONFIG.DEFAULT;
                    clearCurrentViews();
                    changeDefaultColors();
                }
                return pView.performClick();
            }
        });
        MySingleButton confirmChangeBtn = view.findViewById(R.id.confirmChangeBtn);
        confirmChangeBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        confirmChangeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                }
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    pView.setBackgroundColor(DEFAULT_COLORS[10]);
                    confirmChanges();
                    clearCurrentViews();
                }
                return pView.performClick();
            }
        });
        MySingleButton cancelChangeBtn = view.findViewById(R.id.cancelChangeBtn);
        cancelChangeBtn.setBackgroundColor(DEFAULT_COLORS[10]);
        cancelChangeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if(pMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    pView.setBackgroundColor(DEFAULT_COLORS[11]);
                }
                if(pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    pView.setBackgroundColor(DEFAULT_COLORS[10]);
                    cancelChanges();
                    clearCurrentViews();
                }
                return pView.performClick();
            }
        });

        LinearLayout productsList = view.findViewById(R.id.products_list);

        categoryExample = new Category(context, mainActivity.getString(R.string.category_label));
        productsList.addView(categoryExample);

        productAdded = new ProductTableRow(context, mainActivity.getString(R.string.scratched_label), "2", "12.34",
                mainActivity.getString(R.string.category_label), "Unidade");
        productAdded.setClickable(false);
        productsList.addView(productAdded);
        productCanceled = new ProductTableRow(context, mainActivity.getString(R.string.not_added_label), "0", "0",
                mainActivity.getString(R.string.category_label), "Unidade");
        productCanceled.setClickable(false);
        productsList.addView(productCanceled);
        productScratched = new ProductTableRow(context, mainActivity.getString(R.string.riscad_label), "3", "56.78",
                mainActivity.getString(R.string.category_label), "Unidade");
        productScratched.setScratchedBackground();
        productScratched.setClickable(false);
        productsList.addView(productScratched);

        MyTextView totalDaCompraText = view.findViewById(R.id.totalDaCompraText);
        totalDaCompraText.setText("24,68");

        showCartContent.setText(mainActivity.getString(R.string.status_message));

        productCategoryText = new MyTextView(context);
        tbAddProductCategory.addView(productCategoryText);
        productCategoryText.setTextColor(cartScreen.currentFontColor);
        productCategoryText.setBackgroundResource(R.drawable.quick_contact_badge_overlay);
        productCategoryText.setText("Categoria: ");
        productCategoryText.setPadding(8,8,8,8);
        productCategoryText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        productCategoryInsert = new MyEditText(context);
        tbAddProductCategory.addView(productCategoryInsert);
        productCategoryInsert.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        productCategoryInsert.setText(mainActivity.getString(R.string.category_label));
        productCategoryInsert.setPadding(8,8,8,8);
        productCategoryInsert.setTextColor(cartScreen.currentFontInsertColor);
        productCategoryInsert.setBackgroundColor(cartScreen.currentBgColor);
        productCategoryInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productCategoryInsert.setClickable(false);
        productCategoryInsert.setFocusableInTouchMode(false);

        productNameText = new MyTextView(context);
        tbAddProduct.addView(productNameText);
        productNameText.setText("Nome: ");
        productNameText.setPadding(8,8,8,8);
        productNameText.setTextColor(cartScreen.currentFontColor);
        productNameText.setBackgroundColor(cartScreen.currentBgColor);
        productNameText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        productNameInsert = new MyEditText(context);
        tbAddProduct.addView(productNameInsert);
        productNameInsert.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        productNameInsert.setText("Nome do produto");
        productNameInsert.setPadding(8,8,8,8);
        productNameInsert.setTextColor(cartScreen.currentFontInsertColor);
        productNameInsert.setBackgroundColor(cartScreen.currentBgColor);
        productNameInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productNameInsert.setClickable(false);
        productNameInsert.setFocusableInTouchMode(false);

        productQuantText = new MyTextView(context);
        tbAddProductData.addView(productQuantText);
        productQuantText.setBackgroundResource(R.drawable.quick_contact_badge_overlay);
        productQuantText.setText("Unidade");
        productQuantText.setPadding(8,8,8,8);
        productQuantText.setTextColor(cartScreen.currentFontColor);
        productQuantText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        productQuantInsert = new MyEditText(context);
        tbAddProductData.addView(productQuantInsert);
        productQuantInsert.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        productQuantInsert.setText("0.00");
        productQuantInsert.setPadding(8,8,8,8);
        productQuantInsert.setGravity(Gravity.END);
        productQuantInsert.setTextColor(cartScreen.currentFontInsertColor);
        productQuantInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productQuantInsert.setBackgroundColor(cartScreen.currentBgColor);
        productQuantInsert.setClickable(false);
        productQuantInsert.setFocusableInTouchMode(false);

        productPriceText = new MyTextView(context);
        tbAddProductData.addView(productPriceText);
        productPriceText.setText("   Preço: ");
        productPriceText.setPadding(8,8,8,8);
        productPriceText.setTextColor(cartScreen.currentFontColor);
        productPriceText.setBackgroundColor(cartScreen.currentBgColor);
        productPriceText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        productPriceInsert = new MyEditText(context);
        tbAddProductData.addView(productPriceInsert);
        productPriceInsert.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        productPriceInsert.setText("0.00");
        productPriceInsert.setPadding(8,8,8,8);
        productPriceInsert.setGravity(Gravity.END);
        productPriceInsert.setTextColor(cartScreen.currentFontInsertColor);
        productPriceInsert.setBackgroundColor(cartScreen.currentBgColor);
        productPriceInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productPriceInsert.setClickable(false);
        productPriceInsert.setFocusableInTouchMode(false);
    }

    private void clearCurrentViews() {
        if(currentViews.size() > 0) {
            for (View v : currentViews) {
                v.setBackgroundDrawable(Utils.getBorderDrawableTransparent());
            }
            currentViews.clear();
        }
    }

    private void updateCurrentViews(View[] views) {
        if (currentViews.size() > 0) {
            for(View v : currentViews) {
                v.setBackgroundDrawable(Utils.getBorderDrawableTransparent());
            }
            currentViews.clear();
        }
        if(views.length > 0) {
            for (View v : views) {
                v.setBackgroundDrawable(Utils.getBorderDrawable());
                currentViews.add(v);
            }
        }
    }

    private void changeBgColor(int pColor) {
//        MainActivity.getInstance().tadRowAdView.setBackgroundColor(pColor);
        principal.setBackgroundColor(pColor);
        showCartContent.setBackgroundColor(pColor);
        tabShowCartContent.setBackgroundColor(pColor);
        productCategoryText.setBackgroundColor(pColor);
        productCategoryInsert.setBackgroundColor(pColor);
        productNameText.setBackgroundColor(pColor);
        productNameInsert.setBackgroundColor(pColor);
        productQuantText.setBackgroundColor(pColor);
        productQuantInsert.setBackgroundColor(pColor);
        productPriceText.setBackgroundColor(pColor);
        productPriceInsert.setBackgroundColor(pColor);
        changeBgColor = pColor;
    }

    public void setChangeBgColor() {
        cartScreen.currentBgColor = changeBgColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentBgColor = changeBgColor;
    }

    private void changeFontColor(int pColor) {
        productNameText.setTextColor(pColor);
        productCategoryText.setTextColor(pColor);
        productPriceText.setTextColor(pColor);
        productQuantText.setTextColor(pColor);
        changeFontColor = pColor;
    }

    public void setChangeFontColor() {
        cartScreen.currentFontColor = changeFontColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentFontColor = changeFontColor;
    }

    private void changeFontInsertColor(int pColor) {
        productCategoryInsert.setTextColor(pColor);
        productNameInsert.setTextColor(pColor);
        productQuantInsert.setTextColor(pColor);
        productPriceInsert.setTextColor(pColor);
        changeFontInsertColor = pColor;
    }

    public void setChangeFontInsertColor() {
        cartScreen.currentFontInsertColor = changeFontInsertColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentFontInsertColor = changeFontInsertColor;
    }

    private void changeTitlesFontColor(int pColor) {
        int i = 0;
        while (i < tvTitles.size()) {
            tvTitles.get(i).setTextColor(pColor);
            i++;
        }
        productAdded.setTitlesColor(pColor);
        productCanceled.setTitlesColor(pColor);
 //       productScratched.setTitlesColor(pColor);
        changeTitlesFontColor = pColor;
    }

    public void setChangeTitlesFontColor() {
        cartScreen.currentTitlesFontColor = changeTitlesFontColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentTitlesFontColor = changeTitlesFontColor;
    }

    private void changeMessageFontColor(int pColor) {
        showCartContent.setTextColor(pColor);
        changeMessageFontColor = pColor;
    }

    public void setChangeMessageFontColor() {
        cartScreen.currentMessageFontColor = changeMessageFontColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentMessageFontColor = changeMessageFontColor;
    }

    private void changeProductAddedFontColor(int pColor) {
        productAdded.setTextColor(pColor);
        changeProductAddedFontColor = pColor;
    }

    public void setChangeProductAddedFontColor() {
        cartScreen.currentProductAddedFontColor = changeProductAddedFontColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentProductAddedFontColor = changeProductAddedFontColor;
    }

    private void changeProductCanceledFontColor(int pColor) {
        productCanceled.setTextColor(pColor);
        changeProductCanceledFontColor = pColor;
    }

    public void setChangeProductCanceledFontColor() {
        cartScreen.currentProductCanceledFontColor = changeProductCanceledFontColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentProductCanceledFontColor = changeProductCanceledFontColor;
    }

    private void changeProductScratchedFontColor(int pColor) {
        productScratched.setTextColor(pColor);
        productScratched.setTitlesColor(pColor);
        changeProductScratchedFontColor = pColor;
    }

    public void setChangeProductScratchedFontColor() {
        cartScreen.currentProductScratchedFontColor = changeProductScratchedFontColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentProductScratchedFontColor = changeProductScratchedFontColor;
    }

    private void changeCategoryTitleFontColor(int pColor) {
        categoryExample.title.setTextColor(pColor);
        changeCategoryTitleFontColor = pColor;
    }

    public void setChangeCategoryTitleFontColor() {
        cartScreen.currentCategoryTitleFontColor = changeCategoryTitleFontColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentCategoryTitleFontColor = changeCategoryTitleFontColor;
    }

    private void changeCategoryTitleBgColor(int pColor) {
        categoryExample.getMask().setBackgroundColor(pColor);
        changeCategoryTitleBgColor = pColor;
    }

    public void setChangeCategoryTitleBgColor() {
        cartScreen.currentCategoryTitleBgColor = changeCategoryTitleBgColor;
        ((MainActivity)AppManager.getInstance().getActivity()).currentCategoryTitleBgColor = changeCategoryTitleBgColor;
    }

    private void changeDefaultColors() {
        changeBgColor(DEFAULT_COLORS[0]);
        changeFontColor(DEFAULT_COLORS[1]);
        changeFontInsertColor(DEFAULT_COLORS[2]);
        changeTitlesFontColor(DEFAULT_COLORS[3]);
        changeMessageFontColor(DEFAULT_COLORS[4]);
        changeProductAddedFontColor(DEFAULT_COLORS[5]);
        changeProductCanceledFontColor(DEFAULT_COLORS[6]);
        changeProductScratchedFontColor(DEFAULT_COLORS[7]);
        changeCategoryTitleFontColor(DEFAULT_COLORS[8]);
        changeCategoryTitleBgColor(DEFAULT_COLORS[9]);
    }

    private void confirmChanges() {
        setChangeBgColor();
        setChangeFontColor();
        setChangeFontInsertColor();
        setChangeTitlesFontColor();
        setChangeMessageFontColor();
        setChangeMessageFontColor();
        setChangeProductAddedFontColor();
        setChangeProductCanceledFontColor();
        setChangeProductScratchedFontColor();
        setChangeCategoryTitleFontColor();
        setChangeCategoryTitleBgColor();

        Toast.makeText(context, context.getString(R.string.person_complete), Toast.LENGTH_LONG).show();

        DataHandler dataHandler = ((MainActivity)AppManager.getInstance().getActivity()).dataHandler;
        dataHandler.deleteConfigData();
        dataHandler.addConfigData(  changeBgColor,
                                    changeFontColor,
                                    changeFontInsertColor,
                                    changeTitlesFontColor,
                                    changeMessageFontColor,
                                    changeProductAddedFontColor,
                                    changeProductCanceledFontColor,
                                    changeProductScratchedFontColor,
                                    changeCategoryTitleFontColor,
                                    changeCategoryTitleBgColor);
    }

    private void cancelChanges() {
        changeBgColor(cartScreen.currentBgColor);
        changeFontColor(cartScreen.currentFontColor);
        changeFontInsertColor(cartScreen.currentFontInsertColor);
        changeTitlesFontColor(cartScreen.currentTitlesFontColor);
        changeMessageFontColor(cartScreen.currentMessageFontColor);
        changeProductAddedFontColor(cartScreen.currentProductAddedFontColor);
        changeProductCanceledFontColor(cartScreen.currentProductCanceledFontColor);
        changeProductScratchedFontColor(cartScreen.currentProductScratchedFontColor);
        changeCategoryTitleFontColor(cartScreen.currentCategoryTitleFontColor);
        changeCategoryTitleBgColor(cartScreen.currentCategoryTitleBgColor);

        Toast.makeText(context, context.getString(R.string.person_cancel), Toast.LENGTH_LONG).show();
    }

    private void changeBgResource() {
        if (lastConfigBtn != null) lastConfigBtn.setBackgroundColor(DEFAULT_COLORS[10]);
    }

    private void removeBg(View pView) {
        pView.setBackgroundColor(Color.TRANSPARENT);
    }


}