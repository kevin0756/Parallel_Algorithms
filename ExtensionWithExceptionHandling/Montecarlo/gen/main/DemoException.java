

package main;


public class DemoException extends java.lang.Exception {
    public static boolean DEBUG = true;

    public DemoException() {
        super();
        if (main.DemoException.DEBUG) {
            printStackTrace();
        }
    }

    public DemoException(java.lang.String s) {
        super(s);
        if (main.DemoException.DEBUG) {
            printStackTrace();
        }
    }

    public DemoException(int ierr) {
        super(java.lang.String.valueOf(ierr));
        if (main.DemoException.DEBUG) {
            printStackTrace();
        }
    }
}

