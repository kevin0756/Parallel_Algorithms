package main;

public class XalanBenchRunner {
	private AbstractXalanImplementation implementation;
	int repeatFactor;
	
	public XalanBenchRunner(AbstractXalanImplementation imp){
		this.implementation = imp;
	}
	
	private void init(){
		implementation.initialise();
	}
	
	private void kernel(){
		for(int i = 0; i < repeatFactor; i++){
			implementation.run();
		}
		implementation.waitTillFinished();
	}
	
	private void validate(){
		implementation.validate();
	}
	
	private void tidyUp(){
		implementation.tidyUp();
	}
	
	public long run(int repeatFactor){
		this.repeatFactor = repeatFactor;
		init();
		long start = System.currentTimeMillis();
		kernel();
		long end = System.currentTimeMillis();
		validate();
		tidyUp();
		return end - start;
	}
}
