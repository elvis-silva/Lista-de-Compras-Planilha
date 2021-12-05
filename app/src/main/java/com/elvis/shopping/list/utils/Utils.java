package com.elvis.shopping.list.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.elvis.shopping.list.R;
import com.elvis.shopping.list.manager.AppManager;

public class Utils {

    public static ShapeDrawable getBorderDrawable() {
        ShapeDrawable sd = new ShapeDrawable();

        // Specify the shape of ShapeDrawable
        sd.setShape(new RectShape());

        // Specify the border color of shape
        sd.getPaint().setColor(Color.RED);

        // Set the border width
        sd.getPaint().setStrokeWidth(10f);

        // Specify the style is a Stroke
        sd.getPaint().setStyle(Paint.Style.STROKE);

        return sd;
    }

    public static ShapeDrawable getBorderDrawableTransparent() {
        ShapeDrawable sd = new ShapeDrawable();

        // Specify the shape of ShapeDrawable
        sd.setShape(new RectShape());

        // Specify the border color of shape
        sd.getPaint().setColor(Color.TRANSPARENT);

        // Set the border width
        sd.getPaint().setStrokeWidth(10f);

        // Specify the style is a Stroke
        sd.getPaint().setStyle(Paint.Style.STROKE);

        return sd;
    }

    public static void setAnimation(final View view, int animRes) {
        Context context = AppManager.getInstance().getContext();
        Animation animation = AnimationUtils.loadAnimation(context, animRes);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            //    view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(animation);
    }

    public static void openDevPage(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id=6644963173663976446")));
        } catch (android.content.ActivityNotFoundException anfe) {
            viewInBrowser(context, "https://play.google.com/store/apps/dev?id=6644963173663976446");
        }
    }

    public static void rateApp(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            viewInBrowser(context, "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        }
    }

    private static void viewInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (null != intent.resolveActivity(context.getPackageManager())) {
            context.startActivity(intent);
        }
    }
}
