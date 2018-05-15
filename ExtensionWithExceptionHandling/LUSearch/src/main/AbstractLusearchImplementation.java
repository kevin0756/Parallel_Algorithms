//Original code ported from the DaCapo benchmark suite
package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocCollector;

public abstract class AbstractLusearchImplementation {
	public static int numOfDocs;
	public static final int HITS_PER_PAGE = 10;
	public static final String FIELD = "contents";
	
	public static final long OUTPUT_LENGTH_0 = 1763795;
	public static final long OUTPUT_LENGTH_1 = 4377005;
	public static final long OUTPUT_LENGTH_2 = 10577706;

	private static long outputLength;
	protected List<String> fileNames = new ArrayList<String>();
	protected AtomicInteger fileIndex = new AtomicInteger(0);
	
	public void initialise() {
		int size = 2;
		for (int i = 0; i < 128; i++){
			String fileName = "";
			if(i == 50)
				fileName = "data/lusearch/nonExistingFile.txt";
			else
				fileName = "data/lusearch/query" + (i < 10 ? "00" : (i < 100 ? "0" : "")) + i + ".txt";
			fileNames.add(fileName);
		}
		switch(size) {
		case 0:
			numOfDocs = 32;
			outputLength = OUTPUT_LENGTH_0;
			break;
		case 1:
			numOfDocs = 64;
			outputLength = OUTPUT_LENGTH_1;
			break;
		case 2:
			numOfDocs = 128;
			outputLength = OUTPUT_LENGTH_2;
			break;
		default:
			throw new IllegalArgumentException("Size " + size + " must be 0 or 1.");
		}
	}

	public abstract void search();

	public void validate() {
		File directory = new File("output");
		long length = 0;
		for (File f : directory.listFiles()) {
			if (f.isFile()) {
				length += f.length();
				f.delete();
			}
		}
		
		
		if (outputLength != length) {
			System.out.println("Validation failed - total length of output files is incorrect.");
			System.out.println("Expected: " + outputLength + " Actual: " + length);
		}
	}

	public void tidyUp() {
		System.gc();
	}

	/**
	 * This demonstrates a typical paging search scenario, where the search
	 * engine presents pages of size n to the user. The user can then go to the
	 * next page if interested in the next hits.
	 * 
	 * When the query is executed for the first time, then only enough results
	 * are collected to fill 5 result pages. If the user wants to page beyond
	 * this limit, then the query is executed another time and all hits are
	 * collected.
	 * 
	 */
	public static void doPagingSearch(Query query, Searcher searcher, PrintWriter out)
			throws IOException {

		// Collect enough docs to show 5 pages
		TopDocCollector collector = new TopDocCollector(numOfDocs);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		int numTotalHits = collector.getTotalHits();
		if (numTotalHits > 0)
			out.println(numTotalHits + " total matching documents for "
					+ query.toString(FIELD));

		int start = 0;
		int end = Math.min(numTotalHits, HITS_PER_PAGE);

		while (start < Math.min(numOfDocs, numTotalHits)) {
			end = Math.min(hits.length, start + HITS_PER_PAGE);

			for (int i = start; i < end; i++) {
				Document doc = searcher.doc(hits[i].doc);
				String path = doc.get("path");
				if (path != null) {
					out.println("\t" + (i + 1) + ". " + path);
					String title = doc.get("title");
					if (title != null) {
						out.println("   Title: " + doc.get("title"));
					}
				} else {
					out.println((i + 1) + ". " + "No path for this document");
				}

			}
			start = end;
		}
	}
	
	public void waitTillFinished(){}
}
