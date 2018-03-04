package hql.node;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNode;
import hql.HQLTestBase;
import org.junit.Test;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 17:52 2018/3/4
 * @modified by:
 */
public class DynamicTest extends HQLTestBase{
    @Test
    public void test0() {
        HQLNode hqlNode = findStatementHQLNode("dynamicTest1");
        para.put("lovely?", "Y");
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("what a and lovely day", sb.toString() );
    }

    @Test
    public void test1() {
        HQLNode hqlNode = findStatementHQLNode("dynamicTest1");
        para.put("lovely?", "Y");
        para.put("nice?", "Y");
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("what a and lovely and nice day", sb.toString() );
    }

    @Test
    public void test2() {
        HQLNode hqlNode = findStatementHQLNode("dynamicTest2");
        para.put("lovely?", "Y");
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("what a and lovely day", sb.toString() );
    }

    @Test
    public void test3() {
        HQLNode hqlNode = findStatementHQLNode("dynamicTest2");
        para.put("lovely?", "Y");
        para.put("nice?", "Y");
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("what a and lovely and nice day", sb.toString() );
    }

    @Test
    public void test4() {
        HQLNode hqlNode = findStatementHQLNode("dynamicTest3");
        para.put("lovely?", "Y");
        para.put("nice?", "Y");
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("what a very lovely and nice day", sb.toString() );
    }
}
