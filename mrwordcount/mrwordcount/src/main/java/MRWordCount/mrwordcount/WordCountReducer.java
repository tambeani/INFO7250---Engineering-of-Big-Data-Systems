package MRWordCount.mrwordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

	//Input Key is text from output key from Mapper
	//Input value is IntWritable that is output value from Mapper
	//Text is output key of the Reducer
	//IntWritable is the output value for the count
	
	@Override
	public void setup(org.apache.hadoop.mapreduce.Reducer.Context context) {
		//Called once at the start of the task.
	}
	
	@Override
	public void	cleanup(org.apache.hadoop.mapreduce.Reducer.Context context) {
	//Called once at the end of the task.
	
	}
	
	@Override
	public void run(org.apache.hadoop.mapreduce.Reducer.Context context) {
	//Advanced application writers can use the run(org.apache.hadoop.mapreduce.Reducer.Context) method to control how the reduce task works.
	}
	
	
	public void reduce(Text key, Iterable<IntWritable> values, org.apache.hadoop.mapreduce.Reducer.Context context) throws IOException, InterruptedException {
	//This method is called once for each key.
		//key here represents ipaddress
		
		int sum=0;
		
		for(IntWritable val:values) {
			sum+=val.get();
		}
		
		
		
		context.write(key,new IntWritable(sum)); //return key and reduced value
	
	}
	
	

}