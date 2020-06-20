package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.biz.UserBiz;

import com.vo.*;

public class MainServer {

	public static void main(String[] args) {

		int port = 5555;
		Server.Start(port);
		
	}
}
