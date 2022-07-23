package com.info7250.hadoop.stock;
import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class StockMapper extends Mapper <Object, Text, Text, DoubleWritable> {
    DoubleWritable stockhigh ;
    Text ipAddrText = new Text();
    protected void map(Object key, Text value, Mapper<Object, Text, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] lineTokens = line.split(",");     
        try{
        	String symbol = lineTokens[1].trim();
        if(symbol.contains("stock_symbol")) {
        	return;
        }
        if(lineTokens[4].trim().contains("stock_price_high")) {
        	return;
        }
        String highprice = lineTokens[4].trim();
        double high=Double.parseDouble(highprice);
        stockhigh=new DoubleWritable(high);
        //System.out.println("Mapping ipAddr" + ipAddr);
        ipAddrText.set(symbol);
        
        context.write(ipAddrText, stockhigh);
        }catch (Exception e) {
			// TODO: handle exception
		}
    }
}
