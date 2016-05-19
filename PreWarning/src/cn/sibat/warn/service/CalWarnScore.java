package cn.sibat.warn.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
class CalWarnScore implements Callable<Boolean>{
		
		@Override
		public Boolean call()  {
			
			AlgoExcutorService algo = AlgoExcutorService.getInstance();
			ConcurrentLinkedQueue<String> bq = algo.getBlockQueue();
			while(!bq.isEmpty()){
				String company_id = bq.poll();
				System.out.println(company_id);
				try {
					new HandleScore().calScore(company_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		

		
	}

