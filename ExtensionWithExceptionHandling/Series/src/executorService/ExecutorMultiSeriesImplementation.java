package executorService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import main.AbstractSeriesImplementation;
import pu.loopScheduler.AbstractLoopScheduler.LoopCondition;
import pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType;
import pu.loopScheduler.LoopRange;
import pu.loopScheduler.LoopScheduler;
import pu.loopScheduler.LoopSchedulerFactory;
import pu.loopScheduler.ThreadID;

public class ExecutorMultiSeriesImplementation extends AbstractSeriesImplementation {
	private ExecutorService executorService;
	private int numOfThreads;
	
	public ExecutorMultiSeriesImplementation(int threadPoolType, int nThreads) {
		super();
		this.numOfThreads = nThreads;
		if(threadPoolType == 1)
			executorService = Executors.newFixedThreadPool(nThreads);
		else if (threadPoolType == 2)
			executorService = Executors.newScheduledThreadPool(nThreads);	
	}
	
	
	 static class FourierCalc implements Callable<Void> {
			private final double omega;
			private LoopScheduler scheduler;
			private ThreadID threadID; 
			
			FourierCalc(LoopScheduler scheduler, ThreadID threadID, double omega) {
				this.omega = omega;
				this.scheduler = scheduler;
				this.threadID = threadID;
			}

			@Override
			public Void call()  {
				LoopRange range = scheduler.getChunk(threadID.get());
				if(range != null){
					for(int i = range.loopStart; i < range.loopEnd; i++){
					    TestArray[0][i] = TrapezoidIntegrate((double)0.0,
				                          (double)2.0,
				                          1000,
				                          omega * (double)i,
				                          1);                       
		
				        TestArray[1][i] = TrapezoidIntegrate((double)0.0,
				                          (double)2.0,
				                          1000,
				                          omega * (double)i,
				                          2);
					}
				}
				return null;
			}			
		}
	
	
	@Override
	public void calculateFourierSeries(double omega) {
		LoopScheduler scheduler = LoopSchedulerFactory.createLoopScheduler(1, array_rows, 1, numOfThreads, LoopCondition.LessThan, LoopSchedulingType.Static);
		ThreadID threadID = new ThreadID(numOfThreads);
		for (int j = 0; j < numOfThreads; j++){
			executorService.submit(new FourierCalc(scheduler, threadID, omega));
		}
	}
	
	@Override
	public void waitTillFinished() {
		try{
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.MINUTES);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
