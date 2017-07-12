package test.newborn.com.myapplication.bean;

import javax.inject.Inject;

/**
 * Created by admin-1 on 17-6-1.
 */

public class ClassRoom {
    Teacher teacher;
    Student student;

    @Inject
    public ClassRoom(Teacher teacher, Student student) {
        this.teacher = teacher;
        this.student = student;
    }

    public String beginClass() {
        return teacher.teach();
    }

    public String studentClass() {
        return student.begin();
    }
}
