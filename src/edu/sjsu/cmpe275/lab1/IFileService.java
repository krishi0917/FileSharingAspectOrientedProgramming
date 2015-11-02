package edu.sjsu.cmpe275.lab1;

import java.io.IOException;

public interface IFileService {
	
	/**
	* Share a file with another user. The file may or may not be present in the current user's path.
	* @param userId the ID of the current user
	* @param targetUserID the ID of the user, to whom the file needs to be shared.
	* @param filePath location of the file to be shared.
	*/
void shareFile(String userId, String targetUserID, String filePath);
	/**
	* Stop sharng a file with another user. The file may or may not be present in the current user's path.
	* @param userId the ID of the current user
	* @param targetUserID the ID of the user, to whom the sharing needs to be stopped.
	* @param filePath location of the file to be shared.
	*/
void unshareFile(String userId, String targetUserID, String filePath);
	/**
	* Read the shared file or owned file and return the contents.
	* @param userId the ID of the current user
	* @param filePath the path of the file for which the user is requesting access.
	* @return Returns the contents of the file, or null if the file does not exist.
	*/
byte[] readFile(String userId, String filePath);
}
