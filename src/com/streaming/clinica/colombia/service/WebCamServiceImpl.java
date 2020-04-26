package com.streaming.clinica.colombia.service;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import com.streaming.clinica.colombia.constans.ConfigurationConst;
import com.streaming.clinica.colombia.publisher.PublisherServer;
import com.xuggle.xuggler.video.ConverterFactory;

public class WebCamServiceImpl implements WebCamServiceInterface {

	//	
	static {
		Webcam.setDriver(new V4l4jDriver()); // set driver to be used
	}

	private Webcam webcam = null;
	private static WebCamServiceImpl _INSTANCE = null;

	private WebCamServiceImpl() {
		System.out.println("CREA INSTANCIA ....");
		initCam();
		initProcess();
	}

	public static WebCamServiceImpl getIntence() {
		//		if(_INSTANCE == null)

		_INSTANCE = new WebCamServiceImpl();

		return _INSTANCE;
	}


	@Override
	public void initCam() {

		System.out.println("Va a iniciar camara ...");

		Dimension size = WebcamResolution.QVGA.getSize();
		//		System.out.println("DEMIENSION : "+size);
		System.out.println("va a conseguir webcam por defautl");
		webcam = Webcam.getDefault();
		System.out.println("consigue webcam: "+webcam);

		//		List<Webcam> we = Webcam.getWebcams();
		//		webcam = (Webcam)we.stream().filter(x->{ return x.getName().contains("LifeCam") ; }).toArray()[0];
		System.out.println("setea size");
		webcam.setViewSize(size);

		System.out.println("va a iniciar camara");
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
