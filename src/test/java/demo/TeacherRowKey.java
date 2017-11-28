package demo;

import com.sse.myhbase.client.RowKey;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 21:30 2017/11/21
 * @modified by:
 */
public class TeacherRowKey implements RowKey {
    private int row;

    public TeacherRowKey(int row) {
        this.row = row;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.toBytes(row);
    }
}
