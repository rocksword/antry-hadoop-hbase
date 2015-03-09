package com.an.antry.hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxTemperatureMapper extends Mapper<LongWritable, Text, LongWritable, Put> {
    private static final int MISSING = 9999;

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        long keyVal = key.get();
        String line = value.toString();
        String year = line.substring(15, 19);
        int airTemperature;
        // parseInt doesn't like leading plus signs
        if (line.charAt(87) == '+') {
            airTemperature = Integer.parseInt(line.substring(88, 92));
        } else {
            airTemperature = Integer.parseInt(line.substring(87, 92));
        }
        String quality = line.substring(92, 93);
        if (airTemperature != MISSING && quality.matches("[01459]")) {
            byte[] cf = Bytes.toBytes("cf");
            Put put = new Put(Bytes.toBytes(String.valueOf(keyVal)));
            put.add(cf, Bytes.toBytes("t"), Bytes.toBytes(String.valueOf(airTemperature)));
            context.write(null, put);
        }
    }
}
