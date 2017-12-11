package com.sse.myhbase.config;

/**
 * @author : Cai Shunda
 * @description: HBase运行时的一些配置
 * @date: Created in 21:19 2017/11/28
 * @modified by:
 */
public class MyHBaseRuntimeSetting {

    /**
     * 多少Result，可以理解为多少个Result进行一次RPC操作
     */
    private int scanCachingSize = 20;

    /**
     * 删除Batch
     */
    private int deleteBatchSize = 50;

    /**
     * Scan操作的CacheSize是否可以由用户设置
     */
    private boolean intelligentScanSize;

    public MyHBaseRuntimeSetting() {


    }

    public int getScanCachingSize() {
        return scanCachingSize;
    }

    public void setScanCachingSize(int scanCachingSize) {
        this.scanCachingSize = scanCachingSize;
    }

    public int getDeleteBatchSize() {
        return deleteBatchSize;
    }

    public void setDeleteBatchSize(int deleteBatchSize) {
        this.deleteBatchSize = deleteBatchSize;
    }

    public boolean isIntelligentScanSize() {
        return intelligentScanSize;
    }

    public void setIntelligentScanSize(boolean intelligentScanSize) {
        this.intelligentScanSize = intelligentScanSize;
    }
}
