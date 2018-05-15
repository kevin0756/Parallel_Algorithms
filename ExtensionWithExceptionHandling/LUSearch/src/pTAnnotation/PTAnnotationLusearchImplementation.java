

package pTAnnotation;


public class PTAnnotationLusearchImplementation extends main.AbstractLusearchImplementation {
    pt.runtime.TaskIDGroup<java.lang.Void> __futureGroupPtTaskIDGroup__ = new pt.runtime.TaskIDGroup<>();

    volatile boolean __futureGroupPtTaskIDGroup__Synchronized = false;

    volatile java.util.concurrent.locks.Lock __futureGroupPtTaskIDGroup__Lock = new java.util.concurrent.locks.ReentrantLock();

    int numOfThreads;

    int repeatFactor;

    int counter;

    java.lang.Void[] futureGroup;

    public PTAnnotationLusearchImplementation(int type, int numOfThreads, int repeatFactor) {
        pTAnnotation.PTAnnotationLusearchImplementation.this.numOfThreads = numOfThreads;
        pTAnnotation.PTAnnotationLusearchImplementation.this.repeatFactor = repeatFactor;
        futureGroup = new java.lang.Void[repeatFactor];
        counter = 0;
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

    @SuppressWarnings("unchecked")
	@java.lang.Override
    public void search() {
        try {
            pu.pi.ParIterator<java.lang.String> parallelIterator = pu.pi.ParIteratorFactory.createParIterator(fileNames, numOfThreads);
            pt.runtime.TaskInfoOneArg<java.lang.Void, pu.pi.ParIterator<java.lang.String>> __multiTaskPtTask__ = ((pt.runtime.TaskInfoOneArg<java.lang.Void, pu.pi.ParIterator<java.lang.String>>)
            		(pt.runtime.ParaTask.asTask(pt.runtime.ParaTask.TaskType.MULTI, ((pt.functionalInterfaces.FunctorOneArgNoReturn<pu.pi.ParIterator<java.lang.String>>) 
            				(( __parallelIteratorPtNonLambdaArg__) -> searchTask(__parallelIteratorPtNonLambdaArg__))))));
            pt.runtime.ParaTask.registerAsyncCatch(__multiTaskPtTask__, java.io.IOException.class, ( e) -> {
                java.lang.System.out.println(("IOException occurred by thread: " + (java.lang.Thread.currentThread().getId())));
            });
            pt.runtime.ParaTask.registerAsyncCatch(__multiTaskPtTask__, org.apache.lucene.index.CorruptIndexException.class, ( e) -> {
                java.lang.System.out.println(("CorruptedIndexException occurred by thread: " + (java.lang.Thread.currentThread().getId())));
            });
            pt.runtime.TaskIDGroup<java.lang.Void> __multiTaskPtTaskID__ = ((pt.runtime.TaskIDGroup<java.lang.Void>) (__multiTaskPtTask__.start(parallelIterator)));
            if (!(__futureGroupPtTaskIDGroup__Synchronized)) {
                __futureGroupPtTaskIDGroup__.setInnerTask(((counter)++), __multiTaskPtTaskID__);
            }else {
                futureGroup[((counter)++)] = __multiTaskPtTaskID__.getReturnResult();
            }
        } finally {
        }
    }

    public java.lang.Void searchTask(pu.pi.ParIterator<java.lang.String> parallelIterator) throws java.io.IOException, org.apache.lucene.index.CorruptIndexException {
        org.apache.lucene.index.IndexReader reader;
        org.apache.lucene.search.Searcher searcher;
        java.io.BufferedReader in;
        java.io.PrintWriter out;
        while (parallelIterator.hasNext()) {
            reader = org.apache.lucene.index.IndexReader.open("data/lusearch/index-default");
            searcher = new org.apache.lucene.search.IndexSearcher(reader);
            java.lang.String queryString = parallelIterator.next();
            in = new java.io.BufferedReader(new java.io.FileReader(queryString));
            int outputIndex = fileIndex.incrementAndGet();
            out = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter((("output/queryOutput" + outputIndex) + ".txt"))));
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
            out.close();
        } 
        return null;
    }

    @java.lang.Override
    public void waitTillFinished() {
        java.lang.Long start = java.lang.System.currentTimeMillis();
        if (!(__futureGroupPtTaskIDGroup__Synchronized)) {
            __futureGroupPtTaskIDGroup__Lock.lock();
            if (!(__futureGroupPtTaskIDGroup__Synchronized)) {
                try {
                    __futureGroupPtTaskIDGroup__.waitTillFinished();
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
                __futureGroupPtTaskIDGroup__Synchronized = true;
            }
            __futureGroupPtTaskIDGroup__Lock.unlock();
        }
        java.lang.Void temp = futureGroup[0];
        java.lang.Long end = java.lang.System.currentTimeMillis();
        java.lang.System.out.println((("barrier took: " + (end - start)) + " milliseconds."));
        java.lang.System.out.println((("overall, " + (fileIndex.get())) + " files were loaded and processed successfully!"));
    }
}

