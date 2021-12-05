package com.elvis.shopping.list.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.elvis.shopping.list.CartScreen;
import com.elvis.shopping.list.MainActivity;
import com.elvis.shopping.list.R;
import com.elvis.shopping.list.manager.AppManager;
import com.elvis.shopping.list.model.Group;
import com.elvis.shopping.list.model.PantryRow;
import com.elvis.shopping.list.view.MyEditText;
import com.elvis.shopping.list.view.MySingleButton;
import com.elvis.shopping.list.view.MyTextView;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public static final String TYPE_REMOVE_PRODUCT = "remove_product";
    public static final String TYPE_ADD_CATEGORY = "add_category";
    public static final String TYPE_ABOUT = "about";
    public static final String TYPE_CLEAR = "clear";
    public static final String TYPE_SAVE_LIST = "save_list";
    public static final String TYPE_LOAD_LIST = "load_list";
    public static final String TYPE_RENAME_LIST = "rename_list";
    public static final String TYPE_DELETE_LIST = "delete_list";
    public static final String TYPE_WARNING = "warning";
    public static final String TYPE_ADD_PRODUCT_ON_PANTRY = "add_product_on_pantry";
    public static final String TYPE_EDIT_PRODUCT_ON_PANTRY = "edit_product_on_pantry";
    public static final String TYPE_SHOW_OPTIONS = "show_options";
    public static final String TYPE_REMOVE_PRODUCT_ON_PANTRY = "remove_product_on_pantry";
    public static final String TYPE_ADD_CATEGORY_ON_PANTRY = "add_category_on_pantry";
    public static final String TYPE_ADD_UNIT_ON_PANTRY = "add_unit_on_pantry";
    public static final String TYPE_ADD_UNIT_ON_CART = "add_unit_on_cart";
    public static final String TYPE_SAVE_OPTIONS = "save_options";
    public static final String TYPE_RESET_VALUES = "context_menu";

    private String dialogType;
    private String currentStep = "";
    public View currentView, currentViewFocused;
    private Context context;
    public MyEditText categoryInsert, nameSet, quantSet, categorySet, unitSet;
    public String currentCategory, currentUnit, currentTitle, productName, productQuant, productCategory, productUnit;
    private MySingleButton lastBtn;
    public String typeSave = "save";

    SparseArray<Group> groupsCategory = new SparseArray<>();
    SparseArray<Group> groupsUnit = new SparseArray<>();
    private String currentCustomDialogType;

    public CustomDialog(Context pContext, CharSequence pTitle, CharSequence pMessage, String pType) {
        super(pContext);

        context = pContext;
        dialogType = pType.equals("") ? "" : pType;
        buildDialog(pTitle, pMessage);
    }

    private void buildDialog(CharSequence pTitle, CharSequence pMessage) {
        switch (dialogType) {
            case TYPE_ADD_CATEGORY:
                buildAddCategoryDialog(pTitle);
                break;
            case TYPE_SAVE_LIST:
                buildSaveListDialog(pTitle);
                break;
            case TYPE_LOAD_LIST:
                buildLoadListDialog(pTitle);
                break;
            case TYPE_RENAME_LIST:
                buildRenameListDialog(pTitle);
                break;
            case TYPE_DELETE_LIST:
                buildDeleteListDialog(pTitle, pMessage);
                break;
            case TYPE_ABOUT:
                buildAboutDialog(pTitle);
                break;
            case TYPE_WARNING:
                buildWarningDialog(pTitle, pMessage);
                break;
            case TYPE_ADD_PRODUCT_ON_PANTRY:
                buildAddProductOnPantryDialog(pTitle);
                break;
            case TYPE_EDIT_PRODUCT_ON_PANTRY:
                buildEditProductOnPantryDialog(pTitle);
                break;
            case TYPE_SHOW_OPTIONS:
                buildShowOptionsDialog(pTitle);
                break;
            case TYPE_ADD_CATEGORY_ON_PANTRY:
                buildAddCategoryOnPantryDialog(pTitle);
                break;
            case TYPE_ADD_UNIT_ON_PANTRY:
                buildAddUnitOnPantryDialog(pTitle);
                break;
            case TYPE_ADD_UNIT_ON_CART:
                buildAddUnitOnCartDialog(pTitle);
                break;
            case TYPE_SAVE_OPTIONS:
                buildShowSaveOptionsDialog(pTitle);
                break;
            case TYPE_RESET_VALUES:
                buildResetValuesDialog(pTitle);
                break;
            default:
                currentStep = "";

                setContentView(R.layout.dialog_custom);
                setTitle(pTitle);

                MyTextView text = findViewById(R.id.dialogText);
                text.setText("\n" + pMessage + "\n");

                findViewById(R.id.dialogButtonOK).setOnClickListener(this);

                findViewById(R.id.dialogButtonNo).setOnClickListener(this);

                this.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (currentStep.equals("") && !AppManager.getInstance().getCartScreen().currentState.equals(CartScreen.STATE.CART_READY))
                            AppManager.getInstance().showSoftKeyboard(currentView);
                    }
                });
                break;
        }
    }

    private void buildResetValuesDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_context_menu);
        setTitle(pTitle);

        findViewById(R.id.btnResetPrice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().getCartScreen().resetPrice();
                dismiss();
            }
        });

        findViewById(R.id.btnResetQuant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().getCartScreen().resetQuant();
                dismiss();
            }
        });

        findViewById(R.id.btnResetAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().getCartScreen().resetAll();
                dismiss();
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void buildAddUnitOnCartDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_categories);
        setTitle(pTitle);

        ((MyTextView)findViewById(R.id.container_title)).setText("Unidades");
        findViewById(R.id.btn_close).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return v.performClick();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                CartScreen cartScreen = AppManager.getInstance().getCartScreen();
                cartScreen.restoreSoftFocus(cartScreen.productQuantInsert);
            }
        });
    }

    private void buildAddUnitOnPantryDialog(CharSequence pTitle) {
        MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity();
        currentCustomDialogType = mainActivity.currentCustomDialogType;

        setContentView(R.layout.dialog_categories);
        setTitle(pTitle);

        ((MyTextView)findViewById(R.id.container_title)).setText("Unidades");
        findViewById(R.id.btn_close).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return v.performClick();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                changeDialog(currentCustomDialogType);
            }
        });
    }

    private void buildAddCategoryDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_categories);
        setTitle(pTitle);

        ((MyTextView)findViewById(R.id.container_title)).setText("Categorias");
        findViewById(R.id.btn_close).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return v.performClick();
            }
        });

        if(AppManager.getInstance().getCurrentScreen().equals(AppManager.getInstance().getCartScreen())) {
            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    CartScreen cartScreen = AppManager.getInstance().getCartScreen();
                    cartScreen.restoreSoftFocus(cartScreen.productNameInsert);
                }
            });
        }
    }

    private void buildAddCategoryOnPantryDialog(CharSequence pTitle) {
        MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity();
        currentCustomDialogType = mainActivity.currentCustomDialogType;

        setContentView(R.layout.dialog_categories);
        setTitle(pTitle);

        ((MyTextView)findViewById(R.id.container_title)).setText("Categorias");
        findViewById(R.id.btn_close).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return v.performClick();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                changeDialog(currentCustomDialogType);
            }
        });
    }

    private void buildShowOptionsDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_show_options);
        setTitle(pTitle);

        ((MyTextView)findViewById(R.id.container_title)).setText(pTitle);
        findViewById(R.id.btn_close).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return v.performClick();
            }
        });

        findViewById(R.id.btnEdit).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
    }

    private void buildShowSaveOptionsDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_save_options);
        setTitle(pTitle);

        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnPantryUpdate).setOnClickListener(this);
        findViewById(R.id.btnSaveAndPantry).setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);
    }

    private void buildEditProductOnPantryDialog(CharSequence pTitle) {
        final MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity();
        mainActivity.currentCustomDialogType = TYPE_EDIT_PRODUCT_ON_PANTRY;
        setContentView(R.layout.dialog_add_product_on_pantry);
        setTitle(pTitle);

        findViewById(R.id.categoryTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.currentQuantOnPantryEdit = quantSet.getText().toString();
                mainActivity.currentProductNameOnPantryEdit = nameSet.getText().toString();
                mainActivity.currentUnitOnPantryEdit = unitSet.getText().toString();
                mainActivity.currentCategoryOnPantryEdit = categorySet.getText().toString();
                dismiss();
                CustomDialog customDialog = new CustomDialog(context, "Categorias", "", TYPE_ADD_CATEGORY_ON_PANTRY);
                customDialog.setCategoriesList(AppManager.getInstance().getCartScreen().categories, null);
                customDialog.show();
            }
        });

        findViewById(R.id.unitTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.currentQuantOnPantryEdit = quantSet.getText().toString();
                mainActivity.currentProductNameOnPantryEdit = nameSet.getText().toString();
                mainActivity.currentUnitOnPantryEdit = unitSet.getText().toString();
                mainActivity.currentCategoryOnPantryEdit = categorySet.getText().toString();
                dismiss();
                CustomDialog customDialog = new CustomDialog(context, "Unidades", "", TYPE_ADD_UNIT_ON_PANTRY);
                customDialog.setUnitList(AppManager.getInstance().getCartScreen().units);
                customDialog.show();
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.requestFocus();
                currentViewFocused = view;
            }
        };

        nameSet = findViewById(R.id.nameSet);
        nameSet.setPressed(true);
        nameSet.requestFocus();
        currentViewFocused = nameSet;
        nameSet.setOnClickListener(onClickListener);
        quantSet = findViewById(R.id.quantSet);
        quantSet.setOnClickListener(onClickListener);
        categorySet = findViewById(R.id.categorySet);
        categorySet.setOnClickListener(onClickListener);
        unitSet = findViewById(R.id.unitSet);
        unitSet.setOnClickListener(onClickListener);

        findViewById(R.id.btnConfirm).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
    }

    private void buildAddProductOnPantryDialog(CharSequence pTitle) {
        final MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity();
        mainActivity.currentCustomDialogType = TYPE_ADD_PRODUCT_ON_PANTRY;
/*
        if(AppManager.getInstance().getPantryScreen().currentPantryRow != null) {
            AppManager.getInstance().getPantryScreen().currentPantryRow.setBackgroundResource(R.drawable.btn);
        }
*/
        setContentView(R.layout.dialog_add_product_on_pantry);
        setTitle(pTitle);

        findViewById(R.id.categoryTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.currentQuantOnPantryEdit = quantSet.getText().toString();
                mainActivity.currentProductNameOnPantryEdit = nameSet.getText().toString();
                mainActivity.currentUnitOnPantryEdit = unitSet.getText().toString();
                mainActivity.currentCategoryOnPantryEdit = categorySet.getText().toString();
                dismiss();
                CustomDialog customDialog = new CustomDialog(context, "Categorias", "", TYPE_ADD_CATEGORY_ON_PANTRY);
                customDialog.setCategoriesList(AppManager.getInstance().getCartScreen().categories, categorySet);
                customDialog.show();
            }
        });

        findViewById(R.id.unitTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.currentQuantOnPantryEdit = quantSet.getText().toString();
                mainActivity.currentProductNameOnPantryEdit = nameSet.getText().toString();
                mainActivity.currentUnitOnPantryEdit = unitSet.getText().toString();
                mainActivity.currentCategoryOnPantryEdit = categorySet.getText().toString();
                dismiss();
                CustomDialog customDialog = new CustomDialog(context, "Unidades", "", TYPE_ADD_UNIT_ON_PANTRY);
                customDialog.setUnitList(AppManager.getInstance().getCartScreen().units);
                customDialog.show();
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.requestFocus();
                currentViewFocused = view;
            }
        };

        nameSet = findViewById(R.id.nameSet);
        nameSet.setText(mainActivity.currentProductNameOnPantryEdit);
        nameSet.setPressed(true);
        nameSet.requestFocus();
        currentViewFocused = nameSet;
        nameSet.setOnClickListener(onClickListener);
        quantSet = findViewById(R.id.quantSet);
        quantSet.setText(mainActivity.currentQuantOnPantryEdit);
        quantSet.setOnClickListener(onClickListener);
        categorySet = findViewById(R.id.categorySet);
        categorySet.setText(mainActivity.currentCategoryOnPantryEdit);
        categorySet.setOnClickListener(onClickListener);
        unitSet = findViewById(R.id.unitSet);
        unitSet.setText(mainActivity.currentUnitOnPantryEdit);
        unitSet.setOnClickListener(onClickListener);

        findViewById(R.id.btnConfirm).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
    }

    public void hideKeyboard() {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentViewFocused.getWindowToken(), 0);
    }

    private void createCategoriesData() {
        String [] categories = {"Açougue","Alimentos","Aperitivos","Bebidas","Biscoitos","Castanhas","Cereais",
            "Condimentos","Congelados","Compotas","Cortes de Boi","Cortes de Frango","Cortes de Porco","Doces",
            "Enlatados","Farináceos","Fatiados","Frios","Frutas","Grãos","Higiene","Hortaliças","Lanches",
            "Laticínios","Legumes","Limpeza","Mantimentos","Massas","Matinais","Mercearia","Molhos","Óleos",
            "Padaria","Pescados","Sementes","Sobremesas","Temperos","Utilidades","Verduras"};
        Group group = new Group("Categoria");
        int i = 0;
        while (i < categories.length) {
            group.children.add(categories[i]);
            i++;
        }
        groupsCategory.append(0, group);
    }

    private void createUnitData() {
        String [] units = {"Caixa","Envelope","Fardo","Garrafa","Grama","Lata","Litro","Maço","Pacote","Peça",
                "Pote","Quilo","Rolo","Saco","Unidade","Vidro"};
        Group group = new Group("Unidade");
        int i = 0;
        while (i < units.length) {
            group.children.add(units[i]);
            i++;
        }
        groupsUnit.append(0, group);
    }

    private void buildWarningDialog(CharSequence pTitle, CharSequence pMessage) {
        setContentView(R.layout.dialog_custom);
        setTitle(pTitle);

        MyTextView text = findViewById(R.id.dialogText);
        text.setText("\n" + pMessage + "\n");

        MyTextView dialogButtonOk = findViewById(R.id.dialogButtonOK);
        dialogButtonOk.setText("OK");
        dialogButtonOk.setOnClickListener(this);

        MyTextView dialogButtonNo = findViewById(R.id.dialogButtonNo);
        ((TableRow)dialogButtonNo.getParent()).removeView(dialogButtonNo);
    }

    private void buildDeleteListDialog(CharSequence pTitle, CharSequence pMessage) {
        setContentView(R.layout.dialog_custom);
        setTitle(pTitle);

        MyTextView text = findViewById(R.id.dialogText);
        text.setText("\n" + pMessage + "\n");

        findViewById(R.id.dialogButtonOK).setOnClickListener(this);

        findViewById(R.id.dialogButtonNo).setOnClickListener(this);
    }

    private void buildRenameListDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_save_list);

        setTitle(pTitle);
        currentTitle = AppManager.getInstance().getShoppingListsScreen().currentTitle;

        nameSet = findViewById(R.id.nameSet);

        findViewById(R.id.confirmBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
    }

    private void buildLoadListDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_load_list);

        setTitle(pTitle);
        currentTitle = pTitle.toString();

        ((MyTextView)findViewById(R.id.container_title)).setText(pTitle);
        findViewById(R.id.btn_close).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return v.performClick();
            }
        });

        findViewById(R.id.btnLoad).setOnClickListener(this);
        findViewById(R.id.btnRename).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
    }

    private void buildSaveListDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_save_list);
        setTitle(pTitle);

        nameSet = findViewById(R.id.nameSet);

        MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity();
        if(!mainActivity.getActionBarTitle().equals("Nova Lista")) {
            nameSet.setText(mainActivity.getActionBarTitle());
        }

        findViewById(R.id.confirmBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
    }

    private void buildAboutDialog(CharSequence pTitle) {
        setContentView(R.layout.dialog_about);
        String message = "\nLista de Compras - Planilha\n";
        message += "\n Versão 2.2.2\n";
        message += "\nCriado por Elvis Carlos\n";
        message += "\ne-mail: elviscarlossouza@gmail.com\n";
        message += "\nCopyright © 2014\n";

        setTitle(pTitle);
        MyTextView text = findViewById(R.id.text);
        text.setText(message);

        findViewById(R.id.dialogButtonOK).setOnClickListener(this);
    }

    public void setUnitList(String[] units) {
        ((MyTextView)findViewById(R.id.container_title)).setText("Unidades");
        findViewById(R.id.btn_close).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return v.performClick();
            }
        });
        LinearLayout container = findViewById(R.id.container);
        int i = 0;
        while (i < units.length) {
            MyTextView button = new MyTextView(context);
            button.setBackgroundColor(context.getResources().getColor(R.color.fab_material_white));
            button.setText(units[i]);
            button.setGravity(Gravity.CENTER_VERTICAL);
            button.setTextSize(20f);
            button.setTextColor(context.getResources().getColor(R.color.material_black_grey));
            button.setPadding(16,16,16,16);
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.custom_color));
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.fab_material_white));
                        currentUnit = ((MyTextView) view).getText().toString();
                        if(AppManager.getInstance().getCurrentScreen().equals(AppManager.getInstance().getCartScreen())) {
                            AppManager.getInstance().getCartScreen().productQuantText.setText(currentUnit);
                        } else {
                            ((MainActivity)AppManager.getInstance().getActivity()).currentUnitOnPantryEdit = currentUnit;
                        }
                        dismiss();
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.fab_material_white));
                    }
                    return view.performClick();
                }
            });
            container.addView(button);
            i++;
        }
    }

    public void setCategoriesList (String [] pCategories, final MyEditText pCategoryInsert) {
        categoryInsert = pCategoryInsert;
        ((MyTextView)findViewById(R.id.container_title)).setText("Categorias");
        findViewById(R.id.btn_close).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return v.performClick();
            }
        });
        LinearLayout container = findViewById(R.id.container);
        int i = 0;
        while (i < pCategories.length) {
            MyTextView button = new MyTextView(context);
            button.setBackgroundColor(context.getResources().getColor(R.color.fab_material_white));
            button.setText(pCategories[i]);
            button.setGravity(Gravity.CENTER_VERTICAL);
            button.setTextSize(20f);
            button.setTextColor(context.getResources().getColor(R.color.material_black_grey));
            button.setPadding(16,16,16,16);
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.custom_color));
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.fab_material_white));
                        currentCategory = ((MyTextView) view).getText().toString();
                        if(AppManager.getInstance().getCurrentScreen().equals(AppManager.getInstance().getCartScreen())) {
                            categoryInsert.setText(currentCategory);
                        } else {
                            ((MainActivity)AppManager.getInstance().getActivity()).currentCategoryOnPantryEdit = currentCategory;
                        }
                        dismiss();
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.fab_material_white));
                    }
                    return view.performClick();
                }
            });
            container.addView(button);
            i++;
        }
    }

    public void showProductEditDialog() {
        CustomDialog customDialog = new CustomDialog(context, " Editar Produto                             ", "",
                CustomDialog.TYPE_EDIT_PRODUCT_ON_PANTRY);
        customDialog.show();
        customDialog.setData();
        AppManager.getInstance().getPantryScreen().currentDialog = customDialog;
    }

    public void setData() {
        PantryRow product = AppManager.getInstance().getPantryScreen().currentPantryRow;
        nameSet.setText(product.getProductName());
        quantSet.setText(product.getProductQuant().replace(".", "").replace(",", "."));
        unitSet.setText(product.getProductUnit());
        categorySet.setText(product.getProductCategory());
    }

    public void setCategory () {
        categoryInsert.setText(currentCategory);
    }

    @Override
    public void onClick(View v) {
        if (dialogType.equals(TYPE_REMOVE_PRODUCT)) {
            switch (v.getId()) {
                case R.id.dialogButtonOK:
                    AppManager.getInstance().getCartScreen().removeProduct();
                    currentStep = "removing_product";
                    dismiss();
                    break;
                case R.id.dialogButtonNo:
                    dismiss();
                    AppManager.getInstance().showSoftKeyboard(currentView);
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_REMOVE_PRODUCT_ON_PANTRY)) {
            switch (v.getId()) {
                case R.id.dialogButtonOK:
                    AppManager.getInstance().getPantryScreen().deleteProduct(
                            AppManager.getInstance().getPantryScreen().currentPantryRow);
                    dismiss();
                    break;
                case R.id.dialogButtonNo:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_CLEAR)) {
            switch (v.getId()) {
                case R.id.dialogButtonOK:
                    AppManager.getInstance().getCartScreen().destroyList();
                    dismiss();
                    break;
                case R.id.dialogButtonNo:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_SAVE_OPTIONS)) {
            switch (v.getId()) {
                case R.id.btnSave:
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            CustomDialog customDialog = new CustomDialog(context, "Salvar?", "", TYPE_SAVE_LIST);
                            customDialog.show();
                        }
                    });
                    dismiss();
                    break;
                case R.id.btnPantryUpdate:
                    AppManager.getInstance().getPantryScreen().updatePantry(
                            AppManager.getInstance().getCartScreen().productsList);
                    dismiss();
                    break;
                case R.id.btnSaveAndPantry:
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            CustomDialog customDialog = new CustomDialog(context, "Salvar?", "", TYPE_SAVE_LIST);
                            customDialog.typeSave = "save_and_update";
                            customDialog.show();
                        }
                    });
                    dismiss();
                    break;
                case R.id.btnBack:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_ADD_CATEGORY_ON_PANTRY)) {
            switch (v.getId()) {
                case R.id.confirmBtn:
                    ((MainActivity)AppManager.getInstance().getActivity()).currentCategoryOnPantryEdit = currentCategory;
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            changeDialog(currentCustomDialogType);
                        }
                    });
                    dismiss();
                    break;
                case R.id.cancelBtn:
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            changeDialog(currentCustomDialogType);
                        }
                    });
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_ADD_UNIT_ON_PANTRY)) {
            switch (v.getId()) {
                case R.id.confirmBtn:
                    ((MainActivity)AppManager.getInstance().getActivity()).currentUnitOnPantryEdit = currentUnit;
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            changeDialog(currentCustomDialogType);
                        }
                    });
                    dismiss();
                    break;
                case R.id.cancelBtn:
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            changeDialog(currentCustomDialogType);
                        }
                    });
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_DELETE_LIST)) {
            switch (v.getId()) {
                case R.id.dialogButtonOK:
                    AppManager.getInstance().getShoppingListsScreen().removeList();
                    dismiss();
                    break;
                case R.id.dialogButtonNo:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_ADD_CATEGORY)) {
            switch (v.getId()) {
                case R.id.confirmBtn:
                    setCategory();
                    dismiss();
                    break;
                case R.id.cancelBtn:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_SAVE_LIST)) {
            switch (v.getId()) {
                case R.id.confirmBtn:
                    if(typeSave.equals("save")) {
                        AppManager.getInstance().getCartScreen().saveList(nameSet.getText().toString());
                    } else {
                        AppManager.getInstance().getCartScreen().saveList(nameSet.getText().toString());
                        AppManager.getInstance().getPantryScreen().updatePantry(
                                AppManager.getInstance().getCartScreen().productsList);
                    }
                    dismiss();
                    break;
                case R.id.cancelBtn:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_RENAME_LIST)) {
            switch (v.getId()) {
                case R.id.confirmBtn:
                    AppManager.getInstance().getShoppingListsScreen().renameList(currentTitle, nameSet.getText().toString());
                    dismiss();
                    break;
                case R.id.cancelBtn:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_SHOW_OPTIONS)) {
            switch (v.getId()) {
                case R.id.btnEdit:
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            showProductEditDialog();
                        }
                    });
                    dismiss();
                    break;
                case R.id.btnDelete:
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            CustomDialog customDialog = new CustomDialog(context, " Remover Produto               ", "Quer realmente remover " +
                                    AppManager.getInstance().getPantryScreen().currentPantryRow
                                            .getProductName() + " da despensa?", TYPE_REMOVE_PRODUCT_ON_PANTRY);
                            customDialog.show();
                        }
                    });
                    dismiss();
                    break;
                case R.id.btnBack:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_ADD_PRODUCT_ON_PANTRY)) {
            switch (v.getId()) {
                case R.id.btnConfirm:
                    executeAddProductOnPantry();
                    break;
                case R.id.btnCancel:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_ADD_UNIT_ON_CART)) {
            switch (v.getId()) {
                case R.id.confirmBtn:
                    AppManager.getInstance().getCartScreen().productQuantText.setText(currentUnit);
                    dismiss();
                    break;
                case R.id.cancelBtn:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_EDIT_PRODUCT_ON_PANTRY)) {
            switch (v.getId()) {
                case R.id.btnConfirm:
                    executeEditProductOnPantry();
                    break;
                case R.id.btnCancel:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_ABOUT) || dialogType.equals(TYPE_WARNING)) {
            switch (v.getId()) {
                case R.id.dialogButtonOK:
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        } else if (dialogType.equals(TYPE_LOAD_LIST)) {
            switch (v.getId()) {
                case R.id.btnLoad:
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            CustomDialog customDialog = new CustomDialog(context, "Lista Carregada",
                                    "\nPuxe a aba lateral e selecione [LISTA ATUAL] para vê-la.\n\n Boas compras.",
                                    TYPE_WARNING);
                            customDialog.show();
                        }
                    });
                    AppManager.getInstance().getCartScreen().loadList(currentTitle);
                    ((MainActivity)AppManager.getInstance().getActivity()).currentListName = currentTitle;
//                    showMessageLengthLong(currentTitle + " carregada");
                    dismiss();
                    break;
                case R.id.btnBack:
                    dismiss();
                    break;
                case R.id.btnRename:
                    setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            AppManager.getInstance().getShoppingListsScreen().showDialogRename();
                        }
                    });
                    dismiss();
                    break;
                case R.id.btnDelete:
                    CustomDialog customDialog = new CustomDialog(context,
                            "Remover Lista", "Quer realmete remover " + currentTitle + "?",
                            CustomDialog.TYPE_DELETE_LIST);
                    customDialog.show();
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        }
    }

    private void executeEditProductOnPantry() {
        productName = nameSet.getText().toString();
        productCategory = categorySet.getText().toString();
        if (productName.equals("")) {
            Toast.makeText(context, "Dê um nome ao item", Toast.LENGTH_LONG).show();
        } else if (productCategory.equals("")) {
            Toast.makeText(context, "Escolha uma categoria", Toast.LENGTH_LONG).show();
        } else {
            String quant = quantSet.getText().toString().equals("") ? "0" : quantSet.getText().toString();
            productQuant = CalculeUtils.kiloFormat(Float.valueOf(quant));
            productUnit = unitSet.getText().toString();
            AppManager.getInstance().getPantryScreen().updateProduct(
                    productName, productQuant, productUnit, productCategory);
            dismiss();
        }
    }

    private void executeAddProductOnPantry() {
        productName = nameSet.getText().toString();
        productCategory = categorySet.getText().toString();
        if (productName.equals("")) {
            Toast.makeText(context, "Dê um nome ao item", Toast.LENGTH_LONG).show();
        } else if (productCategory.equals("")) {
            Toast.makeText(context, "Escolha uma categoria", Toast.LENGTH_LONG).show();
        } else {
            if (!AppManager.getInstance().getPantryScreen().existProductOnCategory(productCategory, productName)) {
                productUnit = unitSet.getText().toString().equals("") ? "Unidade" : unitSet.getText().toString();
                String quant = quantSet.getText().toString().equals("") ? "0" : quantSet.getText().toString();
                productQuant = CalculeUtils.kiloFormat(Float.valueOf(quant));
                AppManager.getInstance().getPantryScreen().addProduct(productName, productQuant, productUnit, productCategory);
                dismiss();
            } else {
                Toast.makeText(context, "Nome do item já existe", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void changeDialog(String pCurrentCustomDialogType) {
        CustomDialog customDialog;
        MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity();
        if(pCurrentCustomDialogType.equals(TYPE_ADD_PRODUCT_ON_PANTRY)) {
            customDialog = new CustomDialog(context, " Adicionar Produto                          ", "", TYPE_ADD_PRODUCT_ON_PANTRY);
            customDialog.show();
            customDialog.categorySet.setText(mainActivity.currentCategoryOnPantryEdit);
            customDialog.unitSet.setText(mainActivity.currentUnitOnPantryEdit);
            customDialog.quantSet.setText(mainActivity.currentQuantOnPantryEdit);
            customDialog.nameSet.setText(mainActivity.currentProductNameOnPantryEdit);
        } else {
            customDialog = new CustomDialog(context, " Editar Produto                             ", "", TYPE_EDIT_PRODUCT_ON_PANTRY);
            customDialog.show();
            customDialog.categorySet.setText(mainActivity.currentCategoryOnPantryEdit);
            customDialog.unitSet.setText(mainActivity.currentUnitOnPantryEdit);
            customDialog.quantSet.setText(mainActivity.currentQuantOnPantryEdit);
            customDialog.nameSet.setText(mainActivity.currentProductNameOnPantryEdit);
        }
    }

    private void showMessageLengthLong(String pMessage) {
        Toast.makeText(context, pMessage, Toast.LENGTH_LONG).show();
    }

    private void showMessageLengthShort(String pMessage) {
        Toast.makeText(context, pMessage, Toast.LENGTH_SHORT).show();
    }
}