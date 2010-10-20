/*
 * Created on 2010-6-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package sample;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestWorkRun implements Runnable {
	private String con;
	public  TestWorkRun(String _con) {
		con = _con;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		System.out.println(con);
	}

	public static void main(String[] args) {
	}
}
