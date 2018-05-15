

package sequential;


public class SequentialMontecarloImplementation extends main.AbstractMontecarloImplementation {
    @java.lang.Override
    public void runSerial() {
        results = new java.util.Vector(nRunsMC);
        main.PriceStock ps;
        for (int iRun = 0; iRun < (nRunsMC); iRun++) {
            ps = new main.PriceStock();
            ps.setInitAllTasks(initAllTasks);
            ps.setTask(tasks.elementAt(iRun));
            ps.run();
            results.addElement(ps.getResult());
        }
    }
}

