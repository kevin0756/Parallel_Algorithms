

package main;


public class JGFSeriesBench {
    int repeatFactor;

    public JGFSeriesBench(main.AbstractSeriesImplementation implementation) {
        main.JGFSeriesBench.this.implementation = implementation;
    }

    private main.AbstractSeriesImplementation implementation;

    private int size;

    private int[] datasizes = new int[]{ 10000 , 100000 , 1000000 };

    public void setsize(int size) {
        main.JGFSeriesBench.this.size = size;
    }

    public void initialise() {
        implementation.array_rows = datasizes[size];
        implementation.buildTestData();
    }

    public void kernel() {
        for (int i = 0; i < (repeatFactor); i++) {
            implementation.Do();
        }
        implementation.waitTillFinished();
    }

    public void validate() {
        double[][] ref = new double[][]{ new double[]{ 2.8729524964837996 , 0.0 } , new double[]{ 1.1161046676147888 , -1.8819691893398025 } , new double[]{ 0.34429060398168704 , -1.1645642623320958 } , new double[]{ 0.15238898702519288 , -0.8143461113044298 } };
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                double error = java.lang.Math.abs(((main.AbstractSeriesImplementation.TestArray[j][i]) - (ref[i][j])));
                if (error > 1.0E-12) {
                    java.lang.System.out.println(((("Validation failed for coefficient " + j) + ",") + i));
                    java.lang.System.out.println(("Computed value = " + (main.AbstractSeriesImplementation.TestArray[j][i])));
                    java.lang.System.out.println(("Reference value = " + (ref[i][j])));
                }
            }
        }
    }

    public void tidyup() {
        implementation.freeTestData();
    }

    public long run(int size, int repeat) {
        main.JGFSeriesBench.this.repeatFactor = repeat;
        setsize(size);
        initialise();
        long start = java.lang.System.currentTimeMillis();
        kernel();
        long end = java.lang.System.currentTimeMillis();
        validate();
        tidyup();
        return end - start;
    }
}

