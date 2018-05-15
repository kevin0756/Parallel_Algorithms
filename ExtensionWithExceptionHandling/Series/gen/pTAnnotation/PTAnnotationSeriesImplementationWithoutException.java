

package pTAnnotation;


public class PTAnnotationSeriesImplementationWithoutException extends main.AbstractSeriesImplementation {
    int numOfThreads;

    public PTAnnotationSeriesImplementationWithoutException(int type, int numOfThreads) {
        pt.runtime.ParaTask.PTSchedulingPolicy scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.MixedSchedule;
        switch (type) {
            case 1 :
                scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.WorkStealing;
                break;
            case 2 :
                scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.WorkSharing;
                break;
        }
        pTAnnotation.PTAnnotationSeriesImplementationWithoutException.this.numOfThreads = numOfThreads;
        pt.runtime.ParaTask.init(scheduleType, numOfThreads);
    }

    @java.lang.SuppressWarnings(value = "unchecked")
    @java.lang.Override
    public void calculateFourierSeries(double omega) {
        pu.loopScheduler.LoopScheduler scheduler = pu.loopScheduler.LoopSchedulerFactory.createLoopScheduler(1, array_rows, 1, numOfThreads, pu.loopScheduler.AbstractLoopScheduler.LoopCondition.LessThan, pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType.Static);
        pt.runtime.TaskInfoTwoArgs<java.lang.Void, pu.loopScheduler.LoopScheduler, java.lang.Double> __multiTaskPtTask__ = ((pt.runtime.TaskInfoTwoArgs<java.lang.Void, pu.loopScheduler.LoopScheduler, java.lang.Double>) (pt.runtime.ParaTask.asTask(pt.runtime.ParaTask.TaskType.MULTI, ((pt.functionalInterfaces.FunctorTwoArgsNoReturn<pu.loopScheduler.LoopScheduler, java.lang.Double>) (( __schedulerPtNonLambdaArg__, __omegaPtNonLambdaArg__) -> multiFourierCalc(__schedulerPtNonLambdaArg__, __omegaPtNonLambdaArg__))))));
        pt.runtime.TaskIDGroup<java.lang.Void> __multiTaskPtTaskID__ = ((pt.runtime.TaskIDGroup<java.lang.Void>) (__multiTaskPtTask__.start(scheduler, omega)));
        java.lang.Void waiter = __multiTaskPtTaskID__.getReturnResult();
    }

    public java.lang.Void multiFourierCalc(pu.loopScheduler.LoopScheduler scheduler, double omega) {
        pt.runtime.WorkerThread worker = ((pt.runtime.WorkerThread) (java.lang.Thread.currentThread()));
        pu.loopScheduler.LoopRange range = scheduler.getChunk(worker.getThreadID());
        if (range != null) {
            for (int i = range.loopStart; i < (range.loopEnd); i++) {
                try {
                    main.AbstractSeriesImplementation.TestArray[0][i] = main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, (omega * ((double) (i))), 1);
                    main.AbstractSeriesImplementation.TestArray[1][i] = main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, (omega * ((double) (i))), 2);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

