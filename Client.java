package com.gmail.kutepov89.sergey;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class Client implements Runnable {
	private Socket soc;
	private String answer;
	private Thread t;
	private int numReq = 0;

	public Client(Socket soc) {
		super();
		this.soc = soc;
		this.answer = getSysInfo();
		t = new Thread(this);
		t.start();
	}

	public int getNumberRequest() {
		return this.numReq+1;
	}

	public String getSysInfo() {
		String answer = "";

		answer += "<html><head><title>Информация о системе</title> <meta charset='utf-8'></head><body><p>Информация о системе: </p> Запрос: "
				+ getNumberRequest() + "<br>";
		answer += "<table border='2' cellpadding='5' >";
		for (Map.Entry v : System.getenv().entrySet()) {
			answer += "<tr><td>" + v.getKey() + "</td><td>" + v.getValue() + "</td></tr>";
		}
		answer += "</table></body></html>";

		return answer;
	}

	public void run() {
		try (InputStream is = soc.getInputStream();
				OutputStream os = soc.getOutputStream();
				PrintWriter pw = new PrintWriter(os)) {
			byte[] rec1 = new byte[is.available()];
			is.read(rec1);
			String response = "HTTP/1.1 200 OK\r\n" + "Server: My_Server\r\n" + "Content-Type:text/html\r\n"
					+ "Content-Length: " + "\r\n" + "Connection: close\r\n\r\n";
			pw.print(response + answer);
			pw.flush();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}