

package executorService;


public class ExecutorServiceMontecarloImplementation extends main.AbstractMontecarloImplementation {
    private java.util.concurrent.ExecutorService executorService;

    private int numOfThreads;

    private int threadPoolType;

    public ExecutorServiceMontecarloImplementation(int threadPoolType, int nThreads) {
        executorService.ExecutorServiceMontecarloImplementation.this.threadPoolType = threadPoolType;
        executorService.ExecutorServiceMontecarloImplementation.this.numOfThreads = nThreads;
    }

    private void instantiateExecutor() {
        if ((threadPoolType) == 1)
            executorService = java.util.concurrent.Executors.newFixedThreadPool(numOfThreads);
        else
            if ((threadPoolType) == 2)
                executorService = java.util.concurrent.Executors.newScheduledThreadPool(numOfThreads);
            
        
    }

    class CalculateComputationTask implements java.util.concurrent.Callable<java.lang.Void> {
        private final int i;

        public CalculateComputationTask(int i) {
            this.i = i;
        }

        @java.lang.Override
        public java.lang.Void call() throws java.lang.Exception {
            main.PriceStock ps = new main.PriceStock();
            ps.setInitAllTasks(initAllTasks);
            ps.setTask(tasks.elementAt(i));
            ps.run();
            {
                results.addElement(ps.getResult());
            }
            return null;
        }
    }

    @java.lang.Override
    protected void runSerial() {
        instantiateExecutor();
        results = new java.util.Vector(nRunsMC);
        main.PriceStock ps;
        java.util.List<executorService.ExecutorServiceMontecarloImplementation.CalculateComputationTask> calculateComputationTaskList = new java.util.ArrayList<>();
        for (int i = 0; i < (nRunsMC); i++) {
            executorService.submit(new executorService.ExecutorServiceMontecarloImplementation.CalculateComputationTask(i));
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

