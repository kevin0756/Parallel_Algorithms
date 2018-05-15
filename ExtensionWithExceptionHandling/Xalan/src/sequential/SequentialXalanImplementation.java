package sequential;

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
import main.AbstractXalanImplementation;
import pu.pi.ParIterator;
import pu.pi.ParIteratorFactory;

public class SequentialXalanImplementation extends AbstractXalanImplementation {

	Void[] futureGroup = null;
	int count = 0;
	public SequentialXalanImplementation(int repeatFactor) {
		futureGroup = new Void[repeatFactor];
	}
	
	@Override
	public void transform() {
		ParIterator<String> parallelIterator = ParIteratorFactory.createParIterator(fileNameList, 1);
		boolean continueWhileLoop = true;
		while(continueWhileLoop){
			try {
				Void multiTask = task(parallelIterator);
				futureGroup[count++] = multiTask;
				continueWhileLoop = false;
			} catch (TransformerException e) {
				System.err.println("TransformerException occurred by Thread: " + Thread.currentThread().getId());
			} catch (IOException e){
				System.err.println("IOException occurred by Thread: " + Thread.currentThread().getId());
			}
		}
	}
	
	private Void task(ParIterator<String> parallelIterator) throws TransformerException, IOException{
		while(parallelIterator.hasNext()){
			String fileName = parallelIterator.next();
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			int index = fileIndex.getAndIncrement();
			FileOutputStream outputStream = new FileOutputStream(new File(
					BASENAME + index + ".html"));
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
		System.out.println("Overall " + fileIndex.get() + " xml files were loaded and processed successfully");
	}

}
