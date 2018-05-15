package executorService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

import main.AbstractLusearchImplementation;


public class ExecutorServiceLusearchImplementation extends AbstractLusearchImplementation{
	ExecutorService executorService;
	
	public ExecutorServiceLusearchImplementation(int threadPoolType, int nThreads) {
		super();
		if(threadPoolType == 1)
			executorService = Executors.newFixedThreadPool(nThreads);
		else if (threadPoolType == 2)
			executorService = Executors.newScheduledThreadPool(nThreads);
	}
	
	@Override
	public void search() {
		List<SearchTask> taskList = new ArrayList<>();
		
		for(int i = 0; i < numOfDocs; i++) {
			taskList.add(new SearchTask(i));
		}
		
		try {
			executorService.invokeAll(taskList);
		} catch (InterruptedException e){
			e.printStackTrace();
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
	
	class SearchTask implements Callable<Void> {
		final int i;
		
		IndexReader reader;
		Searcher searcher;
		BufferedReader in;
		PrintWriter out;
		
		SearchTask(int i) {
			this.i = i;
		}
		
		@Override
		public Void call() throws Exception {
			try {
				reader = IndexReader.open("data/lusearch/index-default");
				searcher = new IndexSearcher(reader);
				String queryString = "data/lusearch/query" + (i < 10 ? "00" : (i < 100 ? "0" : "")) + i
						+ ".txt";
				in = new BufferedReader(new FileReader(queryString));
				out = new PrintWriter(
						new BufferedWriter(new FileWriter("output/queryOutput" + i + ".txt")));

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
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}	
}
