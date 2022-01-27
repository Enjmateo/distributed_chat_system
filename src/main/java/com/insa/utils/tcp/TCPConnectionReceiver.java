package com.insa.utils.tcp;
import java.lang.Thread; 
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import com.insa.app.*;
import com.insa.utils.Consts;


public class TCPConnectionReceiver extends Thread{
	ServerSocket serverSocket;
	int clientNumber = 0;

	private boolean running = true;

	public TCPConnectionReceiver(boolean type) throws Exception{
		this.serverSocket = new ServerSocket(type?Consts.TCP_PORT_A:Consts.TCP_PORT_B);
		this.start();
	}
    private synchronized boolean isRunning(){return running;}

    public synchronized void close(boolean force) throws IOException{
        if(force){
            serverSocket.close();
        }
        this.running = false;
    }

	public void run(){
		try{
			while(isRunning()){
                Socket socket = serverSocket.accept();
                UsersHandler.getUserByInetAddress(socket.getInetAddress()).connect(socket);
				clientNumber++;
			}
		}catch(Exception e){
			if(isRunning()){
				e.printStackTrace();
				try{
                    close(true);
                }catch(Exception v){
                    throw new RuntimeException("Failed to close TCPObjectReceiver");
                }
			}
		}		
	}
}