package hql.node;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.util.DateUtil;
import hql.HQLTestBase;
import org.junit.Test;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 17:45 2018/3/4
 * @modified by:
 */
public class IsLessEqualTest extends HQLTestBase{
    @Test
    public void test0(){
        HQLNode hqlNode = findStatementHQLNode("isLessEqualTest");
        para.put("gender","M");
        para.put("age", 10);
        para.put("birth", DateUtil.parse("1992-07-24", DateUtil.DayFormat));
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("I am AND boy OR young OR happy birthday", sb.toString());
    }

    @Test
    public void test2(){
        HQLNode hqlNode = findStatementHQLNode("isLessEqualTest");
        para.put("gender","N");
        para.put("age", 9);
        para.put("birth", DateUtil.parse("1992-07-27", DateUtil.DayFormat));
        hqlNode.applyParaMap(para, sb, context, new MyHBaseRuntimeSetting());
        assertEqualHQL("I am OR young ", sb.toString());
    }
}
