package test.newborn.com.demos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.Random;

import test.newborn.com.demos.R;
import test.newborn.com.demos.bean.Dog;
import test.newborn.com.demos.gen.DaoSession;
import test.newborn.com.demos.gen.DogDao;
import test.newborn.com.demos.utils.DBManager;


public class GreenDaoActivity extends AppCompatActivity implements View.OnClickListener {

    Button insert, delete, update, query, addField;
    TextView mTextView;
    private String[] name1 = {"小", "大", "黑", "阿"};
    private String[] name2 = {"红", "福", "狗", "豹", "特"};
    private String[] colors = {"红", "绿", "黑", "白", "黄"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greed_dao);
        findViews();
        initListener();
    }

    private void initListener() {
        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        query.setOnClickListener(this);
        addField.setOnClickListener(this);
    }

    private void findViews() {
        insert = (Button) findViewById(R.id.insert);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        query = (Button) findViewById(R.id.query);
        addField = (Button) findViewById(R.id.addField);
        mTextView = (TextView) findViewById(R.id.content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                doInsert();
                break;
            case R.id.delete:
                doDelete();
                break;
            case R.id.update:
                doUpdate();
                break;
            case R.id.query:
                doQuery();
                break;
            case R.id.addField:
                doAddField();
                break;
        }
    }

    //添加字段
    private void doAddField() {

    }

    //查
    private void doQuery() {
        List<Dog> dogs = DBManager.getInstance().getmDaoSession().getDogDao().loadAll();
        String str = "";
        for (int i = 0; i < dogs.size(); i++) {
            str += "id = " + dogs.get(i).getId() + "-----name = " + dogs.get(i).getName() + "-----color = " + dogs.get(i).getColor() + "\n";
        }
        mTextView.setText(str);
    }

    //改
    private void doUpdate() {
        DogDao dogDao = DBManager.getInstance().getmDaoSession().getDogDao();
        Dog dog = dogDao.queryBuilder().orderDesc(DogDao.Properties.Id).limit(1).build().unique();
//        Dog dog2 = dogDao.queryBuilder().orderAsc(DogDao.Properties.Id).limit(1).build().unique();
        dog.setName("大大大" + new Random().nextInt(100));
//        Dog dd = new Dog();
//        dd.setId(dog.getId());
//        dd.setName("dddd");
        dogDao.update(dog);

    }

    //删
    private void doDelete() {
        DogDao dogDao = DBManager.getInstance().getmDaoSession().getDogDao();
        Dog dog = dogDao.queryBuilder().orderDesc(DogDao.Properties.Id).limit(1).build().unique();
        dogDao.delete(dog);
        Toast.makeText(this, "成功删除" + dog.getName(), Toast.LENGTH_SHORT).show();
    }

    //增
    private void doInsert() {
        Dog dog = new Dog();
        dog.setName(getName());
        dog.setColor(getColor());
        DaoSession daoSession = DBManager.getInstance().getmDaoSession();
        daoSession.getDogDao().insert(dog);
    }

    private String getColor() {
        return colors[new Random().nextInt(colors.length)];
    }

    private String getName() {
        return name1[new Random().nextInt(name1.length)] + name2[new Random().nextInt(name2.length)];
    }
}
