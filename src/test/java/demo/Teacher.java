package demo;

import com.sse.myhbase.client.HBaseColumn;
import com.sse.myhbase.client.HBaseTable;

import java.util.Date;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 21:29 2017/11/21
 * @modified by:
 */
@HBaseTable(defaultFamily = "MyDefaultFamily")
public class Teacher {
    @HBaseColumn(qualifier = "id", family = "MyRecordFamily")
    int id;

    @HBaseColumn(qualifier = "name")
    String name;

    @HBaseColumn(qualifier = "date")
    Date date;

    @HBaseColumn(qualifier = "gender")
    Gender gender;

    @HBaseColumn(qualifier = "age")
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
