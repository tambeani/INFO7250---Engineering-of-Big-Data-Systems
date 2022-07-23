package com.info7250.hadoop.customInputFormat;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReaderWrapper;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class CombinedMyFormat extends CombineFileInputFormat<LongWritable,Text>{

	/*private static class CombineMyKeyMyValueReaderWrapper
    extends CombineFileRecordReaderWrapper< Text, Text > {
        protected CombineMyKeyMyValueReaderWrapper(
            CombineFileSplit split, TaskAttemptContext ctx, Integer idx
        ) throws IOException, InterruptedException {
            super( new CombinedMyFormat(), split, ctx, idx );
        }
    }
	
	@Override
	public RecordReader<Text, Text> createRecordReader(InputSplit split, TaskAttemptContext ctx)
			throws IOException {
		// TODO Auto-generated method stub
		return new CombineFileRecordReader< Text, Text >(
	            ( CombineFileSplit )split, ctx, CombineMyKeyMyValueReaderWrapper.class);
	}*/
	
	@Override
    public RecordReader<LongWritable, Text>
            createRecordReader(InputSplit split, TaskAttemptContext context)
                    throws IOException {

        CombineFileRecordReader<LongWritable, Text> reader = 
                new CombineFileRecordReader<LongWritable, Text>(
                        (CombineFileSplit) split, context, myCombineFileRecordReader.class);        
        return reader;
    }

    public static class myCombineFileRecordReader extends RecordReader<LongWritable, Text> {
        private LineRecordReader lineRecordReader = new LineRecordReader();

        public myCombineFileRecordReader(CombineFileSplit split, 
                TaskAttemptContext context, Integer index) throws IOException {

            FileSplit fileSplit = new FileSplit(split.getPath(index), 
                                                split.getOffset(index),
                                                split.getLength(index), 
                                                split.getLocations());
            lineRecordReader.initialize(fileSplit, context);
        }

        @Override
        public void initialize(InputSplit inputSplit, TaskAttemptContext context)
                throws IOException, InterruptedException {
            //linerecordReader.initialize(inputSplit, context);
        }

        @Override
        public void close() throws IOException {
            lineRecordReader.close();
        }

        @Override
        public float getProgress() throws IOException {
            return lineRecordReader.getProgress();
        }

        @Override
        public LongWritable getCurrentKey() throws IOException,
                InterruptedException {
            return lineRecordReader.getCurrentKey();
        }

        @Override
        public Text getCurrentValue() throws IOException, InterruptedException {
            return lineRecordReader.getCurrentValue();
        }

        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {
            return lineRecordReader.nextKeyValue();
        }        
    }

}
