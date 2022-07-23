package com.info7250.hadoop.customInputFormat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FixedLengthInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
//import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HitMain {
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
        //************************ FixedLengthInputFormat *****************
        
		/*
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");
		//conf.setInt(FixedLengthInputFormat.FIXED_RECORD_LENGTH, 11);
		conf.setInt("fixedlengthinputformat.record.length",8);
		
		Job job = new Job(conf);
		//Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
        
		job.setInputFormatClass(FixedLengthInputFormat.class);
		//FixedLengthInputFormat.setRecordLength(conf, 100);
*/
		        
        //************************ KeyValueTextInputFormat *****************
        
		/*Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");

		Job job = new Job(conf);
		//Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
		
		//job.setInputFormatClass(KeyValueTextInputFormat.class);*/
        
        //************************ NLineInputFormat ************************
        
        Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");
		
		Job job = new Job(conf);
		//Job job = Job.getInstance();
		job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", 4);
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);

        job.setInputFormatClass(NLineInputFormat.class);
		
		//********************* CombineTextInputFormat **********************
		/*Configuration conf = new Configuration();
        conf.set("mapred.textoutputformat.separator", ",");
        conf.set("mapred.max.split.size", "16777216");
        
        Job job = new Job(conf);
        job.getConfiguration().setLong("mapreduce.input.fileinputformat.split.maxsize", (long)(256*1024*1024));

        job.setJarByClass(HitMain.class);
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
        
        job.setInputFormatClass(CombinedMyFormat.class);
        CombinedMyFormat.setMaxInputSplitSize(job, 16777216);*/
        
        // *************************************************
        job.setOutputFormatClass(TextOutputFormat.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.setJobName("myjob");
        
        System.exit(job.waitForCompletion(true) ? 0:1);
	}

}
