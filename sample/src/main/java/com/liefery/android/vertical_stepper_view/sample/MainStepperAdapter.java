package com.liefery.android.vertical_stepper_view.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.liefery.android.vertical_stepper_view.VerticalStepperAdapter;
import com.liefery.android.vertical_stepper_view.VerticalStepperItemView;
import com.liefery.android.vertical_stepper_view.VerticalStepperNavigation;

import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_COMPLETE;

public class MainStepperAdapter extends VerticalStepperAdapter {
    @Override
    public int getCount() {
        return 5;
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
        return position == 2;
    }

    @NonNull
    @Override
    public View onCreateContentView(
        Context context,
        int position,
        final VerticalStepperNavigation navigation,
        final VerticalStepperItemView parent ) {
        View content = new MainItemView( context );

        Button actionContinue = content.findViewById( R.id.action_continue );
        actionContinue.setEnabled( navigation.hasNext() || navigation.isLast() );
        actionContinue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( navigation.isLast() )
                    parent.setState( STATE_COMPLETE );
                else
                    navigation.next();
            }
        } );

        Button actionBack = content.findViewById( R.id.action_back );
        actionBack.setEnabled( navigation.hasPrevious() );
        actionBack.findViewById( R.id.action_back ).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    navigation.previous();
                }
            } );

        return content;
    }
}
