

package main;


public class ToInitAllTasks implements java.io.Serializable {
    private java.lang.String header;

    private java.lang.String name;

    private int startDate;

    private int endDate;

    private double dTime;

    private int returnDefinition;

    private double expectedReturnRate;

    private double volatility;

    private int nTimeSteps;

    private double pathStartValue;

    public ToInitAllTasks(java.lang.String header, java.lang.String name, int startDate, int endDate, double dTime, int returnDefinition, double expectedReturnRate, double volatility, double pathStartValue) {
        main.ToInitAllTasks.this.header = header;
        main.ToInitAllTasks.this.name = name;
        main.ToInitAllTasks.this.startDate = startDate;
        main.ToInitAllTasks.this.endDate = endDate;
        main.ToInitAllTasks.this.dTime = dTime;
        main.ToInitAllTasks.this.returnDefinition = returnDefinition;
        main.ToInitAllTasks.this.expectedReturnRate = expectedReturnRate;
        main.ToInitAllTasks.this.volatility = volatility;
        main.ToInitAllTasks.this.nTimeSteps = nTimeSteps;
        main.ToInitAllTasks.this.pathStartValue = pathStartValue;
    }

    public ToInitAllTasks(main.ReturnPath obj, int nTimeSteps, double pathStartValue) throws main.DemoException {
        main.ToInitAllTasks.this.name = obj.get_name();
        main.ToInitAllTasks.this.startDate = obj.get_startDate();
        main.ToInitAllTasks.this.endDate = obj.get_endDate();
        main.ToInitAllTasks.this.dTime = obj.get_dTime();
        main.ToInitAllTasks.this.returnDefinition = obj.get_returnDefinition();
        main.ToInitAllTasks.this.expectedReturnRate = obj.get_expectedReturnRate();
        main.ToInitAllTasks.this.volatility = obj.get_volatility();
        main.ToInitAllTasks.this.nTimeSteps = nTimeSteps;
        main.ToInitAllTasks.this.pathStartValue = pathStartValue;
    }

    public java.lang.String get_header() {
        return main.ToInitAllTasks.this.header;
    }

    public void set_header(java.lang.String header) {
        main.ToInitAllTasks.this.header = header;
    }

    public java.lang.String get_name() {
        return main.ToInitAllTasks.this.name;
    }

    public void set_name(java.lang.String name) {
        main.ToInitAllTasks.this.name = name;
    }

    public int get_startDate() {
        return main.ToInitAllTasks.this.startDate;
    }

    public void set_startDate(int startDate) {
        main.ToInitAllTasks.this.startDate = startDate;
    }

    public int get_endDate() {
        return main.ToInitAllTasks.this.endDate;
    }

    public void set_endDate(int endDate) {
        main.ToInitAllTasks.this.endDate = endDate;
    }

    public double get_dTime() {
        return main.ToInitAllTasks.this.dTime;
    }

    public void set_dTime(double dTime) {
        main.ToInitAllTasks.this.dTime = dTime;
    }

    public int get_returnDefinition() {
        return main.ToInitAllTasks.this.returnDefinition;
    }

    public void set_returnDefinition(int returnDefinition) {
        main.ToInitAllTasks.this.returnDefinition = returnDefinition;
    }

    public double get_expectedReturnRate() {
        return main.ToInitAllTasks.this.expectedReturnRate;
    }

    public void set_expectedReturnRate(double expectedReturnRate) {
        main.ToInitAllTasks.this.expectedReturnRate = expectedReturnRate;
    }

    public double get_volatility() {
        return main.ToInitAllTasks.this.volatility;
    }

    public void set_volatility(double volatility) {
        main.ToInitAllTasks.this.volatility = volatility;
    }

    public int get_nTimeSteps() {
        return main.ToInitAllTasks.this.nTimeSteps;
    }

    public void set_nTimeSteps(int nTimeSteps) {
        main.ToInitAllTasks.this.nTimeSteps = nTimeSteps;
    }

    public double get_pathStartValue() {
        return main.ToInitAllTasks.this.pathStartValue;
    }

    public void set_pathStartValue(double pathStartValue) {
        main.ToInitAllTasks.this.pathStartValue = pathStartValue;
    }
}

