package cn.com.yumincun;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Component;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.control.BufferControl;
import javax.media.protocol.DataSource;
import javax.media.rtp.Participant;
import javax.media.rtp.RTPControl;
import javax.media.rtp.RTPManager;
import javax.media.rtp.ReceiveStream;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.SessionAddress;
import javax.media.rtp.SessionListener;
import javax.media.rtp.event.ByeEvent;
import javax.media.rtp.event.NewParticipantEvent;
import javax.media.rtp.event.NewReceiveStreamEvent;
import javax.media.rtp.event.ReceiveStreamEvent;
import javax.media.rtp.event.SessionEvent;
import javax.media.rtp.event.StreamMappedEvent;

public class PlayerApplet extends Applet implements ControllerListener,ReceiveStreamListener, SessionListener,Runnable{

	private static final long serialVersionUID = 1L;
	RTPManager mgrs = null;
	Player player = null;
	boolean dataReceived = false;        // �Ƿ���յ���ݵı�־
	Object dataSync = new Object();
	
	Component comp1;
	Component comp2;
	Button btnStartVideo;
	Button btnStopVideo;

	TextField tfServerIP;
	public void init() {
		this.setLayout(null);
		this.initComp();
		this.addComp();
		this.setBounds();
		this.addCompAction();
		this.setSize(600, 400);

	}
	public void initComp(){
		btnStartVideo=new Button("start");
		btnStopVideo=new Button("stop");
	}
	public void addComp(){
		this.add(btnStartVideo);
		this.add(btnStopVideo);
	}
	public void setBounds(){
		btnStartVideo.setBounds(10, 350, 80, 20);
		btnStopVideo.setBounds(150, 350, 80, 20);
	}
	public void addCompAction(){
		btnStartVideo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				BtnStartVideo(e);

			}   
		});
		btnStopVideo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				BtnStopVideo(e);
				
			}

			
		});
	}

	private void BtnStartVideo(ActionEvent e) {
		if(player==null){
			Thread t=new Thread(this);
			t.start();
			System.out.println("thread start");
		}
		

	}

	private void BtnStopVideo(ActionEvent e) {
		if(player!=null){
			remove(comp1);
			remove(comp2);
		}
		
		stopVideo();
		
		System.out.println("thread stop");
	}
	protected boolean initialize() {
		try {
			mgrs = (RTPManager) RTPManager.newInstance();       // Ϊÿһ��RTP�Ự����һ��RTP������
			mgrs.addSessionListener(this);                      // ��ӻỰ����
			mgrs.addReceiveStreamListener(this);                // ��ӽ��յ���ݵļ���

			InetAddress ipAddr = InetAddress.getByName("192.168.1.118");

			SessionAddress localAddr = null;
			SessionAddress destAddr = null;
			if( ipAddr.isMulticastAddress()) {                     // �����鲥�����غ�Ŀ�ĵص�IP��ַ��ͬ��������鲥��ַ
				localAddr= new SessionAddress( ipAddr,22222);
				destAddr = new SessionAddress( ipAddr,22222); 
			}
			else {
				localAddr= new SessionAddress( InetAddress.getLocalHost(),22222);          // �ñ���IP��ַ�Ͷ˿ںŹ���Դ�Ự��ַ
				destAddr = new SessionAddress( ipAddr, 22222);                             // ��Ŀ�Ļ��Ͷˣ���IP��ַ�Ͷ˿ںŹ���Ŀ�ĻỰ��ַ
			}

			mgrs.initialize( localAddr);              // ������Ự��ַ��RTP������

			BufferControl bc = (BufferControl)mgrs.getControl("javax.media.control.BufferControl");
			if (bc != null)
				bc.setBufferLength(350);         // ���û������С��Ҳ����ʹ������ֵ��

			mgrs.addTarget(destAddr);                 // ����Ŀ�ĻỰ��ַ

		}
		catch (Exception e){
			System.err.println("Cannot create the RTP Session: " + e.getMessage());
			return false;
		}

		// �ȴ���ݵ���
		long then = System.currentTimeMillis();
		long waitingPeriod = 60000;   // ���ȴ�60��

		try{
			synchronized (dataSync) {
				while (!dataReceived && System.currentTimeMillis() - then < waitingPeriod) {   // �ȴ��������趨��ʱ��
					if (!dataReceived)
						System.err.println("  - Waiting for RTP data to arrive...");
					dataSync.wait(1000);
				}
			}
		}
		catch (Exception e) {}

		if (!dataReceived) {               // ���趨��ʱ����û�еȵ����
			System.err.println("No RTP data was received.");
			player.close();
			player.stop();
			return false;
		}

		return true;

	}
	public void start() {
		if(player!=null)
			player.start();
	}

	public void stopVideo() {
		if(player!=null){
			player.close();
			player=null;
		}
		if (mgrs != null) {
			mgrs.removeTargets( "Closing session from RTPReceive");
			mgrs.dispose();                      // �ر�RTP�Ự������
			mgrs = null;
		}
	}

	public void destroy() {
		
		if(player!=null){
			player.close();
			player=null;
		}
		if (mgrs != null) {
			mgrs.removeTargets( "Closing session from RTPReceive");
			mgrs.dispose();                      // �ر�RTP�Ự������
			mgrs = null;
		}
	}

	public synchronized void controllerUpdate(ControllerEvent event) {
		if (event instanceof RealizeCompleteEvent) {
			
			if ((comp1 = player.getVisualComponent()) != null)


				this.add(comp1);
			comp1.setBounds(10,10,300,300);
			if ((comp2 = player.getControlPanelComponent()) != null)

				this.add(comp2);
			comp2.setBounds(10,310,300,20);

			validate();
			System.out.println("palyer");
		}
	}

	public  synchronized void update(ReceiveStreamEvent evt) {
		RTPManager mgr = (RTPManager)evt.getSource();
		Participant participant = evt.getParticipant();	// �õ������ߣ������ߣ�
		ReceiveStream stream = evt.getReceiveStream();      // �õ����յ��������

		if (evt instanceof NewReceiveStreamEvent) {           // ���յ��µ������
			try {
				stream = ((NewReceiveStreamEvent)evt).getReceiveStream();   // �õ��������
				DataSource ds = stream.getDataSource();                     // �õ����Դ

				RTPControl ctl = (RTPControl)ds.getControl("javax.media.rtp.RTPControl");  // �õ�RTP������
				if (ctl != null)
					System.err.println("  - Recevied new RTP stream: " + ctl.getFormat());    // �õ�������ݵĸ�ʽ
				else
					System.err.println("  - Recevied new RTP stream");

				if (participant == null)
					System.err.println("      The sender of this stream had yet to be identified.");
				else
					System.err.println("      The stream comes from: " + participant.getCNAME());

				player = javax.media.Manager.createPlayer(ds);       // ͨ�����Դ����һ��ý�岥����
				if (player == null)
					return;

				player.addControllerListener(this);             // �������ӿ��������
				player.realize();
				synchronized (dataSync) {
					dataReceived = true;
					dataSync.notifyAll();
				}
			}catch (Exception e) {
				System.err.println("NewReceiveStreamEvent exception " + e.getMessage());
				return;
			}
		}
		else if (evt instanceof StreamMappedEvent) {         // �����ӳ���¼�
			if (stream != null && stream.getDataSource() != null) {
				DataSource ds = stream.getDataSource();
				RTPControl ctl = (RTPControl)ds.getControl("javax.media.rtp.RTPControl");
				System.err.println("  - The previously unidentified stream ");
				if (ctl != null)
					System.err.println("      " + ctl.getFormat());         // �õ���ʽ
				System.err.println("      had now been identified as sent by: " + participant.getCNAME());
			}
		}
		else if (evt instanceof ByeEvent) {                  // ��ݽ������
			System.err.println("  - Got \"bye\" from: " + participant.getCNAME());


			if (player!= null) {
				player.close();                                      // �رղ��Ŵ���
				
			}
		}
	}

	public  synchronized void update(SessionEvent evt) {
		if (evt instanceof NewParticipantEvent) {
			Participant p = ((NewParticipantEvent)evt).getParticipant();
			System.err.println("  - A new participant had just joined: " + p.getCNAME());
		}
	}
	public void run() {
		this.initialize();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		player.start();
	}
}