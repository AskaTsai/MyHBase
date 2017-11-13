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
    public void testCreatTable() throws IOException {
        MyHBaseClient client = getMyHBaseClient();
        HBaseAdmin hBaseAdmin = client.getHBaseAdmin();
        if (hBaseAdmin.tableExists("people")) {
            System.out.println("表已经存在");
            return;
        }
        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf("people"));
        HColumnDescriptor hcd_info = new HColumnDescriptor("info");
        htd.addFamily(hcd_info);
        htd.addFamily(new HColumnDescriptor("data"));
        hcd_info.setMaxVersions(3);
        hBaseAdmin.createTable(htd);
        hBaseAdmin.close();
        System.out.println("表创建完成！！");
    }
}
