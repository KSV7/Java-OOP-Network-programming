package com.gmail.kutepov89.sergey;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		// Написать сервер, который будет отправлять пользователю информацию о системе и номер запроса.
		try (ServerSocket soc = new ServerSocket(8080)) {
			for (;;) {
				Socket clisoc = soc.accept();
				System.out.println(clisoc.getInetAddress());
				Client cli = new Client(clisoc);
			}
		} catch (IOException e) {
			System.out.println("Error to server Socket Open!!!");
		}
	}

}