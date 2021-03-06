package com.info7250.mongodb.assignment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class PutMerge {

public static void main(String[] args) throws IOException, URISyntaxException {

    Configuration conf = new Configuration();
    FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:9000"),conf);
    //FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:9000"),conf)
    FileSystem local = FileSystem.getLocal(conf);

    Path inputDir = new Path("/home/aniruddha/Downloads/nyse/NYSE/");
    Path hdfsFile = new Path("/nyse/");
    
    //FileSystem fs = FileSystem.get(new URI(<url:port>), configuration);
    //Path filePath = new Path(<path/to/file>);

    //System.out.println(inputDir);
    //System.out.println(hdfsFile);

    try {
        FileStatus[] inputFiles = local.listStatus(inputDir);
        //System.out.println(inputFiles);
        FSDataOutputStream out = hdfs.create(hdfsFile);
        //System.out.println(out);

        for (int i = 0; i < inputFiles.length; i++) {
            System.out.println(inputFiles[i].getPath().getName());
            FSDataInputStream in = local.open(inputFiles[i].getPath());
            byte buffer[] = new byte[256];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
        }
        out.close();
    } catch (Exception e) {
        //e.printStackTrace();

    }
}
}

