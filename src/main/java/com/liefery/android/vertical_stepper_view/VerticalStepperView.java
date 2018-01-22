package com.liefery.android.vertical_stepper_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
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

            // Add an ID to enable the state mechanism
            if ( itemView.getId() == NO_ID )
                itemView.setId( i + 1 );

            addView( itemView, MATCH_PARENT, WRAP_CONTENT );
        }
    }
}
