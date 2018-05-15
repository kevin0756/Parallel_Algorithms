package main;

/**
 * Class that all Fourier Series implementations should extend.
 * @author sgor395
 *
 */
public abstract class AbstractSeriesImplementation {
	
	// Declare class data.
	public int array_rows; 
	public static double [] [] TestArray;  // Array of arrays.
	
	/*
	* buildTestData
	*
	*/

	// Instantiate array(s) to hold fourier coefficients.
	void buildTestData() {
	    // Allocate appropriate length for the double array of doubles.

	    TestArray = new double [2][array_rows];
	}
	
	void Do()
	{
	    double omega;       // Fundamental frequency.

	    
	    // Calculate the fourier series. Begin by calculating A[0].

	    TestArray[0][0]=TrapezoidIntegrate((double)0.0, // Lower bound.
	                            (double)2.0,            // Upper bound.
	                            1000,                    // # of steps.
	                            (double)0.0,            // No omega*n needed.
	                            0) / (double)2.0;       // 0 = term A[0].

	    // Calculate the fundamental frequency.
	    // ( 2 * pi ) / period...and since the period
	    // is 2, omega is simply pi.

	    omega = (double) 3.1415926535897932;

	   calculateFourierSeries(omega);
	}
	
	/**
	 * Function that should calculate the rest of the fourier series above.
	 */
	public abstract void calculateFourierSeries(double omega);
	
	/*
	* TrapezoidIntegrate
	*
	* Perform a simple trapezoid integration on the function (x+1)**x.
	* x0,x1 set the lower and upper bounds of the integration.
	* nsteps indicates # of trapezoidal sections.
	* omegan is the fundamental frequency times the series member #.
	* select = 0 for the A[0] term, 1 for cosine terms, and 2 for
	* sine terms. Returns the value.
	*/

	public static double TrapezoidIntegrate (double x0,     // Lower bound.
	                        double x1,                // Upper bound.
	                        int nsteps,               // # of steps.
	                        double omegan,            // omega * n.
	                        int select)               // Term type.
	{
	    double x;               // Independent variable.
	    double dx;              // Step size.
	    double rvalue;          // Return value.

	    // Initialize independent variable.

	    x = x0;

	    // Calculate stepsize.

	    dx = (x1 - x0) / (double)nsteps;

	    // Initialize the return value.

	    rvalue = thefunction(x0, omegan, select) / (double)2.0;

	    // Compute the other terms of the integral.

	    if (nsteps != 1)
	    {
	            --nsteps;               // Already done 1 step.
	            while (--nsteps > 0)
	            {
	                    x += dx;
	                    rvalue += thefunction(x, omegan, select);
	            }
	    }

	    // Finish computation.

	    rvalue=(rvalue + thefunction(x1,omegan,select) / (double)2.0) * dx;
	    return(rvalue);
	}

	/*
	* thefunction
	*
	* This routine selects the function to be used in the Trapezoid
	* integration. x is the independent variable, omegan is omega * n,
	* and select chooses which of the sine/cosine functions
	* are used. Note the special case for select=0.
	*/

	private static double thefunction(double x,      // Independent variable.
	                double omegan,              // Omega * term.
	                int select)                 // Choose type.
	{

	    // Use select to pick which function we call.

	    switch(select)
	    {
	        case 0: return(Math.pow(x+(double)1.0,x));

	        case 1: return(Math.pow(x+(double)1.0,x) * Math.cos(omegan*x));

	        case 2: return(Math.pow(x+(double)1.0,x) * Math.sin(omegan*x));
	    }

	    // We should never reach this point, but the following
	    // keeps compilers from issuing a warning message.

	    return (0.0);
	}

	/*
	* freeTestData
	*
	* Nulls array that is created with every run and forces garbage
	* collection to free up memory.
	*/

	protected void freeTestData()
	{
	    TestArray = null;    // Destroy the array.
	    System.gc();         // Force garbage collection.
	}
	
	public void waitTillFinished(){}

}
