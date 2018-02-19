package com.liefery.android.vertical_stepper_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import java.util.concurrent.atomic.AtomicInteger;

class Util {
    static float dpToPx( Context context, float dp ) {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.getResources().getDisplayMetrics() );
    }

    static int getThemeColor( Context context, int attr ) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(
            typedValue.data,
            new int[] { attr } );
        int color = a.getColor( 0, 0 );
        a.recycle();
        return color;
    }
}
