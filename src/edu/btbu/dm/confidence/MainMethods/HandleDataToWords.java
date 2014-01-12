package edu.btbu.dm.confidence.MainMethods;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import edu.btbu.dm.confidence.IO.Options;
import edu.btbu.dm.confidence.IO.ReadDataFile;
import edu.btbu.dm.confidence.Utils.DataPreProcess;
import edu.btbu.dm.confidence.Utils.ModelPresentation;

public class HandleDataToWords {
	public static void main(String[] args){
		Options opt = new Options(args);
		System.out.println(opt.toString());
		ReadDataFile rin = new ReadDataFile(opt).readAllData();
		DataPreProcess dp = new DataPreProcess(opt,rin);
		ModelPresentation modelPresen = dp.doProcess();
		modelPresen._init();
		File file = new File("output/words.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			List<String[]> data = dp.lableDataWords;
			for(String[] sentence : data){
				for(String word : sentence){
					bw.write(word+" ");
				}
			}
			data = dp.trainDataWords;
			for(String[] sentence : data){
				for(String word : sentence){
					bw.write(word+" ");
				}
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
