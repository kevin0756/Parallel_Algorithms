//Ported from the JGF benchmark suite
package main;


public class JGFSeriesBench {
	
	int repeatFactor;

	public JGFSeriesBench(AbstractSeriesImplementation implementation) {
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
		for(int i = 0;  i < repeatFactor; i++){
			implementation.Do();
		}
		implementation.waitTillFinished();
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

	//Unused by JMH
	public long run(int size, int repeat) {
		this.repeatFactor = repeat;
		setsize(size);
		initialise();
		long start = System.currentTimeMillis();
		kernel();
		long end = System.currentTimeMillis();
		validate();
		tidyup();
		return end - start;
	}
	
}
