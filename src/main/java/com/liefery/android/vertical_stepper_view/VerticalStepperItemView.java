package com.liefery.android.vertical_stepper_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class VerticalStepperItemView extends FrameLayout {
    public static int STATE_INACTIVE = 0;

    public static int STATE_ACTIVE = 1;

    public static int STATE_COMPLETE = 2;

    private boolean showConnectorLine = true;

    private boolean editable = false;

    private VerticalStepperItemCircleView circle;

    private int number;

    private LinearLayout wrapper;

    private TextView title;

    private TextView summary;

    private FrameLayout contentWrapper;

    private ConnectorLineDrawer connector;

    private int state = STATE_INACTIVE;

    public VerticalStepperItemView( Context context ) {
        super( context );
        initialize( context );
    }

    public VerticalStepperItemView( Context context, AttributeSet attrs ) {
        super( context, attrs );
        initialize( context );
    }

    public VerticalStepperItemView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        initialize( context );
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public VerticalStepperItemView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr,
        int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
        initialize( context );
    }

    private void initialize( Context context ) {
        setWillNotDraw( false );
        setClipChildren( false );
        setClipToPadding( false );

        int padding = (int) ViewUtil.dpToPx( context, 8 );
        setPadding( padding, padding, padding, 0 );

        LayoutInflater.from( context ).inflate(
            R.layout.vertical_stepper_view_item,
            this,
            true );

        circle = (VerticalStepperItemCircleView) findViewById( R.id.vertical_stepper_view_item_circle );
        wrapper = (LinearLayout) findViewById( R.id.vertical_stepper_view_item_wrapper );
        title = (TextView) findViewById( R.id.vertical_stepper_view_item_title );
        summary = (TextView) findViewById( R.id.vertical_stepper_view_item_summary );
        contentWrapper = (FrameLayout) findViewById( R.id.vertical_stepper_view_item_content_wrapper );

        connector = new ConnectorLineDrawer( context );
    }

    public VerticalStepperView getVerticalStepperView() {
        return (VerticalStepperView) getParent();
    }

    public void setShowConnectorLine( boolean show ) {
        showConnectorLine = show;
    }

    public boolean getShowConnectorLine() {
        return showConnectorLine;
    }

    public void setEditable( boolean editable ) {
        this.editable = editable;

        if ( state == STATE_COMPLETE )
            toggleEditMode();
    }

    public boolean isEditable() {
        return editable;
    }

    public void setCircleNumber( int number ) {
        this.number = number;

        if ( state != STATE_COMPLETE )
            circle.setNumber( number );
    }

    public void setTitle( CharSequence title ) {
        this.title.setText( title );
    }

    public void setSummary( CharSequence summary ) {
        this.summary.setText( summary );
    }

    public View getContentView() {
        return contentWrapper.getChildAt( 0 );
    }

    public void setContentView( View view ) {
        contentWrapper.removeAllViews();

        if ( view.getId() == NO_ID )
            view.setId( ViewUtil.generateViewId() );

        contentWrapper.addView( view, MATCH_PARENT, WRAP_CONTENT );
    }

    public void setState( int state ) {
        this.state = state;

        if ( state == STATE_INACTIVE )
            setStateInactive();
        else if ( state == STATE_ACTIVE )
            setStateActive();
        else
            setStateComplete();
    }

    public int getState() {
        return state;
    }

    private void setStateInactive() {
        disableEditMode();
        setMarginBottom( false );
        circle.setNumber( number );
        circle.setBackgroundInactive();
        title.setTextColor( ResourcesCompat.getColor(
            getResources(),
            R.color.vertical_stepper_view_black_38,
            null ) );
        title.setTypeface( title.getTypeface(), Typeface.NORMAL );
        summary.setVisibility( View.GONE );
        contentWrapper.setVisibility( View.GONE );
    }

    private void setStateActive() {
        disableEditMode();
        setMarginBottom( true );
        circle.setNumber( number );
        circle.setBackgroundActive();
        title.setTextColor( ResourcesCompat.getColor(
            getResources(),
            R.color.vertical_stepper_view_black_87,
            null ) );
        title.setTypeface( title.getTypeface(), Typeface.BOLD );
        summary.setVisibility( View.GONE );
        contentWrapper.setVisibility( View.VISIBLE );
    }

    private void setStateComplete() {
        setMarginBottom( false );
        circle.setBackgroundActive();

        toggleEditMode();

        title.setTextColor( ResourcesCompat.getColor(
            getResources(),
            R.color.vertical_stepper_view_black_87,
            null ) );
        title.setTypeface( title.getTypeface(), Typeface.BOLD );
        summary.setVisibility( TextUtils.isEmpty( summary.getText() ) ? View.GONE
                        : View.VISIBLE );
        contentWrapper.setVisibility( View.GONE );
    }

    private void toggleEditMode() {
        if ( isEditable() )
            enabledEditMode();
        else
            disableEditMode();
    }

    private void enabledEditMode() {
        circle.setIconEdit();
        setForeground( ViewUtil.getSelectableItemBackground( getContext() ) );
        setClickable( true );
        setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View view ) {
                VerticalStepperItemView active = getVerticalStepperView()
                                .getAdapter().getActiveItem();

                if ( active != null ) {
                    active.setState( STATE_INACTIVE );
                }

                VerticalStepperItemView.this.setState( STATE_ACTIVE );
            }
        } );
    }

    private void disableEditMode() {
        circle.setIconCheck();
        setForeground( null );
        setClickable( false );
        setOnClickListener( null );
    }

    private void setMarginBottom( boolean active ) {
        MarginLayoutParams params = (MarginLayoutParams) wrapper
                        .getLayoutParams();

        if ( !getShowConnectorLine() )
            params.bottomMargin = 0;
        else if ( active )
            params.bottomMargin = (int) ViewUtil.dpToPx( getContext(), 48 );
        else
            params.bottomMargin = (int) ViewUtil.dpToPx( getContext(), 40 );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );

        if ( showConnectorLine )
            connector.draw( canvas );
    }

    @Override
    protected void onSizeChanged(
        int width,
        int height,
        int oldWidth,
        int oldHeight ) {
        super.onSizeChanged( width, height, oldWidth, oldHeight );

        connector.adjust( getContext(), width, height );
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        SparseArray<Parcelable> container = new SparseArray<>( 1 );
        getContentView().saveHierarchyState( container );

        Bundle bundle = new Bundle( 4 );
        bundle.putParcelable( "super", super.onSaveInstanceState() );
        bundle.putInt( "content-id", getContentView().getId() );
        bundle.putSparseParcelableArray( "content-container", container );
        bundle.putInt( "state", state );
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ) {
        if ( state instanceof Bundle ) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState( bundle.getParcelable( "super" ) );
            getContentView().setId( bundle.getInt( "content-id" ) );
            getContentView().restoreHierarchyState(
                bundle.getSparseParcelableArray( "content-container" ) );
            setState( bundle.getInt( "state" ) );
        } else
            super.onRestoreInstanceState( state );
    }

    @Override
    protected void dispatchSaveInstanceState( SparseArray<Parcelable> container ) {
        dispatchFreezeSelfOnly( container );
    }

    @Override
    protected void dispatchRestoreInstanceState(
        SparseArray<Parcelable> container ) {
        dispatchThawSelfOnly( container );
    }
}
