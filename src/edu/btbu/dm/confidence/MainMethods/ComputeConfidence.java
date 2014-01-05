package edu.btbu.dm.confidence.MainMethods;

import java.util.List;

import Jama.Matrix;
import edu.btbu.dm.confidence.IO.Options;
import edu.btbu.dm.confidence.IO.ReadDataFile;
import edu.btbu.dm.confidence.Model.BayesModel;
import edu.btbu.dm.confidence.Utils.DataPreProcess;
import edu.btbu.dm.confidence.Utils.ModelPresentation;

public class ComputeConfidence {

	public static void main(String[] args){
		Options opt = new Options(args);
		System.out.println(opt.toString());
		ReadDataFile rin = new ReadDataFile(opt).readAllData();
		DataPreProcess dp = new DataPreProcess(opt,rin);
		ModelPresentation modelPresen = dp.doProcess();
		modelPresen._init();
		BayesModel bayes = new BayesModel(opt,dp,modelPresen);
		bayes.ComputeWordPriorPro();
		List<String[]> trainData = dp.trainDataWords;
		if(opt.weakLearning){
			for(int i=0;i<dp.trainDataWords.size();i++){
				String tag = bayes.TrainSample(trainData.get(i));
				
			}
		}else{
			
		}
		System.out.println();

	}
}
