package com.streaming.clinica.colombia.service;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;

import com.github.sarxos.webcam.Webcam;
import com.streaming.clinica.colombia.constans.ConfigurationConst;
import com.streaming.clinica.colombia.publisher.PublisherServer;
import com.xuggle.xuggler.video.ConverterFactory;

public class WebCamServiceImpl implements WebCamServiceInterface {

	private Webcam webcam = null;

	@Override
	public void initCam() {
		System.out.println("Va a iniciar camara ...");

		Dimension size = ConfigurationConst.SIZE;
		webcam = Webcam.getDefault();
//		List<Webcam> we = Webcam.getWebcams();
//		webcam = (Webcam)we.stream().filter(x->{ return x.getName().contains("LifeCam") ; }).toArray()[0];
		webcam.setViewSize(size);
		webcam.open(true);
		System.out.println("Inicia Camara: "+webcam.getName()+" ...");
	}

	@Override
	public void initProcess() {
		System.out.println("Inicia Captura ...");

		while(true) {
			try {
				BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( image, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				baos.close();
				PublisherServer.PUBLISHER.submit(new String(Base64.encodeBase64(imageInByte), "UTF-8"));
				
				Thread.sleep(ConfigurationConst.FRAMES);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

	}

}
