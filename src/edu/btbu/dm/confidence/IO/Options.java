package edu.btbu.dm.confidence.IO;

public class Options {
	String lableData;
	String trainData;
	String outputPath = "output/";
	boolean removeLData = false;
	boolean weakLearning = false;
	int wordLength = 2;
	String[] wordCharacter = null;
	String[] classes = null;
	
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
			if(parame[0].equals("wordLength")){
				wordLength = Integer.valueOf(parame[1]);
			}
			if(parame[0].equals("wordCharacter")){
				wordCharacter = parame[1].split(",");
			}
			if(parame[0].equals("classes")){
				classes = parame[1].split(",");
			}
		}
		
		if(lableData == null || trainData == null){
			System.out.println("ERROR:Data specified error. Please check options");
			System.exit(0);
		} 
	}
	
	public String[] getClasses(){
		return this.classes;
	}
	
	public void setClasses(String[] classes){
		this.classes = classes;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("options:");
		sb.append("{");
		sb.append("lableData=").append(lableData).append("|");
		sb.append("trainData=").append(trainData).append("|");
		sb.append("outputPath=").append(outputPath).append("|");
		sb.append("removeLData=").append(removeLData).append("|");
		sb.append("weakLearning=").append(weakLearning).append("|");
		sb.append("wordLength=").append(wordLength).append("|");
		if(wordCharacter !=null){
			sb.append("wordCharacter=");
			for(String s : wordCharacter){
				sb.append(s).append(",");
			}
			sb.delete(sb.length()-1,sb.length());
		}
		sb.append("|");
		if(classes != null){
			sb.append("classes=");
			for(String s : classes){
				sb.append(s).append(",");
			}
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("}");
		return sb.toString();
	}
}
