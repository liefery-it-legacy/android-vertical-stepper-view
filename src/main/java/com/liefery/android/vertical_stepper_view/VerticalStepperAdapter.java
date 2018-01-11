package com.liefery.android.vertical_stepper_view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

    public ArrayList<VerticalStepperItemView> getItems() {
        return new ArrayList<>( Arrays.asList( itemViews ) );
    }

    public ArrayList<VerticalStepperItemView> getCompletedItems() {
        ArrayList<VerticalStepperItemView> completed = new ArrayList<>();

        for ( VerticalStepperItemView itemView : itemViews ) {
            if ( itemView.getState() == STATE_COMPLETE )
                completed.add( itemView );
        }

        return completed;
    }

    public ArrayList<VerticalStepperItemView> getInactiveItems() {
        ArrayList<VerticalStepperItemView> completed = new ArrayList<>();

        for ( VerticalStepperItemView itemView : itemViews ) {
            if ( itemView.getState() == STATE_INACTIVE )
                completed.add( itemView );
        }

        return completed;
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