## PART 2 - Programming assignment

1. Creating /logs directory

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_creating_dir.png?raw=true)

2. Copy access.log file into HDFS

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_movefile.png?raw=true)

3. Run jar file without combiner

```
package com.info7250.hadoop.accesslogs_mr;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HitMain {
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
		Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.setJobName("myjob");
        
        System.exit(job.waitForCompletion(true) ? 0 :1);
	}

}

```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_2_mr.png?raw=true)

4. Run jar with a combiner

```
package com.info7250.hadoop.accesslogs_mr;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HitMain {
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
		Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setCombinerClass(HitReducer.class);
        job.setReducerClass(HitReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.setJobName("myjob");
        
        System.exit(job.waitForCompletion(true) ? 0 :1);
	}

}
```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_2_cmb.png?raw=true)

## PART 4 – Programming Assignment

#### CombineFileInputFormat, FixedLengthInputFormat, KeyValueTextInputFormat, NLineInputFormat & TextInputFormat

**Code for Combined My Format:**
```
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
```

**Code for driver class:**
```
package com.info7250.hadoop.customInputFormat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FixedLengthInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
//import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HitMain {
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
        //************************ FixedLengthInputFormat *****************
        
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");
		//conf.setInt(FixedLengthInputFormat.FIXED_RECORD_LENGTH, 11);
		conf.setInt("fixedlengthinputformat.record.length",8);
		
		Job job = new Job(conf);
		//Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
        
		job.setInputFormatClass(FixedLengthInputFormat.class);
		//FixedLengthInputFormat.setRecordLength(conf, 100);

		        
        //************************ KeyValueTextInputFormat *****************
        
		/*Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");

		Job job = new Job(conf);
		//Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
		
		//job.setInputFormatClass(KeyValueTextInputFormat.class);*/
        
        //************************ NLineInputFormat ************************
        
        //Configuration conf = new Configuration();
		//conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");

		//Job job = new Job(conf);
		/*Job job = Job.getInstance();
		job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", 4);
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);

        job.setInputFormatClass(NLineInputFormat.class);*/
		
		//********************* CombineTextInputFormat **********************
		/*Configuration conf = new Configuration();
        conf.set("mapred.textoutputformat.separator", ",");
        conf.set("mapred.max.split.size", "16777216");
        
        Job job = new Job(conf);
        job.getConfiguration().setLong("mapreduce.input.fileinputformat.split.maxsize", (long)(256*1024*1024));

        job.setJarByClass(HitMain.class);
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
        
        job.setInputFormatClass(CombinedMyFormat.class);
        CombinedMyFormat.setMaxInputSplitSize(job, 16777216);*/
        
        // *************************************************
        job.setOutputFormatClass(TextOutputFormat.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.setJobName("myjob");
        
        System.exit(job.waitForCompletion(true) ? 0:1);
	}

}
```

**Code for Mapper**:
```
package com.info7250.hadoop.customInputFormat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//public class HitCounter extends Mapper<Text,Text,Text,IntWritable>{ //- KeyValueTextInputFormat 
//public class HitCounter extends Mapper<LongWritable,Text,Text,IntWritable>{ //- NLineInputFormat
public class HitCounter extends Mapper<LongWritable,BytesWritable,Text,IntWritable>{ //- FixedLength
		
	private final static IntWritable one = new IntWritable(1);
	private Text ipaddress = new Text();
	
	//public void map(Text key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException { //- KeyValueTextInputFormat
	//public void map(BytesWritable key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException { //- NLineInputFormat
	public void map(LongWritable key, BytesWritable value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException { //- FixedInputFormat
			
			//String line=value.toString();
			String line = new String(value.get(),StandardCharsets.UTF_8);
			
			String[] tokens=line.split(" ");
			ipaddress.set(tokens[0]);
			
			context.write(ipaddress, one);
			//context gives output key and value
		}

}
```

Output for CombineFileInputFormat:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_4_combine.png?raw=true)

Output for FixedLengthInputFormat:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_4_fix.png?raw=true)

Output for NLineInputFormat:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_4_nline.png?raw=true)

Output for KeyValueTextInputFormat:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_4_nline.png?raw=true)

Output for TextInputFormat:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_2_cmb.png?raw=true)


