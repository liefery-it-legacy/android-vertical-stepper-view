package com.liefery.android.vertical_stepper_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

class ViewUtil {
    static float dpToPx( Context context, float dp ) {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.getResources().getDisplayMetrics() );
    }

    static Drawable getSelectableItemBackground( Context context ) {
        int[] attrs = new int[] { R.attr.selectableItemBackground };
        TypedArray typedArray = context.obtainStyledAttributes( attrs );
        Drawable drawable = typedArray.getDrawable( 0 );
        typedArray.recycle();
        return drawable;
    }
}
