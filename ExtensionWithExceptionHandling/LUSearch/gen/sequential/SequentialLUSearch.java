

package sequential;


public class SequentialLUSearch extends main.AbstractLusearchImplementation {
    java.lang.Void[] futureGroup = null;

    private int counter = 0;

    @java.lang.Override
    public void search() {
        try {
            pu.pi.ParIterator<java.lang.String> parallelIterator = pu.pi.ParIteratorFactory.createParIterator(fileNames, 1);
            pt.runtime.TaskInfoOneArg<java.lang.Void, pu.pi.ParIterator<java.lang.String>> __multiTaskPtTask__ = ((pt.runtime.TaskInfoOneArg<java.lang.Void, pu.pi.ParIterator<java.lang.String>>) (pt.runtime.ParaTask.asTask(pt.runtime.ParaTask.TaskType.ONEOFF, ((pt.functionalInterfaces.FunctorOneArgNoReturn<pu.pi.ParIterator<java.lang.String>>) (( __parallelIteratorPtNonLambdaArg__) -> searchTask(__parallelIteratorPtNonLambdaArg__))))));
            pt.runtime.ParaTask.registerAsyncCatch(__multiTaskPtTask__, java.io.IOException.class, ( e) -> {
                java.lang.System.out.println(("IOException occurred by thread: " + (java.lang.Thread.currentThread().getId())));
            });
            pt.runtime.ParaTask.registerAsyncCatch(__multiTaskPtTask__, org.apache.lucene.index.CorruptIndexException.class, ( e) -> {
                java.lang.System.out.println(("CorruptedIndexException occurred by thread: " + (java.lang.Thread.currentThread().getId())));
            });
            pt.runtime.TaskID<java.lang.Void> __multiTaskPtTaskID__ = __multiTaskPtTask__.start(parallelIterator);
            futureGroup[((counter)++)] = __multiTaskPtTaskID__.getReturnResult();
        } finally {
        }
    }

    public java.lang.Void searchTask(pu.pi.ParIterator<java.lang.String> parallelIterator) throws java.io.IOException, org.apache.lucene.index.CorruptIndexException {
        org.apache.lucene.index.IndexReader reader;
        org.apache.lucene.search.Searcher searcher;
        java.io.BufferedReader in;
        java.io.PrintWriter out;
        int outputIndex = 0;
        while (parallelIterator.hasNext()) {
            reader = org.apache.lucene.index.IndexReader.open("data/lusearch/index-default");
            searcher = new org.apache.lucene.search.IndexSearcher(reader);
            java.lang.String queryString = parallelIterator.next();
            in = new java.io.BufferedReader(new java.io.FileReader(queryString));
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
        java.lang.System.out.println((("overall, " + (fileIndex.get())) + " files were loaded and processed successfully!"));
    }
}

