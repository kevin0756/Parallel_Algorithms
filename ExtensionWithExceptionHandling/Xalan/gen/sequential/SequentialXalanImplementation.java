

package sequential;


public class SequentialXalanImplementation extends main.AbstractXalanImplementation {
    java.lang.Void[] futureGroup = null;

    int count = 0;

    public SequentialXalanImplementation(int repeatFactor) {
        futureGroup = new java.lang.Void[repeatFactor];
    }

    @java.lang.Override
    public void transform() {
        pu.pi.ParIterator<java.lang.String> parallelIterator = pu.pi.ParIteratorFactory.createParIterator(fileNameList, 1);
        boolean continueWhileLoop = true;
        while (continueWhileLoop) {
            try {
                java.lang.Void multiTask = task(parallelIterator);
                futureGroup[((count)++)] = multiTask;
                continueWhileLoop = false;
            } catch (javax.xml.transform.TransformerException e) {
                java.lang.System.err.println(("TransformerException occurred by Thread: " + (java.lang.Thread.currentThread().getId())));
            } catch (java.io.IOException e) {
                java.lang.System.err.println(("IOException occurred by Thread: " + (java.lang.Thread.currentThread().getId())));
            }
        } 
    }

    private java.lang.Void task(pu.pi.ParIterator<java.lang.String> parallelIterator) throws java.io.IOException, javax.xml.transform.TransformerException {
        while (parallelIterator.hasNext()) {
            java.lang.String fileName = parallelIterator.next();
            java.io.FileInputStream inputStream = new java.io.FileInputStream(new java.io.File(fileName));
            int index = fileIndex.getAndIncrement();
            java.io.FileOutputStream outputStream = new java.io.FileOutputStream(new java.io.File(((main.AbstractXalanImplementation.BASENAME) + index)));
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
        java.lang.System.out.println((("Overall " + (fileIndex.get())) + " xml files were loaded and processed successfully"));
    }
}

