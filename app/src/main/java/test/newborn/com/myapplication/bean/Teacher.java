package test.newborn.com.myapplication.bean;

import javax.inject.Inject;

/**
 * Created by admin-1 on 17-6-1.
 */

public class Teacher {
    @Inject
    public Teacher() {
    }

    public String teach() {
        return "上课了！！";
    }
}
