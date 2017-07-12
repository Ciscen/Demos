package test.newborn.com.myapplication;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import test.newborn.com.myapplication.bean.ClassRoom;
import test.newborn.com.myapplication.bean.DaggerMain2ActivityC;
import test.newborn.com.myapplication.bean.Student;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    Button bt;
    @Inject
    ClassRoom cr;

    @Inject
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bt = (Button) findViewById(R.id.button2);
        bt.setOnClickListener(this);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) bt.getLayoutParams();

        bt.setLayoutParams(layoutParams);
    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(String.format("内容条目第%d条", i));
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        DaggerMain2ActivityC.create().inject(this);
    }
}
