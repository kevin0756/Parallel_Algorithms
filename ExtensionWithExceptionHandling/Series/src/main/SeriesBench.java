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

public class SeriesBench {

	public SeriesBench(AbstractSeriesImplementation implementation) {
		this.implementation = implementation;
	}

	private AbstractSeriesImplementation implementation;
	private int size;
	private int datasizes[] = { 10000, 100000, 1000000 };

	public void setsize(int size) {
		this.size = size;
	}

	public void initialise() {
		implementation.array_rows = datasizes[size];
		implementation.buildTestData();
	}

	public void kernel() {
		implementation.Do();
	}

	public void validate() {
		double ref[][] = { { 2.8729524964837996, 0.0 },
				{ 1.1161046676147888, -1.8819691893398025 },
				{ 0.34429060398168704, -1.1645642623320958 },
				{ 0.15238898702519288, -0.8143461113044298 } };

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				double error = Math.abs(AbstractSeriesImplementation.TestArray[j][i]
						- ref[i][j]);
				if (error > 1.0e-12) {
					System.out.println("Validation failed for coefficient " + j
							+ "," + i);
					System.out.println("Computed value = "
							+ AbstractSeriesImplementation.TestArray[j][i]);
					System.out.println("Reference value = " + ref[i][j]);
				}
			}
		}
	}

	public void tidyup() {
		implementation.freeTestData();
	}

	public void run(int size) {

		//JGFInstrumentor.addTimer("Section2:Series:Kernel:"+ this.implementation.getClass().getName(), "coefficients",size);
		setsize(size);
		initialise();
		kernel();
		validate();
		tidyup();

		//JGFInstrumentor.addOpsToTimer("Section2:Series:Kernel:"+ this.implementation.getClass().getName(),
				//(double) (implementation.array_rows * 2 - 1));

		//JGFInstrumentor.printTimer("Section2:Series:Kernel:"+ this.implementation.getClass().getName());
	}
	
}
