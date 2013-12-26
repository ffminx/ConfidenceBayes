package edu.btbu.dm.confidence.Utils;

import java.util.ArrayList;
import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

public class ParserUtil {
	static JiebaSegmenter segmenter = new JiebaSegmenter();
	
	public static List<String> parserResult(String str){
		List<SegToken> tokens = segmenter.process(str, SegMode.SEARCH);
		List<String> result = new ArrayList<String>();
		for(SegToken token : tokens){
			result.add(token.token);
		}
		return result;
	}
}
