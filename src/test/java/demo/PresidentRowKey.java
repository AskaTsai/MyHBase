package demo;

import com.sse.myhbase.client.RowKey;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:29 2017/11/21
 * @modified by:
 */
public class PresidentRowKey implements RowKey{
    private int row;

    public PresidentRowKey(int row) {
        this.row = row;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.toBytes(row);
    }
}
