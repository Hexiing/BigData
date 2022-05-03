package com.example.zookeeper.ServersOnOffLine;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private ZooKeeper zkCli;
    private String connString;
    private int sessTimeout;
    private Watcher watcher;

    private void getConnection(){
        connString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
        sessTimeout = 2000;

        watcher = watchevent -> {
            try {
                getServerList();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        try {
            zkCli = new ZooKeeper(connString,sessTimeout,watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getServerList() throws KeeperException, InterruptedException {
        ArrayList<String> servers = new ArrayList<>();
        List<String> children = zkCli.getChildren("/servers", true);
        for (String node :
                children) {
            byte[] data = zkCli.getData("/servers/" + node, false, null);
            servers.add(new String(data));
        }
        System.out.println(servers);
    }

    private void bussiness(){
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        Client client = new Client();
        client.getConnection();
        client.getServerList();
        client.bussiness();
    }
}
