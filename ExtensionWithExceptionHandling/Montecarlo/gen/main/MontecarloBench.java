

package main;


public class MontecarloBench {
    main.AbstractMontecarloImplementation implementation;

    public int size;

    int[] datasizes = new int[]{ 10000 , 60000 , 120000 };

    int[] input = new int[2];

    int repeatFactor;

    public MontecarloBench(main.AbstractMontecarloImplementation imp) {
        main.MontecarloBench.this.implementation = imp;
    }

    public void setsize(int size) {
        main.MontecarloBench.this.size = size;
    }

    public void initialise() {
        input[0] = 1000;
        input[1] = datasizes[size];
        java.lang.String filename = "hitData";
        implementation.init(filename, input[0], input[1]);
        try {
            implementation.initSerial();
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void application() {
        for (int i = 0; i < (repeatFactor); i++) {
            implementation.runSerial();
            implementation.waitTillFinished();
            implementation.processSerial();
        }
    }

    public void validate() {
        double[] refval = new double[]{ -0.0333976656762814 , -0.03215796752868655 , -0.03115796752868655 };
        double dev = java.lang.Math.abs(((implementation.JGFavgExpectedReturnRateMC) - (refval[size])));
        if (dev > 1.0E-12) {
            java.lang.System.out.println("Validation failed");
            java.lang.System.out.println((((((" expectedReturnRate= " + (implementation.JGFavgExpectedReturnRateMC)) + "  ") + dev) + "  ") + (size)));
        }
    }

    public void tidyup() {
        implementation.tidyUp();
        java.lang.System.gc();
    }

    public long run(int size, int repeat) {
        main.MontecarloBench.this.repeatFactor = repeat;
        setsize(size);
        initialise();
        long start = java.lang.System.currentTimeMillis();
        application();
        long end = java.lang.System.currentTimeMillis();
        validate();
        tidyup();
        return end - start;
    }
}

