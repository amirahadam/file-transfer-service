package com.cognixia.rabbitmq.receiver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import com.cognixia.model.File;
import com.cognixia.service.FileService;

@Component
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
public class RabbitMQReceiver {
	private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());

	@Autowired
	private FileService fileService;

	
	@RabbitHandler
//	@Scheduled(fixedRate = 3600000)
	public void receiver(File file) throws Exception {
		logger.info("File listener invoked - Consuming Message with File : " + file);
//		files = fileService.saveFile(file);
		fileService.toIntranet(file);
		
		// write to recon file
		try {
			java.io.File myObj = new java.io.File("recon.csv");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
				FileWriter myWriter = new FileWriter("recon.csv", true);
				myWriter.write(file.getFileId());
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			} else {
				System.out.println("File already exists.");
				FileWriter myWriter = new FileWriter("recon.csv", true);
				myWriter.write("\r\n"+file.getFileId());
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		//send to recon microservice
		
	}
}