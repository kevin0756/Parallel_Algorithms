

package pJImplementation;


public class PyjamaMontecarloImplementation extends main.AbstractMontecarloImplementation {
    private java.lang.String schedulingPolicy = null;

    public PyjamaMontecarloImplementation(java.lang.String schedulingPolicy, int nThreads) {
        pJImplementation.PyjamaMontecarloImplementation.this.schedulingPolicy = schedulingPolicy;
        pj.Pyjama.omp_set_num_threads(nThreads);
    }

    @java.lang.Override
    public void runSerial() {
        long start = java.lang.System.currentTimeMillis();
        {
            results = new java.util.Vector(nRunsMC);
            switch (schedulingPolicy) {
                case "static" :
                    pj.pr.InternalControlVariables icv_previous__OMP_ParallelRegion_0 = pj.PjRuntime.getCurrentThreadICV();
                    pj.pr.InternalControlVariables icv__OMP_ParallelRegion_0 = pj.PjRuntime.inheritICV(icv_previous__OMP_ParallelRegion_0);
                    int _threadNum__OMP_ParallelRegion_0 = icv__OMP_ParallelRegion_0.nthreads_var.get(icv__OMP_ParallelRegion_0.levels_var);
                    java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist__OMP_ParallelRegion_0 = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();
                    java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist__OMP_ParallelRegion_0 = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();
                    pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0 _OMP_ParallelRegion_0_in = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0(_threadNum__OMP_ParallelRegion_0, icv__OMP_ParallelRegion_0, inputlist__OMP_ParallelRegion_0, outputlist__OMP_ParallelRegion_0);
                    _OMP_ParallelRegion_0_in.runParallelCode();
                    pj.PjRuntime.recoverParentICV(icv_previous__OMP_ParallelRegion_0);
                    java.lang.RuntimeException OMP_ee_0 = ((java.lang.RuntimeException) (_OMP_ParallelRegion_0_in.OMP_CurrentParallelRegionExceptionSlot.get()));
                    if (OMP_ee_0 != null) {
                        throw OMP_ee_0;
                    }
                    break;
                case "dynamic" :
                    pj.pr.InternalControlVariables icv_previous__OMP_ParallelRegion_1 = pj.PjRuntime.getCurrentThreadICV();
                    pj.pr.InternalControlVariables icv__OMP_ParallelRegion_1 = pj.PjRuntime.inheritICV(icv_previous__OMP_ParallelRegion_1);
                    int _threadNum__OMP_ParallelRegion_1 = icv__OMP_ParallelRegion_1.nthreads_var.get(icv__OMP_ParallelRegion_1.levels_var);
                    java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist__OMP_ParallelRegion_1 = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();
                    java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist__OMP_ParallelRegion_1 = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();
                    pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1 _OMP_ParallelRegion_1_in = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1(_threadNum__OMP_ParallelRegion_1, icv__OMP_ParallelRegion_1, inputlist__OMP_ParallelRegion_1, outputlist__OMP_ParallelRegion_1);
                    _OMP_ParallelRegion_1_in.runParallelCode();
                    pj.PjRuntime.recoverParentICV(icv_previous__OMP_ParallelRegion_1);
                    java.lang.RuntimeException OMP_ee_1 = ((java.lang.RuntimeException) (_OMP_ParallelRegion_1_in.OMP_CurrentParallelRegionExceptionSlot.get()));
                    if (OMP_ee_1 != null) {
                        throw OMP_ee_1;
                    }
                    break;
                case "guided" :
                    pj.pr.InternalControlVariables icv_previous__OMP_ParallelRegion_2 = pj.PjRuntime.getCurrentThreadICV();
                    pj.pr.InternalControlVariables icv__OMP_ParallelRegion_2 = pj.PjRuntime.inheritICV(icv_previous__OMP_ParallelRegion_2);
                    int _threadNum__OMP_ParallelRegion_2 = icv__OMP_ParallelRegion_2.nthreads_var.get(icv__OMP_ParallelRegion_2.levels_var);
                    java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist__OMP_ParallelRegion_2 = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();
                    java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist__OMP_ParallelRegion_2 = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();
                    pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2 _OMP_ParallelRegion_2_in = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2(_threadNum__OMP_ParallelRegion_2, icv__OMP_ParallelRegion_2, inputlist__OMP_ParallelRegion_2, outputlist__OMP_ParallelRegion_2);
                    _OMP_ParallelRegion_2_in.runParallelCode();
                    pj.PjRuntime.recoverParentICV(icv_previous__OMP_ParallelRegion_2);
                    java.lang.RuntimeException OMP_ee_2 = ((java.lang.RuntimeException) (_OMP_ParallelRegion_2_in.OMP_CurrentParallelRegionExceptionSlot.get()));
                    if (OMP_ee_2 != null) {
                        throw OMP_ee_2;
                    }
                    break;
                default :
                    throw new java.lang.IllegalArgumentException((("Pyjama scheduling policy: " + (schedulingPolicy)) + " is invalid. Please use one of static, dynamic, or guided"));
            }
        }
        long end = java.lang.System.currentTimeMillis();
        java.lang.System.out.println(("multi-task in Pyjama took: " + (end - start)));
    }

    class _OMP_ParallelRegion_0 {
        private int OMP_threadNumber = 1;

        private pj.pr.InternalControlVariables icv;

        private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_inputList = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();

        private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_outputList = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();

        private java.util.concurrent.locks.ReentrantLock OMP_lock;

        private pi.ParIterator<?> OMP__ParIteratorCreator;

        public java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> OMP_CurrentParallelRegionExceptionSlot = new java.util.concurrent.atomic.AtomicReference<java.lang.Throwable>(null);

        public _OMP_ParallelRegion_0(int thread_num, pj.pr.InternalControlVariables icv, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist) {
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.this.icv = icv;
            if ((false == (pj.Pyjama.omp_get_nested())) && ((pj.Pyjama.omp_get_level()) > 0)) {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.this.OMP_threadNumber = 1;
            }else {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.this.OMP_threadNumber = thread_num;
            }
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.this.OMP_inputList = inputlist;
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.this.OMP_outputList = outputlist;
            icv.currentParallelRegionThreadNumber = pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.this.OMP_threadNumber;
            icv.OMP_CurrentParallelRegionBarrier = new pj.pr.PjCyclicBarrier(pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.this.OMP_threadNumber);
        }

        private void updateOutputListForSharedVars() {
        }

        class MyCallable implements java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> {
            private int alias_id;

            private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_inputList;

            private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_outputList;

            MyCallable(int id, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist) {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.MyCallable.this.alias_id = id;
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.MyCallable.this.OMP_inputList = inputlist;
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.MyCallable.this.OMP_outputList = outputlist;
            }

            @java.lang.Override
            public java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> call() {
                try {
                    {
                        pj.PjRuntime.setBarrier();
                        try {
                            int i = 0;
                            int OMP_iterator = 0;
                            int OMP_end = ((int) ((nRunsMC) - 0)) / 1;
                            if ((((nRunsMC) - 0) % 1) == 0) {
                                OMP_end = OMP_end - 1;
                            }
                            int __omp_loop_thread_num = pj.Pyjama.omp_get_thread_num();
                            int __omp_loop_num_threads = pj.Pyjama.omp_get_num_threads();
                            for (OMP_iterator = __omp_loop_thread_num * 625; (OMP_iterator <= OMP_end) && (625 > 0); OMP_iterator = OMP_iterator + (__omp_loop_num_threads * 625)) {
                                for (int OMP_local_iterator = OMP_iterator; (OMP_local_iterator < (OMP_iterator + 625)) && (OMP_local_iterator <= OMP_end); OMP_local_iterator++) {
                                    i = 0 + (OMP_local_iterator * 1);
                                    {
                                        performSerialIteration(i);
                                    }
                                    if (OMP_end == OMP_local_iterator) {
                                    }
                                }
                            }
                        } catch (pj.pr.exceptions.OmpWorksharingLocalCancellationException wse) {
                        } catch (java.lang.Exception e) {
                            throw e;
                        }
                        pj.PjRuntime.reductionLockForWorksharing.lock();
                        pj.PjRuntime.reductionLockForWorksharing.unlock();
                        pj.PjRuntime.setBarrier();
                    }
                    pj.PjRuntime.setBarrier();
                    pj.PjRuntime.reset_OMP_orderCursor();
                    pj.PjRuntime.setBarrier();
                } catch (pj.pr.exceptions.OmpParallelRegionLocalCancellationException e) {
                    pj.PjRuntime.decreaseBarrierCount();
                } catch (java.lang.Exception e) {
                    pj.PjRuntime.decreaseBarrierCount();
                    pj.pr.PjExecutor.cancelCurrentThreadGroup();
                    OMP_CurrentParallelRegionExceptionSlot.compareAndSet(null, e);
                }
                if (0 == (pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.MyCallable.this.alias_id)) {
                    updateOutputListForSharedVars();
                }
                return null;
            }
        }

        public void runParallelCode() {
            for (int i = 1; i <= ((pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.this.OMP_threadNumber) - 1); i++) {
                java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> slaveThread = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.MyCallable(i, OMP_inputList, OMP_outputList);
                pj.PjRuntime.submit(i, slaveThread, icv);
            }
            java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> masterThread = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_0.MyCallable(0, OMP_inputList, OMP_outputList);
            pj.PjRuntime.getCurrentThreadICV().currentThreadAliasID = 0;
            try {
                masterThread.call();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    class _OMP_ParallelRegion_1 {
        private int OMP_threadNumber = 1;

        private pj.pr.InternalControlVariables icv;

        private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_inputList = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();

        private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_outputList = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();

        private java.util.concurrent.locks.ReentrantLock OMP_lock;

        private pi.ParIterator<?> OMP__ParIteratorCreator;

        public java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> OMP_CurrentParallelRegionExceptionSlot = new java.util.concurrent.atomic.AtomicReference<java.lang.Throwable>(null);

        public _OMP_ParallelRegion_1(int thread_num, pj.pr.InternalControlVariables icv, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist) {
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.this.icv = icv;
            if ((false == (pj.Pyjama.omp_get_nested())) && ((pj.Pyjama.omp_get_level()) > 0)) {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.this.OMP_threadNumber = 1;
            }else {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.this.OMP_threadNumber = thread_num;
            }
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.this.OMP_inputList = inputlist;
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.this.OMP_outputList = outputlist;
            icv.currentParallelRegionThreadNumber = pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.this.OMP_threadNumber;
            icv.OMP_CurrentParallelRegionBarrier = new pj.pr.PjCyclicBarrier(pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.this.OMP_threadNumber);
        }

        private void updateOutputListForSharedVars() {
        }

        class MyCallable implements java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> {
            private int alias_id;

            private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_inputList;

            private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_outputList;

            MyCallable(int id, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist) {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.MyCallable.this.alias_id = id;
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.MyCallable.this.OMP_inputList = inputlist;
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.MyCallable.this.OMP_outputList = outputlist;
            }

            @java.lang.Override
            public java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> call() {
                try {
                    {
                        pj.PjRuntime.setBarrier();
                        try {
                            int i = 0;
                            int OMP_iterator = 0;
                            int OMP_end = ((int) ((nRunsMC) - 0)) / 1;
                            if ((((nRunsMC) - 0) % 1) == 0) {
                                OMP_end = OMP_end - 1;
                            }
                            if (0 == (pj.Pyjama.omp_get_thread_num())) {
                                pj.PjRuntime.get_OMP_loopCursor().getAndSet(0);
                            }
                            pj.PjRuntime.setBarrier();
                            while ((OMP_iterator = pj.PjRuntime.get_OMP_loopCursor().getAndAdd(625)) <= OMP_end) {
                                for (int OMP_local_iterator = OMP_iterator; (OMP_local_iterator < (OMP_iterator + 625)) && (OMP_local_iterator <= OMP_end); OMP_local_iterator++) {
                                    i = 0 + (OMP_local_iterator * 1);
                                    {
                                        performSerialIteration(i);
                                    }
                                    if (OMP_end == OMP_local_iterator) {
                                    }
                                }
                            } 
                        } catch (pj.pr.exceptions.OmpWorksharingLocalCancellationException wse) {
                        } catch (java.lang.Exception e) {
                            throw e;
                        }
                        pj.PjRuntime.reductionLockForWorksharing.lock();
                        pj.PjRuntime.reductionLockForWorksharing.unlock();
                        pj.PjRuntime.setBarrier();
                    }
                    pj.PjRuntime.setBarrier();
                    pj.PjRuntime.reset_OMP_orderCursor();
                    pj.PjRuntime.setBarrier();
                } catch (pj.pr.exceptions.OmpParallelRegionLocalCancellationException e) {
                    pj.PjRuntime.decreaseBarrierCount();
                } catch (java.lang.Exception e) {
                    pj.PjRuntime.decreaseBarrierCount();
                    pj.pr.PjExecutor.cancelCurrentThreadGroup();
                    OMP_CurrentParallelRegionExceptionSlot.compareAndSet(null, e);
                }
                if (0 == (pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.MyCallable.this.alias_id)) {
                    updateOutputListForSharedVars();
                }
                return null;
            }
        }

        public void runParallelCode() {
            for (int i = 1; i <= ((pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.this.OMP_threadNumber) - 1); i++) {
                java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> slaveThread = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.MyCallable(i, OMP_inputList, OMP_outputList);
                pj.PjRuntime.submit(i, slaveThread, icv);
            }
            java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> masterThread = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_1.MyCallable(0, OMP_inputList, OMP_outputList);
            pj.PjRuntime.getCurrentThreadICV().currentThreadAliasID = 0;
            try {
                masterThread.call();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    class _OMP_ParallelRegion_2 {
        private int OMP_threadNumber = 1;

        private pj.pr.InternalControlVariables icv;

        private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_inputList = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();

        private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_outputList = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>();

        private java.util.concurrent.locks.ReentrantLock OMP_lock;

        private pi.ParIterator<?> OMP__ParIteratorCreator;

        public java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> OMP_CurrentParallelRegionExceptionSlot = new java.util.concurrent.atomic.AtomicReference<java.lang.Throwable>(null);

        public _OMP_ParallelRegion_2(int thread_num, pj.pr.InternalControlVariables icv, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist) {
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.this.icv = icv;
            if ((false == (pj.Pyjama.omp_get_nested())) && ((pj.Pyjama.omp_get_level()) > 0)) {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.this.OMP_threadNumber = 1;
            }else {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.this.OMP_threadNumber = thread_num;
            }
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.this.OMP_inputList = inputlist;
            pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.this.OMP_outputList = outputlist;
            icv.currentParallelRegionThreadNumber = pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.this.OMP_threadNumber;
            icv.OMP_CurrentParallelRegionBarrier = new pj.pr.PjCyclicBarrier(pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.this.OMP_threadNumber);
        }

        private void updateOutputListForSharedVars() {
        }

        class MyCallable implements java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> {
            private int alias_id;

            private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_inputList;

            private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> OMP_outputList;

            MyCallable(int id, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> inputlist, java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> outputlist) {
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.MyCallable.this.alias_id = id;
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.MyCallable.this.OMP_inputList = inputlist;
                pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.MyCallable.this.OMP_outputList = outputlist;
            }

            @java.lang.Override
            public java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> call() {
                try {
                    {
                        pj.PjRuntime.setBarrier();
                        try {
                            int i = 0;
                            int OMP_iterator = 0;
                            int OMP_end = ((int) ((nRunsMC) - 0)) / 1;
                            if ((((nRunsMC) - 0) % 1) == 0) {
                                OMP_end = OMP_end - 1;
                            }
                            int OMP_chunkSize = 625;
                            if (0 == (pj.Pyjama.omp_get_thread_num())) {
                                pj.PjRuntime.get_OMP_loopCursor().getAndSet(0);
                            }
                            pj.PjRuntime.setBarrier();
                            while ((OMP_iterator = pj.PjRuntime.get_OMP_loopCursor().getAndAdd(OMP_chunkSize)) <= OMP_end) {
                                for (int OMP_local_iterator = OMP_iterator; (OMP_local_iterator < (OMP_iterator + OMP_chunkSize)) && (OMP_local_iterator <= OMP_end); OMP_local_iterator++) {
                                    i = 0 + (OMP_local_iterator * 1);
                                    {
                                        performSerialIteration(i);
                                    }
                                    if (OMP_end == OMP_local_iterator) {
                                    }
                                }
                                if (OMP_chunkSize > 1)
                                    OMP_chunkSize--;
                                
                            } 
                        } catch (pj.pr.exceptions.OmpWorksharingLocalCancellationException wse) {
                        } catch (java.lang.Exception e) {
                            throw e;
                        }
                        pj.PjRuntime.reductionLockForWorksharing.lock();
                        pj.PjRuntime.reductionLockForWorksharing.unlock();
                        pj.PjRuntime.setBarrier();
                    }
                    pj.PjRuntime.setBarrier();
                    pj.PjRuntime.reset_OMP_orderCursor();
                    pj.PjRuntime.setBarrier();
                } catch (pj.pr.exceptions.OmpParallelRegionLocalCancellationException e) {
                    pj.PjRuntime.decreaseBarrierCount();
                } catch (java.lang.Exception e) {
                    pj.PjRuntime.decreaseBarrierCount();
                    pj.pr.PjExecutor.cancelCurrentThreadGroup();
                    OMP_CurrentParallelRegionExceptionSlot.compareAndSet(null, e);
                }
                if (0 == (pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.MyCallable.this.alias_id)) {
                    updateOutputListForSharedVars();
                }
                return null;
            }
        }

        public void runParallelCode() {
            for (int i = 1; i <= ((pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.this.OMP_threadNumber) - 1); i++) {
                java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> slaveThread = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.MyCallable(i, OMP_inputList, OMP_outputList);
                pj.PjRuntime.submit(i, slaveThread, icv);
            }
            java.util.concurrent.Callable<java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object>> masterThread = new pJImplementation.PyjamaMontecarloImplementation._OMP_ParallelRegion_2.MyCallable(0, OMP_inputList, OMP_outputList);
            pj.PjRuntime.getCurrentThreadICV().currentThreadAliasID = 0;
            try {
                masterThread.call();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void performSerialIteration(int i) {
        {
            main.PriceStock ps = new main.PriceStock();
            ps.setInitAllTasks(initAllTasks);
            ps.setTask(tasks.elementAt(i));
            ps.run();
            results.addElement(ps.getResult());
        }
    }
}

