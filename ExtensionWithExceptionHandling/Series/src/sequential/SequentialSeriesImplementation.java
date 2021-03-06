package sequential;

import main.*;

/**
 * Standard series implementation of a Fourier Series calculation
 * @author sgor395
 *
 */
public class SequentialSeriesImplementation extends AbstractSeriesImplementation{

	@Override
	public void calculateFourierSeries(double omega) {
		
		for (int i = 1; i < array_rows; i++)
	    {
	        // Calculate A[i] terms. Note, once again, that we
	        // can ignore the 2/period term outside the integral
	        // since the period is 2 and the term cancels itself
	        // out.

	        TestArray[0][i] = TrapezoidIntegrate((double)0.0,
	                          (double)2.0,
	                          1000,
	                          omega * (double)i,
	                          1);                       // 1 = cosine term.

	        // Calculate the B[i] terms.

	        TestArray[1][i] = TrapezoidIntegrate((double)0.0,
	                          (double)2.0,
	                          1000,
	                          omega * (double)i,
	                          2);                       // 2 = sine term.
	    }
		
	}

}
