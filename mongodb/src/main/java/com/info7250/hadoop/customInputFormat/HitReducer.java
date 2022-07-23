package com.info7250.hadoop.customInputFormat;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HitReducer extends Reducer<Text,IntWritable,Text,IntWritable>{
	
	public void reduce(Text key, Iterable<IntWritable> values, org.apache.hadoop.mapreduce.Reducer.Context context) throws IOException, InterruptedException {
		//This method is called once for each key.
			//key here represents ipaddress
			
			int sum=0;
			
			for(IntWritable val:values) {
				sum += val.get();
			}
			
			context.write(key,new IntWritable(sum)); //return key and reduced value
		
		}

}
