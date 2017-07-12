package test.newborn.com.demos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import test.newborn.com.demos.R;
import test.newborn.com.demos.adapter.RecyclerViewAdapter;
import test.newborn.com.demos.views.VerticalDash;

public class TimePivoteActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private RecyclerViewAdapter mAdapter;
    private VerticalDash mDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pivate);
        findView();
        initViews();
    }

    private void initViews() {
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRv.setAdapter(new RecyclerViewAdapter(this, getList()));
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mDash.setOffset(dy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private List<Integer> getList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add(i);
        }
        return list;
    }

    private void findView() {
        mRv = (RecyclerView) findViewById(R.id.iv);
        mDash = (VerticalDash) findViewById(R.id.dash);
    }
}
