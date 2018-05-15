

package pTAnnotation;


public class PTAnnotationXalanImplementation extends main.AbstractXalanImplementation {
    pt.runtime.TaskIDGroup<java.lang.Void> __futureGroupPtTaskIDGroup__ = new pt.runtime.TaskIDGroup<>();

    volatile boolean __futureGroupPtTaskIDGroup__Synchronized = false;

    volatile java.util.concurrent.locks.Lock __futureGroupPtTaskIDGroup__Lock = new java.util.concurrent.locks.ReentrantLock();

    int numOfThreads;

    int repeatFactor;

    int count = 0;

    java.lang.Void[] futureGroup;

    public PTAnnotationXalanImplementation(int type, int nThreads, int repeatFactor) {
        pTAnnotation.PTAnnotationXalanImplementation.this.numOfThreads = nThreads;
        pTAnnotation.PTAnnotationXalanImplementation.this.repeatFactor = repeatFactor;
        futureGroup = new java.lang.Void[pTAnnotation.PTAnnotationXalanImplementation.this.repeatFactor];
        pt.runtime.ParaTask.PTSchedulingPolicy scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.MixedSchedule;
        switch (type) {
            case 1 :
                scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.WorkStealing;
                break;
            case 2 :
                scheduleType = pt.runtime.ParaTask.PTSchedulingPolicy.WorkSharing;
                break;
        }
        pt.runtime.ParaTask.init(scheduleType, numOfThreads);
    }

    @SuppressWarnings("unchecked")
	@java.lang.Override
    public void transform() {
        pu.pi.ParIterator<java.lang.String> parallelIterator = pu.pi.ParIteratorFactory.createParIterator(fileNameList, numOfThreads);
        try {
            pt.runtime.TaskInfoOneArg<java.lang.Void, pu.pi.ParIterator<java.lang.String>> __multiTaskPtTask__ = ((pt.runtime.TaskInfoOneArg<java.lang.Void, pu.pi.ParIterator<java.lang.String>>) 
            		(pt.runtime.ParaTask.asTask(pt.runtime.ParaTask.TaskType.MULTI, ((pt.functionalInterfaces.FunctorOneArgNoReturn<pu.pi.ParIterator<java.lang.String>>)
            				(( __parallelIteratorPtNonLambdaArg__) -> multiTransformTask(__parallelIteratorPtNonLambdaArg__))))));
            pt.runtime.ParaTask.registerAsyncCatch(__multiTaskPtTask__, javax.xml.transform.TransformerException.class, ( e) -> {
                java.lang.System.err.println(("TransformerException occurred by Thread: " + (java.lang.Thread.currentThread().getId())));
            });
            pt.runtime.ParaTask.registerAsyncCatch(__multiTaskPtTask__, java.io.IOException.class, ( e) -> {
                java.lang.System.err.println(("IOException occurred by Thread: " + (java.lang.Thread.currentThread().getId())));
            });
            pt.runtime.TaskIDGroup<java.lang.Void> __multiTaskPtTaskID__ = ((pt.runtime.TaskIDGroup<java.lang.Void>) (__multiTaskPtTask__.start(parallelIterator)));
            if (!(__futureGroupPtTaskIDGroup__Synchronized)) {
                __futureGroupPtTaskIDGroup__.setInnerTask(((count)++), __multiTaskPtTaskID__);
            }else {
                futureGroup[((count)++)] = __multiTaskPtTaskID__.getReturnResult();
            }
        } finally {
        }
    }

    public java.lang.Void multiTransformTask(pu.pi.ParIterator<java.lang.String> parallelIterator) throws java.io.IOException, javax.xml.transform.TransformerException {
        while (parallelIterator.hasNext()) {
            java.lang.String fileName = parallelIterator.next();
            java.io.FileInputStream inputStream = new java.io.FileInputStream(fileName);
            int index = fileIndex.getAndIncrement();
            java.io.FileOutputStream outputStream = new java.io.FileOutputStream(new java.io.File(((main.AbstractXalanImplementation.BASENAME) + index + ".html")));
            javax.xml.transform.Result outFile = new javax.xml.transform.stream.StreamResult(outputStream);
            javax.xml.transform.Transformer transformer = template.newTransformer();
            javax.xml.transform.Source inFile = new javax.xml.transform.stream.StreamSource(inputStream);
            transformer.transform(inFile, outFile);
            inputStream.close();
        } 
        return null;
    }

    @java.lang.Override
    public void waitTillFinished() {
        java.lang.Long start = java.lang.System.currentTimeMillis();
        if (!(__futureGroupPtTaskIDGroup__Synchronized)) {
            __futureGroupPtTaskIDGroup__Lock.lock();
            if (!(__futureGroupPtTaskIDGroup__Synchronized)) {
                try {
                    __futureGroupPtTaskIDGroup__.waitTillFinished();
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
                __futureGroupPtTaskIDGroup__Synchronized = true;
            }
            __futureGroupPtTaskIDGroup__Lock.unlock();
        }
        java.lang.Void barrier = futureGroup[0];
        java.lang.Long end = java.lang.System.currentTimeMillis();
        java.lang.System.out.println((("barrier took: " + (end - start)) + " milliseconds."));
        java.lang.System.out.println((("Overall " + (fileIndex.get())) + " xml files were loaded and processed successfully"));
    }
}

