package com.streaming.clinica.colombia.web.socket;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.streaming.clinica.colombia.publisher.PublisherServer;
import com.streaming.clinica.colombia.subscriber.ClientSubscriber;

/**
 * @author Cristian Camilo Beltrán <ccamilo2303@gmail.com>
 * @since  13 abr. 2020
 */
@ServerEndpoint("/live")
public class WebSocketServer {

	
	@OnOpen
	public void onOpen(Session session) {
		
		PublisherServer.PUBLISHER.subscribe(new ClientSubscriber(session));
	}
	
}
