package edu.sjsu.cmpe275.lab1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ShareTest {
	
	IFileService fileService;
	ApplicationContextProvider s;
	ApplicationContext ctx;
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("spring.xml");
		fileService =(IFileService) ctx.getBean("FileService");
		s = new ApplicationContextProvider();
		s.setApplicationContext(ctx);	
	}
	//1. Bob cannot read Alice's File /home/alice/shared/alicefile.txt
	@Test(expected = UnauthorizedException.class)
	public void test1() {
		System.out.println("Test#1: Bob Cannot Read Alice's File");
		System.out.println();
		ctx = ApplicationContextProvider.getApplicationContext();
		User bob = (User) ctx.getBean("Bob");
		fileService.readFile("Bob", "/home/alice/shared/alicefile.txt");
		assertEquals('N', bob.checkPermission("Alice", "alicefile.txt"));
	}
	
	//2. Alice shares her file /home/alice/shared/alicefile.txt with Bob and Bob can read the file
	@Test
	public void test2() {
		System.out.println("Test#2: Alice shares her file with Bob and Bob can read the file");
		System.out.println();
		ctx = ApplicationContextProvider.getApplicationContext();
		User bob = (User) ctx.getBean("Bob");
		fileService.shareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.readFile("Bob", "/home/alice/shared/alicefile.txt");
		assertEquals('Y', bob.checkPermission("Alice", "alicefile.txt"));
	}
	
	//3. Alice shares her file with Bob and Bob can read Alice's file. 
	//Bob then share the file with Carl and Carl can also read Alice's file
	
	@Test
	public void test3(){
		System.out.println("Test#3: Alice shares her file with Bob and Bob can read Alice's file.\n" +  
				"Bob then share the file with Carl and Carl can also read Alice's file");
		System.out.println();
		ctx = ApplicationContextProvider.getApplicationContext();
		User bob = (User) ctx.getBean("Bob");
		User carl = (User) ctx.getBean("Carl");
		fileService.shareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.readFile("Bob", "/home/alice/shared/alicefile.txt");
		fileService.shareFile("Bob", "Carl", "/home/alice/shared/alicefile.txt");
		fileService.readFile("Carl", "/home/alice/shared/alicefile.txt");
		assertEquals('Y', bob.checkPermission("Alice", "alicefile.txt"));
		assertEquals('Y', carl.checkPermission("Alice", "alicefile.txt"));
		
	}
	
	//4. Alice shares her file with Bob and Bob shares carl's file with Alice and gets Exception
	@Test(expected = UnauthorizedException.class)
	public void test4(){
		System.out.println("Test#4: Alice shares her file with Bob and Bob shares carl's file with Alice and gets Exception");
		System.out.println();
		
		fileService.shareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.shareFile("Bob", "Alice", "/home/carl/shared/carlfile.txt");
		ctx = ApplicationContextProvider.getApplicationContext();
		User alice = (User) ctx.getBean("Alice");
		assertEquals('N', alice.checkPermission("Carl", "carlfile.txt"));
		
	}
	
	//5. Alice shares her file with Bob, Bob shares Alice's file with Carl.
	//Alice unshares her file with Carl and carl cannot read Alice File
	
	@Test(expected = UnauthorizedException.class)
	public void test5(){
		System.out.println("Test#5: Alice shares her file with Bob, Bob shares Alice's file with Carl. and\n" +  
				"Alice unshares her file with Carl and carl cannot read Alice File");
		System.out.println();
		ctx = ApplicationContextProvider.getApplicationContext();
		//User bob = (User) ctx.getBean("Bob");
		User carl = (User) ctx.getBean("Carl");
		fileService.shareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.shareFile("Bob", "Carl", "/home/alice/shared/alicefile.txt");
		fileService.unshareFile("Alice", "Carl", "/home/alice/shared/alicefile.txt");
		fileService.readFile("Carl", "/home/alice/shared/alicefile.txt");
		//assertEquals('Y', bob.checkPermission("Alice", "alicefile.txt"));
		assertEquals('N', carl.checkPermission("Alice", "alicefile.txt"));
		
	}
	
	//6. Alice shares her file with Bob, Alice shares her file with carl.
	//Carl shares Alice's file with Bob, Alice unshares her file with Bob and Bob can not read Alice's file
	
	@Test(expected = UnauthorizedException.class)
	public void test6(){
		System.out.println("Test#6: Alice shares her file with Bob, Alice shares her file with carl.\n" +  
				" Carl shares Alice's file with Bob, Alice unshares her file with Bob \n Bob can not read Alice's file");
		System.out.println();
		ctx = ApplicationContextProvider.getApplicationContext();
		User bob = (User) ctx.getBean("Bob");
		//User carl = (User) ctx.getBean("Carl");
		fileService.shareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.shareFile("Alice", "Carl", "/home/alice/shared/alicefile.txt");
		fileService.shareFile("Carl", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.unshareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.readFile("Bob", "/home/alice/shared/alicefile.txt");
		//assertEquals('Y', bob.checkPermission("Alice", "alicefile.txt"));
		assertEquals('N', bob.checkPermission("Alice", "alicefile.txt"));
		
	}
	
	//7. Alice shares her file with Bob, Bob shares Alice's file with Carl.
	//Alice unshares her file with Bob. Bob shares Alice's file with Carl and gets an Exception
	//Carl still has access to Alice's file
	
	@Test(expected = UnauthorizedException.class)
	public void test7(){
		System.out.println("Test#7: Alice shares her file with Bob, Bob shares Alice's file with Carl. \n" +  
				" Alice unshares her file with Bob. Bob shares Alice's file with Carl and gets an Exception \n" +
				" Carl still has access to Alice's file");
		System.out.println();
		ctx = ApplicationContextProvider.getApplicationContext();
		//User bob = (User) ctx.getBean("Bob");
		User carl = (User) ctx.getBean("Carl");
		fileService.shareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.shareFile("Bob", "Carl", "/home/alice/shared/alicefile.txt");
		fileService.unshareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.shareFile("Bob", "Carl", "/home/alice/shared/alicefile.txt");
		fileService.readFile("Carl", "/home/alice/shared/alicefile.txt");
		//assertEquals('Y', bob.checkPermission("Alice", "alicefile.txt"));
		assertEquals('Y', carl.checkPermission("Alice", "alicefile.txt"));
		
	}
	
	//8. Alice shares her file1 with Bob, Bob tries to access Alice file2 and gets an Exception
	
	@Test(expected = UnauthorizedException.class)
	public void test8(){
		System.out.println("Test#8: Alice shares her file1 with Bob, Bob tries to access Alice file2 and gets an Exception");
		System.out.println();
		ctx = ApplicationContextProvider.getApplicationContext();
		User bob = (User) ctx.getBean("Bob");
		fileService.shareFile("Alice", "Bob", "/home/alice/shared/alicefile.txt");
		fileService.readFile("Bob", "/home/alice/shared/alicefile2.txt");
		assertEquals('N', bob.checkPermission("Alice", "alicefile2.txt"));
		
	}
	
	
	
	
	
	

}
