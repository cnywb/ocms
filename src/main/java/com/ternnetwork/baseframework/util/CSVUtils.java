package com.ternnetwork.baseframework.util;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import com.csvreader.CsvReader;

public class CSVUtils {

	public static void main(String[] args) throws Exception {
		  CsvReader r = new CsvReader("/Users/xuwenfeng/Documents/ws/ocms-batch/input/经销商电子账户报表.csv", ',',Charset.forName("GBK"));
	        //读取表头
	        r.readHeaders();
	       
	        //逐条读取记录，直至读完
	        int i=0;
	        while (r.readRecord()) {
	        	
	            //读取一条记录
	            System.out.println(r.getRawRecord());
	            //按列名读取这条记录的值
	        	
	        	i=i+1;
	            System.out.println(i+" "+r.get("VIN")+"  "+r.get("状态"));
	         
	        }

	}

}
