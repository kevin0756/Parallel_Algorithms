

package main;


public class ToResult implements java.io.Serializable {
    private java.lang.String header;

    private double expectedReturnRate = java.lang.Double.NaN;

    private double volatility = java.lang.Double.NaN;

    private double volatility2 = java.lang.Double.NaN;

    private double finalStockPrice = java.lang.Double.NaN;

    private double[] pathValue;

    public ToResult(java.lang.String header, double expectedReturnRate, double volatility, double volatility2, double finalStockPrice, double[] pathValue) {
        main.ToResult.this.header = header;
        main.ToResult.this.expectedReturnRate = expectedReturnRate;
        main.ToResult.this.volatility = volatility;
        main.ToResult.this.volatility2 = volatility2;
        main.ToResult.this.finalStockPrice = finalStockPrice;
        main.ToResult.this.pathValue = pathValue;
    }

    public java.lang.String toString() {
        return header;
    }

    public java.lang.String get_header() {
        return main.ToResult.this.header;
    }

    public void set_header(java.lang.String header) {
        main.ToResult.this.header = header;
    }

    public double get_expectedReturnRate() {
        return main.ToResult.this.expectedReturnRate;
    }

    public void set_expectedReturnRate(double expectedReturnRate) {
        main.ToResult.this.expectedReturnRate = expectedReturnRate;
    }

    public double get_volatility() {
        return main.ToResult.this.volatility;
    }

    public void set_volatility(double volatility) {
        main.ToResult.this.volatility = volatility;
    }

    public double get_volatility2() {
        return main.ToResult.this.volatility2;
    }

    public void set_volatility2(double volatility2) {
        main.ToResult.this.volatility2 = volatility2;
    }

    public double get_finalStockPrice() {
        return main.ToResult.this.finalStockPrice;
    }

    public void set_finalStockPrice(double finalStockPrice) {
        main.ToResult.this.finalStockPrice = finalStockPrice;
    }

    public double[] get_pathValue() {
        return main.ToResult.this.pathValue;
    }

    public void set_pathValue(double[] pathValue) {
        main.ToResult.this.pathValue = pathValue;
    }
}

