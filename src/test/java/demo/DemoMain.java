package demo;

import com.sse.myhbase.client.MyHBaseClient;
import com.sse.myhbase.client.MyHBaseClientImpl;
import com.sse.myhbase.config.HBaseDataSource;
import com.sse.myhbase.config.HBaseTableConfig;
import com.sse.myhbase.config.resource.CachedFileSystemResource;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:41 2017/11/12
 * @modified by:
 */
public class DemoMain {

    final private static Logger logger = Logger.getLogger(DemoMain.class);

    private MyHBaseClient getMyHBaseClient() {
        //HBaseDataSource
        HBaseDataSource hBaseDataSource = new HBaseDataSource();
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(new CachedFileSystemResource("/Users/AskaCai/Documents/Workspace/git/myhbase/src/config/zk_conf"));
        resourceList.add(new CachedFileSystemResource("/Users/AskaCai/Documents/Workspace/git/myhbase/src/config/hbase_conf"));
        hBaseDataSource.setConfigResources(resourceList);
        hBaseDataSource.init();

        //HBaseTableConfig
        HBaseTableConfig hBaseTableConfig = new HBaseTableConfig();
        hBaseTableConfig.setConfigResource(new CachedFileSystemResource("/Users/AskaCai/Documents/Workspace/git/myhbase/src/config/hbasetable_teacher.xml"));
        hBaseTableConfig.init();

        //HBaseClient
        MyHBaseClient client = new MyHBaseClientImpl();
        client.setHBaseDataSource(hBaseDataSource);
        client.setHBaseTableConfig(hBaseTableConfig);

        return client;
    }

    @Test
    public void deleteTable() {
        MyHBaseClient client = getMyHBaseClient();
        client.deleteTable("people");
        client.deleteTable("student");
        client.deleteTable("testtable");
        client.deleteTable("MyTestTableName");
    }

    @Test
    public void createTable() {
        MyHBaseClient client = getMyHBaseClient();
        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf("people"));
        HColumnDescriptor hcd_info = new HColumnDescriptor("info");
        htd.addFamily(hcd_info);
        htd.addFamily(new HColumnDescriptor("data"));
        hcd_info.setMaxVersions(3);
        client.createTable(htd);
    }

    @Test
    public void autoCreateTable() {
        MyHBaseClient client = getMyHBaseClient();
        client.autoCreateTable();
    }

    @Test
    public void putObjectWithAnnotation() {
        MyHBaseClient client = getMyHBaseClient();
        Teacher teacher = new Teacher();
        teacher.setId(13111182);
        teacher.setAge(40);
        teacher.setDate(new Date());
        teacher.setGender(Gender.MALE);
        teacher.setName("蔡老苏");
        client.putObject(new TeacherRowKey(100), teacher);

    }

    @Test
    public void putObjectWithXML() {
        MyHBaseClient client = getMyHBaseClient();
        Student student = new Student();
        student.setId(1510122511);
        student.setAge(25);
        student.setDate(new Date());
        student.setGender(Gender.FEMALE);
        student.setName("蔡顺达");
        client.putObject(new StudentRowKey(110), student);

    }


    @Test
    public void putObjectReflection() {
        MyHBaseClient client = getMyHBaseClient();
        President president = new President();
        president.setId(10010);
        president.setAge(60);
        president.setDate(new Date());
        president.setGender(Gender.FEMALE);
        president.setName("蔡校长");
        client.putObject(new PresidentRowKey(10086), president);
    }

    @Test
    public void findObjectByRowKey(){
        MyHBaseClient client = getMyHBaseClient();
        Student student = client.findObject(new StudentRowKey(110), Student.class);
        logger.info(student);
    }

    @Test
    public void findObjectListByRange() {
        MyHBaseClient client = getMyHBaseClient();
        List<Student> students = client.findObjectList(new StudentRowKey(0), new StudentRowKey(999999), Student.class);
        logger.info(students);
    }

    @Test
    public void deleteObject() {
        MyHBaseClient client = getMyHBaseClient();
        client.deleteObject(new StudentRowKey(110), Student.class);
    }

    @Test
    public void batchDeleteObject() {
        MyHBaseClient client = getMyHBaseClient();
        client.deleteObjectWithRange(new StudentRowKey(110), new StudentRowKey(10086), Student.class);
    }
}
