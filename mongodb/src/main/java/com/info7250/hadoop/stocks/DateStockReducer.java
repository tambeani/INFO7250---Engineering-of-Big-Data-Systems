package com.info7250.hadoop.stocks;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DateStockReducer extends Reducer<Text, CustomWritable, Text, Text> {

    protected void reduce(Text key, Iterable<CustomWritable> values, Reducer<Text, CustomWritable, Text, Text>.Context context) throws IOException, InterruptedException {
    	Text maxDate = new Text();
        long max = 0;
        for(CustomWritable val:values) {
        	
        	if(val.stock_volume > max) {
        		max = val.stock_volume;
        		maxDate.set(val.date);
        	}
           
        }
        
        //finalMax.set(max);
        //System.out.println("Reducing key: "+ key + " final sum: " + sum);
        context.write(key, maxDate);
    }
}