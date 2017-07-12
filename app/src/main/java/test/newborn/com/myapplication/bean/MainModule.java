package test.newborn.com.myapplication.bean;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin-1 on 17-6-1.
 */
@Module
public class MainModule {
//    @Provides
//    public Student provideStudent(String a) {
//        Student student = new Student();
//        student.setString(a);
//        return student;
//    }

    @Provides
    public String provideString() {
        return "hahhahah";
    }
}
