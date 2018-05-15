package main;

public class LUSearchRunner {
	AbstractLusearchImplementation implementation;
	int repeatFact;
	
	public LUSearchRunner(AbstractLusearchImplementation imp){
		this.implementation = imp;
	}
	
	private void init(){
		implementation.initialise();
	}
	
	private void validate(){
		implementation.validate();
	}
	
	private void tidyUp(){
		implementation.tidyUp();
	}
	
	private void kernel(){
		for (int i = 0; i < repeatFact; i++){
			implementation.search();
		}
		implementation.waitTillFinished();
	}
	
	public Long run(int repeat){
		this.repeatFact = repeat;
		init();
		
		Long start = System.currentTimeMillis();
		kernel();
		Long end = System.currentTimeMillis();
		
		validate();
		tidyUp();
		return end - start;
	}
	
}
