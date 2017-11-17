package com.sse.myhbase.client;

import com.sse.myhbase.config.HBaseTableConfig;
import com.sse.myhbase.config.HBaseTableSchema;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.log4j.Logger;

import java.util.List;


/**
 * @Author: Cai Shunda
 * @Description: MyHBaseClinet的默认实现
 * @Date: Created in 21:51 2017/11/1
 * @Modified by:
 */
public class MyHBaseClientImpl extends MyHBaseClientBase{
    private Logger logger = Logger.getLogger(MyHBaseClientImpl.class);

    @Override
    public void createTable(HTableDescriptor tableDescriptor) {
        Util.checkNull(tableDescriptor);
        try {
            HBaseAdmin admin = getHBaseAdmin();
            if (admin.tableExists(tableDescriptor.getTableName())) {
                logger.info("hbase table \"" + tableDescriptor.getTableName() + "\" exist!");
                return;
            }
            admin.createTable(tableDescriptor);
            HTableDescriptor feedback = admin.getTableDescriptor(tableDescriptor.getTableName());
            logger.info("create hbase table " + feedback);
        } catch (Exception e) {
            logger.error(e);
            throw new MyHBaseException(e);
        }
    }

    @Override
    public void deleteTable(String tableName) {
        Util.checkEmptyString(tableName);
        //1. disable 2. delete
        try {
            HBaseAdmin admin = getHBaseAdmin();
            if (admin.tableExists(tableName)) {
                if (!admin.isTableDisabled(tableName)) {
                    admin.disableTable(tableName);
                }
                admin.deleteTable(tableName);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new MyHBaseException(e);
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 自动创建HBaseTableConfig设置的表
     * @Date: 20:52 2017/11/17
     */
    @Override
    public void autoCreateTable() {
        if(isAutoCreate()){
            HBaseTableConfig tableConfig = getHBaseTableConfig();
            Util.checkNull(tableConfig);
            HBaseTableSchema schema = tableConfig.gethBaseTableSchema();
            Util.checkNull(schema);
            HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(schema.getTableName()));
            List<String> familys = tableConfig.getAllConfigedFamilys();
            for (String family : familys) {
                HColumnDescriptor hcd = new HColumnDescriptor(family);
                htd.addFamily(hcd);
            }
            logger.info("trying to auto create table \'" + schema.getTableName() +"\'");
            createTable(htd);
        } else {
            logger.info("this table cannot be auto created.");
        }
    }
}
