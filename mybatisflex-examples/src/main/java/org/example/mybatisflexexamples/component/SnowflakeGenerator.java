package org.example.mybatisflexexamples.component;

import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SnowflakeGenerator {
    public static final String MINI_SNOWFLAKE = "mini_snowflake";

    @PostConstruct
    public void generate() {
        MiniSnowflake miniSnowflake = new MiniSnowflake();
        KeyGeneratorFactory.register(MINI_SNOWFLAKE,
                (entity, _) -> miniSnowflake.nextId());
    }

    private static class MiniSnowflake {
        // 起始时间戳：2026-01-01 00:00:00 UTC (1767225600000L)
        // 建议根据当前年份调整，可以让算法的使用寿命从现在重新计算
        private final long twepoch = 1767225600000L;

        // 各部分配额位数
        private final long workerIdBits = 5L;
        private final long sequenceBits = 6L;

        // 分配对应的最大值
        private final long maxWorkerId = ~(-1L << workerIdBits); // 31
        private final long sequenceMask = ~(-1L << sequenceBits); // 63
        // 位移步长
        private final long workerIdShift = sequenceBits;  // 6
        private final long timestampLeftShift = sequenceBits + workerIdBits;  // 11

        private final long workerId;
        private long sequence = 0L;
        private long lastTimestamp = -1L;

        /**
         * 构造函数
         *
         * @param workerId 工作机器ID (0~31)
         */
        public MiniSnowflake(long workerId) {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("Worker ID 不能大于 %d 或小于 0", maxWorkerId));
            }
            this.workerId = workerId;
        }

        public MiniSnowflake() {
            this.workerId = 1;
        }

        /**
         * 核心方法：线程安全地生成下一个安全 ID
         */
        public synchronized long nextId() {
            long timestamp = timeGen();

            // 钟回拨处理
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(String.format("时钟回拨！拒绝在 %d 毫秒内生成 ID", lastTimestamp - timestamp));
            }

            // 同一毫秒内并发处理
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                if (sequence == 0) {
                    // 当前毫秒的序列号用尽，等待下一毫秒
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                // 新的一毫秒，序列号清零
                sequence = 0L;
            }

            lastTimestamp = timestamp;

            // 核心：组合成 51 位的长整数 (最高位是0，绝对小于 JS 的 53位安全上限)
            return ((timestamp - twepoch) << timestampLeftShift)
                   | (workerId << workerIdShift)
                   | sequence;
        }

        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        protected long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
