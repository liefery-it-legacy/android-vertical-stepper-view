package com.liefery.android.vertical_stepper_view;

import android.support.annotation.Nullable;

import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_ACTIVE;
import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_COMPLETE;
import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_INACTIVE;

public class VerticalStepperNavigation {
    private final VerticalStepperAdapter adapter;

    private final int previous;

    private final int current;

    private final int next;

    public VerticalStepperNavigation(
        VerticalStepperAdapter adapter,
        int previous,
        int current,
        int next ) {
        this.adapter = adapter;
        this.previous = previous;
        this.current = current;
        this.next = next;
    }

    public boolean hasPrevious() {
        return previous != -1;
    }

    @Nullable
    public VerticalStepperItemView previous() {
        if ( hasPrevious() ) {
            adapter.getItem( current ).setState( STATE_INACTIVE );
            adapter.getItem( previous ).setState( STATE_ACTIVE );
            return adapter.getItem( previous );
        } else
            return null;
    }

    public boolean hasNext() {
        return next != -1;
    }

    @Nullable
    public VerticalStepperItemView next() {
        if ( hasNext() ) {
            adapter.getItem( current ).setState( STATE_COMPLETE );
            adapter.getItem( next ).setState( STATE_ACTIVE );
            return adapter.getItem( next );
        } else
            return null;
    }

    public boolean isLast() {
        return next == -1;
    }
}
