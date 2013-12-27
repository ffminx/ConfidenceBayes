package edu.btbu.dm.confidence.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Jama.Matrix;
import edu.btbu.dm.confidence.IO.Options;

public class ModelPresentation {
	Options opt;
	DataPreProcess dp;
	String[] dataClass;
	public HashMap<String,Integer> classFlag;
	public HashMap<String,Integer> wordFlag;
	public Matrix wordCount; 
	public Matrix wordPriorPro;
	public Matrix classCount;
	public Matrix classPriorPro;
	
	
	public ModelPresentation(Options opt,DataPreProcess dp){
		this.opt = opt;
		this.dp = dp;
		classFlag = new HashMap<String,Integer>();
		wordFlag = new HashMap<String,Integer>();
		
		_initFlag();
		
		wordCount = new Matrix(wordFlag.size(),classFlag.size());
		wordPriorPro = new Matrix(wordFlag.size(),classFlag.size());
		
		classCount = new Matrix(1,classFlag.size());
		classPriorPro = new Matrix(1,classFlag.size());
	}

	void _initFlag() {
		dataClass = generateClass(dp.tags);
		int m=0;
		for(String s : dataClass){
			classFlag.put(s, m++);
		}
		
		List<String[]> lableDataWords = dp.lableDataWords;
		HashMap<String,Integer> words = new HashMap<String,Integer>();
		for(String[] wordsList : lableDataWords){
			for(String s : wordsList){
				if(!words.containsKey(s)) words.put(s, 1);
				else words.put(s, words.get(s)+1);
			}
		}
		ValueComparator bvc=new ValueComparator(words);
		TreeMap<String,Integer> wordsSort = new TreeMap<String,Integer>(bvc);
		wordsSort.putAll(words);
		int n=0;
		for(String s : wordsSort.keySet()){
			wordFlag.put(s, n++);
		}
	}
	
	public void _init(){
		String[] tags = dp.tags;
		List<String[]> wordsList = dp.lableDataWords;
		List<String> temp = new ArrayList<String>();
		for(int i=0;i<wordsList.size();i++){
			temp.clear();
			String[] words = wordsList.get(i);
			String tag = tags[i];
			for(String s : words){
				if(temp.contains(s)) continue;
				int row = wordFlag.get(s);
				int column = classFlag.get(tag);
				wordCount.set(row, column, wordCount.get(row, column)+1);
				temp.add(s);
			}
		}
		
		for(String tag : tags){
			int idx = classFlag.get(tag);
			classCount.set(0, idx, classCount.get(0, idx)+1);
		}
		
		int sampleCount = tags.length;
		double base = (double)1/(double)sampleCount;
		classPriorPro = classCount.times(base);
	}
	
	String[] generateClass(String[] tags){
		List<String> classes = new ArrayList<String>();
		for(String tag : tags){
			if(!classes.contains(tag)) classes.add(tag);
		}
		return classes.toArray(new String[0]);
	}
	
	class ValueComparator implements Comparator<Object> {

		  Map<String, Integer> base;
		  public ValueComparator(Map<String, Integer> base) {
		      this.base = base;
		  }

		  public int compare(Object a, Object b) {

		    if(base.get(a) < base.get(b) ||base.get(a) == base.get(b)) {
		      return 1;
		    }  else {
		      return -1;
		    }
		  }
	}
}
