

package executorService;


public class ExecutorMultiSeriesImplementation extends main.AbstractSeriesImplementation {
    private java.util.concurrent.ExecutorService executorService;

    private int numOfThreads;

    public ExecutorMultiSeriesImplementation(int threadPoolType, int nThreads) {
        super();
        executorService.ExecutorMultiSeriesImplementation.this.numOfThreads = nThreads;
        if (threadPoolType == 1)
            executorService = java.util.concurrent.Executors.newFixedThreadPool(nThreads);
        else
            if (threadPoolType == 2)
                executorService = java.util.concurrent.Executors.newScheduledThreadPool(nThreads);
            
        
    }

    static class FourierCalc implements java.util.concurrent.Callable<java.lang.Void> {
        private final double omega;

        private pu.loopScheduler.LoopScheduler scheduler;

        private pu.loopScheduler.ThreadID threadID;

        FourierCalc(pu.loopScheduler.LoopScheduler scheduler, pu.loopScheduler.ThreadID threadID, double omega) {
            this.omega = omega;
            executorService.ExecutorMultiSeriesImplementation.FourierCalc.this.scheduler = scheduler;
            executorService.ExecutorMultiSeriesImplementation.FourierCalc.this.threadID = threadID;
        }

        @java.lang.Override
        public java.lang.Void call() {
            pu.loopScheduler.LoopRange range = scheduler.getChunk(threadID.get());
            if (range != null) {
                for (int i = range.loopStart; i < (range.loopEnd); i++) {
                    main.AbstractSeriesImplementation.TestArray[0][i] = main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, ((omega) * ((double) (i))), 1);
                    main.AbstractSeriesImplementation.TestArray[1][i] = main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, ((omega) * ((double) (i))), 2);
                }
            }
            return null;
        }
    }

    @java.lang.Override
    public void calculateFourierSeries(double omega) {
        pu.loopScheduler.LoopScheduler scheduler = pu.loopScheduler.LoopSchedulerFactory.createLoopScheduler(1, array_rows, 1, numOfThreads, pu.loopScheduler.AbstractLoopScheduler.LoopCondition.LessThan, pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType.Static);
        pu.loopScheduler.ThreadID threadID = new pu.loopScheduler.ThreadID(numOfThreads);
        for (int j = 0; j < (numOfThreads); j++) {
            executorService.submit(new executorService.ExecutorMultiSeriesImplementation.FourierCalc(scheduler, threadID, omega));
        }
    }

    @java.lang.Override
    public void waitTillFinished() {
        try {
            executorService.shutdown();
            executorService.awaitTermination(5, java.util.concurrent.TimeUnit.MINUTES);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}

