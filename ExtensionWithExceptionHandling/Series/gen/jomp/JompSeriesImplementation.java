

package jomp;


public class JompSeriesImplementation extends main.AbstractSeriesImplementation {
    public JompSeriesImplementation(int nThreads) {
        jomp.runtime.OMP.setNumThreads(nThreads);
    }

    public void calculateFourierSeries(double omega) {
        {
            jomp.JompSeriesImplementation.__omp_Class0 __omp_Object0 = new jomp.JompSeriesImplementation.__omp_Class0();
            __omp_Object0.omega = omega;
            try {
                jomp.runtime.OMP.doParallel(__omp_Object0);
            } catch (java.lang.Throwable __omp_exception) {
                java.lang.System.err.println("OMP Warning: Illegal thread exception ignored!");
                java.lang.System.err.println(__omp_exception);
            }
            omega = __omp_Object0.omega;
        }
    }

    private class __omp_Class0 extends jomp.runtime.BusyTask {
        double omega;

        public void go(int __omp_me) throws java.lang.Throwable {
            {
                boolean amLast = false;
                {
                    jomp.runtime.LoopData __omp_WholeData2 = new jomp.runtime.LoopData();
                    jomp.runtime.LoopData __omp_ChunkData1 = new jomp.runtime.LoopData();
                    __omp_WholeData2.start = ((long) (1));
                    __omp_WholeData2.stop = ((long) (array_rows));
                    __omp_WholeData2.step = ((long) (1));
                    jomp.runtime.OMP.setChunkStatic(__omp_WholeData2);
                    while ((!(__omp_ChunkData1.isLast)) && (jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData2, __omp_ChunkData1))) {
                        for (; ;) {
                            if ((__omp_WholeData2.step) > 0) {
                                if ((__omp_ChunkData1.stop) > (__omp_WholeData2.stop))
                                    __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                
                                if ((__omp_ChunkData1.start) >= (__omp_WholeData2.stop))
                                    break;
                                
                            }else {
                                if ((__omp_ChunkData1.stop) < (__omp_WholeData2.stop))
                                    __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                
                                if ((__omp_ChunkData1.start) > (__omp_WholeData2.stop))
                                    break;
                                
                            }
                            for (int i = ((int) (__omp_ChunkData1.start)); i < (__omp_ChunkData1.stop); i += __omp_ChunkData1.step) {
                                {
                                    main.AbstractSeriesImplementation.TestArray[0][i] = main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, ((omega) * ((double) (i))), 1);
                                    main.AbstractSeriesImplementation.TestArray[1][i] = main.AbstractSeriesImplementation.TrapezoidIntegrate(((double) (0.0)), ((double) (2.0)), 1000, ((omega) * ((double) (i))), 2);
                                }
                                if (i == ((__omp_WholeData2.stop) - 1))
                                    amLast = true;
                                
                            }
                            if ((__omp_ChunkData1.startStep) == 0)
                                break;
                            
                            __omp_ChunkData1.start += __omp_ChunkData1.startStep;
                            __omp_ChunkData1.stop += __omp_ChunkData1.startStep;
                        }
                    } 
                    jomp.runtime.OMP.doBarrier(__omp_me);
                    if (amLast) {
                    }
                }
                if (amLast) {
                }
                if ((jomp.runtime.OMP.getThreadNum(__omp_me)) == 0) {
                }
            }
            if ((jomp.runtime.OMP.getThreadNum(__omp_me)) == 0) {
            }
        }
    }
}

