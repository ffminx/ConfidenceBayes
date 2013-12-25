package edu.btbu.dm.confidence.IO;

public class Options {
	String lableData;
	String trainData;
	String outputPath = "output/";
	boolean removeLData = false;
	boolean weakLearning = false;
	
	
	public Options(String[] args){
		String[] options = args;
		for(String option : options){
			String[] parame = option.split("=");
			if(parame[0].equals("lableData")){
				lableData = parame[1];
			}
			if(parame[0].equals("trainData")){
				trainData = parame[1];
			}
			if(parame[0].equals("outputPath")){
				if(parame[1].lastIndexOf("/") != parame[1].length()-1) outputPath = parame[1]+"/";
				else outputPath = parame[1];
			}
			if(parame[0].equals("removeLData")){
				if(parame[1].equals("true")) removeLData = true;
			}
			if(parame[0].equals("weakLearning")){
				if(parame[1].equals("true")) weakLearning = true;
			}
		}
		
		if(lableData == null || trainData == null){
			System.out.println("ERROR:Data specified error. Please check options");
			System.exit(0);
		} 
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("options:");
		sb.append("{");
		sb.append("lableData=").append(lableData).append("|");
		sb.append("trainData=").append(trainData).append("|");
		sb.append("outputPath=").append(outputPath).append("|");
		sb.append("removeLData=").append(removeLData).append("|");
		sb.append("weakLearning=").append(weakLearning);
		sb.append("}");
		return sb.toString();
	}
}
