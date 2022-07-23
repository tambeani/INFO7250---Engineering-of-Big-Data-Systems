package com.info7250.hadoop.customInputFormat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//public class HitCounter extends Mapper<Text,Text,Text,IntWritable>{ //- KeyValueTextInputFormat 
public class HitCounter extends Mapper<LongWritable,Text,Text,IntWritable>{ //- NLineInputFormat
//public class HitCounter extends Mapper<LongWritable,BytesWritable,Text,IntWritable>{ //- FixedLength
		
	private final static IntWritable one = new IntWritable(1);
	private Text ipaddress = new Text();
	
	//public void map(Text key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException { //- KeyValueTextInputFormat
	public void map(BytesWritable key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException { //- NLineInputFormat
	//public void map(LongWritable key, BytesWritable value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException { //- FixedInputFormat
			
			String line=value.toString();
			//String line = new String(value.get(),StandardCharsets.UTF_8);
			
			String[] tokens=line.split(" ");
			ipaddress.set(tokens[0]);
			
			context.write(ipaddress, one);
			//context gives output key and value
		}

}
