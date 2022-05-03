package com.example.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class hdfsClient {

    @Test
    public void test_mkdir() throws IOException, URISyntaxException, InterruptedException {

        URI uri = new URI("hdfs://192.168.10.102:8020");

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(uri,conf,"hex");

        fs.mkdirs(new Path("/hex"));

        fs.close();

    }

}
