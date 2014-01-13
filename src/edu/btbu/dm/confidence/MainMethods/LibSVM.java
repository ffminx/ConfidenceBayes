package edu.btbu.dm.confidence.MainMethods;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;


public class LibSVM {
	public static void main(String[] args) throws Exception{
		int dim = 30;
		DataSource source = new DataSource("data/studySample_"+dim+".csv");
		Instances study = source.getDataSet();
		if(study.classIndex() == -1){
			study.setClassIndex(study.numAttributes()-1);
		}
		
		source = new DataSource("data/trainSample_"+dim+".csv");
		Instances train = source.getDataSet();
		train.setClassIndex(train.numAttributes()-1);
		String[] options = Utils.splitOptions("-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 40.0 -C 1.0 -E 0.001 -P 0.1 -seed 1");
		Classifier SVM = new weka.classifiers.functions.LibSVM();
		SVM.setOptions(options);
		SVM.buildClassifier(study);
		System.out.println(SVM.debugTipText());
		
		
	}
}
