package com.streaming.clinica.colombia.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Prueba extends Conexion{



	public Prueba() throws IOException {
		super("servidor");
		// TODO Auto-generated constructor stub
	}

	public void startServer()//Método para iniciar el servidor
	{
		try
		{
			System.out.println("Esperando..."); //Esperando conexión

			cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente

			System.out.println("Cliente en línea");

			while(true) {
				DataInputStream dis=new DataInputStream(cs.getInputStream());  
				String  str= dis.readUTF();
				if(str != null)
					System.out.println(str);
			}
			//            System.out.println("Fin de la conexión");

			//            ss.close();//Se finaliza la conexión con el cliente
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception{
		new Prueba().startServer();;
		//get the localhost IP address, if server is running on some other IP, you need to use that
		/*InetAddress host = InetAddress.getLocalHost();

		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket = new Socket("192.168.0.12", 10000);
		while(true) {
			DataInputStream din = new DataInputStream(socket.getInputStream());
			String str = din.readUTF();//in.readLine();
			System.out.println("MENSAJE:  "+str);
//			dout.close();  
//			din.close();

		}*/

		//		socket.close();
		/*
		for(int i=0; i<=0;i++){
			//establish socket connection to server
			//write to socket using ObjectOutputStream
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject("Hola"+i);
			//read the server response message

//			ByteBuffer buffer = ByteBuffer.allocate(8192);
////			int read =  socket.getInputStream().read(buffer.array());
////			System.out.println("read: " + read);

			System.out.println("----- va a obtener inputstream");
			DataInputStream in = new DataInputStream(socket.getInputStream());

			String l = in.readUTF();
			System.out.println("LO QUE LEE: "+l);

//			System.out.println("va a leer linea");
//			String message = (String) oiss.readLine();
//			System.out.println("Message: " + message);
			//close resources
//			ois.close();
			oos.close();
			Thread.sleep(100);
		}*/
	}
}
