package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

public abstract class AbstractXalanImplementation {
	protected Templates template;

	public static final String BASENAME = "output/Transformed.";
	
	
	public void run(){
		try {
			transform();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public String[] fileNames = {
			"input/acks.xml",
			"input/acks1.xml",
			"input/acks2.xml",
			"input/acks3.xml",
			"input/acks4.xml",
			"input/acks5.xml",
			"input/acks6.xml",
			"input/acks7.xml",
			"input/acks8.xml",
			"input/acks9.xml",
			
			"input/binding.xml",
			"input/binding1.xml",
			"input/binding2.xml",
			"input/binding3.xml",
			"input/binding4.xml",
			"input/binding5.xml",
			"input/binding6.xml",
			"input/binding7.xml",
			"input/binding8.xml",
			"input/binding9.xml",
			
			"input/changes.xml",
			"input/changes1.xml",
			"input/changes2.xml",
			"input/changes3.xml",
			"input/changes4.xml",
			"input/changes5.xml",
			"input/changes6.xml",
			"input/changes7.xml",
			"input/changes8.xml",
			"input/changes9.xml",
			
			"input/concepts.xml",
			"input/concepts1.xml",
			"input/concepts2.xml",
			"input/concepts3.xml",
			"input/concepts4.xml",
			"input/concepts5.xml",
			"input/concepts6.xml",
			"input/concepts7.xml",
			"input/concepts8.xml",
			"input/concepts9.xml",
			
			"input/controls.xml",
			"input/controls1.xml",
			"input/controls2.xml",
			"input/controls3.xml",
			"input/controls4.xml",
			"input/controls5.xml",
			"input/controls6.xml",
			"input/controls7.xml",
			"input/controls8.xml",
			"input/controls9.xml",
			
			"input/datatypes.xml", 
			"input/datatypes1.xml", 
			"input/datatypes2.xml", 
			"input/datatypes3.xml", 
			"input/datatypes4.xml", 
			"input/datatypes5.xml", 
			"input/datatypes6.xml",
			"input/datatypes7.xml",
			"input/datatypes8.xml",
			"input/datatypes9.xml",
			
			"input/expr.xml",
			"input/expr1.xml",
			"input/expr2.xml",
			"input/expr3.xml",
			"input/expr4.xml",
			"input/expr5.xml",
			"input/expr6.xml",
			"input/expr7.xml",
			"input/expr8.xml",
			"input/expr9.xml",
			
			"input/intro.xml",
			"input/intro1.xml",
			"input/intro2.xml",
			"input/intro3.xml",
			"input/intro4.xml",
			"input/intro5.xml",
			"input/intro6.xml",
			"input/intro7.xml",
			"input/intro8.xml",
			"input/intro9.xml",
			
			"input/nonExisting11.xml",			
			"input/nonExisting12.xml",		
						
			"input/model.xml", 
			"input/model1.xml", 
			"input/model2.xml", 
			"input/model3.xml", 
			"input/model4.xml", 
			"input/model5.xml", 
			"input/model6.xml",
			"input/model7.xml",
			"input/model8.xml",
			"input/model9.xml",
			
			"input/prod-notes.xml",
			"input/prod-notes1.xml",
			"input/prod-notes2.xml",
			"input/prod-notes3.xml",
			"input/prod-notes4.xml",
			"input/prod-notes5.xml",
			"input/prod-notes6.xml",
			"input/prod-notes7.xml",
			"input/prod-notes8.xml",
			"input/prod-notes9.xml",
			
			"input/references.xml",
			"input/references1.xml",
			"input/references2.xml",
			"input/references3.xml",
			"input/references4.xml",
			"input/references5.xml",
			"input/references6.xml",
			"input/references7.xml",
			"input/references8.xml",
			"input/references9.xml",
			
			"input/rpm.xml",
			"input/rpm1.xml",
			"input/rpm2.xml",
			"input/rpm3.xml",
			"input/rpm4.xml",
			"input/rpm5.xml",
			"input/rpm6.xml",
			"input/rpm7.xml",
			"input/rpm8.xml",
			"input/rpm9.xml",
			
			"input/schema.xml", 
			"input/schema1.xml", 
			"input/schema2.xml", 
			"input/schema3.xml", 
			"input/schema4.xml", 
			"input/schema5.xml", 
			"input/schema6.xml",
			"input/schema7.xml",
			"input/schema8.xml",
			"input/schema9.xml",
						
			"input/structure.xml",
			"input/structure1.xml",
			"input/structure2.xml",
			"input/structure3.xml",
			"input/structure4.xml",
			"input/structure5.xml",
			"input/structure6.xml",
			"input/structure7.xml",
			"input/structure8.xml",
			"input/structure9.xml",
			
			"input/terms.xml",
			"input/terms1.xml",
			"input/terms2.xml",
			"input/terms3.xml",
			"input/terms4.xml",
			
			"input/terms5.xml",
			"input/terms6.xml",
			"input/terms7.xml",
			"input/terms8.xml",
			"input/terms9.xml",
			
			"input/ui.xml"
	};
	
	protected List<String> fileNameList = null;
	protected AtomicInteger fileIndex = null;
	public void initialise() {
		fileNameList = new ArrayList<>();
		for(String fileName : fileNames)
			fileNameList.add(fileName);
		fileIndex = new AtomicInteger(0);
		System.out.println("Overall " + fileNameList.size() + " file names.");
		Source styleSheet = new StreamSource(new File("input/xmlspec.xsl"));
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			template = factory.newTemplates(styleSheet);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	
	//Method to be implemented by subclasses
	public abstract void transform();
		
	public void waitTillFinished(){}
	
	public void validate() {
		//TODO not sure how
	}
	
	public void tidyUp() {
		template = null;
		System.gc();
	}
}