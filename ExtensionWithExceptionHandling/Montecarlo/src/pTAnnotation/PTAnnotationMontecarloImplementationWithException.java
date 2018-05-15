

package pTAnnotation;


public class PTAnnotationMontecarloImplementationWithException extends main.AbstractMontecarloImplementation {
    int numOfThreads;

    public PTAnnotationMontecarloImplementationWithException(int type, int nThreads) {
        numOfThreads = nThreads;
        pt.runtime.ParaTask.PTSchedulingPolicy scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.MixedSchedule;
        switch (type) {
            case 1 :
                scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.WorkStealing;
                break;
            case 2 :
                scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.WorkSharing;
                break;
        }
        pt.runtime.ParaTask.init(scheduleType, numOfThreads);
    }

    public java.lang.Long multiCalculate(pu.loopScheduler.LoopScheduler scheduler) {
        pt.runtime.WorkerThread worker = ((pt.runtime.WorkerThread) (java.lang.Thread.currentThread()));
        pu.loopScheduler.LoopRange range = scheduler.getChunk(worker.getThreadID());
        long start = java.lang.System.currentTimeMillis();
        if (range != null) {
            for (int i = range.loopStart; i < (range.loopEnd); i++) {
                main.PriceStock ps = new main.PriceStock();
                ps.setInitAllTasks(initAllTasks);
                ps.setTask(tasks.elementAt(i));
                ps.run();
                results.addElement(ps.getResult());
            }
        }
        long end = java.lang.System.currentTimeMillis();
        return end - start;
    }

    @SuppressWarnings("unchecked")
	@java.lang.Override
    protected void runSerial() {
        try {
            results = new java.util.Vector<>(nRunsMC);
            pu.loopScheduler.LoopScheduler scheduler = pu.loopScheduler.LoopSchedulerFactory.createLoopScheduler(0, nRunsMC, 1, numOfThreads,
            		pu.loopScheduler.AbstractLoopScheduler.LoopCondition.LessThan, pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType.Static);
            pt.runtime.TaskInfoOneArg<java.lang.Long, pu.loopScheduler.LoopScheduler> __multiTaskPtTask__ = ((pt.runtime.TaskInfoOneArg<java.lang.Long, pu.loopScheduler.LoopScheduler>)
            		(pt.runtime.ParaTask.asTask(pt.runtime.ParaTask.TaskType.MULTI, 
            				((pt.functionalInterfaces.FunctorOneArgWithReturn<java.lang.Long, pu.loopScheduler.LoopScheduler>) 
            						(( __schedulerPtNonLambdaArg__) -> multiCalculate(__schedulerPtNonLambdaArg__))))));
            pt.runtime.ParaTask.registerAsyncCatch(__multiTaskPtTask__, java.lang.Error.class, ( e) -> {
                java.lang.System.out.println("Error occurred");
            });
            pt.runtime.ParaTask.registerAsyncCatch(__multiTaskPtTask__, java.lang.RuntimeException.class, ( e) -> {
                e.printStackTrace();
            });
            pt.runtime.TaskIDGroup<java.lang.Long> __multiTaskPtTaskID__ = ((pt.runtime.TaskIDGroup<java.lang.Long>) (__multiTaskPtTask__.start(scheduler)));
            pu.RedLib.LongMaximum __multiTaskPtTaskReductionObject__ = new pu.RedLib.LongMaximum();
            pt.runtime.ParaTask.registerReduction(__multiTaskPtTaskID__, __multiTaskPtTaskReductionObject__);
            java.lang.Long startTime = java.lang.System.currentTimeMillis();
            java.lang.Long waiter = __multiTaskPtTaskID__.getReturnResult();
            java.lang.Long endTime = java.lang.System.currentTimeMillis();
            java.lang.System.out.println((("MonteCarlo for @PT took: " + (endTime - startTime)) + " milliseconds."));
        } catch (java.lang.RuntimeException e) {
            e.printStackTrace();
        } catch (java.lang.Error e) {
            java.lang.System.out.println("Error occurred");
        }
    }
}

