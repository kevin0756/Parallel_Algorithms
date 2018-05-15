

package main;


public class XalanBenchRunner {
    private main.AbstractXalanImplementation implementation;

    int repeatFactor;

    public XalanBenchRunner(main.AbstractXalanImplementation imp) {
        main.XalanBenchRunner.this.implementation = imp;
    }

    private void init() {
        implementation.initialise();
    }

    private void kernel() {
        for (int i = 0; i < (repeatFactor); i++) {
            implementation.run();
        }
        implementation.waitTillFinished();
    }

    private void validate() {
        implementation.validate();
    }

    private void tidyUp() {
        implementation.tidyUp();
    }

    public long run(int repeatFactor) {
        main.XalanBenchRunner.this.repeatFactor = repeatFactor;
        init();
        long start = java.lang.System.currentTimeMillis();
        kernel();
        long end = java.lang.System.currentTimeMillis();
        validate();
        tidyUp();
        return end - start;
    }
}

