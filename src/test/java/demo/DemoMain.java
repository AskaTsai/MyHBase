package demo;

import com.sse.myhbase.client.MyHBaseClient;
import com.sse.myhbase.client.MyHBaseClientImpl;
import com.sse.myhbase.config.HBaseDataSource;
import com.sse.myhbase.config.HBaseTableConfig;
import com.sse.myhbase.config.resource.CachedFileSystemResource;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 22:41 2017/11/12
 * @Modified by:
 */
public class DemoMain {

    private MyHBaseClient getMyHBaseClient(){
        //HBaseDataSource
        HBaseDataSource hBaseDataSource = new HBaseDataSource();
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(new CachedFileSystemResource("/Users/AskaCai/Documents/Workspace/git/myhbase/src/config/zk_conf"));
        resourceList.add(new CachedFileSystemResource("/Users/AskaCai/Documents/Workspace/git/myhbase/src/config/hbase_conf"));
        hBaseDataSource.setConfigResources(resourceList);
        hBaseDataSource.init();

        //HBaseTableConfig
        HBaseTableConfig hBaseTableConfig = new HBaseTableConfig();
        hBaseTableConfig.setConfigResource(new CachedFileSystemResource("/Users/AskaCai/Documents/Workspace/git/myhbase/src/config/hbasetable.xml"));
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
    public void putObjectWithXML() {
        MyHBaseClient client = getMyHBaseClient();
        Student student = new Student();
        student.setId(1510122511);
        student.setAge(25);
        student.setDate(new Date());
        student.setGender(Gender.FEMALE);
        student.setName("蔡顺达");
        client.putObject(new StudentRowKey(110) ,student);
    }

}
