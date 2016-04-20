package cn.sibat.warn.service;

import java.util.HashMap;
import java.util.Map;

public class AchiScore {
	
	public static Map<String,String> getSeIndex(Map<String,Double> map){
		Map<String,String> inMap = new HashMap<>();
		for(String s:map.keySet()){
			switch(s){
			case "拖欠工资":{inMap.put(s, ClassifyIndex.getOne(map.get(s)));break;}
			case "工资未足额支付":{inMap.put(s, ClassifyIndex.getTwo(map.get(s)));break;}
			case "减少福利待遇":{inMap.put(s, ClassifyIndex.getThree(map.get(s)));break;}
			case "调整规章制度":{inMap.put(s, ClassifyIndex.getFour(map.get(s)));break;}
			case "加班时间降低":{inMap.put(s, ClassifyIndex.getFive(map.get(s)));break;}
			case "非法用工":{inMap.put(s, ClassifyIndex.getSix(map.get(s)));break;}
			case "拖欠社保":{inMap.put(s, ClassifyIndex.getSeven(map.get(s)));break;}
			case "缴纳社保人数降低":{inMap.put(s, ClassifyIndex.getEight(map.get(s)));break;}
			case "集体争议、群体诉求人数占全厂人工比重":{inMap.put(s, ClassifyIndex.getNine(map.get(s)));break;}
			case "投诉举报案件同年累计":{inMap.put(s, ClassifyIndex.getTen(map.get(s)));break;}
			case "营业收入降低":{inMap.put(s, ClassifyIndex.getEleven(map.get(s)));break;}
			case "利润总额降低":{inMap.put(s, ClassifyIndex.getTwelve(map.get(s)));break;}
			case "拟搬迁生产（经营）、企业收购兼并":{inMap.put(s, ClassifyIndex.getThirteen(map.get(s)));break;}
			case "生产（经营）停顿":{inMap.put(s, ClassifyIndex.getFourteen(map.get(s)));break;}
			case "经济性裁员":{inMap.put(s, ClassifyIndex.getFifteen(map.get(s)));break;}
			case "法定代表人或经营者失联":{inMap.put(s, ClassifyIndex.getSixteen(map.get(s)));break;}
			case "拖欠房租":{inMap.put(s, ClassifyIndex.getSeventeen(map.get(s)));break;}
			case "拖欠水费":{inMap.put(s, ClassifyIndex.getEighteen(map.get(s)));break;}
			case "拖欠电费":{inMap.put(s, ClassifyIndex.getNineteen(map.get(s)));break;}
			case "拟转卖物业":{inMap.put(s, ClassifyIndex.getTwenty(map.get(s)));break;}
			case "业主拟停租":{inMap.put(s, ClassifyIndex.getTwentyOne(map.get(s)));break;}
			case "被申请财产保护":{inMap.put(s, ClassifyIndex.getTwentyTwo(map.get(s)));break;}
			case "资产被查封、冻结":{inMap.put(s, ClassifyIndex.getTwentyThree(map.get(s)));break;}
			case "生产（经营）场所已纳入旧城改造计划":{inMap.put(s, ClassifyIndex.getTwentyFour(map.get(s)));break;}
			case "行政处罚停止生产（经营）整顿":{inMap.put(s, ClassifyIndex.getTwentyFive(map.get(s)));break;}
			case "主管机关依法取缔":{inMap.put(s, ClassifyIndex.getTwentySix(map.get(s)));break;}
			case "重大经济诉讼纠纷":{inMap.put(s, ClassifyIndex.getTwentySeven(map.get(s)));break;}
			
			}
			
		}
		return inMap;
	}

}
