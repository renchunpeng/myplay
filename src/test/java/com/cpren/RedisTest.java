package com.cpren;

import com.cpren.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private String LOCK_PREFIX = "kn_";

    private Long LOCK_EXPIRE = 1000 * 10L;

    @Test
    public void test() throws InterruptedException {
        boolean rcp = lock("rcp");
        System.out.println(rcp);

        for(int i=0;i<20;i++){
            new Thread(() -> {
                boolean qw = lock("rcp");
                System.out.println(qw);
                try {
                    if(qw) {
                        System.out.println(Thread.currentThread().getName() + "抢到了锁");
                        Thread.sleep(1000L);
                    }else{
                        System.out.println("没抢到锁，不知道干嘛了！");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(1000l * 5);
    }

    /**
     * 测试list数据类型的增删改查
     */
    @Test
    public void hashTest() {
        User rcp = new User();
        rcp.setUserName("任春鹏");
        User wq = new User();
        rcp.setUserName("王倩");
        redisTemplate.opsForHash().put("knowledge","001", rcp);
        redisTemplate.opsForHash().put("knowledge","002",wq);

        Object knowledge = redisTemplate.opsForHash().get("knowledge", "001");
        System.out.println(knowledge);
    }

    @Test
    public void listSearchTest() {
        List<Object> exec = redisTemplate.opsForList().getOperations().exec();
        System.out.println(exec);
    }

    /**
     * 最终加强分布式锁
     * @param key key值
     * @return 是否获取到
     */
    public boolean lock(String key) {
        String lock = LOCK_PREFIX + key;
        // 利用lambda表达式
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
                Boolean acquire = redisConnection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
                if (acquire) {
                    return true;
                } else {
                    byte[] value = redisConnection.get(lock.getBytes());
                    if (Objects.nonNull(value) && value.length > 0) {
                        long expireTime = Long.parseLong(new String(value));
                        if (expireTime < System.currentTimeMillis()) {
                            // 如果锁已经过期
                            byte[] oldValue = redisConnection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                            // 防止死锁
                            return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                        }
                    }
                }
                return false;
            }
        });
    }
}
