package com.liefery.android.vertical_stepper_view;

import android.content.Context;
import android.graphics.*;
import android.support.v4.content.res.ResourcesCompat;

class ConnectorLineDrawer {
    private final Paint paint = new Paint();

    private final RectF line = new RectF();

    ConnectorLineDrawer( Context context ) {
        int grey = ResourcesCompat.getColor(
            context.getResources(),
            R.color.vertical_stepper_view_grey_100,
            null );
        paint.setColor( grey );
    }

    void adjust( Context context, int width, int height ) {
        line.left = Util.dpToPx( context, 19.5f );
        line.right = Util.dpToPx( context, 20.5f );
        line.top = Util.dpToPx( context, 40 );
        line.bottom = height;
    }

    void draw( Canvas canvas ) {
        canvas.drawRect( line, paint );
    }
}
