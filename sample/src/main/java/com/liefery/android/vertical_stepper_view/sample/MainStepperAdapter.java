package com.liefery.android.vertical_stepper_view.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.liefery.android.vertical_stepper_view.VerticalStepperAdapter;
import com.liefery.android.vertical_stepper_view.VerticalStepperItemView;

public class MainStepperAdapter extends VerticalStepperAdapter {
    public MainStepperAdapter( Context context ) {
        super( context );
    }

    @NonNull
    @Override
    public CharSequence getTitle( int position ) {
        return "Title " + position;
    }

    @Nullable
    @Override
    public CharSequence getSummary( int position ) {
        return "Summary " + position;
    }

    @Override
    public boolean isEditable( int position ) {
        return position == 1;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Void getItem( int position ) {
        return null;
    }

    @NonNull
    @Override
    public View onCreateContentView( Context context, int position ) {
        View content = new MainItemView( context );

        Button actionContinue = content.findViewById( R.id.action_continue );
        actionContinue.setEnabled( position < getCount() - 1 );
        actionContinue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                next();
            }
        } );

        Button actionBack = content.findViewById( R.id.action_back );
        actionBack.setEnabled( position > 0 );
        actionBack.findViewById( R.id.action_back ).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    previous();
                }
            } );

        return content;
    }
}
