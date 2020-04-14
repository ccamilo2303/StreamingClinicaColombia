package com.streaming.clinica.colombia.subscriber;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;
/**
 * @author Cristian Camilo Beltrán <ccamilo2303@gmail.com>
 * @since  13 abr. 2020
 */
public class ClientSubscriber implements Flow.Subscriber<String>{

	private Flow.Subscription subscription;
	private Session session;

	/**
	 * @param session
	 */
	public ClientSubscriber(Session session) {
		super();
		this.session = session;
	}


	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("SE SUBSCRIBE");
		this.subscription = subscription;
		subscription.request(1);

	}

	@Override
	public void onNext(String frame) {
		try {
			//			if(session.isOpen() == true) {
			subscription.request(1);
			SendSynchronized.send(session, frame);
//			Async async = session.getAsyncRemote();
//			async.sendText(frame);
			//			}
		}catch(Exception e) {
			System.out.println("Ocurre un error al momento de envíar data por web socket");
			e.printStackTrace();
		}
	}

	@Override
	public void onError(Throwable e) {
		System.out.println("Some error happened in MyFreelancerSubscriber");
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("TERMINA DE ENVÍAR:::...");

	}




	public static void main(String[] args) {
		//		SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
		//		ClientSubscriber clientSubscriber = new ClientSubscriber();
		//		publisher.subscribe(clientSubscriber);
		//		
		//		try {
		//		Thread.sleep(5000);
		//		publisher.submit("HOLAAAA");
		//		Thread.sleep(5000);
		//		publisher.submit("HOLAAAA");
		//		Thread.sleep(5000);
		//		publisher.submit("HOLAAAA");
		//		Thread.sleep(5000);
		//		publisher.close();
		//		}catch(Exception e) {
		//			e.printStackTrace();
		//		}
	}

}
