package com.info7250.hadoop.stock;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class StockReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    protected void reduce(Text key, Iterable<DoubleWritable> values, Reducer<Text, DoubleWritable, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
    	DoubleWritable finalMax = new DoubleWritable(0);
        double max = 0.0;
        for(DoubleWritable val:values) {
        	
        	if(val.get()>max) {
        		max=val.get();
        	}
           
        }
        finalMax.set(max);
        //System.out.println("Reducing key: "+ key + " final sum: " + sum);
        context.write(key, finalMax);
    }
}