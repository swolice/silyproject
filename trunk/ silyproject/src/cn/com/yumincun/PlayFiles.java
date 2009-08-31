package cn.com.yumincun;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.File;

import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.Time;
import javax.media.protocol.DataSource;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Stefan
 * 
 */
public class PlayFiles extends JFrame implements ControllerListener {
	private Container cont;
	private JPanel jPPlayer = new JPanel();;
	private File[] files;
	private int nF = 0;

	// C:/Workspace/StafahJMF/src/com/stafah/jmf/videos
	public PlayFiles(String loc) throws Exception {
		super("Stafah Multi-File Player");
		File dir = new File(loc);
		if (dir.isDirectory()) {
			cont = getContentPane();
			jPPlayer.setLayout(new BorderLayout());
			cont.add(jPPlayer);
			playFiles(dir);

			pack();
			setVisible(true);
		}
	}

	private boolean isVideoFile(File f) {
		boolean bRetVal = false;
		String fName = f.getName();
		if (fName.endsWith("mpeg") || fName.endsWith("mov")
				|| fName.endsWith("avi")) {
			bRetVal = true;
		}
		return bRetVal;
	}

	public void controllerUpdate(ControllerEvent e) {
		Processor p = (Processor) e.getSourceController();
		if (e instanceof ConfigureCompleteEvent) {
			System.out.println("ConfigureCompleteEvent");
			p.setContentDescriptor(null);
			p.realize();
		} else if (e instanceof RealizeCompleteEvent) {
			System.out.println("RealizeCompleteEvent");
			try {
				Component c = p.getVisualComponent();
				if (c != null) {
					jPPlayer.add(c);
				}
			} catch (Exception eX) {
				eX.printStackTrace();
			}
			p.start();
			validate();
		} else if (e instanceof EndOfMediaEvent) {
			System.out.println("EndOfMediaEvent");
			Component c = p.getVisualComponent();
			jPPlayer.remove(c);
			validate();

			p.removeControllerListener(this);
			playNextFile();
		}
	}

	private void playNextFile() {
		nF++;
		if (nF >= files.length) {
			nF = 0;
		}
		File f = files[nF];
		if (isVideoFile(f)) {
			try {
				playFile(f);
			} catch (Exception eX) {
				eX.printStackTrace();
			}
		}
	}

	private void playFile(File f) throws Exception {
		System.out.println("file - '" + f.getPath() + "'");
		MediaLocator mL = new MediaLocator("file:" + f.getCanonicalPath());
		DataSource dS = Manager.createDataSource(mL);
		Processor proc = Manager.createProcessor(dS);
		proc.addControllerListener(this);
		proc.configure();
	}

	private void playFiles(File dir) throws Exception {
		files = dir.listFiles();
		nF = 0;
		// fnd the 1st video file
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (isVideoFile(f)) {
				playFile(f);
				break;
			}
			nF++;
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 1) {
			new PlayFiles(args[0]);
		}
	}
}
