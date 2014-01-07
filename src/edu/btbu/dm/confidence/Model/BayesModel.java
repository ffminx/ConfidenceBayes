package edu.btbu.dm.confidence.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Jama.Matrix;
import edu.btbu.dm.confidence.IO.Options;
import edu.btbu.dm.confidence.Utils.DataPreProcess;
import edu.btbu.dm.confidence.Utils.ModelPresentation;

public class BayesModel {
	List<String[]> lableData;
	List<String[]> trainData;
	double alpha;
	HashMap<String,Integer> classFlag;
	HashMap<String,Integer> wordFlag;
	Matrix wordCount;
	Matrix wordPriorPro;
	Matrix classCount;
	Matrix classPriorPro;
	String[] trainDataResult;
	
	public BayesModel(Options opt, DataPreProcess dp, ModelPresentation modelPre){
		lableData = dp.lableDataWords;
		trainData = dp.trainDataWords;
		alpha = opt.alpha;
		classFlag = modelPre.classFlag;
		wordFlag = modelPre.wordFlag;
		wordCount = modelPre.wordCount;
		classCount = modelPre.classCount;
		classPriorPro = modelPre.classPriorPro;
		wordPriorPro = new Matrix(wordCount.getRowDimension(),wordCount.getColumnDimension());
	}
	
	//计算p(xk|yi)
	public void ComputeWordPriorPro(){
		for(String xk : wordFlag.keySet()){
			int wordIdx = wordFlag.get(xk);
			Matrix wordCountMatrix = wordCount.getMatrix(wordIdx, wordIdx,0,classFlag.size()-1);
			Matrix smoothMatrix = classPriorPro.times(alpha);
			Matrix result = smoothMatrix.plus(classCount).arrayLeftDivide(smoothMatrix.plus(wordCountMatrix));
			wordPriorPro.setMatrix(wordIdx, wordIdx, 0,classFlag.size()-1,result);
		}
	}
	
	//预测某句子类别
	//先预测句子中词的类别概率，然后求平均。
	public String TrainSample(String[] words){
		Matrix resultMatrix = new Matrix(1,classFlag.size());
		double count = 0;
		for(String word : words){
			if(wordFlag.containsKey(word)){
				Matrix PriorPro = wordPriorPro.getMatrix(wordFlag.get(word), wordFlag.get(word), 0, classFlag.size()-1);
				resultMatrix.plusEquals(PriorPro.arrayTimes(classPriorPro));
				count++;
			}
		}
		resultMatrix.times(1/count);
		int i,j=0;
		double d = 0;
		for(i=0;i<resultMatrix.getColumnDimension();i++){
			if(resultMatrix.get(0, i) > d){
				d = resultMatrix.get(0, i);
				j = i;
			}
		}
		String result = null;
		for(String key : classFlag.keySet()){
			Integer value = classFlag.get(key);
			if(value == j){
				result = key;
				break;
			}
		}
		return result;
	}
	
	//以预测的结果更新先验概率
	public void WeakLearningUpdate(String[] words,String tag){
		List<String> temp = new ArrayList<String>();
		for(String word : words){
			if(temp.contains(word)) continue;
			if(wordFlag.containsKey(word)){
				int row = wordFlag.get(word);
				int column = classFlag.get(tag);
				wordCount.set(row, column, wordCount.get(row, column)+1);
				temp.add(word);
			}
		}

		classCount.set(0, classFlag.get(tag), classCount.get(0, classFlag.get(tag))+1);
		
		int sum = 0;
		for(int i=0;i<classCount.getColumnDimension();i++){
			sum+=classCount.get(0, i);
		}
		
		classPriorPro = classCount.times(Double.valueOf(1)/Double.valueOf(sum));
		
		for(String word : temp){
			int wordIdx = wordFlag.get(word);
			Matrix wordCountMatrix = wordCount.getMatrix(wordIdx, wordIdx,0,classFlag.size()-1);
			Matrix smoothMatrix = classPriorPro.times(alpha);
			Matrix result = smoothMatrix.plus(classCount).arrayLeftDivide(smoothMatrix.plus(wordCountMatrix));
			wordPriorPro.setMatrix(wordIdx, wordIdx, 0,classFlag.size()-1,result);
		}
	}
}
