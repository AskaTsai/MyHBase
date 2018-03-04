package hql.node;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNode;
import hql.HQLTestBase;
import org.junit.Test;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 18:35 2018/3/3
 * @modified by:
 */
public class IsEqualNodeTest extends HQLTestBase{
    @Test
    public void test0() {
        HQLNode hqlNode = findStatementHQLNode("isEqualTest");
        para.put("gender","Y");
        para.put("age", 30);
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("Select  * from big table where AND gender=y OR age=30", sb.toString());
    }

    @Test
    public void test1() {
        HQLNode hqlNode = findStatementHQLNode("isEqualTest");
        para.put("gender","N");
        para.put("age", 20);
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("Select  * from big table where", sb.toString());
    }
}
