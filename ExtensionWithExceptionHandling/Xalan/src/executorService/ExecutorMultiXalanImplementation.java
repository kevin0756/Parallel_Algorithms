package executorService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import main.AbstractXalanImplementation;
import pu.loopScheduler.LoopRange;
import pu.loopScheduler.LoopScheduler;
import pu.loopScheduler.LoopSchedulerFactory;
import pu.loopScheduler.LoopSchedulerFactory.LoopSchedulingType;
import pu.loopScheduler.ThreadID;
import pu.pi.ParIterator;
import pu.pi.ParIteratorFactory;
import pu.loopScheduler.AbstractLoopScheduler.LoopCondition;

public class ExecutorMultiXalanImplementation extends AbstractXalanImplementation {

	private ExecutorService executorService;
	private int numOfThreads;
	
	public ExecutorMultiXalanImplementation(int threadPoolType, int nThreads) {
		super();
		this.numOfThreads = nThreads;
		if(threadPoolType == 1)
			executorService = Executors.newFixedThreadPool(nThreads);
		else if (threadPoolType == 2)
			executorService = Executors.newScheduledThreadPool(nThreads);
	}
	
	@Override
	public void transform() {
		ParIterator<String> parallelIterator = ParIteratorFactory.createParIterator(fileNameList, numOfThreads);
		boolean continuteWhileLoop = true;
		while(continuteWhileLoop){
			try{
				for(int i = 0; i < numOfThreads; i++)
					executorService.submit(new TransformTask(parallelIterator));		
				continuteWhileLoop = false;
			}catch(Exception e){
				System.err.println("error " + e.getClass() + " was thrown by thread " + Thread.currentThread().getId());
			}
		}
	}
	
	class TransformTask implements Callable<Void> {
		private ParIterator<String> iterator;

		public TransformTask(ParIterator<String> parallelIterator) {
			iterator = parallelIterator;
		}

		@Override
		public Void call() throws TransformerException, IOException {
			while(iterator.hasNext()){
				String fileName = iterator.next();
				FileInputStream inputStream = new FileInputStream(new File(fileName));
				int index = fileIndex.getAndIncrement();
				FileOutputStream outputStream = new FileOutputStream(new File(BASENAME + index + ".html"));
				Result outFile = new StreamResult(outputStream);
				Transformer transformer = template.newTransformer();
				Source inFile = new StreamSource(inputStream);
				transformer.transform(inFile, outFile);
				inputStream.close();
			}
			return null;
		}
	}
	
	@Override
	public void waitTillFinished() {
		try{
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.MINUTES);
			System.out.println("Overall " + fileIndex.get() + " were successfully loaded and processed."); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
