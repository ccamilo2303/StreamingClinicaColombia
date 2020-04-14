package com.streaming.clinica.colombia;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import javax.imageio.ImageIO;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.tomcat.util.codec.binary.Base64;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.xuggler.video.ConverterFactory;

@ServerEndpoint("/endpoint")
public class MyWebSocket {

	//    private static PushTimeService pst;
//	public static String encodeToString(BufferedImage image, String type) {
//        String imageString = null;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
// 
//        try {
//            ImageIO.write(image, type, bos);
//            byte[] imageBytes = bos.toByteArray();
// 
//            BASE64Encoder encoder = new BASE64Encoder();
//            imageString = encoder.encode(imageBytes);
// 
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imageString;
//    }
	
	private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }
	
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("onOpen::" + session.getId());

		try {
			 
//			Dimension size = WebcamResolution.QVGA.getSize();
			Dimension size = WebcamResolution.HD.getSize();
			//
			//        			writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
			List<Webcam> we = Webcam.getWebcams();


			Webcam webcam = Webcam.getDefault();
//			        			Webcam webcam = (Webcam)we.stream().filter(x->{ return x.getName().contains("LifeCam") ; }).toArray()[0];
			webcam.setViewSize(size);
			webcam.open(true);

			long start = System.currentTimeMillis();

			for (int i = 0; i < 150; i--) {

				System.out.println("Capture frame " + i);

				BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
				System.out.println(image);
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		        ImageIO.write(image, "png", baos);
		        
		        
//				ByteArrayOutputStream os = new ByteArrayOutputStream();
//				OutputStream b64 = new OutputStream(os);
//				ImageIO.write(image, "png", b64);
//				String result = os.toString("UTF-8");
				
				File outputfile = new File("F:\\pruebasWebCam\\temporal\\image"+i+".jpg");
				ImageIO.write(image, "jpg", outputfile);
				String v = encodeFileToBase64Binary(outputfile);
				session.getBasicRemote().sendText(v);
				//        				IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);
				//
				//        				IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
				//        				frame.setKeyFrame(i == 0);
				//        				frame.setQuality(0);
				//
				//        				writer.encodeVideo(0, frame);

				// 10 FPS
				Thread.sleep(16);
			}

//			for(int x = 0 ; x < 100 ; x ++) {
//
//				session.getBasicRemote().sendText("Hello Client " + session.getId() + "! "+x);
//				System.out.println("Envía");
//				Thread.sleep(33);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@OnClose
	public void onClose(Session session) {
		System.out.println("onClose::" +  session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("onMessage::From=" + session.getId() + " Message=" + message);

		try {
			session.getBasicRemote().sendText("Hello Client " + session.getId() + "!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("onError::" + t.getMessage());
	}
}