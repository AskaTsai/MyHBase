package hql.node;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNode;
import hql.HQLTestBase;
import org.junit.Test;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:56 2018/3/4
 * @modified by:
 */
public class TextTest extends HQLTestBase{
    @Test
    public void test() {
        HQLNode hqlNode = findStatementHQLNode("textTest");
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("hello, Aska", sb.toString());
    }
}
