package cn.blooming.jxgjsj.api.redis;

import redis.clients.jedis.Jedis;

public class TestConnection {
    public static void main(String[] args) {
        // 连接本地的 Redis 服务
        Jedis jedis = new Jedis("94.191.62.87");
        jedis.auth("lihui");
        System.out.println("连接成功");

        jedis.set("test","eee");

        System.out.println(jedis.get("test"));
        // 查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());
    }
}
