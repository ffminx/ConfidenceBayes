package edu.btbu.dm.confidence.MainMethods;

import Jama.Matrix;
import edu.btbu.dm.confidence.IO.Options;
import edu.btbu.dm.confidence.IO.ReadDataFile;
import edu.btbu.dm.confidence.Utils.DataPreProcess;
import edu.btbu.dm.confidence.Utils.ModelPresentation;

public class ComputeConfidence {

	public static void main(String[] args){
		Options opt = new Options(args);
		System.out.println(opt.toString());
		ReadDataFile rin = new ReadDataFile(opt).readAllData();
		ModelPresentation model = new DataPreProcess(opt,rin).doProcess();
		Matrix wordsCount = model.ComputeWordsCounts();
		System.out.println();
	}
}
