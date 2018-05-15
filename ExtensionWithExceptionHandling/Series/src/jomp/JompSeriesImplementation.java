package jomp;

import main.*;

public class JompSeriesImplementation extends AbstractSeriesImplementation {
	
	public JompSeriesImplementation(int nThreads){
		jomp.runtime.OMP.setNumThreads(nThreads);
	}

	public void calculateFourierSeries(double omega) {
// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.omega = omega;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  omega = __omp_Object0.omega;
}
// OMP PARALLEL BLOCK ENDS

	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  double omega;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

                          { // OMP FOR BLOCK BEGINS
                          // copy of firstprivate variables, initialized
                          // copy of lastprivate variables
                          // variables to hold result of reduction
                          boolean amLast=false;
                          {
                            // firstprivate variables + init
                            // [last]private variables
                            // reduction variables + init to default
                            // -------------------------------------
                            jomp.runtime.LoopData __omp_WholeData2 = new jomp.runtime.LoopData();
                            jomp.runtime.LoopData __omp_ChunkData1 = new jomp.runtime.LoopData();
                            __omp_WholeData2.start = (long)( 1);
                            __omp_WholeData2.stop = (long)( array_rows);
                            __omp_WholeData2.step = (long)(1);
                            jomp.runtime.OMP.setChunkStatic(__omp_WholeData2);
                            while(!__omp_ChunkData1.isLast && jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData2, __omp_ChunkData1)) {
                            for(;;) {
                              if(__omp_WholeData2.step > 0) {
                                 if(__omp_ChunkData1.stop > __omp_WholeData2.stop) __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                 if(__omp_ChunkData1.start >= __omp_WholeData2.stop) break;
                              } else {
                                 if(__omp_ChunkData1.stop < __omp_WholeData2.stop) __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                 if(__omp_ChunkData1.start > __omp_WholeData2.stop) break;
                              }
                              for(int i = (int)__omp_ChunkData1.start; i < __omp_ChunkData1.stop; i += __omp_ChunkData1.step) {
                                // OMP USER CODE BEGINS

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
                                // OMP USER CODE ENDS
                                if (i == (__omp_WholeData2.stop-1)) amLast = true;
                              } // of for 
                              if(__omp_ChunkData1.startStep == 0)
                                break;
                              __omp_ChunkData1.start += __omp_ChunkData1.startStep;
                              __omp_ChunkData1.stop += __omp_ChunkData1.startStep;
                            } // of for(;;)
                            } // of while
                            // call reducer
                            jomp.runtime.OMP.doBarrier(__omp_me);
                            // copy lastprivate variables out
                            if (amLast) {
                            }
                          }
                          // set global from lastprivate variables
                          if (amLast) {
                          }
                          // set global from reduction variables
                          if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                          }
                          } // OMP FOR BLOCK ENDS

    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}
