package edu.btbu.dm.confidence.MainMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LibSVM {
	public static void main(String[] args){
		File file = new File("E:\\毕业设计\\words.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			ArrayList<String> list = new ArrayList<String>();
			while((line=br.readLine())!=null){
				String[] sep = line.split(" ");
				for(String s : sep){
					if(!list.contains(s)) list.add(s);
				}
			}
			System.out.print(list.size());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
