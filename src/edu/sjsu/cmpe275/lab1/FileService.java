package edu.sjsu.cmpe275.lab1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.naming.Context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FileService implements IFileService{		
	
	@Override
	public void shareFile(String userId, String targetUserID, String filePath) {
		
		String[] fileDetails = filePath.split("/");
		String fileOwner = fileDetails[2];
		String fileName = fileDetails[4];
		fileOwner = Character.toString(fileOwner.charAt(0)).toUpperCase() + fileOwner.substring(1);
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		User targetUser = (User)ctx.getBean(targetUserID);
		User sourceUser = (User)ctx.getBean(userId);
		targetUser.setFilePermission(fileOwner, fileName, 'Y');
	
	}

	@Override
	public void unshareFile(String userId, String targetUserID, String filePath) {

		String[] fileDetails = filePath.split("/");
		String fileOwner = fileDetails[2];
		String fileName = fileDetails[4];
		fileOwner = Character.toString(fileOwner.charAt(0)).toUpperCase() + fileOwner.substring(1);
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		User targetUser = (User)ctx.getBean(targetUserID);
		User sourceUser = (User)ctx.getBean(userId);
		targetUser.setFilePermission(fileOwner, fileName, 'N');
	}

	@Override
	public byte[] readFile(String userId, String filePath) {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		User user = (User)context.getBean(userId);
		String[] fileDetails = filePath.split("/");
		String fileOwner = fileDetails[2];
		String fileName = fileDetails[4];
		fileOwner = Character.toString(fileOwner.charAt(0)).toUpperCase() + fileOwner.substring(1);
		try
		    {
				Path path = Paths.get(filePath);
				byte[] data = Files.readAllBytes(path);
		        return data;
		    }catch (FileNotFoundException ex){
		    	ex.printStackTrace();
		    	return null;
		    }catch (IOException e)
		    {
		        e.printStackTrace();
		        return null;
		    }
		
	}
	
	
	
}
