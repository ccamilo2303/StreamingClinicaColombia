package com.streaming.clinica.colombia.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.tomcat.util.codec.binary.Base64;

import com.streaming.clinica.colombia.publisher.PublisherServer;

/**
 * @author Cristian Camilo Beltrán <ccamilo2303@gmail.com>
 * @since  25 abr. 2020
 */
public class SocketClienteService {
	
	private final int PUERTO = 1234; //Puerto para la conexión
	private final String HOST = "localhost"; //Host para la conexión
	protected String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
	protected ServerSocket ss; //Socket del servidor
	protected Socket cs; //Socket del cliente
	protected DataOutputStream salidaServidor, salidaCliente; //Flujo de datos de salida


	
	private static SocketClienteService _INSTANCE = null;
	
	private SocketClienteService() throws Exception{
		init();
	}
	
	public static SocketClienteService getInstance() throws Exception {
		
		if(_INSTANCE == null)
			_INSTANCE = new SocketClienteService();
		
		return _INSTANCE;
	}
	
	public void init() throws Exception{
		System.out.println("va a iniciar hilo ... ");
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					
					System.out.println("VA A CREAR PUERTO : "+PUERTO);
					ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234

					System.out.println("Esperando..."); //Esperando conexión

					cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente

					System.out.println("Cliente en línea");

					while(true) {
						DataInputStream dis=new DataInputStream(cs.getInputStream());  
						String  dir = dis.readUTF();
						if(dir != null) {
							try {
								System.out.println("recibe: "+dir);
								File f = new File(dir);
								InputStream in = new FileInputStream(f);
								
								byte[] imageInByte = new byte[in.available()];
								in.read(imageInByte);
								
								PublisherServer.PUBLISHER.submit(new String(Base64.encodeBase64(imageInByte), "UTF-8"));
								in.close();
								f.delete();
							}catch(Exception e) {
								e.printStackTrace();
							}
						}
					}
					//            System.out.println("Fin de la conexión");

					//            ss.close();//Se finaliza la conexión con el cliente
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
		System.out.println("inicia hilo ... ");

	}
	
}
