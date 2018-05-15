package sequential;

import java.util.Vector;
import main.*;

public class SequentialMontecarloImplementation extends
		AbstractMontecarloImplementation {
	@Override
	public void runSerial() {
		results = new Vector(nRunsMC);
		PriceStock ps;
		//Run results sequentially
		for (int iRun = 0; iRun < nRunsMC; iRun++) {
			ps = new PriceStock();
			ps.setInitAllTasks(initAllTasks);
			ps.setTask(tasks.elementAt(iRun));
			ps.run();
			results.addElement(ps.getResult());
		}
	}
}
