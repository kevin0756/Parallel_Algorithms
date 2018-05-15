

package main;


public class PriceStock extends main.Universal {
    public static boolean DEBUG = true;

    protected static java.lang.String prompt = "PriceStock> ";

    private main.MonteCarloPath mcPath;

    private java.lang.String taskHeader;

    private long randomSeed = -1;

    private double pathStartValue = java.lang.Double.NaN;

    private main.ToResult result;

    private double expectedReturnRate = java.lang.Double.NaN;

    private double volatility = java.lang.Double.NaN;

    private double volatility2 = java.lang.Double.NaN;

    private double finalStockPrice = java.lang.Double.NaN;

    private double[] pathValue;

    public PriceStock() {
        super();
        mcPath = new main.MonteCarloPath();
        set_prompt(main.PriceStock.prompt);
        set_DEBUG(main.PriceStock.DEBUG);
    }

    public void setInitAllTasks(java.lang.Object obj) {
        main.ToInitAllTasks initAllTasks = ((main.ToInitAllTasks) (obj));
        mcPath.set_name(initAllTasks.get_name());
        mcPath.set_startDate(initAllTasks.get_startDate());
        mcPath.set_endDate(initAllTasks.get_endDate());
        mcPath.set_dTime(initAllTasks.get_dTime());
        mcPath.set_returnDefinition(initAllTasks.get_returnDefinition());
        mcPath.set_expectedReturnRate(initAllTasks.get_expectedReturnRate());
        mcPath.set_volatility(initAllTasks.get_volatility());
        int nTimeSteps = initAllTasks.get_nTimeSteps();
        mcPath.set_nTimeSteps(nTimeSteps);
        main.PriceStock.this.pathStartValue = initAllTasks.get_pathStartValue();
        mcPath.set_pathStartValue(pathStartValue);
        mcPath.set_pathValue(new double[nTimeSteps]);
        mcPath.set_fluctuations(new double[nTimeSteps]);
    }

    public void setTask(java.lang.Object obj) {
        main.ToTask task = ((main.ToTask) (obj));
        main.PriceStock.this.taskHeader = task.get_header();
        main.PriceStock.this.randomSeed = task.get_randomSeed();
    }

    public void run() {
        try {
            mcPath.computeFluctuationsGaussian(randomSeed);
            mcPath.computePathValue(pathStartValue);
            main.RatePath rateP = new main.RatePath(mcPath);
            main.ReturnPath returnP = rateP.getReturnCompounded();
            returnP.estimatePath();
            expectedReturnRate = returnP.get_expectedReturnRate();
            volatility = returnP.get_volatility();
            volatility2 = returnP.get_volatility2();
            finalStockPrice = rateP.getEndPathValue();
            pathValue = mcPath.get_pathValue();
        } catch (main.DemoException demoEx) {
            errPrintln(demoEx.toString());
        }
    }

    public java.lang.Object getResult() {
        java.lang.String resultHeader = (((("Result of task with Header=" + (taskHeader)) + ": randomSeed=") + (randomSeed)) + ": pathStartValue=") + (pathStartValue);
        main.ToResult res = new main.ToResult(resultHeader, expectedReturnRate, volatility, volatility2, finalStockPrice, pathValue);
        return ((java.lang.Object) (res));
    }
}

