package pTAnnotation;

import main.AbstractLusearchImplementation;
import pt.runtime.ParaTask;
import pt.runtime.ParaTask.PTSchedulingPolicy;
import pu.pi.ParIterator;
import pu.pi.ParIteratorFactory;
import apt.annotations.Future;
import apt.annotations.TaskInfoType;

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



public class PTAnnotationLusearchImplementationAnnotated extends AbstractLusearchImplementation{
	
	int numOfThreads;
	int repeatFactor;
	int counter;
	@Future
	Void[] futureGroup;
	static final int taskCount = 8;
	public PTAnnotationLusearchImplementationAnnotated(int type, int numOfThreads, int repeatFactor) {
		this.numOfThreads = numOfThreads;
		this.repeatFactor = repeatFactor;
		futureGroup = new Void[repeatFactor];
		counter = 0;
		ParaTask.PTSchedulingPolicy scheduleType = PTSchedulingPolicy.MixedSchedule;
		switch(type){
		case 1:
			scheduleType = PTSchedulingPolicy.WorkStealing;
			break;
		case 2:
			scheduleType = PTSchedulingPolicy.WorkSharing;
			break;
		}
		ParaTask.init(scheduleType, numOfThreads);
	}

	@Override
	public void search() {
		try{
			ParIterator<String> parallelIterator = ParIteratorFactory.createParIterator(fileNames, numOfThreads);
			@Future(taskType=TaskInfoType.MULTI, taskCount=taskCount)
			Void multiTask = searchTask(parallelIterator);
			futureGroup[counter++] = multiTask;			
		}catch(CorruptIndexException e){
			System.out.println("CorruptedIndexException occurred by thread: " + Thread.currentThread().getId());
		}catch(IOException e){
			System.out.println("IOException occurred by thread: " + Thread.currentThread().getId()); 
		}
	}

	public Void searchTask(ParIterator<String> parallelIterator) throws CorruptIndexException, IOException {
		IndexReader reader;
		Searcher searcher;
		BufferedReader in;
		PrintWriter out;
	
		while (parallelIterator.hasNext()){
			
			reader = IndexReader.open("data/lusearch/index-default");
			searcher = new IndexSearcher(reader);
			
			String queryString = parallelIterator.next();
			in = new BufferedReader(new FileReader(queryString));
			int outputIndex = fileIndex.getAndIncrement();
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
		Long start = System.currentTimeMillis();
		Void temp = futureGroup[0];
		Long end = System.currentTimeMillis();
		System.out.println("barrier took: " + (end - start) + " milliseconds.");
		System.out.println("overall, " + fileIndex.get() + " files were loaded and processed successfully!");
	}
}
