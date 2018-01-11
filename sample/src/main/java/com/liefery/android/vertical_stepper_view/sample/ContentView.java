package com.liefery.android.vertical_stepper_view.sample;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class ContentView extends LinearLayout {
    public ContentView( Context context ) {
        super( context );
        initialize( context );
    }

    public ContentView( Context context, AttributeSet attrs ) {
        super( context, attrs );
        initialize( context );
    }

    public ContentView( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        initialize( context );
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public ContentView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr,
        int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
        initialize( context );
    }

    private void initialize( Context context ) {
        setOrientation( VERTICAL );
        setClipChildren( false );
        LayoutInflater.from( context ).inflate( R.layout.item, this, true );
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.wtf( "WTF", "SAVING CONTENT VIEW STATE" );

        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ) {
        super.onRestoreInstanceState( state );

        Log.wtf( "WTF", "RESTORING CONTENT VIEW STATE" );
    }
}
