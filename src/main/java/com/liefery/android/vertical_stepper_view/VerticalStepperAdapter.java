package com.liefery.android.vertical_stepper_view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.*;

public abstract class VerticalStepperAdapter {
    private VerticalStepperItemView[] itemViews = new VerticalStepperItemView[getCount()];

    public VerticalStepperItemView getItem( int position ) {
        return itemViews[position];
    }

    public abstract int getCount();

    @NonNull
    public abstract CharSequence getTitle( int position );

    @Nullable
    public abstract CharSequence getSummary( int position );

    public abstract boolean isEditable( int position );

    public int getInitialState( int position ) {
        return position == 0 ? STATE_ACTIVE : STATE_INACTIVE;
    }

    public int getCircleNumber( int position ) {
        return position + 1;
    }

    public boolean showConnectorLine( int position ) {
        return position < getCount() - 1;
    }

    public void notifyDataSetChanged() {
        for ( int i = 0; i < itemViews.length; i++ ) {
            applyData( itemViews[i], i );
        }
    }

    @NonNull
    public abstract View onCreateContentView(
        Context context,
        int position,
        final VerticalStepperNavigation navigation,
        final VerticalStepperItemView parent );

    public VerticalStepperItemView getView( Context context, int position ) {
        if ( itemViews[position] == null ) {
            VerticalStepperItemView itemView = new VerticalStepperItemView(
                context );
            initializeData( context, itemView, position );
            itemViews[position] = itemView;
        }

        return itemViews[position];
    }

    private void initializeData(
        Context context,
        VerticalStepperItemView itemView,
        int position ) {
        View contentView = onCreateContentView(
            context,
            position,
            onCreateNavigation( position ),
            itemView );
        itemView.setContentView( contentView );

        itemView.setState( getInitialState( position ) );
        applyData( itemView, position );
    }

    private void applyData( VerticalStepperItemView itemView, int position ) {
        itemView.setCircleNumber( getCircleNumber( position ) );
        itemView.setTitle( getTitle( position ) );
        itemView.setSummary( getSummary( position ) );
        itemView.setEditable( isEditable( position ) );
        itemView.setShowConnectorLine( showConnectorLine( position ) );
    }

    public VerticalStepperItemView[] getItems() {
        return Arrays.copyOf( itemViews, itemViews.length );
    }

    public VerticalStepperItemView[] getCompletedItems() {
        ArrayList<VerticalStepperItemView> completed = new ArrayList<>();

        for ( VerticalStepperItemView itemView : itemViews ) {
            if ( itemView.getState() == STATE_COMPLETE )
                completed.add( itemView );
        }

        return completed.toArray( new VerticalStepperItemView[completed.size()] );
    }

    public VerticalStepperItemView[] getInactiveItems() {
        ArrayList<VerticalStepperItemView> completed = new ArrayList<>();

        for ( VerticalStepperItemView itemView : itemViews ) {
            if ( itemView.getState() == STATE_INACTIVE )
                completed.add( itemView );
        }

        return completed.toArray( new VerticalStepperItemView[completed.size()] );
    }

    @Nullable
    public VerticalStepperItemView getActiveItem() {
        for ( VerticalStepperItemView itemView : itemViews ) {
            if ( itemView.getState() == STATE_ACTIVE )
                return itemView;
        }

        return null;
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

    Bundle saveInstanceState() {
        Bundle bundle = new Bundle( 2 );

        Parcelable[] states = new Parcelable[itemViews.length];

        for ( int i = 0; i < states.length; i++ ) {
            states[i] = itemViews[i].onSaveInstanceState();
        }

        bundle.putParcelableArray( "states", states );

        return bundle;
    }

    void restoreInstanceState( Bundle bundle ) {
        Parcelable[] states = bundle.getParcelableArray( "states" );

        for ( int i = 0; i < states.length; i++ ) {
            itemViews[i].onRestoreInstanceState( states[i] );
        }
    }
}