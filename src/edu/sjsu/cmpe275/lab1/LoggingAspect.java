package edu.sjsu.cmpe275.lab1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;

@Aspect
public class LoggingAspect {
	
	/**
	 * 
	 * Aspect for sharing the file
	 */
	@Around("execution(public void IFileService.shareFile(..))")
	public void checkSharing(ProceedingJoinPoint jp){

		boolean valid = false;
		String source = jp.getArgs()[0].toString();
		String target = jp.getArgs()[1].toString();
		String filePath = jp.getArgs()[2].toString();
		String[] fileDetails = filePath.split("/");
		String fileOwner = fileDetails[2];
		String fileName = fileDetails[4];
		
		fileOwner = Character.toString(fileOwner.charAt(0)).toUpperCase() + fileOwner.substring(1);
		
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		User targetUser = (User)ctx.getBean(target);
		User sourceUser = (User)ctx.getBean(source);
	
		if(fileOwner.equalsIgnoreCase(source)){
			valid = true;
		}
		else{
			char perm = sourceUser.checkPermission(fileOwner, fileName);
			if(perm == 'Y')
				valid = true;
		}
		
		if(valid){
			System.out.println(source +" shares the file " + filePath +  " with " + target);
			try {
				jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		else{
			String ex = source +" cannot share the file " + filePath +  " with " + target;
			throw new UnauthorizedException(ex);
		}
		return;
		
	}
	
	/**
	 * Aspect for unsharing file
	 * 
	 */
	@Around("execution(public void IFileService.unshareFile(..))")
	public void checkUnSharing(ProceedingJoinPoint jp){
		boolean valid = false;
		String source = jp.getArgs()[0].toString();
		String target = jp.getArgs()[1].toString();
		String filePath = jp.getArgs()[2].toString();
		String[] fileDetails = filePath.split("/");
		String fileOwner = fileDetails[2];
		String fileName = fileDetails[4];
		
		fileOwner = Character.toString(fileOwner.charAt(0)).toUpperCase() + fileOwner.substring(1);
		
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		User targetUser = (User)ctx.getBean(target);
		User sourceUser = (User)ctx.getBean(source);
		
		if(fileOwner.equalsIgnoreCase(source)){
			valid = true;
		}
		else{
			char perm = sourceUser.checkPermission(fileOwner, fileName);
			if(perm == 'Y')
				valid = true;
		}
		
		if(valid){
			try {
				System.out.println(source +" unshares the file " + filePath +  " with " + target);
				jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		else{
			String ex = source +" cannot unshare the file " + filePath +  " with " + target;
			throw new UnauthorizedException(ex);
		}
		return;
		
	}
	
	/**
	 * 
	 * Aspect for reading the file
	 */
	
	@Around("execution(public byte[] IFileService.readFile(..))")
	public void checkReadPerm(ProceedingJoinPoint jp){
		String userId = jp.getArgs()[0].toString();
		String filePath = jp.getArgs()[1].toString();
		String[] fileDetails = filePath.split("/");
		String fileOwner = fileDetails[2];
		String fileName = fileDetails[4];
		fileOwner = Character.toString(fileOwner.charAt(0)).toUpperCase() + fileOwner.substring(1);
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		User user = (User)context.getBean(userId);

		if(user.checkPermission(fileOwner, fileName) == 'Y'){
			try {
				System.out.println(userId +" reads the file " + filePath);
				jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		else{
			String ex = userId +" cannot read the file " + filePath;
			throw new UnauthorizedException(ex);
			
		}
		return;
	}	
}
