package edu.btbu.dm.confidence.MainMethods;

import edu.btbu.dm.confidence.IO.Options;
import edu.btbu.dm.confidence.IO.ReadDataFile;

public class ComputeConfidence {

	public static void main(String[] args){
		Options opt = new Options(args);
		System.out.println(opt.toString());
		ReadDataFile read = new ReadDataFile(opt).readAllData();
		
	}
}
