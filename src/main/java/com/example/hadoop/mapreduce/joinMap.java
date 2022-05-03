package com.example.hadoop.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class joinMap {
}

class joinmapper extends Mapper<LongWritable, Text,Text, NullWritable>{

    private Map bufferTable = new HashMap<String,String>();

    @Override
    public void run(Context context) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("ddw")));
        String line;
        while ( (line = reader.readLine()) != null ){
            bufferTable.put(line.split(" ")[0], line.split(" ")[1]);
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
    }
}
