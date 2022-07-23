package com.info7250.hadoop.mrwordcount;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Hello world!
 *
 */
public class WordCountMain 
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException, InterruptedException
    
    //hadoop jar <jar> [mainClass] args... is command to run jar file
    //hadoop jar <jar> [mainClass] /inputdir /outputdir "inputdir is arggs[0] and outputdir is "args[1] " they are input from cmd . then we use it below to set path
    //args is array which is input to main methos they come from the command line
    {
    	 Job job = Job.getInstance();
         job.setJarByClass(WordCountMain.class);
         
         // Specify various job-specific parameters     
         job.setMapperClass(WordCountMapper.class);
         job.setReducerClass(WordCountReducer.class);
         
         job.setInputFormatClass(TextInputFormat.class);
         job.setOutputFormatClass(TextOutputFormat.class);
         
         job.setOutputKeyClass(Text.class);
         job.setOutputValueClass(IntWritable.class);
         
         FileInputFormat.addInputPath(job, new Path(args[0]));
         FileOutputFormat.setOutputPath(job, new Path(args[1]));
         
         job.setJobName("myjob");
         
         System.exit(job.waitForCompletion(true) ? 0 :1);
    }
}
