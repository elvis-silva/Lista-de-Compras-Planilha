package com.elvis.shopping.list;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elvis.shopping.list.data.DataHandler;
import com.elvis.shopping.list.impl.IActivity;
import com.elvis.shopping.list.manager.AppManager;
import com.elvis.shopping.list.model.ListModel;
import com.elvis.shopping.list.model.ListName;
import com.elvis.shopping.list.utils.CustomDialog;
import com.elvis.shopping.list.view.MyButton;
import com.elvis.shopping.list.view.MySingleButton;
import com.elvis.shopping.list.view.MyTextView;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingLists implements IActivity {

    private int id = 0;
//    private String listName;
    private Context context;
    public String currentTitle;
    private MyTextView currentBtn;
    public LinearLayout container, principal;
    private MainActivity mainActivity;
    private DataHandler dataHandler;
    private ArrayList<String> listNames = new ArrayList<>();
    private int backControl = 0;

    public ShoppingLists(Context pContext) {
        context = pContext;

        mainActivity = (MainActivity)AppManager.getInstance().getActivity();
        dataHandler = mainActivity.dataHandler;
    }

    @Override
    public void createView(View pView) {
        backControl = 0;

        final int currentBgColor = mainActivity.currentBgColor;
        principal = pView.findViewById(R.id.principal_shopping_list);
        principal.setBackgroundColor(currentBgColor);
        container = pView.findViewById(R.id.container);
        container.setBackgroundColor(currentBgColor);
//        TextView title = pView.findViewById(R.id.title);
//        title.setTextColor(mainActivity.currentTitlesFontColor);

        ArrayList<String> namesList = new ArrayList<>();

        final Cursor cursor = dataHandler.returnListNameData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            namesList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();

        Collections.sort(namesList, String.CASE_INSENSITIVE_ORDER);

        int i = 0;
        while (i < namesList.size()) {
            MyTextView btn = new MyTextView(context);
            btn.setText(namesList.get(i));
            btn.setGravity(Gravity.CENTER);
            btn.setTextSize(18f);
            btn.setTextColor(AppManager.getInstance().getCartScreen().currentProductAddedFontColor);
            btn.setPadding(16,16,16,16);
            btn.setBackgroundResource(R.drawable.btn_dialog_bg);
            btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        view.setBackgroundColor(context.getResources().getColor(R.color.custom_color));
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.setBackgroundColor(currentBgColor);
                        currentBtn = (MyTextView) view;
                        String listName = currentBtn.getText().toString();
                        currentTitle = listName;
                        CustomDialog customDialog = new CustomDialog(
                                context, listName, "", CustomDialog.TYPE_LOAD_LIST);
                        customDialog.show();
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                        view.setBackgroundColor(currentBgColor);
                    }
                    return view.performClick();
                }
            });
            btn.setBackgroundColor(currentBgColor);
            container.addView(btn);
            i++;
        }
    }

    public void restoreBtnBackground() {
        if (currentBtn != null) currentBtn.setBackgroundResource(R.drawable.btn);
    }

    public void showDialogRename() {
        CustomDialog customDialog = new CustomDialog(
                context, "Renomear " + currentTitle, "", CustomDialog.TYPE_RENAME_LIST);
        customDialog.show();
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
            mainActivity.showOfferWall();
        }
    }

    public void setId(int pID) {
        id = pID;
    }

    public void renameList(String pCurrentName, String pNewName) {
        dataHandler.renameList(pCurrentName, pNewName);

        currentBtn.setText(pNewName);

        if(dataHandler.getListLoaded().equals(currentTitle)) {
            dataHandler.deleteListLoaded();
            dataHandler.addListLoaded(pNewName);
        }

        currentTitle = pNewName;

        if(mainActivity.currentListName.equals(pCurrentName)) {
            AppManager.getInstance().getCartScreen().loadList(currentTitle);
            mainActivity.currentListName = currentTitle;
        }

        sortList();
    }

    public void removeList() {
        Cursor cursor = dataHandler.returnListNameData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListName listNameData = dataHandler.parseListName(cursor);
            if(listNameData.getName().equals(currentTitle)) {
                dataHandler.deleteListName(listNameData);
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();

        cursor = dataHandler.returnSaveListData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListModel listModel = dataHandler.parseListModel(cursor);
            if(listModel.getListName().equals(currentTitle)) {
                dataHandler.deleteListModel(listModel);
            }
            cursor.moveToNext();
        }
        cursor.close();

        container.removeView(currentBtn);

        if (dataHandler.getListLoaded().equals(currentTitle)) dataHandler.deleteListLoaded();

        mainActivity.currentListName = dataHandler.getListLoaded();

        Toast.makeText(context, currentTitle + " removida", Toast.LENGTH_SHORT).show();
    }

    public void sortList() {
        MyTextView btn;
        int i = 0;
        while (i < container.getChildCount()) {
            btn = (MyTextView) container.getChildAt(i);
            listNames.add(btn.getText().toString());
            i++;
        }
        Collections.sort(listNames, String.CASE_INSENSITIVE_ORDER);

        i = 0;
        while (i < container.getChildCount()) {
            btn = (MyTextView) container.getChildAt(i);
            btn.setText(listNames.get(i));
//            btn.setBackgroundResource(R.drawable.btn);
            if(listNames.get(i).equals(currentTitle)) {
//                btn.setBackgroundResource(R.drawable.btn_pressed);
                currentBtn = btn;
            }
            i++;
        }

        listNames.clear();
    }
}
