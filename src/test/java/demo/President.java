package demo;

import java.util.Date;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:29 2017/11/21
 * @modified by:
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
