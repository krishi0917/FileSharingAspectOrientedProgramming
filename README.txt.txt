A text file sharing service is already implemented. Everybody in the system has a home folder. One can share their files in their home folder with others.
package edu.sjsu.cmpe275.lab1;
public interface IFileService { /*
•	Share a file with another user. The file may or may not be present in the current user's path.
•	@paramuserId the ID of the current user
•	@paramtargetUserID the ID of the user, to whom the file needs to be shared.
•	@paramfilePath location of the file to be shared. / voidshareFile(String userId, String targetUserID, String filePath); /
•	Stop sharng a file with another user. The file may or may not be present in the current user's path.
•	@paramuserId the ID of the current user
•	@paramtargetUserID the ID of the user, to whom the sharing needs to be stopped.
•	@paramfilePath location of the file to be shared. / voidunshareFile(String userId, String targetUserID, String filePath); /
•	Read the shared file or owned file and return the contents.
•	@paramuserId the ID of the current user
•	@paramfilePath the path of the file for which the user is requesting access.
•	@return Returns the contents of the file, or null if the file does not exist. */ byte[] readFile(string userId, String filePath); }
The existing implementation of the service forgot to enforce access control; i.e., the shareFile, unshareFile method does not check whether the current user owns the file in the first place, and readFile does not to check that either. Your task is to use AOP to enforce the following access control, as well as adding logging.
The location of the files is in the following pattern /home/{userid}/shared/{filename}
Both user IDs and file names are case sensitive and alphanumeric, with the exception that underscores are also allowed.
One can share his files with anybody.
One can only read files that are shared with him, or his own files. In any other case, anUnauthorizedException is thrown.
If Alice shares her file with Bob, Bob can further share Alice’s file with Carl.
One can only unsharehis own file. When unsharing a file with someone that the file is not shared by any means, the operation is silently ignored.
Both sharing and unsharing of Alice’s files with Alice have no effect; i.e. Alice always has access to her files.
Log a message before each invocation of the above three methods, with messages exactly as the following, one message per line, except you need to replace the user IDs with the right ones. All logs go to the console through System.out.print*.
Alice reads the file {filePath} Alice shares the file {filePath} with Carl Alice unshares the file {filePath} with Carl
To help measure the correctness of your service, create at least the following JUnit test cases:
testA: Bob cannot read Alice’s file /home/Alice/shared/alicetext1.txt.
testB: Alice shares her file /home/Alice/shared/alicetext1.txt with Bob, and Bob can read Alice’s file.
testC: Alice shares her file /home/Alice/shared/alicetext1.txt with Bob, and Bob can read Alice’s file, and Bob shares Alice’s file with Carl, and Carl can read Alice’s file.
testD: Alice shares her file with Bob; Bob shares Carl’s file with Alice and gets an exception.
testE: Alice shares her file with Bob, Bob shares Alice’s file with Carl, Alice unshares her file with Carl, and Carl cannot read Alice’s file.
testF: Alice shares her file with Bob, Alice shared her file with Carl, Carl shares Alice’s file with Bob, Alice unshares her file with Bob, and Bob cannot read Alice’s file.
testG:Alice shares her file with Bob, Bob shares Alice’s file with Carl, and Alice unshares her file with Bob. Bob shares Alice’s file with Carl with again, and gets an exception. Carl still has access to Alice’s file.
testH:Alice shares her file1 with Bob, Bob tries to access Alice’s file2 and gets an exception.
