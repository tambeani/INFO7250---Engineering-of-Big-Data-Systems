package com.info7250.hadoop.accesslogs_mr;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HitMain {
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
		Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters     
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        
        
        FileInputFormat.addInputPath(job, new Path("/logs/access.log"));
        FileOutputFormat.setOutputPath(job, new Path("/output"));
        
        job.setJobName("myjob");
        
        System.exit(job.waitForCompletion(true) ? 0 :1);
	}

}
