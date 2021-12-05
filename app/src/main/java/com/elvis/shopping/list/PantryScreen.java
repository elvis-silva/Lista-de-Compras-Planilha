package com.elvis.shopping.list;

import android.content.Context;
import android.database.Cursor;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elvis.shopping.list.data.DataHandler;
import com.elvis.shopping.list.impl.IActivity;
import com.elvis.shopping.list.manager.AppManager;
import com.elvis.shopping.list.model.Category;
import com.elvis.shopping.list.model.PantryRow;
import com.elvis.shopping.list.model.ProductTableRow;
import com.elvis.shopping.list.utils.CalculeUtils;
import com.elvis.shopping.list.utils.CustomDialog;

import java.util.ArrayList;
import java.util.Collections;

public class PantryScreen implements IActivity {

    private Context context;
    private MainActivity mainActivity;
    private DataHandler dataHandler;
    private LinearLayout container;
    public PantryRow currentPantryRow;
    public CustomDialog currentDialog;
    private int backControl = 0;

    public PantryScreen(Context pContext) {
        mainActivity = (MainActivity) AppManager.getInstance().getActivity();
        context = pContext;
        dataHandler = mainActivity.dataHandler;
    }

    @Override
    public void createView(View pView) {
        backControl = 0;
        int currentBgColor = mainActivity.currentBgColor;
        LinearLayout principal = pView.findViewById(R.id.principal_pantry);
        principal.setBackgroundColor(currentBgColor);
        container = pView.findViewById(R.id.container);
        container.setBackgroundColor(currentBgColor);
//        TextView title = pView.findViewById(R.id.title);
//        title.setTextColor(mainActivity.currentTitlesFontColor);

        loadPantryData();
    }

    private void loadPantryData() {
        Cursor cursor = dataHandler.returnPantryData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PantryRow product = dataHandler.parseProductOnPantryData(cursor);
            addProductLoaded(product);
            cursor.moveToNext();
        }
        cursor.close();
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
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                hideSoftKeyboard();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(backControl == 0) {
            Toast.makeText(context, "Pressione outra vez para sair", Toast.LENGTH_SHORT).show();
            backControl++;
        } else {
            mainActivity.showOfferWall();
        }
    }

    private void addProductLoaded(PantryRow pProduct) {
        Category category;
        if(container.getChildCount() == 0) {
            category = new Category(context, pProduct.getProductCategory());
            container.addView(category);
            category.addView(pProduct);
        } else {
            Boolean productAdded = false;
            int i = 0;
            while (i < container.getChildCount()) {
                category = (Category) container.getChildAt(i);
                if (pProduct.getProductCategory().equals(category.getCategoryName())) {
                    category.addView(pProduct);
                    sortProductsOnCategory(category.getCategoryName());
                    productAdded = true;
                    break;
                }
                i++;
            }
            if (!productAdded) {
                category = new Category(context, pProduct.getProductCategory());
                container.addView(category);
                category.addView(pProduct);
                sortCategories();
            }
        }
    }

    public void addProduct(String pProductName, String pProductQuant, String pUnit, String pProductCategory) {
        Category category;
        if(container.getChildCount() == 0) {
            category = new Category(context, pProductCategory);
            container.addView(category);
            PantryRow pantryRow = new PantryRow(context, pProductName, pProductQuant, pUnit, pProductCategory);
            category.addView(pantryRow);
            dataHandler.addProductOnPantryData(pantryRow);
        } else {
            int i = 0;
            Boolean productAdded = false;
            while (i < container.getChildCount()) {
                category = (Category) container.getChildAt(i);
                if(category.getCategoryName().equals(pProductCategory)) {
                    PantryRow pantryRow = new PantryRow(context, pProductName, pProductQuant, pUnit, pProductCategory);
                    category.addView(pantryRow);
                    sortProductsOnCategory(pProductCategory);
                    productAdded = true;
                    dataHandler.addProductOnPantryData(pantryRow);
                    break;
                }
                i++;
            }
            if(!productAdded) {
                category = new Category(context, pProductCategory);
                container.addView(category);
                PantryRow pantryRow = new PantryRow(context, pProductName, pProductQuant, pUnit, pProductCategory);
                category.addView(pantryRow);
                dataHandler.addProductOnPantryData(pantryRow);
                sortCategories();
            }
        }
        Toast.makeText(context, pProductName + " adicionado na despensa", Toast.LENGTH_SHORT).show();
    }

    public void addProduct(PantryRow pPantryRow) {
        Category category;
        if(container.getChildCount() == 0) {
            category = new Category(context, pPantryRow.getProductCategory());
            container.addView(category);
            category.addView(pPantryRow);
            dataHandler.addProductOnPantryData(pPantryRow);
        } else {
            Boolean productAdded = false;
            int i = 0;
            while (i < container.getChildCount()) {
                category = (Category) container.getChildAt(i);
                if (pPantryRow.getProductCategory().equals(category.getCategoryName())) {
                    category.addView(pPantryRow);
                    dataHandler.addProductOnPantryData(pPantryRow);
                    sortProductsOnCategory(category.getCategoryName());
                    productAdded = true;
                    break;
                }
                i++;
            }
            if (!productAdded) {
                category = new Category(context, pPantryRow.getProductCategory());
                container.addView(category);
                category.addView(pPantryRow);
                dataHandler.addProductOnPantryData(pPantryRow);
                sortCategories();
            }
        }
        Toast.makeText(context, pPantryRow.getProductName() + " adicionado na despensa", Toast.LENGTH_SHORT).show();
    }

    public void updateProduct(String pProductName, String pProductQuant, String pProductUnit, String pProductCategory) {
        if(!pProductName.equals(currentPantryRow.getProductName()) ||
                !pProductQuant.equals(currentPantryRow.getProductQuant()) ||
                !pProductUnit.equals(currentPantryRow.getProductUnit()) ||
                !pProductCategory.equals(currentPantryRow.getProductCategory())) {

            Category category = (Category) currentPantryRow.getParent();
            category.removeView(currentPantryRow);
            if(category.getChildCount() == 1) deleteCategory(category);

            currentPantryRow.updateData(pProductName, pProductQuant, pProductUnit, pProductCategory);

            sortProduct(currentPantryRow);
        }
    }

    private void sortProduct(PantryRow pProduct) {
        Category category;
        int i = 0;
        Boolean productAdded = false;
        while (i < container.getChildCount()) {
            category = (Category) container.getChildAt(i);
            if(pProduct.getProductCategory().equals(category.getCategoryName())) {
                category.addView(pProduct);
                sortProductsOnCategory(category.getCategoryName());
                productAdded = true;
                break;
            }
            i++;
        }
        if(!productAdded) {
            category = new Category(context, pProduct.getProductCategory());
            category.addView(pProduct);
            container.addView(category);
            sortCategories();
        }
    }

    private void sortCategories() {
        ArrayList<String> categoriesNames = new ArrayList<>();
        ArrayList<Category> categories = new ArrayList<>();
        Category category;
        int i = 0;
        while (i < container.getChildCount()) {
            category = (Category) container.getChildAt(i);
            categoriesNames.add(category.getCategoryName());
            categories.add(category);
            i++;
        }
        Collections.sort(categoriesNames, String.CASE_INSENSITIVE_ORDER);
        container.removeAllViews();

        i = 0;
        while (i < categoriesNames.size()) {
            String categoryName = categoriesNames.get(i);
            int j = 0;
            while (j < categories.size()) {
                category = categories.get(j);
                if(category.getCategoryName().equals(categoryName)) {
                    container.addView(category);
                    break;
                }
                j++;
            }
            i++;
        }
    }

    private void sortProductsOnCategory(String pCategoryName) {
        ArrayList<String> productsNames = new ArrayList<>();
        ArrayList<PantryRow> pantryRows = new ArrayList<>();
        PantryRow pantryRow;
        Category category;
        int i = 0;
        while (i < container.getChildCount()) {
            category = (Category) container.getChildAt(i);
            if(category.getCategoryName().equals(pCategoryName)) {
                int j = 0;
                while (j < category.getChildCount()) {
                    if (j > 0) {
                        pantryRow = (PantryRow) category.getChildAt(j);
                        productsNames.add(pantryRow.getProductName());
                        pantryRows.add(pantryRow);
                    }
                    j++;
                }
                do {
                    category.removeViewAt(category.getChildCount() - 1);
                } while (category.getChildCount() > 1);

                Collections.sort(productsNames, String.CASE_INSENSITIVE_ORDER);

                int k = 0;
                do {
                    String currentProductName = productsNames.get(k);
                    j = 0;
                    while (j < pantryRows.size()) {
                        pantryRow = pantryRows.get(j);
                        if (pantryRow.getProductName().equals(currentProductName)) {
                            category.addView(pantryRow);
                            pantryRows.remove(j);
                            k++;
                            break;
                        }
                        j++;
                    }
                } while (pantryRows.size() > 0);
            }
            i++;
        }
    }

    public void hideSoftKeyboard() {
        AppManager.getInstance().closeSoftKeyboard();
       // ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
       //         hideSoftInputFromWindow(currentDialog.currentViewFocused.getWindowToken(), 0);
    }

    public void deleteProduct(PantryRow pProduct) {
        int i = 0;
        while (i < container.getChildCount()) {
            Category category = (Category) container.getChildAt(i);
            if (pProduct.getProductCategory().equals(category.getCategoryName())) {
                category.removeView(pProduct);
                dataHandler.deleteProductOnPantryData(pProduct);
                if(category.getChildCount() == 1) deleteCategory(category);
                Toast.makeText(context, pProduct.getProductName() + " foi removido da despensa", Toast.LENGTH_SHORT).show();
                break;
            }
            i++;
        }
    }

    public void deleteCategory(Category pCategory) {
        container.removeView(pCategory);
    }

    public boolean existProductOnCategory(String pCategory, String pProductName) {
        int i = 0;
        while (i < container.getChildCount()) {
            Category category = (Category) container.getChildAt(i);
            if(pCategory.equals(category.getCategoryName())) {
                int j = 0;
                while (j < category.getChildCount()) {
                    if(j > 0) {
                        PantryRow pantryRow = (PantryRow) category.getChildAt(j);
                        if(pantryRow.getProductName().equals(pProductName)) {
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

    public void updatePantry(LinearLayout pProductsList) {
        Cursor cursor = dataHandler.returnPantryData();
        ProductTableRow productTableRow;
        PantryRow product;
        PantryRow productOnPantry;
        int productAddedTotal = 0;
        int productUpdatedTotal = 0;
        int i = 0;
        while (i < pProductsList.getChildCount()) {
            Category category = (Category) pProductsList.getChildAt(i);
            int j = 0;
            while (j < category.getChildCount()) {
                if(j > 0) {
                    productTableRow = (ProductTableRow) category.getChildAt(j);
                    if(Float.valueOf(productTableRow.getProductTotalValue()) > 0 && !productTableRow.isScratched()) {
                        Boolean productAdded = false;
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            productOnPantry = dataHandler.parseProductOnPantryData(cursor);
                            if (productOnPantry.getProductName().equals(productTableRow.getProductName()) &&
                                    productOnPantry.getProductCategory().equals(category.getCategoryName())) {
                                float quant = Float.valueOf(productTableRow.getProductQuant()) +
                                        CalculeUtils.returnFloat(productOnPantry.getProductQuant());
                                String quantString = CalculeUtils.kiloFormat(quant);
                                dataHandler.updateProductOnPantry(productOnPantry, productOnPantry.getProductName(),
                                        quantString, productTableRow.getProductUnit(), category.getCategoryName());
                                productAdded = true;
                                productUpdatedTotal++;
                                break;
                            }
                            cursor.moveToNext();
                        }

                        if (!productAdded) {
                            product = new PantryRow(context, productTableRow.getProductName(),
                                    CalculeUtils.kiloFormat(Float.valueOf(productTableRow.getProductQuant())),
                                    productTableRow.getProductUnit(), category.getCategoryName());
                            dataHandler.addProductOnPantryData(product);
                            productAddedTotal++;
                        }
                    }
                }
                j++;
            }
            i++;
        }
        cursor.close();
        if(productAddedTotal != 0 || productUpdatedTotal != 0) {
            String itemAddedMessage = productAddedTotal != 0 ?
                    "\nTotal de itens adicionados: " + String.valueOf(productAddedTotal) :
                    "\nNenhum item novo adicionado";
            String itemUpdatedMessage = productUpdatedTotal != 0 ?
                    "\n\nTotal de itens atualizados: " + String.valueOf(productUpdatedTotal) :
                    "\n\nNenhum item atualizado";
            CustomDialog customDialog = new CustomDialog(mainActivity, "Despensa Atualizada",
                     itemAddedMessage + itemUpdatedMessage, CustomDialog.TYPE_WARNING);
            customDialog.show();
        } else {
            Toast.makeText(context, "Nada adicionado ou atualizado na despensa", Toast.LENGTH_LONG).show();
        }
    }
}