package test.newborn.com.myapplication.bean;

import dagger.Component;
import test.newborn.com.myapplication.Main2Activity;

/**
 * Created by admin-1 on 17-6-1.
 */
@Component(modules = MainModule.class)
public interface Main2ActivityC {
    void inject(Main2Activity activity);
}
