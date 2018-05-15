

package main;


public abstract class AbstractMontecarloImplementation extends main.Universal {
    public double JGFavgExpectedReturnRateMC = 0.0;

    public static boolean DEBUG = true;

    protected static java.lang.String prompt = "AppDemo> ";

    public static final int Serial = 1;

    private java.lang.String dataFilename;

    private int nTimeStepsMC = 0;

    protected int nRunsMC = 0;

    private double dTime = 1.0 / 365.0;

    private boolean initialised = false;

    private int runMode;

    protected java.util.Vector tasks;

    protected java.util.Vector results;

    public void init(java.lang.String dataFilename, int nTimeStepsMC, int nRunsMC) {
        main.AbstractMontecarloImplementation.this.dataFilename = dataFilename;
        main.AbstractMontecarloImplementation.this.nTimeStepsMC = nTimeStepsMC;
        main.AbstractMontecarloImplementation.this.nRunsMC = nRunsMC;
        main.AbstractMontecarloImplementation.this.initialised = false;
        set_prompt(main.AbstractMontecarloImplementation.prompt);
        set_DEBUG(main.AbstractMontecarloImplementation.DEBUG);
    }

    main.PriceStock psMC;

    double pathStartValue = 100.0;

    double avgExpectedReturnRateMC = 0.0;

    double avgVolatilityMC = 0.0;

    protected main.ToInitAllTasks initAllTasks = null;

    public void initSerial() throws java.io.FileNotFoundException {
        try {
            main.RatePath rateP = new main.RatePath(dataFilename);
            rateP.dbgDumpFields();
            main.ReturnPath returnP = rateP.getReturnCompounded();
            returnP.estimatePath();
            returnP.dbgDumpFields();
            double expectedReturnRate = returnP.get_expectedReturnRate();
            double volatility = returnP.get_volatility();
            initAllTasks = new main.ToInitAllTasks(returnP, nTimeStepsMC, pathStartValue);
            java.lang.String slaveClassName = "MonteCarlo.PriceStock";
            initTasks(nRunsMC);
        } catch (main.DemoException demoEx) {
            dbgPrintln(demoEx.toString());
            java.lang.System.exit((-1));
        }
    }

    protected abstract void runSerial();

    public void processSerial() {
        try {
            processResults();
        } catch (main.DemoException demoEx) {
            dbgPrintln(demoEx.toString());
            java.lang.System.exit((-1));
        }
    }

    private void initTasks(int nRunsMC) {
        tasks = new java.util.Vector(nRunsMC);
        for (int i = 0; i < nRunsMC; i++) {
            java.lang.String header = "MC run " + (java.lang.String.valueOf(i));
            main.ToTask task = new main.ToTask(header, (((long) (i)) * 11));
            tasks.addElement(((java.lang.Object) (task)));
        }
    }

    private void processResults() throws main.DemoException {
        double avgExpectedReturnRateMC = 0.0;
        double avgVolatilityMC = 0.0;
        double runAvgExpectedReturnRateMC = 0.0;
        double runAvgVolatilityMC = 0.0;
        main.ToResult returnMC;
        if ((results) == null) {
            java.lang.System.out.println("results is null");
        }
        if ((nRunsMC) != (results.size())) {
            errPrintln("Fatal: TaskRunner managed to finish with no all the results gathered in!");
            java.lang.System.exit((-1));
        }
        main.RatePath avgMCrate = new main.RatePath(nTimeStepsMC, "MC", 19990109, 19991231, dTime);
        for (int i = 0; i < (nRunsMC); i++) {
            returnMC = ((main.ToResult) (results.elementAt(i)));
            avgMCrate.inc_pathValue(returnMC.get_pathValue());
            avgExpectedReturnRateMC += returnMC.get_expectedReturnRate();
            avgVolatilityMC += returnMC.get_volatility();
            runAvgExpectedReturnRateMC = avgExpectedReturnRateMC / ((double) (i + 1));
            runAvgVolatilityMC = avgVolatilityMC / ((double) (i + 1));
        }
        avgMCrate.inc_pathValue((((double) (1.0)) / ((double) (nRunsMC))));
        avgExpectedReturnRateMC /= nRunsMC;
        avgVolatilityMC /= nRunsMC;
        JGFavgExpectedReturnRateMC = avgExpectedReturnRateMC;
    }

    protected void tidyUp() {
    }

    public java.lang.String get_dataFilename() {
        return main.AbstractMontecarloImplementation.this.dataFilename;
    }

    public void set_dataFilename(java.lang.String dataFilename) {
        main.AbstractMontecarloImplementation.this.dataFilename = dataFilename;
    }

    public int get_nTimeStepsMC() {
        return main.AbstractMontecarloImplementation.this.nTimeStepsMC;
    }

    public void set_nTimeStepsMC(int nTimeStepsMC) {
        main.AbstractMontecarloImplementation.this.nTimeStepsMC = nTimeStepsMC;
    }

    public int get_nRunsMC() {
        return main.AbstractMontecarloImplementation.this.nRunsMC;
    }

    public void set_nRunsMC(int nRunsMC) {
        main.AbstractMontecarloImplementation.this.nRunsMC = nRunsMC;
    }

    public java.util.Vector get_tasks() {
        return main.AbstractMontecarloImplementation.this.tasks;
    }

    public void set_tasks(java.util.Vector tasks) {
        main.AbstractMontecarloImplementation.this.tasks = tasks;
    }

    public java.util.Vector get_results() {
        return main.AbstractMontecarloImplementation.this.results;
    }

    public void set_results(java.util.Vector results) {
        main.AbstractMontecarloImplementation.this.results = results;
    }

    public void waitTillFinished() {
    }
}

