package com.liefery.android.vertical_stepper_view.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.liefery.android.vertical_stepper_view.VerticalStepperView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main );

        VerticalStepperView stepper = findViewById( R.id.stepper_list );
        stepper.setStepperAdapter( new MainStepperAdapter( this ) );
    }
}