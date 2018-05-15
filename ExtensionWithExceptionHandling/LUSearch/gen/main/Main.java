

package main;


public class Main {
    private static boolean repeat = true;

    public static void main(java.lang.String[] args) throws java.io.IOException {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.System.in));
        while (main.Main.repeat) {
            java.lang.System.out.println("***************** RUNNING LUSearch BENCHMARK *****************");
            java.lang.System.out.println("1. ExecutorService-Multi");
            java.lang.System.out.println("2. ParallelTask");
            java.lang.System.out.println("***********************************************************");
            try {
                java.lang.System.out.print("Choose one of the benchmark approaches listed above: ");
                java.lang.String approach = reader.readLine();
                java.lang.System.out.print("Insert the number of threads to run the benchmark with: ");
                java.lang.String numOfThreasd = reader.readLine();
                main.Main.runBenchmark(approach, java.lang.Integer.parseInt(numOfThreasd));
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } 
    }

    public static int paraTaskSchdulingPolicy() {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.System.in));
        while (true) {
            java.lang.System.out.println("\n\nSelect ParaTask\'s scheduling policy from the following options:");
            java.lang.System.out.println("1. Mixed");
            java.lang.System.out.println("2. WorkStealing");
            java.lang.System.out.println("3. WorkSharing");
            java.lang.System.out.print("Enter the schduling policy: ");
            java.lang.String policy = "";
            try {
                policy = reader.readLine();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            switch (policy) {
                case "Mixed" :
                    return 0;
                case "WorkStealing" :
                    return 1;
                case "WorkSharing" :
                    return 2;
            }
            java.lang.System.out.println("Invalid entry, please try again!");
        } 
    }

    public static int benchmarkDataSize() {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.System.in));
        while (true) {
            java.lang.System.out.println("\n\nSelect the benchmark data size from the following options:");
            java.lang.System.out.println("0. 32 Documents");
            java.lang.System.out.println("1. 64 Documents");
            java.lang.System.out.print("Enter the number ONLY! (e.g., 0): ");
            java.lang.String size = "";
            try {
                size = reader.readLine();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            if ((size.equals("0")) || (size.equals("1")))
                return java.lang.Integer.parseInt(size);
            else {
                java.lang.System.out.println("Invalid entry, please try again!");
            }
        } 
    }

    public static int benchmarkRepeatRate() {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.System.in));
        while (true) {
            java.lang.System.out.print("\nEnter benchmark repeat rate (a number bigger than 0): ");
            java.lang.String line = "";
            try {
                line = reader.readLine();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            int repeatRate = java.lang.Integer.parseInt(line);
            if (repeatRate > 0)
                return repeatRate;
            else
                java.lang.System.out.println("\n\nInvalid entry, please try again!");
            
        } 
    }

    public static int executorType() {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.System.in));
        while (true) {
            java.lang.System.out.println("\n\nSelect the type of ExecutorService from the following options:");
            java.lang.System.out.println("1. Executor service with fixed thread pool");
            java.lang.System.out.println("2. Executor service with scheduled thread pool");
            java.lang.System.out.print("Enter the number ONLY! (e.g., 1): ");
            java.lang.String line = "";
            try {
                line = reader.readLine();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            if ((line.equals("1")) || (line.equals("2")))
                return java.lang.Integer.parseInt(line);
            else
                java.lang.System.out.println("Invalid entry, please try again!");
            
        } 
    }

    public static void runBenchmark(java.lang.String approach, int numOfThreads) {
        main.LUSearchRunner LUSearchBench = null;
        main.Main.repeat = false;
        int benchmarkDataSize = -1;
        int repeatFactor = -1;
        switch (approach) {
            case "1" :
                LUSearchBench = new main.LUSearchRunner(new executorService.ExecutorMultiLUSearchImplementation(main.Main.executorType(), numOfThreads));
                break;
            case "2" :
                int schedulingPolicy = main.Main.paraTaskSchdulingPolicy();
                benchmarkDataSize = main.Main.benchmarkDataSize();
                repeatFactor = main.Main.benchmarkRepeatRate();
                LUSearchBench = new main.LUSearchRunner(new pTAnnotation.PTAnnotationLusearchImplementation(schedulingPolicy, numOfThreads, repeatFactor));
                break;
            default :
                java.lang.System.out.println("\n\nInvalid benchmakr approach, please try again!");
                main.Main.repeat = true;
                break;
        }
        if (benchmarkDataSize == (-1))
            benchmarkDataSize = main.Main.benchmarkDataSize();
        
        if (repeatFactor == (-1))
            repeatFactor = main.Main.benchmarkRepeatRate();
        
        long runtime;
        if (!(main.Main.repeat)) {
            runtime = LUSearchBench.run(benchmarkDataSize, repeatFactor);
            java.lang.System.out.println((("\n\nprocessing took : " + runtime) + " milli seconds."));
        }
    }
}

