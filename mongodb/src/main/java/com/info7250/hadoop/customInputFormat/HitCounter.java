package com.info7250.hadoop.customInputFormat;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HitCounter extends Mapper<Text,Text,Text,IntWritable>{
	
	private final static IntWritable one = new IntWritable(1);
	private Text ipaddress = new Text();
	
	public void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException {
			
			String line=value.toString();
			
			String[] tokens=line.split(" ");
			ipaddress.set(tokens[0]);
			
			context.write(ipaddress, one);
			//context gives output key and value
		}

}
