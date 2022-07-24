package com.info7250.hadoop.stocks;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import Part5.NYSEWritable;
import Part5.Part5Mapper;
import Part5.Part5Reducer;

import org.apache.hadoop.io.Text;


public class DateStockDriver {
	public static void main(String[] args) throws Exception {

    Configuration configuration = new Configuration();

    Job job = Job.getInstance(configuration, "Ip Addr COunter");
    job.setJarByClass(DateStockDriver.class);
    
     job.setInputFormatClass(TextInputFormat.class);
     job.setOutputFormatClass(TextOutputFormat.class);

    /*job.setMapperClass(DateStockMapper.class);
    //job.setCombinerClass(IpReducer.class);
    job.setReducerClass(DateStockReducer.class);

    //output of the mapper
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);*/
   //Mapper and Reducer for Part 5
     job.setMapperClass(Part5Mapper.class);
     job.setCombinerClass(Part5Reducer.class);
     job.setReducerClass(Part5Reducer.class);

     //Output Types for Part 5
     job.setOutputKeyClass(Text.class);
     job.setOutputValueClass(NYSEWritable.class);

     job.setInputFormatClass(TextInputFormat.class);
     job.setOutputFormatClass(TextOutputFormat.class);

     FileInputFormat.setInputPaths(job, new Path(args[0]));
     FileOutputFormat.setOutputPath(job, new Path(args[1]));
     System.exit(job.waitForCompletion(true)? 0: 1);
}
}