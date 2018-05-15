

package main;


public class LUSearchRunner {
    main.AbstractLusearchImplementation implementation;

    int repeatFact;

    public LUSearchRunner(main.AbstractLusearchImplementation imp) {
        main.LUSearchRunner.this.implementation = imp;
    }

    private void init(int size) {
        implementation.initialise(size);
    }

    private void validate() {
        implementation.validate();
    }

    private void tidyUp() {
        implementation.tidyUp();
    }

    private void kernel() {
        for (int i = 0; i < (repeatFact); i++) {
            implementation.search();
        }
        implementation.waitTillFinished();
    }

    public java.lang.Long run(int size, int repeat) {
        main.LUSearchRunner.this.repeatFact = repeat;
        init(size);
        java.lang.Long start = java.lang.System.currentTimeMillis();
        kernel();
        java.lang.Long end = java.lang.System.currentTimeMillis();
        validate();
        tidyUp();
        return end - start;
    }
}

