package pTAnnotation;

import main.AbstractXalanImplementation;
import pt.runtime.ParaTask;
import pt.runtime.ParaTask.PTSchedulingPolicy;
import pu.pi.ParIterator;
import pu.pi.ParIteratorFactory;
import apt.annotations.Future;
import apt.annotations.TaskInfoType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;



public class PTAnnotationXalanImplementationAnnotated extends AbstractXalanImplementation {
	int numOfThreads;
	int repeatFactor;
	int count = 0;
	
	@Future
	Void[] futureGroup;
	
	public PTAnnotationXalanImplementationAnnotated(int type, int nThreads, int repeatFactor) {
		this.numOfThreads = nThreads;
		this.repeatFactor = repeatFactor;
		futureGroup = new Void[this.repeatFactor];
		
		PTSchedulingPolicy scheduleType = PTSchedulingPolicy.MixedSchedule;
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
	public void transform() {
		ParIterator<String> parallelIterator = ParIteratorFactory.createParIterator(fileNameList, numOfThreads);
	
		try {
			@Future(taskType = TaskInfoType.MULTI)
			Void multiTask = multiTransformTask(parallelIterator);;
			futureGroup[count++] = multiTask;
		} catch (TransformerException e) {
			System.err.println("TransformerException occurred by Thread: " + Thread.currentThread().getId());
		} catch (IOException e){
			System.err.println("IOException occurred by Thread: " + Thread.currentThread().getId());
		}
	}
	
	public Void multiTransformTask(ParIterator<String> parallelIterator) throws TransformerException, IOException{
		while(parallelIterator.hasNext()){
			String fileName = parallelIterator.next();
			FileInputStream inputStream = new FileInputStream(fileName);
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
	
	@Override
	public void waitTillFinished(){
		Long start = System.currentTimeMillis();
		Void barrier = futureGroup[0];
		Long end = System.currentTimeMillis();
		System.out.println("barrier took: " + (end - start) + " milliseconds.");
		System.out.println("Overall " + fileIndex.get() + " xml files were loaded and processed successfully");
	}
}
