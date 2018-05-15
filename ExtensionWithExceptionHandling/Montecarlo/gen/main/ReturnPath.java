

package main;


public class ReturnPath extends main.PathId {
    public static boolean DEBUG = true;

    protected static java.lang.String prompt = "ReturnPath> ";

    public static int COMPOUNDED = 1;

    public static int NONCOMPOUNDED = 2;

    private double[] pathValue;

    private int nPathValue = 0;

    private int returnDefinition = 0;

    private double expectedReturnRate = java.lang.Double.NaN;

    private double volatility = java.lang.Double.NaN;

    private double volatility2 = java.lang.Double.NaN;

    private double mean = java.lang.Double.NaN;

    private double variance = java.lang.Double.NaN;

    public ReturnPath() {
        super();
        set_prompt(main.ReturnPath.prompt);
        set_DEBUG(main.ReturnPath.DEBUG);
    }

    public ReturnPath(double[] pathValue, int nPathValue, int returnDefinition) {
        set_prompt(main.ReturnPath.prompt);
        set_DEBUG(main.ReturnPath.DEBUG);
        main.ReturnPath.this.pathValue = pathValue;
        main.ReturnPath.this.nPathValue = nPathValue;
        main.ReturnPath.this.returnDefinition = returnDefinition;
    }

    public double[] get_pathValue() throws main.DemoException {
        if ((main.ReturnPath.this.pathValue) == null)
            throw new main.DemoException("Variable pathValue is undefined!");
        
        return main.ReturnPath.this.pathValue;
    }

    public void set_pathValue(double[] pathValue) {
        main.ReturnPath.this.pathValue = pathValue;
    }

    public int get_nPathValue() throws main.DemoException {
        if ((main.ReturnPath.this.nPathValue) == 0)
            throw new main.DemoException("Variable nPathValue is undefined!");
        
        return main.ReturnPath.this.nPathValue;
    }

    public void set_nPathValue(int nPathValue) {
        main.ReturnPath.this.nPathValue = nPathValue;
    }

    public int get_returnDefinition() throws main.DemoException {
        if ((main.ReturnPath.this.returnDefinition) == 0)
            throw new main.DemoException("Variable returnDefinition is undefined!");
        
        return main.ReturnPath.this.returnDefinition;
    }

    public void set_returnDefinition(int returnDefinition) {
        main.ReturnPath.this.returnDefinition = returnDefinition;
    }

    public double get_expectedReturnRate() throws main.DemoException {
        if ((main.ReturnPath.this.expectedReturnRate) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable expectedReturnRate is undefined!");
        
        return main.ReturnPath.this.expectedReturnRate;
    }

    public void set_expectedReturnRate(double expectedReturnRate) {
        main.ReturnPath.this.expectedReturnRate = expectedReturnRate;
    }

    public double get_volatility() throws main.DemoException {
        if ((main.ReturnPath.this.volatility) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable volatility is undefined!");
        
        return main.ReturnPath.this.volatility;
    }

    public void set_volatility(double volatility) {
        main.ReturnPath.this.volatility = volatility;
    }

    public double get_volatility2() throws main.DemoException {
        if ((main.ReturnPath.this.volatility2) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable volatility2 is undefined!");
        
        return main.ReturnPath.this.volatility2;
    }

    public void set_volatility2(double volatility2) {
        main.ReturnPath.this.volatility2 = volatility2;
    }

    public double get_mean() throws main.DemoException {
        if ((main.ReturnPath.this.mean) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable mean is undefined!");
        
        return main.ReturnPath.this.mean;
    }

    public void set_mean(double mean) {
        main.ReturnPath.this.mean = mean;
    }

    public double get_variance() throws main.DemoException {
        if ((main.ReturnPath.this.variance) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable variance is undefined!");
        
        return main.ReturnPath.this.variance;
    }

    public void set_variance(double variance) {
        main.ReturnPath.this.variance = variance;
    }

    public void computeExpectedReturnRate() throws main.DemoException {
        main.ReturnPath.this.expectedReturnRate = ((mean) / (get_dTime())) + (0.5 * (volatility2));
    }

    public void computeVolatility() throws main.DemoException {
        if ((main.ReturnPath.this.variance) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable variance is not defined!");
        
        main.ReturnPath.this.volatility2 = (variance) / (get_dTime());
        main.ReturnPath.this.volatility = java.lang.Math.sqrt(volatility2);
    }

    public void computeMean() throws main.DemoException {
        if ((main.ReturnPath.this.nPathValue) == 0)
            throw new main.DemoException("Variable nPathValue is undefined!");
        
        main.ReturnPath.this.mean = 0.0;
        for (int i = 1; i < (nPathValue); i++) {
            mean += pathValue[i];
        }
        main.ReturnPath.this.mean /= ((double) ((nPathValue) - 1.0));
    }

    public void computeVariance() throws main.DemoException {
        if (((main.ReturnPath.this.mean) == (java.lang.Double.NaN)) || ((main.ReturnPath.this.nPathValue) == 0))
            throw new main.DemoException("Variable mean and/or nPathValue are undefined!");
        
        main.ReturnPath.this.variance = 0.0;
        for (int i = 1; i < (nPathValue); i++) {
            variance += ((pathValue[i]) - (mean)) * ((pathValue[i]) - (mean));
        }
        main.ReturnPath.this.variance /= ((double) ((nPathValue) - 1.0));
    }

    public void estimatePath() throws main.DemoException {
        computeMean();
        computeVariance();
        computeExpectedReturnRate();
        computeVolatility();
    }

    public void dbgDumpFields() {
        super.dbgDumpFields();
    }
}

