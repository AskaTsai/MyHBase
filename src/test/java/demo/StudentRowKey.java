package demo;

import com.sse.myhbase.client.RowKey;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 22:22 2017/11/20
 * @Modified by:
 */
public class StudentRowKey  implements RowKey{
    private int row;

    public StudentRowKey(int row) {
        this.row = row;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.toBytes(row);
    }
}
