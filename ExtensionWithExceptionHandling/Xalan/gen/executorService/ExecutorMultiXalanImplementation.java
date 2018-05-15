

package executorService;


public class ExecutorMultiXalanImplementation extends main.AbstractXalanImplementation {
    private java.util.concurrent.ExecutorService executorService;

    private int numOfThreads;

    public ExecutorMultiXalanImplementation(int threadPoolType, int nThreads) {
        super();
        executorService.ExecutorMultiXalanImplementation.this.numOfThreads = nThreads;
        if (threadPoolType == 1)
            executorService = java.util.concurrent.Executors.newFixedThreadPool(nThreads);
        else
            if (threadPoolType == 2)
                executorService = java.util.concurrent.Executors.newScheduledThreadPool(nThreads);
            
        
    }

    @java.lang.Override
    public void transform() {
        pu.loopScheduler.LoopScheduler scheduler = pu.loopScheduler.LoopSchedulerFactory.createLoopScheduler(0, fileNames.length, 1, numOfThreads, pu.loopScheduler.AbstractLoopScheduler.LoopCondition.LessThan, pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType.Static);
        pu.loopScheduler.ThreadID threadID = new pu.loopScheduler.ThreadID(numOfThreads);
        for (int i = 0; i < (numOfThreads); i++) {
            executorService.submit(new executorService.ExecutorMultiXalanImplementation.TransformTask(scheduler, threadID));
        }
    }

    class TransformTask implements java.util.concurrent.Callable<java.lang.Void> {
        private pu.loopScheduler.LoopScheduler scheduler;

        private pu.loopScheduler.ThreadID threadID;

        public TransformTask(pu.loopScheduler.LoopScheduler scheduler, pu.loopScheduler.ThreadID threadID) {
            executorService.ExecutorMultiXalanImplementation.TransformTask.this.scheduler = scheduler;
            executorService.ExecutorMultiXalanImplementation.TransformTask.this.threadID = threadID;
        }

        @java.lang.Override
        public java.lang.Void call() {
            pu.loopScheduler.LoopRange range = scheduler.getChunk(threadID.get());
            if (range != null) {
                for (int i = range.loopStart; i < (range.loopEnd); i++) {
                    try {
                        java.lang.String fileName = fileNames[i].substring(((fileNames[i].lastIndexOf("/")) + 1));
                        java.io.FileOutputStream outputStream = new java.io.FileOutputStream(new java.io.File((((main.AbstractXalanImplementation.BASENAME) + fileName) + ".html")));
                        javax.xml.transform.Result outFile = new javax.xml.transform.stream.StreamResult(outputStream);
                        javax.xml.transform.Transformer transformer = template.newTransformer();
                        java.io.FileInputStream inputStream = new java.io.FileInputStream(new java.io.File(fileNames[i]));
                        javax.xml.transform.Source inFile = new javax.xml.transform.stream.StreamSource(inputStream);
                        transformer.transform(inFile, outFile);
                        inputStream.close();
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                    }
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

