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
	HashMap<String,Integer> classFlag;
	HashMap<String,Integer> wordFlag;
	Matrix wordsCount; 
	Matrix wordsFrequency;
	
	
	public ModelPresentation(Options opt,DataPreProcess dp){
		this.opt = opt;
		this.dp = dp;
		classFlag = new HashMap<String,Integer>();
		wordFlag = new HashMap<String,Integer>();
		
		_initFlag();
		
		wordsCount = new Matrix(wordFlag.size(),classFlag.size());
		wordsFrequency = new Matrix(wordFlag.size(),classFlag.size());
				
	}

	void _initFlag() {
		String[] dataClasses = generateClass(dp.tags);
		int m=0;
		for(String s : dataClasses){
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
	
	public Matrix ComputeWordsCounts(){
		String[] tags = dp.tags;
		List<String[]> wordsList = dp.lableDataWords;
		for(int i=0;i<wordsList.size();i++){
			String[] words = wordsList.get(i);
			String tag = tags[i];
			for(String s : words){
				int row = wordFlag.get(s);
				int column = classFlag.get(tag);
				wordsCount.set(row, column, wordsCount.get(row, column)+1);
			}
		}
		return wordsCount;
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
