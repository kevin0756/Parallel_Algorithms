

package main;


public class PathId extends main.Universal {
    public static boolean DEBUG = true;

    protected static java.lang.String prompt = "PathId> ";

    private java.lang.String name;

    private int startDate = 0;

    private int endDate = 0;

    private double dTime = java.lang.Double.NaN;

    public PathId() {
        super();
        set_prompt(main.PathId.prompt);
        set_DEBUG(main.PathId.DEBUG);
    }

    public PathId(java.lang.String name) {
        set_prompt(main.PathId.prompt);
        set_DEBUG(main.PathId.DEBUG);
        main.PathId.this.name = name;
    }

    public java.lang.String get_name() throws main.DemoException {
        if ((main.PathId.this.name) == null)
            throw new main.DemoException("Variable name is undefined!");
        
        return main.PathId.this.name;
    }

    public void set_name(java.lang.String name) {
        main.PathId.this.name = name;
    }

    public int get_startDate() throws main.DemoException {
        if ((main.PathId.this.startDate) == 0)
            throw new main.DemoException("Variable startDate is undefined!");
        
        return main.PathId.this.startDate;
    }

    public void set_startDate(int startDate) {
        main.PathId.this.startDate = startDate;
    }

    public int get_endDate() throws main.DemoException {
        if ((main.PathId.this.endDate) == 0)
            throw new main.DemoException("Variable endDate is undefined!");
        
        return main.PathId.this.endDate;
    }

    public void set_endDate(int endDate) {
        main.PathId.this.endDate = endDate;
    }

    public double get_dTime() throws main.DemoException {
        if ((main.PathId.this.dTime) == (java.lang.Double.NaN))
            throw new main.DemoException("Variable dTime is undefined!");
        
        return main.PathId.this.dTime;
    }

    public void set_dTime(double dTime) {
        main.PathId.this.dTime = dTime;
    }

    public void copyInstanceVariables(main.PathId obj) throws main.DemoException {
        main.PathId.this.name = obj.get_name();
        main.PathId.this.startDate = obj.get_startDate();
        main.PathId.this.endDate = obj.get_endDate();
        main.PathId.this.dTime = obj.get_dTime();
    }

    public void dbgDumpFields() {
    }
}

