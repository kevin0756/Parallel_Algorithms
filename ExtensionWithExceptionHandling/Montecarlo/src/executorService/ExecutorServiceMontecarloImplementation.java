package executorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import main.AbstractMontecarloImplementation;
import main.PriceStock;

public class ExecutorServiceMontecarloImplementation extends AbstractMontecarloImplementation{
	private ExecutorService executorService;
	private int numOfThreads;
	private int threadPoolType;
	public ExecutorServiceMontecarloImplementation(int threadPoolType, int nThreads) {
		this.threadPoolType = threadPoolType;
		this.numOfThreads = nThreads;
	}
	
	private void instantiateExecutor(){
		if(threadPoolType == 1)
			executorService = Executors.newFixedThreadPool(numOfThreads);
		else if (threadPoolType == 2)
			executorService = Executors.newScheduledThreadPool(numOfThreads);
	}
	
	class CalculateComputationTask implements Callable<Void> {
		private final int i;
		
		public CalculateComputationTask(int i) {
			this.i = i;
		}
		
		@Override
		public Void call() throws Exception {
			PriceStock ps = new PriceStock();
			ps.setInitAllTasks(initAllTasks);
			ps.setTask(tasks.elementAt(i));
			ps.run();
			{
				results.addElement(ps.getResult());
			}
			return null;
		}
		
	}

	@Override
	protected void runSerial() {
		instantiateExecutor();
		results = new Vector(nRunsMC);
		// Now do the computation.
		PriceStock ps;
		
		List<CalculateComputationTask> calculateComputationTaskList = new ArrayList<>();
		
		for(int i = 0; i < nRunsMC; i++) {
			executorService.submit(new CalculateComputationTask(i));
		}		
	}
	
	@Override
	public void waitTillFinished() {
		try {
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
