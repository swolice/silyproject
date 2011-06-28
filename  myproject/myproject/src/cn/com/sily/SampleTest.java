package cn.com.sily;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SampleTest {
	public static void main(String[] args) {
		myKeyEvent samp = new myKeyEvent();
	}
}

class myKeyEvent extends JFrame {
	public myKeyEvent() {
		super();
		final JLabel label = new JLabel();
		label.setText("备注：");
		this.getContentPane().add(label);

		final JScrollPane scrollPane = new JScrollPane();
		this.getContentPane().add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.addKeyListener(new aKeyListener());
		textArea.setLineWrap(true);
		textArea.setRows(3);
		textArea.setColumns(15);
		scrollPane.setViewportView(textArea);
		this.setVisible(true);
		this.setBounds(39, 39, 400, 400);
	}

	class aKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			String keyText = KeyEvent.getKeyText(e.getKeyCode());
			if (e.isActionKey()) {
				System.out.println("您按下的是动作键”" + keyText + "“");
			} else {
				System.out.println("您按下的是非动作键“" + keyText + "“");
				char keyChar = e.getKeyChar();
				switch (keyChar) {
				case KeyEvent.VK_CONTROL:
					System.out.println("Ctrl键被按下");
					break;
				case KeyEvent.VK_ALT:
					System.out.println("Alt键被按下");
					break;
				case KeyEvent.VK_SHIFT:
					System.out.println("Shift键被按下");
					break;
				}
			}
		}

		public void keyTyped(KeyEvent e) {
			System.out.println("此次输入的是“" + e.getKeyChar() + "”");
		}

		public void keyReleased(KeyEvent e) {
			String keyText = KeyEvent.getKeyText(e.getKeyCode());
			System.out.println("您释放的是“" + keyText + "”键");
			System.out.println();
		}
	}
}
