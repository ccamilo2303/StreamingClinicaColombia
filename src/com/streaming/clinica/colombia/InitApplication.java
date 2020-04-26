package com.streaming.clinica.colombia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.tomcat.util.codec.binary.Base64;

import com.streaming.clinica.colombia.publisher.PublisherServer;

/**
 * @author Cristian Camilo Beltrán <ccamilo2303@gmail.com>
 * @since  13 abr. 2020
 */
@WebListener
public class InitApplication implements ServletContextListener{

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {


	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		

	}

}
