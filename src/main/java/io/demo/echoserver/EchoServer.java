package io.demo.echoserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * A simple server that takes size delimited byte arrays and just echos them back to the sender.
 */
class EchoServer extends Thread {
	public final int port;
	private final ServerSocket serverSocket;
	private final List<Thread> threads;
	private final List<Socket> sockets;
	private volatile boolean closing = false;
	private final AtomicBoolean renegotiate = new AtomicBoolean();

	public static void main(String[] args) throws Exception {
		Map<String, Object> configs = new HashMap<String, Object>();
		EchoServer server = new EchoServer(configs);
		System.out.println("starting...");
		server.start();
	}

	public EchoServer(Map<String, ?> configs) throws Exception {
		this.serverSocket = new ServerSocket(8080);
		this.port = this.serverSocket.getLocalPort();
		this.threads = Collections.synchronizedList(new ArrayList<>());
		this.sockets = Collections.synchronizedList(new ArrayList<>());
	}

	public void renegotiate() {
		renegotiate.set(true);
	}

	@Override
	public void run() {
		try {
			while (!closing) {
				final Socket socket = serverSocket.accept();
				synchronized (sockets) {
					if (closing) {
						break;
					}
					sockets.add(socket);
					Thread thread = new Thread() {
						@Override
						public void run() {
							try {
								BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								DataOutputStream output = new DataOutputStream(socket.getOutputStream());
								while (socket.isConnected() && !socket.isClosed()) {
									String line = input.readLine();
									System.out.println("Server: " + line);
									output.writeChars(line + "\n");
									output.flush();

								}
							} catch (IOException e) {
								// ignore
							} finally {
								try {
									socket.close();
								} catch (IOException e) {
									// ignore
								}
							}
						}
					};
					thread.start();
					threads.add(thread);
				}
			}
		} catch (IOException e) {
			// ignore
		}
	}

	public void closeConnections() throws IOException {
		synchronized (sockets) {
			for (Socket socket : sockets)
				socket.close();
		}
	}

	public void close() throws IOException, InterruptedException {
		closing = true;
		this.serverSocket.close();
		closeConnections();
		for (Thread t : threads)
			t.join();
		join();
	}
}