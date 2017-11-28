package demo;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:01 2017/11/15
 * @modified by:
 */
public class Student {
    int id;
    String name;
    Date date;
    Gender gender;
    int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
