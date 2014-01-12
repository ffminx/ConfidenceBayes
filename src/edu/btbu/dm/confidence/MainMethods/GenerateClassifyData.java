package edu.btbu.dm.confidence.MainMethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import Jama.Matrix;
import edu.btbu.dm.confidence.IO.Options;
import edu.btbu.dm.confidence.IO.ReadDataFile;
import edu.btbu.dm.confidence.Utils.DataPreProcess;
import edu.btbu.dm.confidence.Utils.ModelPresentation;

public class GenerateClassifyData {
	public static void main(String[] args) throws IOException{
		Options opt = new Options(args);
		System.out.println(opt.toString());
		ReadDataFile rin = new ReadDataFile(opt).readAllData();
		DataPreProcess dp = new DataPreProcess(opt,rin);
		ModelPresentation modelPresen = dp.doProcess();
		modelPresen._init();
		
		HashMap<String,Integer> wordFlag = modelPresen.wordFlag;
		System.out.println(wordFlag.size());
		int dim = 20;
		Matrix wordVectorMatrix = new Matrix(wordFlag.size(),dim);
		File wordVectorFile = new File("data/words_out_20.txt");
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(wordVectorFile));
			String line;
			while((line=br.readLine())!=null){
				String[] sep = line.split(" ");
				if(!wordFlag.containsKey(sep[0])) continue;
				int Idx = wordFlag.get(sep[0]);
				for(int i=1;i<sep.length;i++){
					wordVectorMatrix.set(Idx, i-1, Double.valueOf(sep[i]));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String[]> lableData = dp.lableDataWords;
		String[] tags = dp.tags;
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("data/studySample.txt")));
		StringBuilder sb = new StringBuilder();
		for(int k=0;k<lableData.size();k++){
			String[] sentence = lableData.get(k);
			sb.delete(0, sb.length());
			Matrix vector = new Matrix(1,dim);
			int count = 0;
			for(String word : sentence){
				if(wordFlag.containsKey(word)){
					Matrix wordVector = wordVectorMatrix.getMatrix(wordFlag.get(word),wordFlag.get(word),0,dim-1);
					vector.plusEquals(wordVector);
					count++;
				}
			}
			vector.times(Double.valueOf(1)/Double.valueOf(count));
			for(int i=0;i<vector.getColumnDimension();i++){
				sb.append(vector.get(0, i));
				sb.append(" ");
			}
			sb.append(tags[k]);
			sb.append("\r\n");
			bw.write(sb.toString());
		}
		bw.close();
		
		List<String[]> trainData = dp.trainDataWords;
		bw = new BufferedWriter(new FileWriter(new File("data/trainSample.txt")));
		sb = new StringBuilder();
		for(int k=0;k<trainData.size();k++){
			String[] sentence = trainData.get(k);
			sb.delete(0, sb.length());
			Matrix vector = new Matrix(1,dim);
			int count = 0;
			for(String word : sentence){
				if(wordFlag.containsKey(word)){
					Matrix wordVector = wordVectorMatrix.getMatrix(wordFlag.get(word),wordFlag.get(word),0,dim-1);
					vector.plusEquals(wordVector);
					count++;
				}
			}
			vector.times(Double.valueOf(1)/Double.valueOf(count));
			for(int i=0;i<vector.getColumnDimension();i++){
				sb.append(vector.get(0, i));
				sb.append(" ");
			}
			sb.append("\r\n");
			bw.write(sb.toString());
		}
		bw.close();
	} 
}
