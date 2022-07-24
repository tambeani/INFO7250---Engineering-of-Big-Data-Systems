package com.info7250.hadoop.stocks;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.Writable;

public class CustomWritable implements Writable{

	public long stock_volume;
	public double stock_price_adj_close;
	//public Date stock_date = new Date();
	public String date;
	
	public CustomWritable(long stock_volume, double stock_price_adj_close, String date) {
		super();
		this.stock_volume = stock_volume;
		this.stock_price_adj_close = stock_price_adj_close;
		this.date = date;
	}

	//private final static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	
	public long getStock_volume() {
		return stock_volume;
	}

	public void setStock_volume(long stock_volume) {
		this.stock_volume = stock_volume;
	}

	/*public Date getStock_date() {
		return stock_date;
	}*/

	/*public void setStock_date(Date stock_date) {
		this.stock_date = stock_date;
	}*/

	public double getStock_price_adj_close() {
		return stock_price_adj_close;
	}

	public void setStock_price_adj_close(double stock_price_adj_close) {
		this.stock_price_adj_close = stock_price_adj_close;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		stock_volume = in.readLong();
		date = in.readLine();
		stock_price_adj_close = in.readDouble();		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(stock_volume);
		out.writeDouble(stock_price_adj_close);
		out.writeChars(date);
	}

	@Override
	public String toString() {
		return this.date + " - " + this.stock_price_adj_close + " - " + this.stock_volume;
	}

}
