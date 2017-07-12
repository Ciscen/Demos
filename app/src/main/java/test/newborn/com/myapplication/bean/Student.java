package test.newborn.com.myapplication.bean;

import javax.inject.Inject;

/**
 * Created by admin-1 on 17-6-1.
 */

public class Student {
    private String string;

    @Inject
    public Student(String a) {
        string = a + a;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String begin() {
        return "我是学生";
    }
}
