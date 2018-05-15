package pTAnnotation;


import main.AbstractSeriesImplementation;
import pt.runtime.ParaTask;
import pt.runtime.ParaTask.PTSchedulingPolicy;
import pt.runtime.WorkerThread;
import pu.loopScheduler.AbstractLoopScheduler.LoopCondition;
import pu.loopScheduler.LoopRange;
import pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType;
import pu.loopScheduler.LoopScheduler;
import pu.loopScheduler.LoopSchedulerFactory;
import apt.annotations.Future;
import apt.annotations.TaskInfoType;

public class PTAnnotationSeriesImplementationAnnotated extends AbstractSeriesImplementation{
	int numOfThreads;
	public PTAnnotationSeriesImplementationAnnotated(int type, int numOfThreads) {
		PTSchedulingPolicy scheduleType = PTSchedulingPolicy.MixedSchedule;
		switch(type){
		case 1:
			scheduleType = PTSchedulingPolicy.WorkStealing;
			break;
		case 2:
			scheduleType = PTSchedulingPolicy.WorkSharing;
			break;
		}
		this.numOfThreads = numOfThreads;
		ParaTask.init(scheduleType, numOfThreads);
	}

	@Override
	public void calculateFourierSeries(double omega) {
		try{
			//new LoopSplitter(1, array_rows, 1, numOfThreads, LoopCondition.LessThan);
			LoopScheduler scheduler = LoopSchedulerFactory.createLoopScheduler(1, array_rows, 1, numOfThreads, LoopCondition.LessThan, LoopSchedulingType.Static);
			@Future(taskType=TaskInfoType.MULTI)
			Void multiTask = multiFourierCalc(scheduler, omega);
			Void waiter = multiTask;
		}catch(RuntimeException e){
			System.out.println("RuntimeException encountered by thread: " + Thread.currentThread().getId());
		}catch(Error e){
			System.out.println("Error encountered by thread: " + Thread.currentThread().getId()); 
		}
	}
	
	public Void multiFourierCalc(LoopScheduler scheduler, double omega){
		WorkerThread worker = (WorkerThread) Thread.currentThread();
		LoopRange range = scheduler.getChunk(worker.getThreadID());
		if(range != null){
			for (int i = range.loopStart; i < range.loopEnd; i++){
				try{
					// Calculate A[i] terms. Note, once again, that we
			        // can ignore the 2/period term outside the integral
			        // since the period is 2 and the term cancels itself
			        // out.
			        TestArray[0][i] = TrapezoidIntegrate((double)0.0,
			                          (double)2.0,
			                          1000,
			                          omega * (double)i,
			                          1);                       // 1 = cosine term.
			
			        // Calculate the B[i] terms.
			        TestArray[1][i] = TrapezoidIntegrate((double)0.0,
			                          (double)2.0,
			                          1000,
			                          omega * (double)i,
			                          2);                       // 2 = sine term.
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}	
}
