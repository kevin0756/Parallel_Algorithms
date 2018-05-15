

package executorService;


public class ExecutorMultiLUSearchImplementation extends main.AbstractLusearchImplementation {
    java.util.concurrent.ExecutorService executorService;

    int numOfThreads;

    public ExecutorMultiLUSearchImplementation(int threadPoolType, int nThreads) {
        super();
        numOfThreads = nThreads;
        if (threadPoolType == 1)
            executorService = java.util.concurrent.Executors.newFixedThreadPool(nThreads);
        else
            if (threadPoolType == 2)
                executorService = java.util.concurrent.Executors.newScheduledThreadPool(nThreads);
            
        
    }

    @java.lang.Override
    public void search() {
        try {
            pu.loopScheduler.LoopScheduler scheduler = pu.loopScheduler.LoopSchedulerFactory.createLoopScheduler(0, main.AbstractLusearchImplementation.numOfDocs, 1, numOfThreads, pu.loopScheduler.AbstractLoopScheduler.LoopCondition.LessThan, pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType.Static);
            pu.loopScheduler.ThreadID threadID = new pu.loopScheduler.ThreadID(numOfThreads);
            for (int i = 0; i < (numOfThreads); i++) {
                executorService.submit(new executorService.ExecutorMultiLUSearchImplementation.SearchTask(scheduler, threadID));
            }
        } catch (java.lang.Exception e) {
        }
    }

    class SearchTask implements java.util.concurrent.Callable<java.lang.Void> {
        private org.apache.lucene.index.IndexReader reader;

        private org.apache.lucene.search.Searcher searcher;

        private java.io.BufferedReader in;

        private java.io.PrintWriter out;

        private pu.loopScheduler.LoopScheduler scheduler;

        private pu.loopScheduler.ThreadID threadId;

        SearchTask(pu.loopScheduler.LoopScheduler scheduler, pu.loopScheduler.ThreadID threadID) {
            executorService.ExecutorMultiLUSearchImplementation.SearchTask.this.scheduler = scheduler;
            executorService.ExecutorMultiLUSearchImplementation.SearchTask.this.threadId = threadID;
        }

        @java.lang.Override
        public java.lang.Void call() throws java.io.IOException, org.apache.lucene.index.CorruptIndexException {
            pu.loopScheduler.LoopRange range = scheduler.getChunk(threadId.get());
            if (range != null) {
                for (int i = range.loopStart; i < (range.loopEnd); i++) {
                    reader = org.apache.lucene.index.IndexReader.open("data/lusearch/index-default");
                    searcher = new org.apache.lucene.search.IndexSearcher(reader);
                    java.lang.String queryString = (("data/lusearch/query" + (i < 10 ? "00" : i < 100 ? "0" : "")) + i) + ".txt";
                    in = new java.io.BufferedReader(new java.io.FileReader(queryString));
                    out = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter((("output/queryOutput" + i) + ".txt"))));
                    org.apache.lucene.analysis.Analyzer analyzer = new org.apache.lucene.analysis.standard.StandardAnalyzer();
                    org.apache.lucene.queryParser.QueryParser parser = new org.apache.lucene.queryParser.QueryParser("contents", analyzer);
                    while (true) {
                        java.lang.String line = in.readLine();
                        if ((line == null) || ((line.length()) == (-1)))
                            break;
                        
                        line = line.trim();
                        if ((line.length()) == 0)
                            break;
                        
                        org.apache.lucene.search.Query query = null;
                        try {
                            query = parser.parse(line);
                        } catch (java.lang.Exception e) {
                            e.printStackTrace();
                        }
                        searcher.search(query, null, 10);
                        main.AbstractLusearchImplementation.doPagingSearch(query, searcher, out);
                    } 
                    reader.close();
                    out.flush();
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

