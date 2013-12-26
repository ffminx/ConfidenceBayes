package edu.btbu.dm.confidence.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.btbu.dm.confidence.Utils.ParserUtil;

public class ReadDataFile {
	Options opt;
	public String[] tags;
	public List<String[]> lableData;
	public List<String[]> trainData;
	File lableDataPath;
	File trainDataPath;
	
	
	public ReadDataFile(Options opt){
		this.opt = opt;
		lableData = new ArrayList<String[]>();
		trainData = new ArrayList<String[]>();
		lableDataPath = new File(opt.lableData);
		trainDataPath = new File(opt.trainData);
	}
	
	public ReadDataFile readAllData(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lableDataPath),"gbk"));
			String line;
			List<String> tempTags = new ArrayList<String>();
			while((line=br.readLine())!=null){
				String[] arrays = line.split(",");
				lableData.add(filter(arrays[0]));
				tempTags.add(arrays[1]);
			}
			tags = (String[]) tempTags.toArray(new String[0]);
			br.close();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(trainDataPath),"gbk"));
			while((line = br.readLine())!=null){
				trainData.add(filter(line.trim()));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Train Data not Exists...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Read Data error...");
		} 
		return this;
	}

	public String[] filter(String str){
		List<String> keywords = ParserUtil.parserResult(str);
		Iterator<String> iterator = keywords.iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			if(word.length()<opt.wordLength) iterator.remove();
		}
		return keywords.toArray(new String[0]);
	}
}
