package com.streaming.clinica.colombia;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.streaming.clinica.colombia.service.WebCamServiceImpl;
import com.streaming.clinica.colombia.service.WebCamServiceInterface;

/**
 * @author Cristian Camilo Beltrán <ccamilo2303@gmail.com>
 * @since  13 abr. 2020
 */
@WebListener
public class InitApplication implements ServletContextListener{
	
	private WebCamServiceInterface webCamService = new WebCamServiceImpl();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				webCamService.initCam();
				webCamService.initProcess();
			}
		};
		
		Thread thread = new Thread(runnable);
		thread.start();
		
	}

}
