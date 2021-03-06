package com.wuda.tester.mysql.statistic;

import com.wuda.tester.mysql.TableName;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据生成过程中的统计信息.该类不是线程安全的,所以统计信息不是精确的, 但是也不会太离谱,尽可能会减小误差.
 *
 * @author wuda
 */
public class DataGenerateStat {

    /**
     * 数据生成任务执行的总次数.
     */
    private AtomicInteger totalTaskCount = new AtomicInteger();
    /**
     * 数据生成任务成功的次数.
     */
    private AtomicInteger successTaskCount = new AtomicInteger();
    /**
     * 数据生成任务失败的次数.
     */
    private AtomicInteger failureTaskCount = new AtomicInteger();

    /**
     * 已经保存到数据库的数量.key-表名,value-数量
     */
    private Map<TableName, AtomicInteger> inserted_entity_count = new HashMap<>();

    /**
     * 获取给定table已经插入的数量.
     *
     * @param tableName 表名
     * @return 数量
     */
    public int getInsertedCount(TableName tableName) {
        AtomicInteger counter = inserted_entity_count.get(tableName);
        if (counter != null) {
            return counter.get();
        }
        return 0;
    }

    /**
     * 给定的table数量加一,并且返回增加后的值.
     *
     * @param tableName 表名称
     * @param count     增加的数量
     * @return 增加后的数量
     */
    public int insertedIncrementAndGet(TableName tableName, int count) {
        AtomicInteger counter = inserted_entity_count.get(tableName);
        if (counter != null) {
            return counter.addAndGet(count);
        }
        counter = new AtomicInteger(count);
        inserted_entity_count.put(tableName, counter);
        return counter.get();
    }

    /**
     * 增加并且获取{@link #totalTaskCount}.
     *
     * @return int
     */
    public int incrementAndGetTotalTaskCount() {
        return totalTaskCount.incrementAndGet();
    }

    /**
     * 获取{@link #totalTaskCount}.
     *
     * @return int
     */
    public int getTotalTaskCount() {
        return totalTaskCount.get();
    }

    /**
     * 增加并且获取{@link #successTaskCount}.
     *
     * @return int
     */
    public int incrementAndGetSuccessTaskCount() {
        return successTaskCount.incrementAndGet();
    }

    /**
     * 获取{@link #successTaskCount}.
     *
     * @return int
     */
    public int getSuccessTaskCount() {
        return successTaskCount.get();
    }

    /**
     * 增加并且获取{@link #failureTaskCount}.
     *
     * @return int
     */
    public int incrementAndGetFailureTaskCount() {
        return failureTaskCount.incrementAndGet();
    }

    /**
     * 获取{@link #failureTaskCount}.
     *
     * @return int
     */
    public int getFailureTaskCount() {
        return failureTaskCount.get();
    }

    /**
     * 获取失败率.
     *
     * @return 失败率
     */
    public float getFailureRate() {
        int totalPlus1 = totalTaskCount.get() + 1;
        return (float) failureTaskCount.get() / totalPlus1;
    }
}
