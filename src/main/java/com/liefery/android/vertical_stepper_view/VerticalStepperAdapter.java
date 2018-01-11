package com.liefery.android.vertical_stepper_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_ACTIVE;
import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_INACTIVE;

public abstract class VerticalStepperAdapter {
    private VerticalStepperItemView[] itemViews = new VerticalStepperItemView[getCount()];

    public VerticalStepperAdapter() {
    }

    public VerticalStepperItemView getItem( int position ) {
        return itemViews[position];
    }

    public abstract int getCount();

    @NonNull
    public abstract CharSequence getTitle( int position );

    @Nullable
    public abstract CharSequence getSummary( int position );

    public abstract boolean isEditable( int position );

    @NonNull
    public abstract View onCreateContentView(
        Context context,
        int position,
        final VerticalStepperNavigation navigation,
        VerticalStepperItemView parent );

    public VerticalStepperItemView getView( Context context, int position ) {
        if ( itemViews[position] == null ) {
            VerticalStepperItemView itemView = new VerticalStepperItemView(
                context );
            View contentView = onCreateContentView(
                context,
                position,
                onCreateNavigation( position ),
                itemView );
            itemView.setState( position == 0 ? STATE_ACTIVE : STATE_INACTIVE );
            itemView.setCircleNumber( position + 1 );
            itemView.setContentView( contentView );
            itemView.setTitle( getTitle( position ) );
            itemView.setSummary( getSummary( position ) );
            itemView.setEditable( isEditable( position ) );
            itemView.setShowConnectorLine( position < getCount() - 1 );
            itemViews[position] = itemView;
        }

        return itemViews[position];
    }

    private VerticalStepperNavigation onCreateNavigation( int position ) {
        if ( itemViews.length == 1 )
            return new VerticalStepperNavigation( this, -1, position, -1 );
        else if ( position == 0 )
            return new VerticalStepperNavigation(
                this,
                -1,
                position,
                position + 1 );
        else if ( position == getCount() - 1 )
            return new VerticalStepperNavigation(
                this,
                position - 1,
                position,
                -1 );
        else
            return new VerticalStepperNavigation(
                this,
                position - 1,
                position,
                position + 1 );
    }
}