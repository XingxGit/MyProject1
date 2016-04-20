package cn.sibat.warn.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
class CalWarnScore implements Callable<Boolean>{
		
		@Override
		public Boolean call() throws Exception {
			
			AlgoExcutorService algo = AlgoExcutorService.getInstance();
			ConcurrentLinkedQueue<String> bq = algo.getBlockQueue();
			while(!bq.isEmpty()){
				String company_id = bq.poll();
				System.out.println(company_id);
				new HandleScore().calScore(company_id);
			}
			return true;
		}
		

		
	}

