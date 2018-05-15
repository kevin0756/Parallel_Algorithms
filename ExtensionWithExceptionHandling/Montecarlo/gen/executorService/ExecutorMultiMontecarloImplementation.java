

package executorService;


public class ExecutorMultiMontecarloImplementation extends main.AbstractMontecarloImplementation {
    private java.util.concurrent.ExecutorService executorService;

    private int numOfThreads;

    private int threadPoolType;

    public ExecutorMultiMontecarloImplementation(int threadPoolType, int nThreads) {
        executorService.ExecutorMultiMontecarloImplementation.this.threadPoolType = threadPoolType;
        executorService.ExecutorMultiMontecarloImplementation.this.numOfThreads = nThreads;
    }

    private void instantiateExecutor() {
        if ((threadPoolType) == 1)
            executorService = java.util.concurrent.Executors.newFixedThreadPool(numOfThreads);
        else
            if ((threadPoolType) == 2)
                executorService = java.util.concurrent.Executors.newScheduledThreadPool(numOfThreads);
            
        
    }

    @java.lang.Override
    protected void runSerial() {
        instantiateExecutor();
        results = new java.util.Vector(nRunsMC);
        pu.loopScheduler.LoopScheduler scheduler = pu.loopScheduler.LoopSchedulerFactory.createLoopScheduler(0, nRunsMC, 1, numOfThreads, pu.loopScheduler.AbstractLoopScheduler.LoopCondition.LessThan, pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType.Static);
        pu.loopScheduler.ThreadID threadID = new pu.loopScheduler.ThreadID(numOfThreads);
        for (int j = 0; j < (numOfThreads); j++) {
            executorService.submit(new executorService.ExecutorMultiMontecarloImplementation.CalculateComputationTask(scheduler, threadID));
        }
    }

    class CalculateComputationTask implements java.util.concurrent.Callable<java.lang.Void> {
        private pu.loopScheduler.LoopScheduler scheduler;

        private pu.loopScheduler.ThreadID threadID;

        public CalculateComputationTask(pu.loopScheduler.LoopScheduler scheduler, pu.loopScheduler.ThreadID threadID) {
            executorService.ExecutorMultiMontecarloImplementation.CalculateComputationTask.this.scheduler = scheduler;
            executorService.ExecutorMultiMontecarloImplementation.CalculateComputationTask.this.threadID = threadID;
        }

        @java.lang.Override
        public java.lang.Void call() throws java.lang.Exception {
            pu.loopScheduler.LoopRange range = scheduler.getChunk(threadID.get());
            if (range != null) {
                for (int i = range.loopStart; i < (range.loopEnd); i++) {
                    main.PriceStock ps = new main.PriceStock();
                    ps.setInitAllTasks(initAllTasks);
                    ps.setTask(tasks.elementAt(i));
                    ps.run();
                    results.addElement(ps.getResult());
                }
            }
            return null;
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

