package edu.sjsu.cmpe275.lab1;

import java.io.IOException;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		IFileService fileService =(IFileService) ctx.getBean("FileService");
		ApplicationContextProvider s = new ApplicationContextProvider();
		s.setApplicationContext(ctx);
		fileService.shareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		System.out.println(fileService.readFile("Bob", "/home/alice/shared/alicefile.txt"));
		fileService.shareFile("Bob", "Carl", "/home/alice/shared/alicefile.txt");
		System.out.println(fileService.readFile("Carl", "/home/alice/shared/alicefile.txt"));

	}

}
