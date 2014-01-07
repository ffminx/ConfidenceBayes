package edu.btbu.dm.confidence.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class WriteDataFile {
	File outputFile;
	BufferedWriter bw;
	
	public WriteDataFile(Options opt){
		StringBuilder outputPath = new StringBuilder(opt.outputPath);
		if(!new File(outputPath.toString()).exists()){
			new File(outputPath.toString()).mkdir();
		}
		if(opt.weakLearning){
			outputPath.append("weakLearningOutPut.csv");
		}else{
			outputPath.append("OutPut.csv");
		}
		outputFile = new File(outputPath.toString());
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile),"gbk"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Write output Error...");
		}
	}
	
	public void WriteTrainOutput(List<String> trainData,String[] tags){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<trainData.size();i++){
			sb.append(trainData.get(i));
			sb.append(",");
			sb.append(tags[i]);
			sb.append("\r\n");
			try {
				bw.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("OutPutFile write error...");
			}
			sb.delete(0, sb.length());
		}
	}
	
	public void close(){
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Write output Error...");
		}
	}
}
