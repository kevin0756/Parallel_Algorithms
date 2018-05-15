

package main;


public abstract class AbstractSeriesImplementation {
    public int array_rows;

    public static double[][] TestArray;

    void buildTestData() {
        main.AbstractSeriesImplementation.TestArray = new double[2][array_rows];
    }

    void Do() {
        double omega;
        main.AbstractSeriesImplementation.TestArray[0][0] = (main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, ((double) (0.0)), 0)) / ((double) (2.0));
        omega = ((double) (3.141592653589793));
        calculateFourierSeries(omega);
    }

    public abstract void calculateFourierSeries(double omega);

    public static double TrapezoidIntegrate(double x0, double x1, int nsteps, double omegan, int select) {
        double x;
        double dx;
        double rvalue;
        x = x0;
        dx = (x1 - x0) / ((double) (nsteps));
        rvalue = (main.AbstractSeriesImplementation.thefunction(x0, omegan, select)) / ((double) (2.0));
        if (nsteps != 1) {
            --nsteps;
            while ((--nsteps) > 0) {
                x += dx;
                rvalue += main.AbstractSeriesImplementation.thefunction(x, omegan, select);
            } 
        }
        rvalue = (rvalue + ((main.AbstractSeriesImplementation.thefunction(x1, omegan, select)) / ((double) (2.0)))) * dx;
        return rvalue;
    }

    private static double thefunction(double x, double omegan, int select) {
        switch (select) {
            case 0 :
                return java.lang.Math.pow((x + ((double) (1.0))), x);
            case 1 :
                return (java.lang.Math.pow((x + ((double) (1.0))), x)) * (java.lang.Math.cos((omegan * x)));
            case 2 :
                return (java.lang.Math.pow((x + ((double) (1.0))), x)) * (java.lang.Math.sin((omegan * x)));
        }
        return 0.0;
    }

    protected void freeTestData() {
        main.AbstractSeriesImplementation.TestArray = null;
        java.lang.System.gc();
    }

    public void waitTillFinished() {
    }
}

