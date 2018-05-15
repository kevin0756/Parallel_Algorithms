package sequential;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

public class SequentialLUSearch extends AbstractLusearchImplementation {

	Void[] futureGroup = null;
	private int counter = 0;
	
	public SequentialLUSearch(int repeatFactor){
		futureGroup = new Void[repeatFactor];
	}
	
	@Override
	public void search() {
		boolean continueBlock = true;
		ParIterator<String> parallelIterator = ParIteratorFactory.createParIterator(fileNames, 1);
		while(continueBlock){
			try{
				Void multiTask = searchTask(parallelIterator);
				futureGroup[counter++] = multiTask;
				continueBlock = false;
			}catch(CorruptIndexException e){
				System.out.println("CorruptedIndexException occurred by thread: " + Thread.currentThread().getId());
			}catch(IOException e){
				System.out.println("IOException occurred by thread: " + Thread.currentThread().getId()); 
			}
		}
	}

	public Void searchTask(ParIterator<String> parallelIterator) throws CorruptIndexException, IOException {
		IndexReader reader;
		Searcher searcher;
		BufferedReader in;
		PrintWriter out;
		int outputIndex = 0;
		while (parallelIterator.hasNext()){
			
			reader = IndexReader.open("data/lusearch/index-default");
			searcher = new IndexSearcher(reader);
			
			String queryString = parallelIterator.next();
			in = new BufferedReader(new FileReader(queryString));
			outputIndex = fileIndex.getAndIncrement();
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
			out.close();
			
		}		
		return null;
	}
	
	@Override
	public void waitTillFinished(){
		System.out.println("overall, " + fileIndex.get() + " files were loaded and processed successfully!");
	}

}
