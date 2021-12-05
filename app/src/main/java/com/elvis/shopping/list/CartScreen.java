package com.elvis.shopping.list;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.elvis.shopping.list.data.DataHandler;
import com.elvis.shopping.list.impl.IActivity;
import com.elvis.shopping.list.manager.AppManager;
import com.elvis.shopping.list.model.Category;
import com.elvis.shopping.list.model.ListModel;
import com.elvis.shopping.list.model.Product;
import com.elvis.shopping.list.model.ProductTableRow;
import com.elvis.shopping.list.utils.CalculeUtils;
import com.elvis.shopping.list.utils.CustomDialog;
import com.elvis.shopping.list.utils.LOG;
import com.elvis.shopping.list.utils.Utils;
import com.elvis.shopping.list.view.MyButton;
import com.elvis.shopping.list.view.MyEditText;
import com.elvis.shopping.list.view.MyTextView;

import java.util.ArrayList;
import java.util.Collections;

public class CartScreen implements IActivity {

    private MainActivity mainActivity;
    private static final String TAG = CartScreen.class.getSimpleName();

    private String TOTAL_PRODUCTS_TEXT;
    private String CART_EMPTY_MESSAGE;
    private String EDITING;
    private String INSERTING;

    private Context context;

    private int cartSize = 0;
    private int productNumber = 1;
//    public float textSize;
//    public float subTextSize;
    private MyButton deleteBtn, addProductBtn, confirmBtn, scratchBtn, destroyBtn, backBtn;
    private View currentViewFocused, scrollView;
    public MyEditText productNameInsert, productPriceInsert, productQuantInsert, productCategoryInsert;
    public MyTextView showCartContent, productNameText, productQuantText, productPriceText, textViewTotalText,
            totalDaCompraText, productCategoryText;
    private TableRow tbAddProduct, tbAddProductCategory, tbAddProductData, tabShowCartContent, tableRowTotal;
    public LinearLayout productsList, container, containerAddProductData;
    private RelativeLayout principal;
    private ProductTableRow productEdit;
    private ArrayList<String> productNameList = new ArrayList<>();
    public ArrayList<Category> categoriesList = new ArrayList<>();
    private ArrayList<String> categoriesNameList = new ArrayList<>();
    private Boolean softInputVisible = false;
    public Boolean layoutNormal = false;
    private DataHandler dataHandler;
    private String lastProductCategory;
    public static int [] DEFAULT_COLORS = {
            Color.argb(255,227,215,244), Color.argb(255,25,25,112),
            Color.BLACK, Color.argb(255,0,0,139), Color.argb(255,147,112,219),
            Color.argb(255,0,128,0), Color.RED, Color.argb(255,210,170,0),
            Color.BLACK, Color.argb(255,204,204,255), Color.argb(255,136,136,136),
            Color.argb(255,60,60,136)};
    public int currentBgColor, currentFontColor, currentFontInsertColor, currentTitlesFontColor, currentMessageFontColor,
            currentProductAddedFontColor, currentProductCanceledFontColor, currentProductScratchedFontColor,
            currentCategoryTitleFontColor, currentCategoryTitleBgColor;

    private Boolean contentRemoved = false;
    public Boolean showListLoaded = false;
    public String listNameLoaded = "";

    private int backControl = 0;

    public String [] units = {"Caixa","Envelope","Fardo","Garrafa","Grama","Lata","Litro","Maço",
            "Pacote","Peça", "Pote","Quilo","Rolo","Saco","Unidade","Vidro"};
    public String [] categories = {"Açougue","Alimentos","Aperitivos","Bebidas","Biscoitos","Castanhas","Cereais",
            "Condimentos","Congelados","Compotas","Cortes de boi","Cortes de frango","Cortes de porco","Diversos","Doces",
            "Enlatados","Farináceos","Fatiados","Frios","Frutas","Grãos","Higiene","Hortaliças","Lanches","Laticínios",
            "Legumes","Limpeza","Mantimentos","Massas","Matinais","Mercearia","Molhos","Óleos","Padaria","Pescados",
            "Sementes","Sobremesas","Temperos","Utilidades","Verduras","Outros"};

    public static CartScreen instance;

    public STATE currentState = STATE.CART_EMPTY;
    private String productNameToRestore, productScratchedToRestore, productCategoryToRestore, productUnitToRestore;
    private float quantToRestore, priceToRestore;
    private Category categoryToRestory;

    public enum STATE {
        CART_EMPTY,
        CART_READY,
        INSERT_PRODUCT,
        EDIT_PRODUCT,
        EDIT_LIST
    }

    public enum CONFIG {
        BACKGROUND,
        FONT,
        FONT_INSERT,
        FONT_TITLES,
        MESSAGE_FONT,
        PRODUCT_ADDED_FONT,
        PRODUCT_CANCELED_FONT,
        CATEGORY_TITLE_FONT,
        CATEGORY_TITLE_BG,
        PRODUCT_SCRACHED_FONT,
        DEFAULT
    }

    public CartScreen(Context pContext) {
        instance = this;
        context = pContext;
        mainActivity = (MainActivity) AppManager.getInstance().getActivity();

        dataHandler = mainActivity.dataHandler;
        currentBgColor = mainActivity.currentBgColor;
        currentFontColor = mainActivity.currentFontColor;
        currentFontInsertColor = mainActivity.currentFontInsertColor;
        currentTitlesFontColor = mainActivity.currentTitlesFontColor;
        currentMessageFontColor = mainActivity.currentMessageFontColor;
        currentProductAddedFontColor = mainActivity.currentProductAddedFontColor;
        currentProductCanceledFontColor = mainActivity.currentProductCanceledFontColor;
        currentProductScratchedFontColor = mainActivity.currentProductScratchedFontColor;
        currentCategoryTitleFontColor = mainActivity.currentCategoryTitleFontColor;
        currentCategoryTitleBgColor = mainActivity.currentCategoryTitleBgColor;

        TOTAL_PRODUCTS_TEXT = context.getString(R.string.total_products_text);
        CART_EMPTY_MESSAGE = context.getString(R.string.cart_empty_message);
        EDITING = context.getString(R.string.editing_text);
        INSERTING = context.getString(R.string.inserting_text);

/*        int screenSize = context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                textSize = 20f;
                subTextSize = 12f;
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                textSize = 20f;
                subTextSize = 12f;
                break;
            default:
                textSize = 16f;
                subTextSize = 12f;
                layoutNormal = true;
                break;
        }*/
    }

    @Override
    public void createView(View pView) {
        LOG.i(TAG, "CREATE VIEW");

        backControl = 0;
        currentBgColor = mainActivity.currentBgColor;
        currentFontColor = mainActivity.currentFontColor;
        currentFontInsertColor = mainActivity.currentFontInsertColor;
        currentTitlesFontColor = mainActivity.currentTitlesFontColor;
        currentMessageFontColor = mainActivity.currentMessageFontColor;
        currentProductAddedFontColor = mainActivity.currentProductAddedFontColor;
        currentProductCanceledFontColor = mainActivity.currentProductCanceledFontColor;
        currentProductScratchedFontColor = mainActivity.currentProductScratchedFontColor;
        currentCategoryTitleFontColor = mainActivity.currentCategoryTitleFontColor;
        currentCategoryTitleBgColor = mainActivity.currentCategoryTitleBgColor;
        listNameLoaded = dataHandler.getListLoaded();
        String currentListName = listNameLoaded;
        LOG.i(TAG, "List Name : " +listNameLoaded);

        tabShowCartContent = pView.findViewById(R.id.tabShowCartContent);

        tableRowTotal = pView.findViewById(R.id.tableRowTotal);

        principal = pView.findViewById(R.id.principal_cart);
        principal.setBackgroundColor(currentBgColor);

        scrollView = pView.findViewById(R.id.scrollView);

        showCartContent = pView.findViewById(R.id.showCart);
        showCartContent.setTextColor(currentMessageFontColor);
        destroyBtn = pView.findViewById(R.id.destroyBtn);
        addProductBtn = pView.findViewById(R.id.addBtn);
        deleteBtn = pView.findViewById(R.id.deleteBtn);
        scratchBtn = pView.findViewById(R.id.riskBtn);
        confirmBtn = pView.findViewById(R.id.confirmBtn);
        backBtn = pView.findViewById(R.id.backBtn);

        tbAddProductCategory = pView.findViewById(R.id.tableRowAddCategory);
        tbAddProduct = pView.findViewById(R.id.tableRowAddProduct);
        tbAddProductData = pView.findViewById(R.id.tableRowAddProductData);

        containerAddProductData = pView.findViewById(R.id.containerAddProductData);
        containerAddProductData.removeAllViews();
/*
        TableRow titlesTableRow = pView.findViewById(R.id.titlesTableRow);
        int i = 0;
        while (i < titlesTableRow.getChildCount()) {
            TextView textView = (TextView) titlesTableRow.getChildAt(i);
            textView.setTextColor(currentTitlesFontColor);
            i++;
        }
*/
        totalDaCompraText = pView.findViewById(R.id.totalDaCompraText);
        totalDaCompraText.setTextColor(currentTitlesFontColor);
        totalDaCompraText.setText("0,00");

        textViewTotalText = pView.findViewById(R.id.textViewTotalText);
        textViewTotalText.setTextColor(currentTitlesFontColor);

//        container = pView.findViewById(R.id.container);
        productsList = pView.findViewById(R.id.products_list);
        showCartContent.setText(CART_EMPTY_MESSAGE);

        destroyBtn.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMmotionEvent) {
                if (pMmotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    CustomDialog customDialog = new CustomDialog(pView.getContext(), "Rasgar Lista",
                            "Quer realmente rasgar esta lista?\n\nCertifique-se de salvar a lista para poder carregá-la quando precisar.",
                            CustomDialog.TYPE_CLEAR);
                    customDialog.show();
                }
                return pView.performClick();
            }
        });

        deleteBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if (pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    AppManager.getInstance().closeSoftKeyboard();
                    CustomDialog customDialog = new CustomDialog(pView.getContext(), "Remover Produto",
                            "Remover " + productEdit.getProductName() + "?", CustomDialog.TYPE_REMOVE_PRODUCT);
                    customDialog.currentView = currentViewFocused;
                    customDialog.show();
                }
                return pView.performClick();
            }
        });

        addProductBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if (pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (currentState.equals(STATE.CART_EMPTY) || currentState.equals(STATE.CART_READY)) {
                        showInsertSceneButtons();
                        openProductInsert();
                        AppManager.getInstance().openSoftKeyboard();
                    }
                }
                return pView.performClick();
            }
        });

        confirmBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if (pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (currentState.equals(STATE.INSERT_PRODUCT)) {
                        if(contentRemoved) {
                            tabShowCartContent.addView(showCartContent);
                            tableRowTotal.addView(textViewTotalText);
                            tableRowTotal.addView(totalDaCompraText);
                            contentRemoved = false;
                        }
                        AppManager.getInstance().closeSoftKeyboard();
                        softInputVisible = false;
                        insertProduct();
                        updateTotaldaCompra();
                    } else if (currentState.equals(STATE.EDIT_PRODUCT)) {
                        if(contentRemoved) {
                            tabShowCartContent.addView(showCartContent);
                            tableRowTotal.addView(textViewTotalText);
                            tableRowTotal.addView(totalDaCompraText);
                            contentRemoved = false;
                        }
                        currentState = STATE.CART_READY;
                        AppManager.getInstance().closeSoftKeyboard();
                        updateProductList();
                        exitEditProduct();
                        updateTotaldaCompra();
                    }
                    showInitialSceneButtons();
                }
                return pView.performClick();
            }
        });

        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    restoreData();
                }
                return v.performClick();
            }
        });

        scratchBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pMotionEvent) {
                if (pMotionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (currentState.equals(STATE.EDIT_PRODUCT)) {
                        if (!productEdit.isScratched()) {
                            productEdit.setScratchedBackground();
                            scratchProduct(productEdit.getProduct());
                        } else {
                            productEdit.removeScratchedBackground();
                            removeScratch(productEdit.getProduct());
                        }
                        updateTotaldaCompra();
                        AppManager.getInstance().closeSoftKeyboard();
                        showInitialSceneButtons();
                        exitEditProduct();
                        currentState = STATE.CART_READY;
                    }
                }
                return pView.performClick();
            }
        });
        if (!showListLoaded) {
            loadProductsList();
        } else {
            loadList(currentListName);
        }
        showInitialSceneButtons();
    }

    private void showAddCategoryDialog () {
        CustomDialog customDialog = new CustomDialog(context, "CATEGORIAS", "", CustomDialog.TYPE_ADD_CATEGORY);
        customDialog.setCategoriesList(categories, productCategoryInsert);
        customDialog.currentView = currentViewFocused;
        customDialog.currentCategory = productCategoryInsert.getText().toString();
        customDialog.show();
        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                AppManager.getInstance().openSoftKeyboard();
                productNameInsert.requestFocus();
            }
        });
    }

    public void destroyList() {
        productNameList.clear();
        productsList.removeAllViews();
        updateTotaldaCompra();
        showCartContent.setText(CART_EMPTY_MESSAGE);
        showInitialSceneButtons();
        cartSize = 0;

        showListLoaded = false;

        dataHandler.deleteList();
        dataHandler.deleteListLoaded();
        String nameList = dataHandler.getListLoaded();

        mainActivity.currentListName = nameList;
        mainActivity.mTitle = nameList;
        mainActivity.restoreActionBar();
    }

    public void resetPrice() {
        if(showCartContent.getText().equals(CART_EMPTY_MESSAGE)) {
            Toast.makeText(context, CART_EMPTY_MESSAGE, Toast.LENGTH_SHORT).show();
        } else {
            float totalDaCompra = 0;
            int i = 0;
            ProductTableRow productTableRow;
            Category category;
            while (i < productsList.getChildCount()) {
                category = (Category) productsList.getChildAt(i);
                int j = 0;
                while (j < category.getChildCount()) {
                    if (j > 0) {
                        productTableRow = (ProductTableRow) category.getChildAt(j);
                        productTableRow.computed = true;
                        productTableRow.updateData(productTableRow.getProductName(), productTableRow.getProductQuant(),
                                "0", productTableRow.getProductUnit());
                        dataHandler.updateProduct(productTableRow.getProduct(), productTableRow.getProductName(),
                                productTableRow.getProductQuant(), productTableRow.getProductPrice(),
                                productTableRow.getProduct().getScratched(), productTableRow.getProductCategory(),
                                productTableRow.getProductUnit());
                    }
                    j++;
                }
                i++;
            }

            String totalText = CalculeUtils.moneyFormat(Float.valueOf(String.valueOf(totalDaCompra)));
            totalDaCompraText.setText(totalText);

            Toast.makeText(context, "Preços foram zerados", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetQuant() {
        if(showCartContent.getText().equals(CART_EMPTY_MESSAGE)) {
            Toast.makeText(context, CART_EMPTY_MESSAGE, Toast.LENGTH_SHORT).show();
        } else {
            float totalDaCompra = 0;
            int i = 0;
            ProductTableRow productTableRow;
            Category category;
            while (i < productsList.getChildCount()) {
                category = (Category) productsList.getChildAt(i);
                int j = 0;
                while (j < category.getChildCount()) {
                    if (j > 0) {
                        productTableRow = (ProductTableRow) category.getChildAt(j);
                        productTableRow.computed = true;
                        productTableRow.updateData(productTableRow.getProductName(), "0",
                                productTableRow.getProductPrice(), productTableRow.getProductUnit());
                        dataHandler.updateProduct(productTableRow.getProduct(), productTableRow.getProductName(),
                                productTableRow.getProductQuant(), productTableRow.getProductPrice(),
                                productTableRow.getProduct().getScratched(), productTableRow.getProductCategory(),
                                productTableRow.getProductUnit());
                    }
                    j++;
                }
                i++;
            }

            String totalText = CalculeUtils.moneyFormat(Float.valueOf(String.valueOf(totalDaCompra)));
            totalDaCompraText.setText(totalText);

            Toast.makeText(context, "Quantidades foram zeradas", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetAll() {
        if(showCartContent.getText().equals(CART_EMPTY_MESSAGE)) {
            Toast.makeText(context, CART_EMPTY_MESSAGE, Toast.LENGTH_SHORT).show();
        } else {
            float totalDaCompra = 0;
            int i = 0;
            ProductTableRow productTableRow;
            Category category;
            while (i < productsList.getChildCount()) {
                category = (Category) productsList.getChildAt(i);
                int j = 0;
                while (j < category.getChildCount()) {
                    if (j > 0) {
                        productTableRow = (ProductTableRow) category.getChildAt(j);
                        productTableRow.computed = true;
                        productTableRow.updateData(productTableRow.getProductName(),
                                "0", "0", productTableRow.getProductUnit());
                        dataHandler.updateProduct(productTableRow.getProduct(), productTableRow.getProductName(),
                                productTableRow.getProductQuant(), productTableRow.getProductPrice(),
                                productTableRow.getProduct().getScratched(), productTableRow.getProductCategory(),
                                productTableRow.getProductUnit());
                    }
                    j++;
                }
                i++;
            }

            String totalText = CalculeUtils.moneyFormat(Float.valueOf(String.valueOf(totalDaCompra)));
            totalDaCompraText.setText(totalText);

            Toast.makeText(context, "Lista zerada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
        if(currentState.equals(STATE.EDIT_PRODUCT)) {
            restoreProduct();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LOG.i(TAG, String.valueOf(keyCode));

        if(keyCode == KeyEvent.KEYCODE_ENDCALL || keyCode == KeyEvent.KEYCODE_ENTER) {
            if (currentState.equals(STATE.INSERT_PRODUCT)) {
                insertProduct();
                //       insertNextProduct();
            } else if (currentState.equals(STATE.EDIT_PRODUCT)) {
                if (contentRemoved) {
                    tabShowCartContent.addView(showCartContent);
                    tableRowTotal.addView(textViewTotalText);
                    tableRowTotal.addView(totalDaCompraText);
                    contentRemoved = false;
                }
                showInitialSceneButtons();
                currentState = STATE.CART_READY;
                updateProductList();
                exitEditProduct();
                updateTotaldaCompra();
            }
            AppManager.getInstance().closeSoftKeyboard();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (currentState.equals(STATE.INSERT_PRODUCT)) {
            backControl = 0;
            AppManager.getInstance().closeSoftKeyboard();
            showInitialSceneButtons();
            softInputVisible = false;
            insertProduct();
            if(contentRemoved) {
                tabShowCartContent.addView(showCartContent);
                tableRowTotal.addView(textViewTotalText);
                tableRowTotal.addView(totalDaCompraText);
                contentRemoved = false;
            }
        } else if (currentState.equals(STATE.EDIT_PRODUCT)) {
            backControl = 0;
            AppManager.getInstance().closeSoftKeyboard();
            showInitialSceneButtons();
            currentState = STATE.CART_READY;
            updateProductList();
            exitEditProduct();
            updateTotaldaCompra();
            if(contentRemoved) {
                tabShowCartContent.addView(showCartContent);
                tableRowTotal.addView(textViewTotalText);
                tableRowTotal.addView(totalDaCompraText);
                contentRemoved = false;
            }
        } else {
            if(backControl == 0) {
                Toast.makeText(context, "Pressione outra vez para sair", Toast.LENGTH_SHORT).show();
                backControl++;
            } else {
                mainActivity.showOfferWall();
            }
        }
    }

    private void scratchProduct(Product pProduct) {
        Toast.makeText(context, pProduct.getProductName() + " riscado da lista.", Toast.LENGTH_SHORT).show();

        pProduct.setScratched(Product.SCRATCHED_TRUE);
    }

    public void removeScratch(Product pProduct) {
        Toast.makeText(context, pProduct.getProductName() + " voltou pra lista.", Toast.LENGTH_SHORT).show();

        pProduct.setScratched(Product.SCRATCHED_FALSE);
    }

    private void loadProductsList() {
        LOG.i(TAG, "LOAD PRODUCTS LIST");
        cartSize = 0;

        Cursor cursor = dataHandler.returnData();
        cursor.moveToFirst();
        currentState = cursor.getCount() >= 1 ? STATE.CART_READY : STATE.CART_EMPTY;
        Category category;
        while (!cursor.isAfterLast()) {
            Product product = dataHandler.parseProduct(cursor);
            ProductTableRow productTableRow = new ProductTableRow(context, product.getProductName(),
                    product.getProductQuant(), product.getProductPrice(),
                    product.getProductCategory(), product.getProductUnit());
            productTableRow.setProduct(product);
            if(product.getScratched().equals(Product.SCRATCHED_TRUE)) {
                productTableRow.setScratchedBackground();
            }
            if(categoriesList.isEmpty()) {
                LOG.i(TAG, "Categories List is empty");
                category = new Category(context, productTableRow.getProductCategory());
                category.addView(productTableRow);
                productsList.addView(category);
                categoriesList.add(category);
            } else {
                LOG.i(TAG, "Categories List not empty");
                int i = 0;
                int j = 0;
                while(i < productsList.getChildCount()) {
                    category = (Category) productsList.getChildAt(i);
                    if(productTableRow.getProductCategory().equals(category.getCategoryName())) {
                        if(productTableRow.getParent() != null) {
                            ((ViewGroup) productTableRow.getParent()).removeView(productTableRow);
                        }
                        category.addView(productTableRow);
                        j++;
                    }
                    i++;
                }
                if(j == 0) {
                    category = new Category(context, productTableRow.getProductCategory());
                    category.addView(productTableRow);
                    productsList.addView(category);
                    categoriesList.add(category);
                }
            }
            productNameList.add(product.getProductName());
            cartSize++;
            productNumber++;
            cursor.moveToNext();
        }
        if (cartSize >= 1) {
            LOG.i(TAG, "Cart Size : " + String.valueOf(cartSize));
            String text = TOTAL_PRODUCTS_TEXT + String.valueOf(cartSize);
            showCartContent.setText(text);
            sortProducts(null);
            updateTotaldaCompra();
        } else {
            LOG.i(TAG, "Cart is empty");
            showCartContent.setText(CART_EMPTY_MESSAGE);
        }
        cursor.close();
        categoriesList.clear();
    }

    public void loadList(String pListName) {
        LOG.i(TAG, "LOAD LIST");
        dataHandler.deleteList();
        listNameLoaded = pListName;
        showListLoaded = true;
        categoriesList.clear();
        productsList.removeAllViews();
        productNameList.clear();
        productNumber = 0;
        cartSize = 0;

        Cursor cursor = dataHandler.returnSaveListData();
        cursor.moveToFirst();
        currentState = cursor.getCount() >= 1 ? STATE.CART_READY : STATE.CART_EMPTY;
        Category category;
        while (!cursor.isAfterLast()) {
            ListModel listModel = dataHandler.parseListModel(cursor);
            if (listModel.getListName().equals(mainActivity.currentListName)) {
                Product product = new Product();
                product.setProductName(listModel.getProductName());
                product.setProductQuant(listModel.getProductQuant());
                product.setProductPrice(listModel.getProductPrice());
                product.setScratched(listModel.getProductScratched());
                product.setProductCategory(listModel.getProductCategory());
                product.setProductUnit(listModel.getProductUnit());

                dataHandler.addProduct(product.getProductName(), product.getProductQuant(), product.getProductPrice(),
                        product.getScratched(), product.getProductCategory(), product.getProductUnit());

                ProductTableRow productTableRow = new ProductTableRow(context, product.getProductName(),
                        product.getProductQuant(), product.getProductPrice(),
                        product.getProductCategory(), product.getProductUnit());
                productTableRow.setProduct(product);
                if (product.getScratched().equals(Product.SCRATCHED_TRUE)) {
                    productTableRow.setScratchedBackground();
                }
                if (categoriesList.isEmpty()) {
                    category = new Category(context, productTableRow.getProductCategory());
                    category.addView(productTableRow);
                    productsList.addView(category);
                    categoriesList.add(category);
                } else {
                    int i = 0;
                    int j = 0;
                    while (i < productsList.getChildCount()) {
                        category = (Category) productsList.getChildAt(i);
                        if (productTableRow.getProductCategory().equals(category.getCategoryName())) {
                            category.addView(productTableRow);
                            j++;
                        }
                        i++;
                    }
                    if (j == 0) {
                        category = new Category(context, productTableRow.getProductCategory());
                        category.addView(productTableRow);
                        productsList.addView(category);
                        categoriesList.add(category);
                    }
                }
                productNameList.add(product.getProductName());
                cartSize++;
                productNumber++;
            }
            cursor.moveToNext();
        }
        cursor.close();
        if (cartSize >= 1) {
            String text = TOTAL_PRODUCTS_TEXT + String.valueOf(cartSize);
            showCartContent.setText(text);
            sortProducts(null);
            updateTotaldaCompra();
        } else {
            showCartContent.setText(CART_EMPTY_MESSAGE);
        }
        dataHandler.deleteListLoaded();
        dataHandler.addListLoaded(pListName);
    }

    public void saveList(String pListName) {
        dataHandler.deleteList();
        int categoriesCount = productsList.getChildCount();
        int productsTotal = 0;
        int i;
        if(pListName.equals(mainActivity.currentListName)) {
            Cursor cursor = dataHandler.returnSaveListData();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ListModel listModel = dataHandler.parseListModel(cursor);
                if(listModel.getListName().equals(pListName)) {
                    dataHandler.deleteListModel(listModel);
                }
                cursor.moveToNext();
            }
            cursor.close();
            i = 0;
            while (i < categoriesCount) {
                Category category = (Category) productsList.getChildAt(i);
                int categoryChilds = category.getChildCount();
                int j = 0;
                while (j < categoryChilds) {
                    if (j > 0) {
                        ProductTableRow productTableRow = (ProductTableRow) category.getChildAt(j);
                        String productScratched = productTableRow.isScratched() ? Product.SCRATCHED_TRUE : Product.SCRATCHED_FALSE;
                        dataHandler.addProduct(productTableRow.getProductName(), productTableRow.getProductQuant(),
                                productTableRow.getProductPrice(), productScratched, productTableRow.getProductCategory(),
                                productTableRow.getProductUnit());
                        dataHandler.addList(pListName, productTableRow.getProductName(), productTableRow.getProductQuant(),
                                productTableRow.getProductPrice(), productScratched, productTableRow.getProductCategory(),
                                productTableRow.getProductUnit());
                        productsTotal++;
                    }
                    j++;
                }
                i++;
            }
        } else {
            dataHandler.deleteListLoaded();
            dataHandler.addListLoaded(pListName);
            dataHandler.addListName(pListName);
            i = 0;
            while (i < categoriesCount) {
                Category category = (Category) productsList.getChildAt(i);
                int categoryChilds = category.getChildCount();
                int j = 0;
                while (j < categoryChilds) {
                    if (j > 0) {
                        ProductTableRow productTableRow = (ProductTableRow) category.getChildAt(j);
                        String productScratched = productTableRow.isScratched() ? Product.SCRATCHED_TRUE : Product.SCRATCHED_FALSE;
                        dataHandler.addProduct(productTableRow.getProductName(), productTableRow.getProductQuant(),
                                productTableRow.getProductPrice(), productScratched, productTableRow.getProductCategory(),
                                productTableRow.getProductUnit());
                        dataHandler.addList(pListName, productTableRow.getProductName(), productTableRow.getProductQuant(),
                                productTableRow.getProductPrice(), productScratched, productTableRow.getProductCategory(),
                                productTableRow.getProductUnit());
                        productsTotal++;
                    }
                    j++;
                }
                i++;
            }
        }

        mainActivity.currentListName = pListName;
        mainActivity.mTitle = pListName;
        mainActivity.restoreActionBar();

        Toast.makeText(context, "Lista " + pListName + " salva.\nTotal de itens: " + String.valueOf(productsTotal),
                Toast.LENGTH_LONG).show();
    }

    public void restoreSoftFocus(View pView) {
 //       AppManager.getInstance().openSoftKeyboard();
//        productQuantInsert.setPressed(true);
//        productQuantInsert.setFocusableInTouchMode(true);
//        productQuantInsert.setFocusable(true);
//        productQuantInsert.requestFocus();
//        currentViewFocused = productQuantInsert;
//
//        showSoftKeyboard(productQuantInsert);//showSoftKeyboard(currentViewFocused);

//        pView.setPressed(true);
//        pView.setFocusableInTouchMode(true);
//        pView.setFocusable(true);
        pView.requestFocus();

        currentViewFocused = pView;

//        showSoftKeyboard(currentViewFocused);
    }

    public void deleteProduct(Product pProduct) {
        dataHandler.deleteProduct(pProduct);
    }
/*
    private Product parseProduct(Cursor pCursor) {
        Product product = new Product();
        product.setID((pCursor.getInt(0)));
        product.setProductName(pCursor.getString(1));
        product.setProductQuant(pCursor.getString(2));
        product.setProductPrice(pCursor.getString(3));
        product.setScratched(pCursor.getString(4));
        product.setProductCategory(pCursor.getString(5));
        product.setProductUnit(pCursor.getString(6));
        return product;
    }
*/
    public void showInitialSceneButtons() {
        deleteBtn.setVisibility(View.INVISIBLE);
        confirmBtn.setVisibility(View.INVISIBLE);
        backBtn.setVisibility(View.INVISIBLE);
        scratchBtn.setVisibility(View.INVISIBLE);
        addProductBtn.setVisibility(View.VISIBLE);
        if(productsList.getChildCount() >= 1) {
            destroyBtn.setVisibility(View.VISIBLE);
        } else {
            destroyBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void showInsertSceneButtons() {
        deleteBtn.setVisibility(View.INVISIBLE);
        confirmBtn.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.VISIBLE);
        scratchBtn.setVisibility(View.INVISIBLE);
        addProductBtn.setVisibility(View.INVISIBLE);
        destroyBtn.setVisibility(View.INVISIBLE);
    }

    public void showEditSceneButtons() {
        deleteBtn.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.VISIBLE);
        scratchBtn.setVisibility(View.VISIBLE);
        addProductBtn.setVisibility(View.INVISIBLE);
        destroyBtn.setVisibility(View.INVISIBLE);
    }

    public void insertNextProduct() {

//        AppManager.getInstance().openSoftKeyboard();

        productNameInsert.setText("");
//        productNameInsert.setPressed(true);
//        productNameInsert.setFocusableInTouchMode(true);
//        productNameInsert.setFocusable(true);
//        productNameInsert.requestFocus();
        currentViewFocused = productNameInsert;//showSoftKeyboard(productNameInsert);

        productQuantInsert.setText("");
//        productQuantInsert.setFocusable(false);

        productPriceInsert.setText("");
//        productPriceInsert.setFocusable(false);
    }

    public void openProductInsert() {
        currentState = STATE.INSERT_PRODUCT;
        AppManager.getInstance().openSoftKeyboard();

        if(containerAddProductData.getChildCount() > 0) containerAddProductData.removeAllViews();

        containerAddProductData.addView(tbAddProductCategory);
        containerAddProductData.addView(tbAddProduct);
        containerAddProductData.addView(tbAddProductData);

        Utils.setAnimation(containerAddProductData, R.anim.move_up_to_down);

        /*if(layoutNormal) {
            tabShowCartContent.removeView(showCartContent);
            tableRowTotal.removeAllViews();
            contentRemoved = true;
        } else {*/
            showCartContent.setText(INSERTING);
        //}

        softInputVisible = true;

        // INIT CONTAINER ITEM CREATE
        productCategoryText = new MyTextView(context);
        tbAddProductCategory.addView(productCategoryText);
        productCategoryText.setBackgroundResource(R.drawable.quick_contact_badge_overlay);
        productCategoryText.setText("Categoria: ");
        productCategoryText.setPadding(8,8,8,8);
        productCategoryText.setTextColor(currentFontColor);
        productCategoryText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        productCategoryText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    AppManager.getInstance().closeSoftKeyboard();
                    showAddCategoryDialog();
                }
                return v.performClick();
            }
        });

        productCategoryInsert = new MyEditText(context);
        tbAddProductCategory.addView(productCategoryInsert);
        /*if(layoutNormal) {
            productCategoryInsert.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        } else {*/
            productCategoryInsert.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
      //  }
        productCategoryInsert.setText("");
        productCategoryInsert.setHint("Categoria");
        productCategoryInsert.setPadding(8,8,8,8);
        productCategoryInsert.setHintTextColor(context.getResources().getColor(R.color.fab_material_grey_500));
        productCategoryInsert.setTextColor(currentFontInsertColor);
        productCategoryInsert.setBackgroundColor(currentBgColor);
        productCategoryInsert.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productCategoryInsert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.i(TAG, String.valueOf(actionId));
                if(actionId == EditorInfo.IME_ACTION_DONE) {
//                    AppManager.getInstance().openSoftKeyboard();
                    addItemOnCart();
                }
                return false;
            }
        });
        productCategoryInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productCategoryInsert.requestFocus();
                currentViewFocused = productCategoryInsert;
            }
        });
        productCategoryInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
/*
        final MySingleButton btn = new MySingleButton(context);
        btn.setBackgroundResource(R.drawable.btn_add_category_bg);
        tbAddProductCategory.addView(btn);
        btn.setPadding(8,8,8,8);
        btn.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    AppManager.getInstance().closeSoftKeyboard();
                    showAddCategoryDialog();
                }
                return view.performClick();
            }
        });
*/
        productNameText = new MyTextView(context);
        tbAddProduct.addView(productNameText);
        productNameText.setText("Nome: ");
        productNameText.setPadding(8,8,8,8);
        productNameText.setTextColor(currentFontColor);
        productNameText.setBackgroundColor(currentBgColor);
        productNameText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        productNameText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    currentViewFocused = productNameInsert;

//                    productNameInsert.setPressed(true);
//                    productNameInsert.setFocusableInTouchMode(true);
//                    productNameInsert.setFocusable(true);
//                    AppManager.getInstance().openSoftKeyboard();
                    productNameInsert.requestFocus();//showSoftKeyboard(productNameInsert);

//                    productQuantInsert.setFocusable(false);
//                    productPriceInsert.setFocusable(false);
                }
                return view.performClick();
            }
        });

        productNameInsert = new MyEditText(context);
        tbAddProduct.addView(productNameInsert);
        /*if(layoutNormal) {
            productNameInsert.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        } else {*/
            productNameInsert.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        //}
        productNameInsert.setText("");
        productNameInsert.setHint("Nome do Produto");
        productNameInsert.setPadding(8,8,8,8);
        productNameInsert.setHintTextColor(context.getResources().getColor(R.color.fab_material_grey_500));
        productNameInsert.setTextColor(currentFontInsertColor);
        productNameInsert.setBackgroundColor(currentBgColor);
        productNameInsert.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productNameInsert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.i(TAG, String.valueOf(actionId));
                if(actionId == EditorInfo.IME_ACTION_DONE) {
//                    AppManager.getInstance().openSoftKeyboard();
                    addItemOnCart();
                }
                return false;
            }
        });
        productNameInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productNameInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentViewFocused = productNameInsert;

//                productNameInsert.setPressed(true);
//                productNameInsert.setFocusableInTouchMode(true);
//                productNameInsert.setFocusable(true);
//                AppManager.getInstance().openSoftKeyboard();
                productNameInsert.requestFocus();

 //               productQuantInsert.setFocusable(false);
 //               productPriceInsert.setFocusable(false);

            }
        });

/*        productNameInsert.setPressed(true);
        productNameInsert.setFocusableInTouchMode(true);
        productNameInsert.setFocusable(true);*/
        productNameInsert.requestFocus();
//        showSoftKeyboard(productNameInsert);
        currentViewFocused = productNameInsert;

        productQuantText = new MyTextView(context);
        tbAddProductData.addView(productQuantText);
        productQuantText.setBackgroundResource(R.drawable.quick_contact_badge_overlay);
        productQuantText.setText("Unidade");
        productQuantText.setPadding(8,8,8,8);
        productQuantText.setTextColor(currentFontColor);
        productQuantText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    CustomDialog customDialog = new CustomDialog(context, "Unidades", "", CustomDialog.TYPE_ADD_UNIT_ON_CART);
                    customDialog.setUnitList(units);
                    customDialog.currentUnit = ((MyTextView) v).getText().toString();
                    customDialog.show();
                    customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            AppManager.getInstance().openSoftKeyboard();
                            productNameInsert.requestFocus();
                        }
                    });
                }
                return v.performClick();
            }
        });
        productQuantText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        productQuantInsert = new MyEditText(context);
        tbAddProductData.addView(productQuantInsert);
        productQuantInsert.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        productQuantInsert.setHint("0.00");
        productQuantInsert.setHintTextColor(context.getResources().getColor(R.color.fab_material_grey_500));
        productQuantInsert.setPadding(8,8,8,8);
        productQuantInsert.setGravity(Gravity.END);
        productQuantInsert.setTextColor(currentFontInsertColor);
        productQuantInsert.setBackgroundColor(currentBgColor);
        productQuantInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productQuantInsert.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productQuantInsert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.i(TAG, String.valueOf(actionId));
                if(actionId == EditorInfo.IME_ACTION_DONE) {
//                    AppManager.getInstance().openSoftKeyboard();
                    addItemOnCart();
                }
                return false;
            }
        });
        productQuantInsert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    productQuantInsert.setText("");
                    currentViewFocused = productQuantInsert;

//                    productQuantInsert.setPressed(true);
//                    productQuantInsert.setFocusableInTouchMode(true);
//                    productQuantInsert.setFocusable(true);
         //           AppManager.getInstance().openSoftKeyboard();
                    productQuantInsert.requestFocus();

//                    productPriceInsert.setFocusable(false);
                }
                return view.performClick();
            }
        });

        productPriceText = new MyTextView(context);
        tbAddProductData.addView(productPriceText);
        productPriceText.setText("   Preço: ");
        productPriceText.setPadding(8,8,8,8);
        productPriceText.setTextColor(currentFontColor);
        productPriceText.setBackgroundColor(currentBgColor);
        productPriceText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        productPriceText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    productPriceInsert.setText("");
                    currentViewFocused = productPriceInsert;

//                    productPriceInsert.setPressed(true);
//                    productPriceInsert.setFocusableInTouchMode(true);
//                    productPriceInsert.setFocusable(true);
//                    AppManager.getInstance().openSoftKeyboard();
                    productPriceInsert.requestFocus();

//                    productQuantInsert.setFocusable(false);
                }
                return v.performClick();
            }
        });

        productPriceInsert = new MyEditText(context);
        tbAddProductData.addView(productPriceInsert);
        productPriceInsert.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        productPriceInsert.setHint("0.00");
        productPriceInsert.setHintTextColor(context.getResources().getColor(R.color.fab_material_grey_500));
        productPriceInsert.setPadding(8,8,8,8);
        productPriceInsert.setGravity(Gravity.END);
        productPriceInsert.setTextColor(currentFontInsertColor);
        productPriceInsert.setBackgroundColor(currentBgColor);
        productPriceInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productPriceInsert.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productPriceInsert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.i(TAG, String.valueOf(actionId));
                if(actionId == EditorInfo.IME_ACTION_DONE) {
//                    AppManager.getInstance().openSoftKeyboard();
                    addItemOnCart();
                }
                return false;
            }
        });
        productPriceInsert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    productPriceInsert.setText("");
                    currentViewFocused = productPriceInsert;

 //                   productPriceInsert.setPressed(true);
//                    productPriceInsert.setFocusableInTouchMode(true);
//                    productPriceInsert.setFocusable(true);
//                    AppManager.getInstance().openSoftKeyboard();
                    productPriceInsert.requestFocus();

//                    productQuantInsert.setFocusable(false);
                }
                return view.performClick();
            }
        });

//        showSoftKeyboard(productNameInsert);
        // END CONTAINER INIT CREATE
    }

    public void openProductEdit(ProductTableRow pProductEdit, String pProductName, String pProductQuant,
                                String pProductPrice, String pProductCategory, String pProductUnit) {
        currentState = STATE.EDIT_PRODUCT;

        AppManager.getInstance().openSoftKeyboard();

        productNameToRestore = pProductName;
        productUnitToRestore = pProductUnit;
        priceToRestore = pProductPrice.equals("") ? 0 : Float.valueOf(pProductPrice);
        quantToRestore = pProductQuant.equals("") ? 0 : Float.valueOf(pProductQuant);
        productCategoryToRestore = pProductCategory;
        productScratchedToRestore = String.valueOf(pProductEdit.isScratched());
        categoryToRestory = (Category) pProductEdit.getParent();

        if(containerAddProductData.getChildCount() > 0) containerAddProductData.removeAllViews();

        containerAddProductData.addView(tbAddProductCategory);
        containerAddProductData.addView(tbAddProduct);
        containerAddProductData.addView(tbAddProductData);

        containerAddProductData.startAnimation(AnimationUtils.loadAnimation(context, R.anim.move_up_to_down));

        pProductEdit.computed = false;

        productEdit = pProductEdit;
        productNameList.remove(pProductName);

        deleteProduct(productEdit.getProduct());

        /*if(layoutNormal) {
            tabShowCartContent.removeView(showCartContent);
            tableRowTotal.removeAllViews();
            contentRemoved = true;
        } else {*/
            String text = EDITING + pProductName;
            showCartContent.setText(text);
        //}

        lastProductCategory = pProductCategory;

        productCategoryText = new MyTextView(context);
        tbAddProductCategory.addView(productCategoryText);
        productCategoryText.setBackgroundResource(R.drawable.quick_contact_badge_overlay);
        productCategoryText.setText("Categoria: ");
        productCategoryText.setTextColor(currentFontColor);
        productCategoryText.setPadding(8,8,8,8);
        productCategoryText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        productCategoryText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    AppManager.getInstance().closeSoftKeyboard();
                    showAddCategoryDialog();
                }
                return v.performClick();
            }
        });

        productCategoryInsert = new MyEditText(context);
        tbAddProductCategory.addView(productCategoryInsert);
        /*if(layoutNormal) {
            productCategoryInsert.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        } else {*/
            productCategoryInsert.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        //}
        productCategoryInsert.setHint("Categoria");
        productCategoryInsert.setPadding(8,8,8,8);
        productCategoryInsert.setHintTextColor(context.getResources().getColor(R.color.fab_material_grey_500));
        productCategoryInsert.setText(pProductCategory);
        productCategoryInsert.setTextColor(currentFontInsertColor);
        productCategoryInsert.setBackgroundColor(currentBgColor);
        productCategoryInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        productCategoryInsert.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productCategoryInsert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.i(TAG, String.valueOf(actionId));
                if(actionId == EditorInfo.IME_ACTION_DONE) {
//                    AppManager.getInstance().openSoftKeyboard();
                    addItemOnCart();
                }
                return false;
            }
        });
        productCategoryInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentViewFocused = productCategoryInsert;
                productCategoryInsert.requestFocus();
            }
        });

        productNameText = new MyTextView(context);
        tbAddProduct.addView(productNameText);
        productNameText.setText("Nome: ");
        productNameText.setPadding(8,8,8,8);
        productNameText.setTextColor(currentFontColor);
        productNameText.setBackgroundColor(currentBgColor);
        productNameText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        productNameText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    currentViewFocused = productNameInsert;

//                    productNameInsert.setPressed(true);
//                    productNameInsert.setFocusableInTouchMode(true);
//                    productNameInsert.setFocusable(true);
                    productNameInsert.requestFocus();

//                    productQuantInsert.setFocusable(false);
//                    productPriceInsert.setFocusable(false);
                }
                return view.performClick();
            }
        });
/*
        final MySingleButton btn = new MySingleButton(context);
        btn.setBackgroundResource(R.drawable.btn_add_category_bg);
        tbAddProductCategory.addView(btn);
        btn.setPadding(8,8,8,8);
        btn.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    AppManager.getInstance().closeSoftKeyboard();
                    showAddCategoryDialog();
                }
                return view.performClick();
            }
        });
*/
        productNameInsert = new MyEditText(context);
        tbAddProduct.addView(productNameInsert);
        /*if(layoutNormal) {
            productNameInsert.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        } else {*/
            productNameInsert.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        //}
        productNameInsert.setHint("Nome do Produto");
        productNameInsert.setPadding(8,8,8,8);
        productNameInsert.setHintTextColor(context.getResources().getColor(R.color.fab_material_grey_500));
        productNameInsert.setText(pProductName);
        productNameInsert.setTextColor(currentFontInsertColor);
        productNameInsert.setBackgroundColor(currentBgColor);
        productNameInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productNameInsert.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productNameInsert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.i(TAG, String.valueOf(actionId));
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    addItemOnCart();
                }
                return false;
            }
        });
        productNameInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentViewFocused = productNameInsert;

//                productNameInsert.setPressed(true);
//                productNameInsert.setFocusableInTouchMode(true);
//                productNameInsert.setFocusable(true);
                productNameInsert.requestFocus();

//                productQuantInsert.setFocusable(false);
//                productPriceInsert.setFocusable(false);
            }
        });

        currentViewFocused = productNameInsert;
//        productNameInsert.setPressed(true);
//        productNameInsert.setFocusableInTouchMode(true);
//        productNameInsert.setFocusable(true);
        productNameInsert.requestFocus();

//        showSoftKeyboard(productNameInsert);

        productQuantText = new MyTextView(context);
        tbAddProductData.addView(productQuantText);
        productQuantText.setBackgroundResource(R.drawable.quick_contact_badge_overlay);
        productQuantText.setText(pProductUnit);
        productQuantText.setPadding(8,8,8,8);
        productQuantText.setTextColor(currentFontColor);
        productQuantText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().closeSoftKeyboard();
                CustomDialog customDialog = new CustomDialog(context, "Unidades", "", CustomDialog.TYPE_ADD_UNIT_ON_CART);
                customDialog.setUnitList(units);
                customDialog.currentUnit = ((TextView) view).getText().toString();
                customDialog.show();
                customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
 //                       AppManager.getInstance().openSoftKeyboard();
                        productNameInsert.requestFocus();
                    }
                });
            }
        });
        productQuantText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        productQuantInsert = new MyEditText(context);
        tbAddProductData.addView(productQuantInsert);
        productQuantInsert.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        productQuantInsert.setHint("0.00");
        productQuantInsert.setHintTextColor(context.getResources().getColor(R.color.fab_material_grey_500));
        productQuantInsert.setText(pProductQuant);
        productQuantInsert.setPadding(8,8,8,8);
        productQuantInsert.setGravity(Gravity.END);
        productQuantInsert.setTextColor(currentFontInsertColor);
        productQuantInsert.setBackgroundColor(currentBgColor);
        productQuantInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productQuantInsert.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productQuantInsert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.i(TAG, String.valueOf(actionId));
                if(actionId == EditorInfo.IME_ACTION_DONE) {
//                    AppManager.getInstance().openSoftKeyboard();
                    addItemOnCart();
                }
                return false;
            }
        });
        productQuantInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentViewFocused = productQuantInsert;
                productQuantInsert.requestFocus();
            }
        });/*new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
 //                   productQuantInsert.setText("");
                    currentViewFocused = productQuantInsert;

//                    productQuantInsert.setPressed(true);
//                    productQuantInsert.setFocusableInTouchMode(true);
//                    productQuantInsert.setFocusable(true);
                    productQuantInsert.requestFocus();

//                    productPriceInsert.setFocusable(false);
                }

                return view.performClick();
            }
        }*/

        productPriceText = new MyTextView(context);
        tbAddProductData.addView(productPriceText);
        productPriceText.setText("   Preço: ");
        productPriceText.setPadding(8,8,8,8);
        productPriceText.setTextColor(currentFontColor);
        productPriceText.setBackgroundColor(currentBgColor);
        productPriceText.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        productPriceText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    productPriceInsert.setText("");
                    currentViewFocused = productPriceInsert;

//                    productPriceInsert.setPressed(true);
//                    productPriceInsert.setFocusableInTouchMode(true);
//                    productPriceInsert.setFocusable(true);
                    productPriceInsert.requestFocus();

//                    productQuantInsert.setFocusable(false);
                }
                return view.performClick();
            }
        });

        productPriceInsert = new MyEditText(context);
        tbAddProductData.addView(productPriceInsert);
        productPriceInsert.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        productPriceInsert.setHint("0.00");
        productPriceInsert.setHintTextColor(context.getResources().getColor(R.color.fab_material_grey_500));
        productPriceInsert.setText(pProductPrice);
        productPriceInsert.setPadding(8,8,8,8);
        productPriceInsert.setGravity(Gravity.END);
        productPriceInsert.setTextColor(currentFontInsertColor);
        productPriceInsert.setBackgroundColor(currentBgColor);
        productPriceInsert.setLayoutParams(new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        productPriceInsert.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productPriceInsert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.i(TAG, String.valueOf(actionId));
                if(actionId == EditorInfo.IME_ACTION_DONE) {
//                    AppManager.getInstance().openSoftKeyboard();
                    addItemOnCart();
                }
                return false;
            }
        });
        productPriceInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentViewFocused = productPriceInsert;
                productPriceInsert.requestFocus();
            }
        });/*new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    productPriceInsert.setText("");
                    currentViewFocused = productPriceInsert;

//                    productPriceInsert.setPressed(true);
//                    productPriceInsert.setFocusableInTouchMode(true);
//                    productPriceInsert.setFocusable(true);
                    productPriceInsert.requestFocus();

//                    productQuantInsert.setFocusable(false);
                }
                return view.performClick();
            }
        }*/
    }

    public void insertProduct() {
        if (!softInputVisible) {
            currentState = STATE.CART_READY;

            if (!String.valueOf(productNameInsert.getText()).equals("")) {
                cartSize++;

                String text = TOTAL_PRODUCTS_TEXT + String.valueOf(cartSize);
                showCartContent.setText(text);

                final String productName = productNameInsert.getText().toString();
                final String productQuant = productQuantInsert.getText().toString();
                final String productPrice = productPriceInsert.getText().toString();
                final String productUnit = productQuantText.getText().toString();

                Toast.makeText(context, productName + " adicionado", Toast.LENGTH_SHORT).show();

                // PRODUCT CATEGORY
                final String productCategory = productCategoryInsert.getText().toString();

                ProductTableRow productRow = new ProductTableRow(context, productName, productQuant,
                        productPrice, productCategory, productUnit);

                Category currentCategory = null;
                int i = 0;
                boolean productAdded = false;
                if (productsList.getChildCount() > 0) {
                    while (i < productsList.getChildCount()) {
                        currentCategory = (Category) productsList.getChildAt(i);
                        if (currentCategory.getCategoryName().equals(productCategory)) {
                            currentCategory.addView(productRow);
                            productAdded = true;
                            break;
                        }
                        i++;
                    }
                    if (!productAdded) {
                        currentCategory = new Category(context, productCategory);
                        currentCategory.addView(productRow);
                        productsList.addView(currentCategory);
                    }
                } else {
                    currentCategory = new Category(context, productCategory);
                    currentCategory.addView(productRow);
                    productsList.addView(currentCategory);
                }
                productRow.computed = true;
                productRow.setProduct(dataHandler.addProduct(productName, productQuant, productPrice,
                        Product.SCRATCHED_FALSE, productCategory, productUnit));

                productRow.computed = true;

                updateTotaldaCompra();

                productNumber++;

                productNameList.add(productName);

                sortProducts(currentCategory);
            }

            containerAddProductData.removeAllViews();

            exitEditProduct();

        } else {
            if (!String.valueOf(productNameInsert.getText()).equals("")) {
                cartSize++;

                final String productName = productNameInsert.getText().toString();
                final String productQuant = productQuantInsert.getText().toString();
                final String productPrice = productPriceInsert.getText().toString();
                final String productUnit = productQuantText.getText().toString();

                Toast.makeText(context, productName + " adicionado", Toast.LENGTH_SHORT).show();

                // PRODUCT CATEGORY
                final String productCategory = productCategoryInsert.getText().toString();

                ProductTableRow productRow = new ProductTableRow(context, productName, productQuant,
                        productPrice, productCategory, productUnit);
                Category currentCategory = null;
                int i = 0;
                boolean productAdded = false;
                if (productsList.getChildCount() > 0) {
                    while (i < productsList.getChildCount()) {
                        currentCategory = (Category) productsList.getChildAt(i);
                        if (currentCategory.getCategoryName().equals(productCategory)) {
                            currentCategory.addView(productRow);
                            productAdded = true;
                            break;
                        }
                        i++;
                    }
                    if (!productAdded) {
                        currentCategory = new Category(context, productCategory);
                        currentCategory.addView(productRow);
                        productsList.addView(currentCategory);
                    }
                } else {
                    currentCategory = new Category(context, productCategory);
                    currentCategory.addView(productRow);
                    productsList.addView(currentCategory);
                }
                productRow.computed = true;
                productRow.setProduct(dataHandler.addProduct(productName, productQuant, productPrice,
                        Product.SCRATCHED_FALSE, productCategory, productUnit));

                updateTotaldaCompra();

                productNumber++;

                productNameList.add(productName);

                sortProducts(currentCategory);
            }
        }
    }

    public void updateProductList() {
        final String productName = String.valueOf(productNameInsert.getText());

        final float quant = String.valueOf(productQuantInsert.getText()).equals("") ? 0 : Float.valueOf(String.valueOf(productQuantInsert.getText()));
        final float price = String.valueOf(productPriceInsert.getText()).equals("") ? 0 : Float.valueOf(String.valueOf(productPriceInsert.getText()));

        final String productQuant = String.valueOf(quant);
        final String productPrice = String.valueOf(price);
        final String productCategory = String.valueOf(productCategoryInsert.getText());
        final String productUnit = productQuantText.getText().toString();

        productEdit.updateData(productName, productQuant, productPrice, productUnit);
        productEdit.setProductCategory(productCategory);

        Category category = (Category) productEdit.getParent();
        if(!productCategory.equals(lastProductCategory)) {
            category = (Category) productEdit.getParent();
            category.removeView(productEdit);
            if(category.getChildCount() == 1) {
                productsList.removeView(category);
            }
            int i = 0;
            Boolean computed = false;
            while (i < productsList.getChildCount()) {
                category = (Category) productsList.getChildAt(i);
                if(productCategory.equals(category.getCategoryName())) {
                    category.addView(productEdit);
                    computed = true;
                    break;
                }
                i++;
            }
            if(!computed) {
                category = new Category(context, productCategory);
                category.addView(productEdit);
                productsList.addView(category);
            }
        }

        productEdit.setProduct(dataHandler.addProduct(productName, productQuant, productPrice,
                productEdit.getProduct().getScratched(), productCategory, productUnit));

        productNameList.add(productName);

        sortProducts(category);
    }

    public void changeProduct(ProductTableRow pProductEdit, String pProductName, String pProductQuant, String pProductPrice, String pProductCategory, String pProductUnit) {
        productEdit.computed = false;

        productEdit = pProductEdit;
        productNameList.remove(pProductName);

        productNameInsert.setText(pProductName);
        productQuantInsert.setText(pProductQuant);
        productPriceInsert.setText(pProductPrice);
        productCategoryInsert.setText(pProductCategory);
        productQuantText.setText(pProductUnit);
        lastProductCategory = pProductCategory;
    }

    public void updateTotaldaCompra() {
        float totalDaCompra = 0;
        int i = 0;
        ProductTableRow productTableRow;
        Category category;
        while (i < productsList.getChildCount()) {
            category = (Category) productsList.getChildAt(i);
            int j = 0;
            while (j < category.getChildCount()) {
                if(j > 0) {
                    productTableRow = (ProductTableRow) category.getChildAt(j);
                    productTableRow.computed = true;
                    if (!productTableRow.isScratched()) {
                        totalDaCompra += Float.valueOf(productTableRow.getProductTotalValue());
                    }
                }
                j++;
            }
            i++;
        }

        String totalText = CalculeUtils.moneyFormat(Float.valueOf(String.valueOf(totalDaCompra)));
        totalDaCompraText.setText(totalText);

        if (i == 0) showCartContent.setText(CART_EMPTY_MESSAGE);
    }

    public void exitEditProduct () {
        containerAddProductData.removeAllViews();
        tbAddProductCategory.removeAllViews();
        tbAddProduct.removeAllViews();
        tbAddProductData.removeAllViews();
        productCategoryInsert = null;
        productCategoryText = null;
        productNameInsert = null;
        productPriceInsert = null;
        productNameText = null;
        productQuantText = null;
        productPriceText = null;

        String text = TOTAL_PRODUCTS_TEXT + String.valueOf(cartSize);
        showCartContent.setText(text);
    }

    private void restoreData() {

        if(contentRemoved) {
            tabShowCartContent.addView(showCartContent);
            tableRowTotal.addView(textViewTotalText);
            tableRowTotal.addView(totalDaCompraText);
            contentRemoved = false;
        }
        if (currentState.equals(STATE.EDIT_PRODUCT)) {
            restoreProduct();
        }

//        hideSoftKeyboard();
        AppManager.getInstance().closeSoftKeyboard();
        showInitialSceneButtons();

        containerAddProductData.removeAllViews();
        tbAddProductCategory.removeAllViews();
        tbAddProduct.removeAllViews();
        tbAddProductData.removeAllViews();
        productCategoryInsert = null;
        productCategoryText = null;
        productNameInsert = null;
        productPriceInsert = null;
        productNameText = null;
        productQuantText = null;
        productPriceText = null;

        String text = TOTAL_PRODUCTS_TEXT + String.valueOf(cartSize);
        showCartContent.setText(text);

        currentState = STATE.CART_READY;
    }

    private void restoreProduct() {
        final String productName = productNameToRestore;

        final float quant = quantToRestore;
        final float price = priceToRestore;

        final String productScratched = productScratchedToRestore;
        final String productQuant = String.valueOf(quant);
        final String productPrice = String.valueOf(price);
        final String productCategory = productCategoryToRestore;
        final String productUnit = productUnitToRestore;
/*
        int i = 0;
        Boolean computed = false;
        Category category;
        while (i < productsList.getChildCount()) {
            category = (Category) productsList.getChildAt(i);
            if(productCategory.equals(category.getCategoryName())) {
                category.addView(productEdit);
                computed = true;
                break;
            }
            i++;
        }
        if(!computed) {
            category = new Category(context, productCategory);
            category.addView(productEdit);
            productsList.addView(category);
        }
*/
        productEdit.setProduct(dataHandler.addProduct(productName, productQuant, productPrice,
                productScratched, productCategory, productUnit));

        productNameList.add(productName);

        sortProducts(categoryToRestory);
    }

    public void sortProducts(Category pCategory) {
        if(pCategory == null) {
            Category category;
            int i = 0;
            while (i < productsList.getChildCount()) {
                category = (Category) productsList.getChildAt(i);
                categoriesNameList.add(category.getCategoryName());
                i++;
            }
            Collections.sort(categoriesNameList, String.CASE_INSENSITIVE_ORDER);

            ProductTableRow productTableRow;
            i = 0;
            int j;
            int k;
            int l;
            while (i < categoriesNameList.size()) {
                j = 0;
                while (j < productsList.getChildCount()) {
                    category = (Category) productsList.getChildAt(j);
                    if (category.getCategoryName().equals(categoriesNameList.get(i))) {
                        productsList.removeViewAt(j);
                        productsList.addView(category);
                        k = 0;
                        while (k < category.getChildCount()) {
                            if (k > 0) {
                                productTableRow = (ProductTableRow) category.getChildAt(k);
                                productNameList.add(productTableRow.getProductName());
                            }
                            k++;
                        }
                        Collections.sort(productNameList, String.CASE_INSENSITIVE_ORDER);
                        k = 0;
                        while (k < productNameList.size()) {
                            l = 0;
                            while (l < category.getChildCount()) {
                                if (l > 0) {
                                    productTableRow = (ProductTableRow) category.getChildAt(l);
                                    if (productTableRow.getProductName().equals(productNameList.get(k))) {
                                        category.removeViewAt(l);
                                        category.addView(productTableRow);
                                    }
                                }
                                l++;
                            }
                            k++;
                        }
                    }
                    j++;
                }
                i++;
            }
        } else {
            ArrayList<String> categoriesNames = new ArrayList<>();
            ArrayList<Category> categories = new ArrayList<>();
            int i = 0;
            while (i < productsList.getChildCount()) {
                Category category = (Category) productsList.getChildAt(i);
                categories.add(category);
                categoriesNames.add(category.getCategoryName());
                i++;
            }
            Collections.sort(categoriesNames, String.CASE_INSENSITIVE_ORDER);

            i = 0;
            while (i < categoriesNames.size()) {
                int j = 0;
                while (j < productsList.getChildCount()) {
                    Category category = (Category) productsList.getChildAt(j);
                    if(categoriesNames.get(i).equals(category.getCategoryName())) {
                        productsList.removeView(category);
                        productsList.addView(category);
                    }
                    j++;
                }
                i++;
            }
            ArrayList<String> productNames = new ArrayList<>();
            ArrayList<ProductTableRow> productTableRows = new ArrayList<>();
            ProductTableRow product;
            i = 0;
            while (i < pCategory.getChildCount()) {
                if(i > 0) {
                    product = (ProductTableRow) pCategory.getChildAt(i);
                    productTableRows.add(product);
                    String productName = product.getProductName();
                    productNames.add(productName);
                }
                i++;
            }
            Collections.sort(productNames, String.CASE_INSENSITIVE_ORDER);
            i = 0;
            while (i < productNames.size()) {
                int j = 0;
                while (j < pCategory.getChildCount()) {
                    if (j > 0) {
                        product = (ProductTableRow) pCategory.getChildAt(j);
                        if (product.getProductName().equals(productNames.get(i))) {
                            pCategory.removeView(product);
                            pCategory.addView(product);
                            break;
                        }
                    }
                    j++;
                }
                i++;
            }
        }
    }

    public void removeProduct() {
        Toast.makeText(context, productEdit.getProductName() + " removido.", Toast.LENGTH_LONG).show();

        if(contentRemoved) {
            tabShowCartContent.addView(showCartContent);
            tableRowTotal.addView(textViewTotalText);
            tableRowTotal.addView(totalDaCompraText);
            contentRemoved = false;
        }

        containerAddProductData.removeAllViews();

        showInitialSceneButtons();

        currentState = STATE.CART_READY;
        deleteProduct(productEdit.getProduct());
        cartSize--;
        productNameList.remove(productEdit.getProductName());

        Category productParent = (Category) productEdit.getParent();
        productParent.removeView(productEdit);
        if(productParent.getChildCount() == 1) {
            productsList.removeView(productParent);
        }
        productEdit = null;

        exitEditProduct();

        if (productsList.getChildCount() == 0) {
            showCartContent.setText(CART_EMPTY_MESSAGE);
        }

        updateTotaldaCompra();

        showInitialSceneButtons();
    }

    public void showEditing(String pProductName) {
        String text = EDITING + pProductName;
        showCartContent.setText(text);
    }

    public static CartScreen getInstance() {
        return instance;
    }

    public boolean existProductOnCategory(String pCategory, String pProductName) {
        int i = 0;
        while (i < productsList.getChildCount()) {
            Category category = (Category) productsList.getChildAt(i);
            if(pCategory.equals(category.getCategoryName())) {
                int j = 0;
                while (j < category.getChildCount()) {
                    if(j > 0) {
                        ProductTableRow product = (ProductTableRow) category.getChildAt(j);
                        if(product.getProductName().equals(pProductName)) {
                            return true;
                        }
                    }
                    j++;
                }
                break;
            }
            i++;
        }
        return false;
    }

    private void showSoftKeyboard(@Nullable View pView) {
//        mainActivity.getSoftKeyboard().openSoftKeyboard();
//        mainActivity.getSoftKeyboard().onFocusChange(pView, true);
//        MyEditText myEditText = (MyEditText)pView;
//           if (myEditText.requestFocus()) {
//          InputMethodManager im = (InputMethodManager) AppManager.getInstance().getActivity().
//                  getSystemService(Context.INPUT_METHOD_SERVICE);
//          im.showSoftInput(myEditText, InputMethodManager.SHOW_IMPLICIT);
          mainActivity.getInputMethodManager((ViewGroup) pView.getParent()).showSoftInput(pView, InputMethodManager.SHOW_IMPLICIT);
//          softInputVisible = true;
//           }
    }

    public void hideSoftKeyboard(@Nullable View pView) {
//        mainActivity.getSoftKeyboard().onFocusChange(pView, false);
        mainActivity.getSoftKeyboard().closeSoftKeyboard();
    }

    private void hideSoftKeyboard() {
/*        productNameInsert.setFocusableInTouchMode(true);
        productNameInsert.setFocusable(true);
        productNameInsert.requestFocus();
        currentViewFocused = productNameInsert;
        //((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
        //        .hideSoftInputFromWindow(currentViewFocused.getWindowToken(), 0);
        mainActivity.getInputMethodManager().hideSoftInputFromWindow(currentViewFocused.getWindowToken(), 0);*/
        //mainActivity.getSoftKeyboard().openSoftKeyboard(false);
        AppManager.getInstance().closeSoftKeyboard();
    }

    private void addItemOnCart() {
        AppManager.getInstance().closeSoftKeyboard();
        if (currentState.equals(STATE.INSERT_PRODUCT)) {
//            insertNextProduct();
            if(contentRemoved) {
                tabShowCartContent.addView(showCartContent);
                tableRowTotal.addView(textViewTotalText);
                tableRowTotal.addView(totalDaCompraText);
                contentRemoved = false;
            }
            softInputVisible = false;
            insertProduct();
            updateTotaldaCompra();
            showInitialSceneButtons();
            currentState = STATE.CART_READY;
        } else if (currentState.equals(STATE.EDIT_PRODUCT)) {
            if(contentRemoved) {
                tabShowCartContent.addView(showCartContent);
                tableRowTotal.addView(textViewTotalText);
                tableRowTotal.addView(totalDaCompraText);
                contentRemoved = false;
            }
            showInitialSceneButtons();
            currentState = STATE.CART_READY;
            updateProductList();
            exitEditProduct();
            updateTotaldaCompra();
        }
    }
}
