package hql;

import com.sse.myhbase.config.HBaseTableConfigParser;
import com.sse.myhbase.hql.HBaseQuery;
import com.sse.myhbase.hql.HQLNode;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 18:36 2018/3/3
 * @modified by:
 */
public class HQLTestBase {

    final private static Logger logger = Logger.getLogger(HQLTestBase.class);
    final private static String xmlFilePath = "/Users/AskaCai/Documents/Workspace/git/myhbase/src/config/hql.xml";

    protected StringBuilder sb = new StringBuilder();
    protected Map<String, Object> para = new HashMap<>();
    protected Map<Object, Object> context = new HashMap<>();

    @Before
    public void before() {
        sb = new StringBuilder();
        para = new HashMap<>();
        context = new HashMap<>();
    }

    /**
     * @Author: Cai Shunda
     * @Description: 在xml文件中找到指定id的HQL节点，找不到的话返回null
     * @Param:
     * @Date: 20:29 2018/3/3
     */
    protected HQLNode findStatementHQLNode(String id) {
        List<HBaseQuery> hBaseQueries = HBaseTableConfigParser.parseHBaseQuery(xmlFilePath);

        for (HBaseQuery hBaseQuery : hBaseQueries) {
            if (hBaseQuery.getId().equals(id)) {
                return hBaseQuery.getHqlNode();
            }
        }
        logger.info("can not find statement with id=" + id);
        return null;
    }

    /**
     * @Author: Cai Shunda
     * @Description: 判断HBase查询语句是否相同
     * @Param:
     * @Date: 21:15 2018/3/3
     */
    protected void  assertEqualHQL(String s1, String s2) {
        String[] s1Array = s1.split("[ \n\r\t]");
        List<String> s1List = new ArrayList<>();
        for (String s : s1Array) {
            if (!s.isEmpty()) {
                s1List.add(s);
            }
        }

        String[] s2Array = s2.split("[ \n\r\t]");
        List<String> s2List = new ArrayList<>();
        for (String s : s2Array) {
            if (!s.isEmpty()) {
                s2List.add(s);
            }
        }


        logger.info("s1 List=" + s1List);
        logger.info("s2 List=" + s2List);

        Assert.assertTrue(s1List.size() == s2List.size());

        for (int i = 0; i < s1List.size(); i++) {
            Assert.assertEquals(s1List.get(i), s2List.get(i));
        }
    }
}
