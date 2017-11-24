package demo;

import java.util.Date;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 22:29 2017/11/21
 * @Modified by:
 */
public class President {
    int id;
    String name;
    Date date;
    Gender gender;
    int age;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
