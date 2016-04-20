package cn.sibat.warn.service;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AlgoExcutorService extends TimerTask{
	private static AlgoExcutorService algo = new AlgoExcutorService();
	volatile ConcurrentLinkedQueue<String> blockQueue;
	private ExecutorService executorService;
	public AlgoExcutorService() {
		blockQueue = new ConcurrentLinkedQueue<String>();
		executorService = Executors.newCachedThreadPool();
		
	}
	
	public static AlgoExcutorService getInstance(){
		return algo;
	}
	public ConcurrentLinkedQueue<String> getBlockQueue() {
		return blockQueue;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}
	
	

	@Override
	public void run() {
		executorService.submit(new CalWarnScore());
		
	}

	

}
