/**************************************************************************
 *                                                                         *
 *             Java Grande Forum Benchmark Suite - Version 2.0             *
 *                                                                         *
 *                            produced by                                  *
 *                                                                         *
 *                  Java Grande Benchmarking Project                       *
 *                                                                         *
 *                                at                                       *
 *                                                                         *
 *                Edinburgh Parallel Computing Centre                      *
 *                                                                         * 
 *                email: epcc-javagrande@epcc.ed.ac.uk                     *
 *                                                                         *
 *                                                                         *
 *      This version copyright (c) The University of Edinburgh, 1999.      *
 *                         All rights reserved.                            *
 *                                                                         *
 **************************************************************************/

package main;

import java.io.FileNotFoundException;

public class MontecarloBench {
	AbstractMontecarloImplementation implementation;
	public int size;
	int datasizes[] = { 10000, 60000, 120000 };
	int input[] = new int[2];
	int repeatFactor;

	public MontecarloBench(AbstractMontecarloImplementation imp) {
		this.implementation = imp;
	}

	public void setsize(int size) {
		this.size = size;
	}

	public void initialise() {
		input[0] = 1000;
		input[1] = datasizes[size];

		String filename = "hitData";

		implementation.init(filename, (input[0]), (input[1]));
		try {
			implementation.initSerial();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void application() {
		for (int i = 0; i < repeatFactor; i++){
			implementation.runSerial();
			implementation.waitTillFinished();
			implementation.processSerial();
		}	
	}

	public void validate() {
		double refval[] = { -0.0333976656762814, -0.03215796752868655, -0.03115796752868655 };
		double dev = Math.abs(implementation.JGFavgExpectedReturnRateMC
				- refval[size]);
		if (dev > 1.0e-12) {
			System.out.println("Validation failed");
			System.out.println(" expectedReturnRate= "
					+ implementation.JGFavgExpectedReturnRateMC + "  " + dev
					+ "  " + size);
		}
	}

	public void tidyup() {
		implementation.tidyUp();
		System.gc();
	}

	public long run(int size, int repeat) {
		this.repeatFactor = repeat;
		setsize(size);
		initialise();
		long start = System.currentTimeMillis();
		application();
		long end = System.currentTimeMillis();
		validate();
		tidyup();
		return end - start;
	}

}
