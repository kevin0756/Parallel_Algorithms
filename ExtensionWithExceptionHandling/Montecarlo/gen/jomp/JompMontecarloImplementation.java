

package jomp;


public class JompMontecarloImplementation extends main.AbstractMontecarloImplementation {
    public JompMontecarloImplementation(int nThreads) {
        jomp.runtime.OMP.setNumThreads(nThreads);
    }

    public void runSerial() {
        results = new java.util.Vector(nRunsMC);
        main.PriceStock ps;
        long start = java.lang.System.currentTimeMillis();
        {
            jomp.JompMontecarloImplementation.__omp_Class0 __omp_Object0 = new jomp.JompMontecarloImplementation.__omp_Class0();
            try {
                jomp.runtime.OMP.doParallel(__omp_Object0);
            } catch (java.lang.Throwable __omp_exception) {
                java.lang.System.err.println("OMP Warning: Illegal thread exception ignored!");
                java.lang.System.err.println(__omp_exception);
            }
        }
        long end = java.lang.System.currentTimeMillis();
        java.lang.System.out.println(("Multi-task in Jomp took: " + (end - start)));
    }

    private class __omp_Class0 extends jomp.runtime.BusyTask {
        public void go(int __omp_me) throws java.lang.Throwable {
            main.PriceStock ps = new main.PriceStock();
            {
                boolean amLast = false;
                {
                    jomp.runtime.LoopData __omp_WholeData2 = new jomp.runtime.LoopData();
                    jomp.runtime.LoopData __omp_ChunkData1 = new jomp.runtime.LoopData();
                    __omp_WholeData2.start = ((long) (0));
                    __omp_WholeData2.stop = ((long) (nRunsMC));
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
                            for (int iRun = ((int) (__omp_ChunkData1.start)); iRun < (__omp_ChunkData1.stop); iRun += __omp_ChunkData1.step) {
                                {
                                    ps = new main.PriceStock();
                                    ps.setInitAllTasks(initAllTasks);
                                    ps.setTask(tasks.elementAt(iRun));
                                    ps.run();
                                    synchronized(jomp.runtime.OMP.getLockByName("")) {
                                        {
                                            results.addElement(ps.getResult());
                                        }
                                    }
                                }
                                if (iRun == ((__omp_WholeData2.stop) - 1))
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

