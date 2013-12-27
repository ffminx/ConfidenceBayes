package edu.btbu.dm.confidence.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.btbu.dm.confidence.IO.Options;
import edu.btbu.dm.confidence.IO.ReadDataFile;

public class DataPreProcess {
	Options opt;
	ReadDataFile rin;
	public List<String[]> lableDataWords;
	public List<String[]> trainDataWords;
	String[] tags;
	
	public DataPreProcess(Options opt, ReadDataFile rin){
		this.opt = opt;
		this.rin = rin;
		lableDataWords = new ArrayList<String[]>();
		trainDataWords = new ArrayList<String[]>();
		tags = rin.tags;
	}
	
	public ModelPresentation doProcess(){
		for(String s : rin.lableData){
			lableDataWords.add(filter(s));
		}
		for(String s : rin.trainData){
			trainDataWords.add(filter(s));
		}
		return new ModelPresentation(opt,this);
	}
	
	String[] filter(String str){
		List<String> result = ParserUtil.parserResult(str);
		Iterator<String> iterator = result.iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			if(word.length() < opt.wordLength) iterator.remove();
		}
		return result.toArray(new String[0]);
	}
}
