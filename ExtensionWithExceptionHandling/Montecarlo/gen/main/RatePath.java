

package main;


public class RatePath extends main.PathId {
    public static boolean DEBUG = true;

    protected static java.lang.String prompt = "RatePath> ";

    public static int DATUMFIELD = 4;

    public static final int MINIMUMDATE = 19000101;

    public static final double EPSILON = 10.0 * (java.lang.Double.MIN_VALUE);

    private double[] pathValue;

    private int[] pathDate;

    private int nAcceptedPathValue = 0;

    public RatePath(java.lang.String filename) throws java.io.FileNotFoundException, main.DemoException {
        set_prompt(main.RatePath.prompt);
        set_DEBUG(main.RatePath.DEBUG);
        readRatesFile(filename);
    }

    public RatePath(double[] pathValue, java.lang.String name, int startDate, int endDate, double dTime) {
        set_name(name);
        set_startDate(startDate);
        set_endDate(endDate);
        set_dTime(dTime);
        set_prompt(main.RatePath.prompt);
        set_DEBUG(main.RatePath.DEBUG);
        main.RatePath.this.pathValue = pathValue;
        main.RatePath.this.nAcceptedPathValue = pathValue.length;
    }

    public RatePath(main.MonteCarloPath mc) throws main.DemoException {
        set_name(mc.get_name());
        set_startDate(mc.get_startDate());
        set_endDate(mc.get_endDate());
        set_dTime(mc.get_dTime());
        pathValue = mc.get_pathValue();
        nAcceptedPathValue = mc.get_nTimeSteps();
        pathDate = new int[nAcceptedPathValue];
    }

    public RatePath(int pathValueLength, java.lang.String name, int startDate, int endDate, double dTime) {
        set_name(name);
        set_startDate(startDate);
        set_endDate(endDate);
        set_dTime(dTime);
        set_prompt(main.RatePath.prompt);
        set_DEBUG(main.RatePath.DEBUG);
        main.RatePath.this.pathValue = new double[pathValueLength];
        main.RatePath.this.nAcceptedPathValue = pathValue.length;
    }

    public void inc_pathValue(double[] operandPath) throws main.DemoException {
        if ((pathValue.length) != (operandPath.length))
            throw new main.DemoException("The path to update has a different size to the path to update with!");
        
        for (int i = 0; i < (pathValue.length); i++)
            pathValue[i] += operandPath[i];
        
    }

    public void inc_pathValue(double scale) throws main.DemoException {
        if ((pathValue) == null)
            throw new main.DemoException("Variable pathValue is undefined!");
        
        for (int i = 0; i < (pathValue.length); i++)
            pathValue[i] *= scale;
        
    }

    public double[] get_pathValue() throws main.DemoException {
        if ((main.RatePath.this.pathValue) == null)
            throw new main.DemoException("Variable pathValue is undefined!");
        
        return main.RatePath.this.pathValue;
    }

    public void set_pathValue(double[] pathValue) {
        main.RatePath.this.pathValue = pathValue;
    }

    public int[] get_pathDate() throws main.DemoException {
        if ((main.RatePath.this.pathDate) == null)
            throw new main.DemoException("Variable pathDate is undefined!");
        
        return main.RatePath.this.pathDate;
    }

    public void set_pathDate(int[] pathDate) {
        main.RatePath.this.pathDate = pathDate;
    }

    public double getEndPathValue() {
        return getPathValue(((pathValue.length) - 1));
    }

    public double getPathValue(int index) {
        return pathValue[index];
    }

    public main.ReturnPath getReturnCompounded() throws main.DemoException {
        if (((pathValue) == null) || ((nAcceptedPathValue) == 0)) {
            throw new main.DemoException("The Rate Path has not been defined!");
        }
        double[] returnPathValue = new double[nAcceptedPathValue];
        returnPathValue[0] = 0.0;
        try {
            for (int i = 1; i < (nAcceptedPathValue); i++) {
                returnPathValue[i] = java.lang.Math.log(((pathValue[i]) / (pathValue[(i - 1)])));
            }
        } catch (java.lang.ArithmeticException aex) {
            throw new main.DemoException(("Error in getReturnLogarithm:" + (aex.toString())));
        }
        main.ReturnPath rPath = new main.ReturnPath(returnPathValue, nAcceptedPathValue, main.ReturnPath.COMPOUNDED);
        rPath.copyInstanceVariables(main.RatePath.this);
        rPath.estimatePath();
        return rPath;
    }

    public main.ReturnPath getReturnNonCompounded() throws main.DemoException {
        if (((pathValue) == null) || ((nAcceptedPathValue) == 0)) {
            throw new main.DemoException("The Rate Path has not been defined!");
        }
        double[] returnPathValue = new double[nAcceptedPathValue];
        returnPathValue[0] = 0.0;
        try {
            for (int i = 1; i < (nAcceptedPathValue); i++) {
                returnPathValue[i] = ((pathValue[i]) - (pathValue[(i - 1)])) / (pathValue[i]);
            }
        } catch (java.lang.ArithmeticException aex) {
            throw new main.DemoException(("Error in getReturnPercentage:" + (aex.toString())));
        }
        main.ReturnPath rPath = new main.ReturnPath(returnPathValue, nAcceptedPathValue, main.ReturnPath.NONCOMPOUNDED);
        rPath.copyInstanceVariables(main.RatePath.this);
        rPath.estimatePath();
        return rPath;
    }

    private void readRatesFile(java.lang.String filename) throws java.io.FileNotFoundException, main.DemoException {
        java.io.BufferedReader in;
        in = new java.io.BufferedReader(new java.io.FileReader((("data" + (java.io.File.separator)) + filename)));
        int iLine = 0;
        int initNlines = 100;
        int nLines = 0;
        java.lang.String aLine;
        java.util.Vector allLines = new java.util.Vector(initNlines);
        try {
            while ((aLine = in.readLine()) != null) {
                iLine++;
                allLines.addElement(aLine);
            } 
        } catch (java.io.IOException ioex) {
            throw new main.DemoException(("Problem reading data from the file " + (ioex.toString())));
        }
        nLines = iLine;
        main.RatePath.this.pathValue = new double[nLines];
        main.RatePath.this.pathDate = new int[nLines];
        nAcceptedPathValue = 0;
        iLine = 0;
        for (java.util.Enumeration enumeration = allLines.elements(); enumeration.hasMoreElements();) {
            aLine = ((java.lang.String) (enumeration.nextElement()));
            java.lang.String[] field = main.Utilities.splitString(",", aLine);
            int aDate = java.lang.Integer.parseInt(("19" + (field[0])));
            double aPathValue = java.lang.Double.valueOf(field[main.RatePath.DATUMFIELD]).doubleValue();
            if ((aDate <= (main.RatePath.MINIMUMDATE)) || ((java.lang.Math.abs(aPathValue)) < (main.RatePath.EPSILON))) {
                dbgPrintln((((("Skipped erroneous data in " + filename) + " indexed by date=") + (field[0])) + "."));
            }else {
                pathDate[iLine] = aDate;
                pathValue[iLine] = aPathValue;
                iLine++;
            }
        }
        nAcceptedPathValue = iLine;
        set_name("hitData");
        set_startDate(pathDate[0]);
        set_endDate(pathDate[((nAcceptedPathValue) - 1)]);
        set_dTime(((double) (1.0 / 365.0)));
    }
}

