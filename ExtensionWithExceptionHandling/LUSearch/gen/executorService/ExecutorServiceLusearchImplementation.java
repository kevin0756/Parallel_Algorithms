

package executorService;


public class ExecutorServiceLusearchImplementation extends main.AbstractLusearchImplementation {
    java.util.concurrent.ExecutorService executorService;

    public ExecutorServiceLusearchImplementation(int threadPoolType, int nThreads) {
        super();
        if (threadPoolType == 1)
            executorService = java.util.concurrent.Executors.newFixedThreadPool(nThreads);
        else
            if (threadPoolType == 2)
                executorService = java.util.concurrent.Executors.newScheduledThreadPool(nThreads);
            
        
    }

    @java.lang.Override
    public void search() {
        java.util.List<executorService.ExecutorServiceLusearchImplementation.SearchTask> taskList = new java.util.ArrayList<>();
        for (int i = 0; i < (main.AbstractLusearchImplementation.numOfDocs); i++) {
            taskList.add(new executorService.ExecutorServiceLusearchImplementation.SearchTask(i));
        }
        try {
            executorService.invokeAll(taskList);
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
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

    class SearchTask implements java.util.concurrent.Callable<java.lang.Void> {
        final int i;

        org.apache.lucene.index.IndexReader reader;

        org.apache.lucene.search.Searcher searcher;

        java.io.BufferedReader in;

        java.io.PrintWriter out;

        SearchTask(int i) {
            this.i = i;
        }

        @java.lang.Override
        public java.lang.Void call() throws java.lang.Exception {
            try {
                reader = org.apache.lucene.index.IndexReader.open("data/lusearch/index-default");
                searcher = new org.apache.lucene.search.IndexSearcher(reader);
                java.lang.String queryString = (("data/lusearch/query" + ((i) < 10 ? "00" : (i) < 100 ? "0" : "")) + (i)) + ".txt";
                in = new java.io.BufferedReader(new java.io.FileReader(queryString));
                out = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter((("output/queryOutput" + (i)) + ".txt"))));
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
                out.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

