

package main;


public abstract class AbstractLusearchImplementation {
    public static int numOfDocs;

    public static final int HITS_PER_PAGE = 10;

    public static final java.lang.String FIELD = "contents";

    public static final long OUTPUT_LENGTH_0 = 1763795;

    public static final long OUTPUT_LENGTH_1 = 4377005;

    private static long outputLength;

    protected java.util.List<java.lang.String> fileNames = new java.util.ArrayList<java.lang.String>();

    protected java.util.concurrent.atomic.AtomicInteger fileIndex = new java.util.concurrent.atomic.AtomicInteger((-1));

    public void initialise(int size) {
        for (int i = 0; i < 128; i++) {
            java.lang.String fileName = "";
            if (i == 50)
                fileName = "data/lusearch/nonExistingFile.txt";
            else
                fileName = (("data/lusearch/query" + (i < 10 ? "00" : i < 100 ? "0" : "")) + i) + ".txt";
            
            fileNames.add(fileName);
        }
        switch (size) {
            case 0 :
                main.AbstractLusearchImplementation.numOfDocs = 32;
                main.AbstractLusearchImplementation.outputLength = main.AbstractLusearchImplementation.OUTPUT_LENGTH_0;
                break;
            case 1 :
                main.AbstractLusearchImplementation.numOfDocs = 64;
                main.AbstractLusearchImplementation.outputLength = main.AbstractLusearchImplementation.OUTPUT_LENGTH_1;
                break;
            default :
                throw new java.lang.IllegalArgumentException((("Size " + size) + " must be 0 or 1."));
        }
    }

    public abstract void search();

    public void validate() {
        java.io.File directory = new java.io.File("output");
        long length = 0;
        for (java.io.File f : directory.listFiles()) {
            if (f.isFile()) {
                length += f.length();
                f.delete();
            }
        }
        if ((main.AbstractLusearchImplementation.outputLength) != length) {
            java.lang.System.out.println("Validation failed - total length of output files is incorrect.");
            java.lang.System.out.println(((("Expected: " + (main.AbstractLusearchImplementation.outputLength)) + " Actual: ") + length));
        }
    }

    public void tidyUp() {
        java.lang.System.gc();
    }

    public static void doPagingSearch(org.apache.lucene.search.Query query, org.apache.lucene.search.Searcher searcher, java.io.PrintWriter out) throws java.io.IOException {
        org.apache.lucene.search.TopDocCollector collector = new org.apache.lucene.search.TopDocCollector(main.AbstractLusearchImplementation.numOfDocs);
        searcher.search(query, collector);
        org.apache.lucene.search.ScoreDoc[] hits = collector.topDocs().scoreDocs;
        int numTotalHits = collector.getTotalHits();
        if (numTotalHits > 0)
            out.println(((numTotalHits + " total matching documents for ") + (query.toString(main.AbstractLusearchImplementation.FIELD))));
        
        int start = 0;
        int end = java.lang.Math.min(numTotalHits, main.AbstractLusearchImplementation.HITS_PER_PAGE);
        while (start < (java.lang.Math.min(main.AbstractLusearchImplementation.numOfDocs, numTotalHits))) {
            end = java.lang.Math.min(hits.length, (start + (main.AbstractLusearchImplementation.HITS_PER_PAGE)));
            for (int i = start; i < end; i++) {
                org.apache.lucene.document.Document doc = searcher.doc(hits[i].doc);
                java.lang.String path = doc.get("path");
                if (path != null) {
                    out.println(((("\t" + (i + 1)) + ". ") + path));
                    java.lang.String title = doc.get("title");
                    if (title != null) {
                        out.println(("   Title: " + (doc.get("title"))));
                    }
                }else {
                    out.println((((i + 1) + ". ") + "No path for this document"));
                }
            }
            start = end;
        } 
    }

    public void waitTillFinished() {
    }
}

