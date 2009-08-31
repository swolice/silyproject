package cn.com.mianshi;

import java.net.*;
import java.io.*;

public class Server {
	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public Server() {
		try {
			ss = new ServerSocket(10000);
			while (true) {
				socket = ss.accept();
				String RemoteIP = socket.getInetAddress().getHostAddress();
				String RemotePort = ":" + socket.getLocalPort();
				System.out.println("A client come in!IP:" + RemoteIP
						+ RemotePort);
				in = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));
				String line = in.readLine();
				System.out.println("Cleint send is :" + line);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Your Message Received!");
				out.close();
				in.close();
				socket.close();
			}
		} catch (IOException e) {
			out.println("wrong");
		}
	}

	public static void main(String[] args) {
		new Server();
	}
};
