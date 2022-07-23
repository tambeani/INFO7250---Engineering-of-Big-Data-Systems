package com.info7250.hadoop.customInputFormat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
//import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HitMain {
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");

		Job job = new Job(conf);
		
		//
		//Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
        
        //************************ KeyValueTextInputFormat *****************
        //job.setInputFormatClass(KeyValueTextInputFormat.class);
        //job.setOutputFormatClass(TextOutputFormat.class);
        
        //************************ NLineInputFormat ************************
        job.setInputFormatClass(NLineInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.setJobName("myjob");
        
        System.exit(job.waitForCompletion(true) ? 0:1);
	}

}
