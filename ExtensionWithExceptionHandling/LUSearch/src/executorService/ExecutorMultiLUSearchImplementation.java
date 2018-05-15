package executorService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

import main.AbstractLusearchImplementation;
import pu.pi.ParIterator;
import pu.pi.ParIteratorFactory;
import pu.loopScheduler.ThreadID;

public class ExecutorMultiLUSearchImplementation extends AbstractLusearchImplementation {

	ExecutorService executorService;
	int numOfThreads;
	
	
	public ExecutorMultiLUSearchImplementation(int threadPoolType, int nThreads) {
		super();
		numOfThreads = nThreads;
		
		if(threadPoolType == 1)
			executorService = Executors.newFixedThreadPool(nThreads);
		else if (threadPoolType == 2)
			executorService = Executors.newScheduledThreadPool(nThreads);
	}
	
	@Override
	public void search() {
		boolean continueBlock = true;
		ParIterator<String> parallelIterator = ParIteratorFactory.createParIterator(fileNames, numOfThreads);
		while(continueBlock){
			try{
				for(int i = 0; i < numOfThreads; i++)
					executorService.submit(new SearchTask(parallelIterator));
				continueBlock = false;
			}catch(Exception e){
				System.out.println("Exception occurred by thread: " + Thread.currentThread().getId()); 
			}
		}
	}
	
	class SearchTask implements Callable<Void> {		
		private IndexReader reader;
		private Searcher searcher;
		private BufferedReader in;
		private PrintWriter out;
		private ParIterator<String> parallelIterator;
				
		SearchTask(ParIterator<String> parallelIterator) {
			this.parallelIterator = parallelIterator;
		}
		
		@Override
		public Void call() throws CorruptIndexException, IOException {
			while(parallelIterator.hasNext()){
				reader = IndexReader.open("data/lusearch/index-default");
				searcher = new IndexSearcher(reader);
				String queryString = parallelIterator.next();
				in = new BufferedReader(new FileReader(queryString));
				int outputIndex = fileIndex.incrementAndGet();
				out = new PrintWriter(
						new BufferedWriter(new FileWriter("output/queryOutput" + outputIndex + ".txt")));
		
				Analyzer analyzer = new StandardAnalyzer();
				QueryParser parser = new QueryParser("contents", analyzer);
				while (true) {
					String line = in.readLine();
					if (line == null || line.length() == -1)
						break;
		
					line = line.trim();
					if (line.length() == 0)
						break;
		
					Query query = null;
					try {
						query = parser.parse(line);
					} catch (Exception e) {
						e.printStackTrace();
					}
					searcher.search(query, null, 10);
					doPagingSearch(query, searcher, out);
				}
				reader.close();
				out.flush();
				
			}
			return null;
		}		
	}
	
	@Override
	public void waitTillFinished(){
		try{
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.MINUTES);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
