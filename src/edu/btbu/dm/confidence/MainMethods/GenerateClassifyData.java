package edu.btbu.dm.confidence.MainMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Jama.Matrix;
import edu.btbu.dm.confidence.IO.Options;
import edu.btbu.dm.confidence.IO.ReadDataFile;
import edu.btbu.dm.confidence.Utils.DataPreProcess;
import edu.btbu.dm.confidence.Utils.ModelPresentation;

public class GenerateClassifyData {
	public static void main(String[] args){
		Options opt = new Options(args);
		System.out.println(opt.toString());
		ReadDataFile rin = new ReadDataFile(opt).readAllData();
		DataPreProcess dp = new DataPreProcess(opt,rin);
		ModelPresentation modelPresen = dp.doProcess();
		modelPresen._init();
		
		HashMap<String,Integer> wordFlag = modelPresen.wordFlag;
		int dim = 5;
		Matrix wordVectorMatrix = new Matrix(wordFlag.size(),dim);
		File wordVectorFile = new File("data/words_out_5.txt");
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(wordVectorFile));
			String line;
			while((line=br.readLine())!=null){
				String[] sep = line.split(" ");
				int Idx = wordFlag.get(sep[0]);
				for(int i=1;i<sep.length;i++){
					wordVectorMatrix.set(Idx, i-1, Double.valueOf(sep[i]));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
