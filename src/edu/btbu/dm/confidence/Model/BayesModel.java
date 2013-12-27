package edu.btbu.dm.confidence.Model;

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
			System.out.println();
		}
	}
}
