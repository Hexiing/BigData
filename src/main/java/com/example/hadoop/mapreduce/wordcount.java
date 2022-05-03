package com.example.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class wordcount {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(wordcount.class);
        job.setMapperClass(mapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job,new Path("D:\\ideaProjects\\BigData\\datas\\1.txt"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\ideaProjects\\BigData\\out1"));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class mapper extends Mapper<LongWritable, Text,Text, IntWritable>{

    private IntWritable toOne = new IntWritable(1);
    private Text Word;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] wordsInALine = value.toString().split(" ");
        for (String word :
                wordsInALine) {

            Word = new Text(word);
            context.write(Word, toOne);
        }
    }
}

class reducer extends Reducer<Text,IntWritable,Text,IntWritable>{

    private IntWritable sum = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int s = 0;

        for (IntWritable v :
                values) {
            s += v.get();
        }

        sum.set(s);

        context.write(key,sum);
    }
}