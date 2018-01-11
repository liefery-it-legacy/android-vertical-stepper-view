package com.liefery.android.vertical_stepper_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class VerticalStepperView extends LinearLayout {
    private VerticalStepperAdapter adapter;

    public VerticalStepperView( Context context ) {
        super( context );
        initialize( context );
    }

    public VerticalStepperView( Context context, AttributeSet attrs ) {
        super( context, attrs );
        initialize( context );
    }

    public VerticalStepperView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        initialize( context );
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public VerticalStepperView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr,
        int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
        initialize( context );
    }

    private void initialize( Context context ) {
        setOrientation( VERTICAL );
    }

    public VerticalStepperAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter( VerticalStepperAdapter adapter ) {
        this.adapter = adapter;

        removeAllViews();

        int count = adapter.getCount();

        for ( int i = 0; i < count; i++ ) {
            VerticalStepperItemView itemView = adapter
                            .getView( getContext(), i );
            addView( itemView, MATCH_PARENT, WRAP_CONTENT );
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Bundle adapterState = null;

        if ( adapter != null )
            adapterState = adapter.saveInstanceState();

        Bundle bundle = new Bundle( 2 );
        bundle.putParcelable( "super", superState );
        bundle.putBundle( "adapter", adapterState );
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ) {
        if ( state instanceof Bundle ) {
            Bundle bundle = (Bundle) state;

            Parcelable superState = bundle.getParcelable( "super" );
            super.onRestoreInstanceState( superState );

            Bundle adapterState = bundle.getBundle( "adapter" );
            if ( adapter != null && adapterState != null ) {
                adapter.restoreInstanceState( adapterState );
            }
        } else
            super.onRestoreInstanceState( state );
    }
}
