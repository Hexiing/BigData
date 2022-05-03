package com.example.zookeeper.ServersOnOffLine;

import org.apache.zookeeper.*;

import java.io.IOException;

public class Server {

    private ZooKeeper zkCli;
    private String connString;
    private int sessTimeout;
    private Watcher watcher;

    private void getConnection(){
        connString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
        sessTimeout = 2000;

        watcher = watchevent -> {
        };

        try {
            zkCli = new ZooKeeper(connString,sessTimeout,watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void register(String serverName){
        try {
            String create = zkCli.create("/servers/" + serverName, serverName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(serverName + " is Online");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bussiness(){
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.getConnection();
        server.register(args[0]);
        server.bussiness();
    }
}
