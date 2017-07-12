package test.newborn.com.demos.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.newborn.com.demos.R;

public class BottomSheetActivity extends AppCompatActivity {

    BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        behavior = BottomSheetBehavior.from(findViewById(R.id.scroll));
    }

    public void bottomeSheet(View view) {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}
