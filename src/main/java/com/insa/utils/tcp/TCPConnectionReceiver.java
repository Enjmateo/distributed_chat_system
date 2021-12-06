package com.insa.utils.tcp;
import java.lang.Thread; 
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import com.insa.app.*;


public class TCPConnectionReceiver extends Thread{
	ServerSocket serverSocket;
	int clientNumber = 0;

	private boolean running = true;

	public TCPConnectionReceiver(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
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