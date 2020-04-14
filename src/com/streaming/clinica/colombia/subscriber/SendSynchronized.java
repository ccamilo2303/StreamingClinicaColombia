package com.streaming.clinica.colombia.subscriber;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;

/**
 * @author Cristian Camilo Beltrán <ccamilo2303@gmail.com>
 * @since  13 abr. 2020
 */
public class SendSynchronized {
	
	public static synchronized void send(Session session, String frame) {
		Async async = session.getAsyncRemote();
		async.sendText(frame);
	}
	
}
