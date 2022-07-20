package com.info7250.hadoop.accesslogs_mr;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HitCounter extends Mapper<LongWritable,Text,Text,IntWritable>{
	
	private final static IntWritable one = new IntWritable(1);
	private Text ipaddress = new Text();
	
	public void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException {
		//Called once for each key/value pair in the input split.
			//LongWritable is input key type of map function coming from Input format
			//Text is input value type of map function coming from Input format
			//in new Mapper class we use context to emit key , value pair
			//we need to convert value to string , tha value represents the line , we need to appply string operations to Text 
			
			
			String line=value.toString();
			
			String[] tokens=line.split(" ");
			ipaddress.set(tokens[0]);
			
			context.write(ipaddress, one);
			//context gives output key and value
		}

}
