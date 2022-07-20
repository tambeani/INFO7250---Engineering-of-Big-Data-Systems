package com.info7250.hadoop.mrwordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable,Text,Text,IntWritable> {

	//For map-reducer which is for hadoop we will be using data type of hadoop
	//LongWritable is the type of input key
	//Text is the type of input value
	//Text is the type of output key (in accordance our data we are using ip address so it will Text )
	//IntWritable is the output type here we are using count as output to send it to reducer
	//<> are called as generics
	
	
	private final static IntWritable one=new IntWritable(1);
	private Text ipaddress=new Text();
	
	
	@Override
	public void setup(org.apache.hadoop.mapreduce.Mapper.Context context) {
		//Called once at the beginning of the task.
	}
	
	@Override
	public void cleanup(org.apache.hadoop.mapreduce.Mapper.Context context) {
	//Called once at the end of the task.
		
	}
	
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
