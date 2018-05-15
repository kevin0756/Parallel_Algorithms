package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import executorService.ExecutorMultiMontecarloImplementation;
import jomp.JompMontecarloImplementation;
import pTAnnotation.PTAnnotationMontecarloImplementationWithException;
import pTAnnotation.PTAnnotationMontecarloImplementationWithoutException;



public class Main {
	private static boolean repeat = true;
	
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(repeat){
			System.out.println("***************** RUNNING MonteCarlo BENCHMARK *****************");		
			System.out.println("1. ExecutorService");
			System.out.println("2. ParallelTask-WithExceptionHandling");
			System.out.println("3. ParallelTask-WithoutExceptionHandling");
			System.out.println("4. Jomp");
			System.out.println("***********************************************************");		

			try {
				System.out.print("Choose one of the benchmark approaches listed above (just a number e.g., 0): ");
				String approach = reader.readLine();
				System.out.print("Insert the number of threads to run the benchmark with: ");
				String numOfThreasd = reader.readLine();
				runBenchmark(approach, Integer.parseInt(numOfThreasd));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static int paraTaskSchdulingPolicy(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.println("\n\nSelect ParaTask's scheduling policy from the following options:");
			System.out.println("1. Mixed");
			System.out.println("2. WorkStealing");
			System.out.println("3. WorkSharing");
			System.out.print("Enter the schduling policy: " );
			String policy = "";
			try{
				policy = reader.readLine();
			}catch(IOException e){
				e.printStackTrace();
			}
			
			switch(policy){
			case "1":
				return 0;
			case "2":
				return 1;
			case "3":
				return 2;
			}
			System.out.println("Invalid entry, please try again!");
		}
	}
	
	public static int benchmarkDataSize(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.println("\n\nSelect the benchmark data size from the following options:");
			System.out.println("0. 10,000 MonteCarlo Simulations");
			System.out.println("1. 60,000 MonteCarlo Simulations");
			System.out.println("2. 120,000 MonteCarlo Simulations (fails for validation, but runtime is correct)");
			System.out.print("Enter the number ONLY! (e.g., 0): ");
			String size = "";
			try{
				size = reader.readLine();
			}catch(IOException e){
				e.printStackTrace();
			}
			if(size.equals("0") || size.equals("1") || size.equals("2"))
				return Integer.parseInt(size);
			else{
				System.out.println("Invalid entry, please try again!");
			}
		}
	}
	
	public static int benchmarkRepeatRate(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.print("\nEnter benchmark repeat rate (a number bigger than 0): ");
			String line = "";
			try{
				line = reader.readLine();
			}catch(Exception e){
				e.printStackTrace();
			}
			int repeatRate = Integer.parseInt(line);
			if (repeatRate > 0)
				return repeatRate;
			else
				System.out.println("\n\nInvalid entry, please try again!");
		}
	}
	
	public static int executorType(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.println("\n\nSelect the type of ExecutorService from the following options:");
			System.out.println("1. Executor service with fixed thread pool");
			System.out.println("2. Executor service with scheduled thread pool");
			System.out.print("Enter the number ONLY! (e.g., 1): ");
			String line = "";
			try{
				line = reader.readLine();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if (line.equals("1") || line.equals("2"))
				return Integer.parseInt(line);
			else
				System.out.println("Invalid entry, please try again!");
		}
	}
	
	public static void runBenchmark(String approach, int numOfThreads){
		MontecarloBench MonteCarloBench = null;
		repeat = false;
		switch(approach){
		case "1":
			MonteCarloBench = new MontecarloBench(new ExecutorMultiMontecarloImplementation(executorType(), numOfThreads));
			break;
		case "2":
			MonteCarloBench = new MontecarloBench(new PTAnnotationMontecarloImplementationWithException(paraTaskSchdulingPolicy(), numOfThreads));
			break;
		case "3":
			MonteCarloBench = new MontecarloBench(new PTAnnotationMontecarloImplementationWithoutException(paraTaskSchdulingPolicy(), numOfThreads));
			break;
		case "4":
			//scheduling: static, dynamic, guided
			MonteCarloBench = new MontecarloBench(new JompMontecarloImplementation(numOfThreads));
			break;
		default:
			System.out.println("\n\nInvalid benchmakr approach, please try again!");
			repeat = true;
			break;
		}
		
		long runtime;
		if(!repeat){
			runtime = MonteCarloBench.run(benchmarkDataSize(), benchmarkRepeatRate());
			System.out.println("\n\nprocessing took : " + runtime + " milli seconds.");
		}
	}
}
