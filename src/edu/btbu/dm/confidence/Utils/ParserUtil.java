package edu.btbu.dm.confidence.Utils;

import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class ParserUtil {
	static JiebaSegmenter segmenter = new JiebaSegmenter();
	
	public static String[] parserResult(String str){
		List<SegToken> tokens = segmenter.process(str, SegMode.SEARCH);
		String[] result = new String[tokens.size()];
		for(int i=0;i<tokens.size();i++){
			
		}
		return null;
	}
}
