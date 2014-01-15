package edu.btbu.dm.confidence.MainMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class LibSVM {
	public static final int dim = 20;
	
	public static void main(String[] args) throws Exception{
		DataSource source = new DataSource("data/trainSample_"+dim+".csv");
		Instances trainData = source.getDataSet();
		trainData.setClassIndex(trainData.numAttributes()-1);
		
		String[] cls = new String[]{"very not sure","not sure","little not sure", "little sure", "sure", "very sure"};
		List<Instances> clsData = new ArrayList<Instances>();
		for(int i=0;i<cls.length;i++){
			clsData.add(new Instances(trainData));
		}
		for(int i=0;i<cls.length;i++){
			clsData.get(i).delete();
		}
		for(int i=0;i<trainData.numInstances();i++){
			Instance ins = trainData.instance(i);
			clsData.get((int)ins.classValue()).add(ins);
		}
		
		List<Classifier> models = new ArrayList<Classifier>();
		List<HashMap<Integer,String>> modelsFlag = new ArrayList<HashMap<Integer,String>>();
		for(int i=0;i<cls.length;i++){
			for(int j=i+1;j<cls.length;j++){
				String modelName = cls[i]+" VS "+cls[j];
				Instances data = new Instances(clsData.get(i));
				Instances dataNeedAdd = new Instances(clsData.get(j));
				for(int k=0;k<dataNeedAdd.numInstances();k++){
					Instance ins = dataNeedAdd.instance(k);
					data.add(ins);
				}
			data.setClassIndex(data.numAttributes()-1);
			Classifier model = doClassify(data);
			models.add(model);
			HashMap<Integer,String> map = new HashMap<Integer,String>();
			map.put(0,cls[i]);
			map.put(1, cls[j]);
			modelsFlag.add(map);
			System.out.println(modelName+" build Classifier success....");
			}	
		}
		
		System.out.println(models.size());
		
		String[] tags = Predict(models,modelsFlag,cls);
		System.out.println(tags);
	}
	
	public static Classifier doClassify(Instances data) throws Exception{
//		String[] options = weka.core.Utils.splitOptions("-S 0 -t 2 -B 1 -h 0");
//		Classifier SVM = new weka.classifiers.functions.LibSVM();
//		SVM.setOptions(options);
//		SVM.buildClassifier(data);
//		return SVM;
		Classifier cls = new SMO();
		cls.buildClassifier(data);
		return cls;
	}
	
	public static String[] Predict(List<Classifier> models,List<HashMap<Integer,String>> modelsFlag,String[] cls) throws Exception{
		
		DataSource source = new DataSource("data/testSample_"+dim+".csv");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		String[] tags = new String[data.numInstances()];
		int[] votes = new int[cls.length];
		for(int i=0;i<data.numInstances();i++){
			votes = new int[cls.length];
			Instance ins = data.instance(i);
			for(int m=0;m<models.size();m++){
				double result = models.get(m).classifyInstance(ins);
				HashMap<Integer,String> modelFlag = modelsFlag.get(m);
				String clsResult = modelFlag.get((int) result);
				int clsIdx = getClsIdx(cls,clsResult);
				votes[clsIdx]++;
			}
			int vote = MaxIdx(votes);
			tags[i] = cls[vote];
		}
		return tags;
	}
	private static int getClsIdx(String[] cls, String clsResult) {
		// TODO Auto-generated method stub
		int k=0;
		for(int i=0;i<cls.length;i++){
			if(cls[i].equals(clsResult)) k = i;
		}
		return k;
	}

	public static int MaxIdx(int[] votes){
		int k=0;
		for(int i=0;i<votes.length;i++){
			if(votes[i]>votes[k]) k=i; 
		}
		return k;
	}
}
