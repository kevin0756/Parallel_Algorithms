package executorService;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import main.AbstractMontecarloImplementation;
import main.PriceStock;
import pu.loopScheduler.AbstractLoopScheduler.LoopCondition;
import pu.loopScheduler.LoopRange;
import pu.loopScheduler.LoopScheduler;
import pu.loopScheduler.LoopSchedulerFactory;
import pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType;
import pu.loopScheduler.ThreadID;

public class ExecutorMultiMontecarloImplementation extends AbstractMontecarloImplementation {

	private ExecutorService executorService;
	private int numOfThreads;
	private int threadPoolType;
	public ExecutorMultiMontecarloImplementation(int threadPoolType, int nThreads) {
		this.threadPoolType = threadPoolType;		
		this.numOfThreads = nThreads;
	}
	
	private void instantiateExecutor(){
		if(threadPoolType == 1)
			executorService = Executors.newFixedThreadPool(numOfThreads);
		else if (threadPoolType == 2)
			executorService = Executors.newScheduledThreadPool(numOfThreads);
	}
	
	@Override
	protected void runSerial() {
		instantiateExecutor();
		results = new Vector(nRunsMC);
		LoopScheduler scheduler = LoopSchedulerFactory.createLoopScheduler(0, nRunsMC, 1, numOfThreads, LoopCondition.LessThan, LoopSchedulingType.Static);
		ThreadID threadID = new ThreadID(numOfThreads);
		for(int j = 0; j < numOfThreads; j++){
			executorService.submit(new CalculateComputationTask(scheduler, threadID));
		}		
	}
	
	class CalculateComputationTask implements Callable<Void> {
		private LoopScheduler scheduler;
		private ThreadID threadID;
		
		public CalculateComputationTask(LoopScheduler scheduler, ThreadID threadID) {
			this.scheduler = scheduler;
			this.threadID = threadID;
		}
		
		@Override
		public Void call() throws Exception {
			LoopRange range = scheduler.getChunk(threadID.get());
			if(range != null){
				for (int i = range.loopStart; i < range.loopEnd; i++){
					PriceStock ps = new PriceStock();
					ps.setInitAllTasks(initAllTasks);
					ps.setTask(tasks.elementAt(i));
					ps.run();
					results.addElement(ps.getResult());					
				}
			}
			return null;
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
