

package sequential;


public class SequentialSeriesImplementation extends main.AbstractSeriesImplementation {
    @java.lang.Override
    public void calculateFourierSeries(double omega) {
        for (int i = 1; i < (array_rows); i++) {
            main.AbstractSeriesImplementation.TestArray[0][i] = main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, (omega * ((double) (i))), 1);
            main.AbstractSeriesImplementation.TestArray[1][i] = main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, (omega * ((double) (i))), 2);
        }
    }
}

