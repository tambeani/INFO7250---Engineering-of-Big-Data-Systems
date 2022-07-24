package com.info7250.hadoop.stocks;
import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class DateStockMapper extends Mapper <Object, Text, Text, CustomWritable> {
    
	CustomWritable stockObj ;
    Text stockSymbol = new Text();
    
    protected void map(Object key, Text value, Mapper<Object, Text, Text, CustomWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] lineTokens = line.split(",");     
        String symbol = lineTokens[1].trim();
        if(symbol.contains("stock_symbol")) {
        	return;
        }
        if(lineTokens[2].trim().contains("date")) {
        	return;
        }
        if(lineTokens[7].trim().contains("stock")) {
        	return;
        }
        if(lineTokens[8].trim().contains("stock")) {
        	return;
        }
        
        String date = lineTokens[2].trim();
        String stockvolume = lineTokens[7].trim();
        String stockadj = lineTokens[8].trim();
        
        stockObj=new CustomWritable(Long.parseLong(stockvolume), Double.parseDouble(stockadj), date);
        stockSymbol.set(symbol);
        
        System.out.print(stockObj);
        
        context.write(stockSymbol, stockObj);

    }
}
