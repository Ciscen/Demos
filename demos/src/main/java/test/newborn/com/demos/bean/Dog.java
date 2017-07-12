package test.newborn.com.demos.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xiaochongzi on 17-7-3
 */
@Entity
public class Dog {
    @Id(autoincrement = true)
    private Long id;

    private String color;
    private String name;
    @Transient
    private int age;
    @Generated(hash = 290580665)
    public Dog(Long id, String color, String name) {
        this.id = id;
        this.color = color;
        this.name = name;
    }
    @Generated(hash = 2001499333)
    public Dog() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }


}
