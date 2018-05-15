

package main;


public class Universal {
    private static boolean UNIVERSAL_DEBUG;

    private boolean DEBUG;

    private java.lang.String prompt;

    public Universal() {
        super();
        main.Universal.this.DEBUG = true;
        main.Universal.this.UNIVERSAL_DEBUG = true;
        main.Universal.this.prompt = "Universal> ";
    }

    public boolean get_DEBUG() {
        return main.Universal.this.DEBUG;
    }

    public void set_DEBUG(boolean DEBUG) {
        main.Universal.this.DEBUG = DEBUG;
    }

    public boolean get_UNIVERSAL_DEBUG() {
        return main.Universal.this.UNIVERSAL_DEBUG;
    }

    public void set_UNIVERSAL_DEBUG(boolean UNIVERSAL_DEBUG) {
        main.Universal.this.UNIVERSAL_DEBUG = UNIVERSAL_DEBUG;
    }

    public java.lang.String get_prompt() {
        return main.Universal.this.prompt;
    }

    public void set_prompt(java.lang.String prompt) {
        main.Universal.this.prompt = prompt;
    }

    public void dbgPrintln(java.lang.String s) {
        if ((DEBUG) || (main.Universal.UNIVERSAL_DEBUG)) {
            java.lang.System.out.println((("DBG " + (prompt)) + s));
        }
    }

    public void dbgPrint(java.lang.String s) {
        if ((DEBUG) || (main.Universal.UNIVERSAL_DEBUG)) {
            java.lang.System.out.print((("DBG " + (prompt)) + s));
        }
    }

    public void errPrintln(java.lang.String s) {
        java.lang.System.err.println(((prompt) + s));
    }

    public void errPrint(java.lang.String s) {
        java.lang.System.err.print(((prompt) + s));
    }
}

