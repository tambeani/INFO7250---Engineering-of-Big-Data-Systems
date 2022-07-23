package com.info7250.hadoop.customInputFormat;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//public class HitCounter extends Mapper<Text,Text,Text,IntWritable>{ //- KeyValueTextInputFormat 
//public class HitCounter extends Mapper<LongWritable,Text,Text,IntWritable>{ //- NLineInputFormat
public class HitCounter extends Mapper<BytesWritable,Text,Text,IntWritable>{ //- FixedLengthFormat
		
	private final static IntWritable one = new IntWritable(1);
	private Text ipaddress = new Text();
	
	//public void map(Text key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException { //- KeyValueTextInputFormat
	public void map(BytesWritable key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException { //- NLineInputFormat
			
			String line=value.toString();
			
			String[] tokens=line.split(" ");
			ipaddress.set(tokens[0]);
			
			context.write(ipaddress, one);
			//context gives output key and value
		}

}
