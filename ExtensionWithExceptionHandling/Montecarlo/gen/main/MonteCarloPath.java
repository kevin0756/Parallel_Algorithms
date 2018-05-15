

package main;


public class MonteCarloPath extends main.PathId {
    public static boolean DEBUG = true;

    protected static java.lang.String prompt = "MonteCarloPath> ";

    public static int DATUMFIELD = main.RatePath.DATUMFIELD;

    private double[] fluctuations;

    private double[] pathValue;

    private int returnDefinition = 0;

    private double expectedReturnRate = java.lang.Double.NaN;

    private double volatility = java.lang.Double.NaN;

    private int nTimeSteps = 0;

    private double pathStartValue = java.lang.Double.NaN;

    public MonteCarloPath() {
        super();
        set_prompt(main.MonteCarloPath.prompt);
        set_DEBUG(main.MonteCarloPath.DEBUG);
    }

    public MonteCarloPath(main.ReturnPath returnPath, int nTimeSteps) throws main.DemoException {
        copyInstanceVariables(returnPath);
        main.MonteCarloPath.this.nTimeSteps = nTimeSteps;
        main.MonteCarloPath.this.pathValue = new double[nTimeSteps];
        main.MonteCarloPath.this.fluctuations = new double[nTimeSteps];
        set_prompt(main.MonteCarloPath.prompt);
        set_DEBUG(main.MonteCarloPath.DEBUG);
    }

    public MonteCarloPath(main.PathId pathId, int returnDefinition, double expectedReturnRate, double volatility, int nTimeSteps) throws main.DemoException {
        copyInstanceVariables(pathId);
        main.MonteCarloPath.this.returnDefinition = returnDefinition;
        main.MonteCarloPath.this.expectedReturnRate = expectedReturnRate;
        main.MonteCarloPath.this.volatility = volatility;
        main.MonteCarloPath.this.nTimeSteps = nTimeSteps;
        main.MonteCarloPath.this.pathValue = new double[nTimeSteps];
        main.MonteCarloPath.this.fluctuations = new double[nTimeSteps];
        set_prompt(main.MonteCarloPath.prompt);
        set_DEBUG(main.MonteCarloPath.DEBUG);
    }

    public MonteCarloPath(java.lang.String name, int startDate, int endDate, double dTime, int returnDefinition, double expectedReturnRate, double volatility, int nTimeSteps) {
        set_name(name);
        set_startDate(startDate);
        set_endDate(endDate);
        set_dTime(dTime);
        main.MonteCarloPath.this.returnDefinition = returnDefinition;
        main.MonteCarloPath.this.expectedReturnRate = expectedReturnRate;
        main.MonteCarloPath.this.volatility = volatility;
        main.MonteCarloPath.this.nTimeSteps = nTimeSteps;
        main.MonteCarloPath.this.pathValue = new double[nTimeSteps];
        main.MonteCarloPath.this.fluctuations = new double[nTimeSteps];
        set_prompt(main.MonteCarloPath.prompt);
        set_DEBUG(main.MonteCarloPath.DEBUG);
    }

    public double[] get_fluctuations() throws main.DemoException {
        if ((main.MonteCarloPath.this.fluctuations) == null)
            throw new main.DemoException("Variable fluctuations is undefined!");
        
        return main.MonteCarloPath.this.fluctuations;
    }

    public void set_fluctuations(double[] fluctuations) {
        main.MonteCarloPath.this.fluctuations = fluctuations;
    }

    public double[] get_pathValue() throws main.DemoException {
        if ((main.MonteCarloPath.this.pathValue) == null)
            throw new main.DemoException("Variable pathValue is undefined!");
        
        return main.MonteCarloPath.this.pathValue;
    }

    public void set_pathValue(double[] pathValue) {
        main.MonteCarloPath.this.pathValue = pathValue;
    }

    public int get_returnDefinition() throws main.DemoException {
        if ((main.MonteCarloPath.this.returnDefinition) == 0)
            throw new main.DemoException("Variable returnDefinition is undefined!");
        
        return main.MonteCarloPath.this.returnDefinition;
    }

    public void set_returnDefinition(int returnDefinition) {
        main.MonteCarloPath.this.returnDefinition = returnDefinition;
    }

    public double get_expectedReturnRate() throws main.DemoException {
        if ((main.MonteCarloPath.this.expectedReturnRate) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable expectedReturnRate is undefined!");
        
        return main.MonteCarloPath.this.expectedReturnRate;
    }

    public void set_expectedReturnRate(double expectedReturnRate) {
        main.MonteCarloPath.this.expectedReturnRate = expectedReturnRate;
    }

    public double get_volatility() throws main.DemoException {
        if ((main.MonteCarloPath.this.volatility) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable volatility is undefined!");
        
        return main.MonteCarloPath.this.volatility;
    }

    public void set_volatility(double volatility) {
        main.MonteCarloPath.this.volatility = volatility;
    }

    public int get_nTimeSteps() throws main.DemoException {
        if ((main.MonteCarloPath.this.nTimeSteps) == 0)
            throw new main.DemoException("Variable nTimeSteps is undefined!");
        
        return main.MonteCarloPath.this.nTimeSteps;
    }

    public void set_nTimeSteps(int nTimeSteps) {
        main.MonteCarloPath.this.nTimeSteps = nTimeSteps;
    }

    public double get_pathStartValue() throws main.DemoException {
        if ((main.MonteCarloPath.this.pathStartValue) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable pathStartValue is undefined!");
        
        return main.MonteCarloPath.this.pathStartValue;
    }

    public void set_pathStartValue(double pathStartValue) {
        main.MonteCarloPath.this.pathStartValue = pathStartValue;
    }

    private void copyInstanceVariables(main.ReturnPath obj) throws main.DemoException {
        set_name(obj.get_name());
        set_startDate(obj.get_startDate());
        set_endDate(obj.get_endDate());
        set_dTime(obj.get_dTime());
        main.MonteCarloPath.this.returnDefinition = obj.get_returnDefinition();
        main.MonteCarloPath.this.expectedReturnRate = obj.get_expectedReturnRate();
        main.MonteCarloPath.this.volatility = obj.get_volatility();
    }

    public void writeFile(java.lang.String dirName, java.lang.String filename) throws main.DemoException {
        try {
            java.io.File ratesFile = new java.io.File(dirName, filename);
            if ((ratesFile.exists()) && (!(ratesFile.canWrite())))
                throw new main.DemoException("Cannot write to specified filename!");
            
            java.io.PrintWriter out = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(ratesFile)));
            for (int i = 0; i < (nTimeSteps); i++) {
                out.print("19990101,");
                for (int j = 1; j < (main.MonteCarloPath.DATUMFIELD); j++) {
                    out.print("0.0000,");
                }
                out.print(((pathValue[i]) + ","));
                out.println("0.0000,0.0000");
            }
            out.close();
        } catch (java.io.IOException ioex) {
            throw new main.DemoException(ioex.toString());
        }
    }

    public main.RatePath getRatePath() throws main.DemoException {
        return new main.RatePath(main.MonteCarloPath.this);
    }

    public void computeFluctuationsGaussian(long randomSeed) throws main.DemoException {
        if ((nTimeSteps) > (fluctuations.length))
            throw new main.DemoException("Number of timesteps requested is greater than the allocated array!");
        
        java.util.Random rnd;
        if (randomSeed == (-1)) {
            rnd = new java.util.Random();
        }else {
            rnd = new java.util.Random(randomSeed);
        }
        double mean = ((expectedReturnRate) - ((0.5 * (volatility)) * (volatility))) * (get_dTime());
        double sd = (volatility) * (java.lang.Math.sqrt(get_dTime()));
        double gauss;
        double meanGauss = 0.0;
        double variance = 0.0;
        for (int i = 0; i < (nTimeSteps); i++) {
            gauss = rnd.nextGaussian();
            meanGauss += gauss;
            variance += gauss * gauss;
            fluctuations[i] = mean + (sd * gauss);
        }
        meanGauss /= ((double) (nTimeSteps));
        variance /= ((double) (nTimeSteps));
    }

    public void computeFluctuationsGaussian() throws main.DemoException {
        computeFluctuationsGaussian(((long) (-1)));
    }

    public void computePathValue(double startValue) throws main.DemoException {
        pathValue[0] = startValue;
        if (((returnDefinition) == (main.ReturnPath.COMPOUNDED)) || ((returnDefinition) == (main.ReturnPath.NONCOMPOUNDED))) {
            for (int i = 1; i < (nTimeSteps); i++) {
                pathValue[i] = (pathValue[(i - 1)]) * (java.lang.Math.exp(fluctuations[i]));
            }
        }else {
            throw new main.DemoException("Unknown or undefined update method.");
        }
    }
}

