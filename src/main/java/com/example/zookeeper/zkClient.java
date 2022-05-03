package com.example.zookeeper;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/*
编写java代码 连接操作zookeeper集群
1.创建子节点
2.获取子节点并监听节点变化
 */
public class zkClient {

    ZooKeeper zk;
    String connectString; //连接哪一个zookeeper集群
    Watcher watcher;
    int sessionTimeOut; //连接超时时间(毫秒)

    /*
    创建ZooKeeper客户端对象
     */
    @Before
    public void init() {
        connectString = "hadoop102:2181,hadoop103:2181,hadoop103:2181";
        watcher = watchedEvent -> {
            try {
                List<String> childrenNode = zk.getChildren("/zookeeper", true);
                for (String node:childrenNode) System.out.println(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        sessionTimeOut = 2000;

        try {
            zk = new ZooKeeper(connectString, sessionTimeOut, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        init();
        try {
            /*
            create(String 创建的节点 路径，byte[] 数据，控制权限, 结点类型)
             */
            zk.create("/hex", "hexi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getChildren() throws InterruptedException {
        /*
        getChildren(String 监听路径, Boolean 是否开启监听器)  如果设为true使用创建zk对象是传入的watcher
        getChildren(String 监听路径, Watcher 要使用的监听器)
         */
        try {
            List<String> childrenNode = zk.getChildren("/zookeeper", true);
            for (String node:childrenNode) System.out.println(node);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(Long.MAX_VALUE);
    }
}
