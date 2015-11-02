package edu.sjsu.cmpe275.lab1;

import java.util.HashMap;
import java.util.Map;

public class User {
	Map<String, Map<String, Character>> userFileMap;
	String userId;
	
	public User(){
		this.userFileMap = new HashMap<String, Map<String,Character>>();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setFilePermission(String provider, String fileName, char perm)
	{	
		if(!this.userFileMap.containsKey(provider)){
			this.userFileMap.put(provider, new HashMap<String, Character>());
		}
		
			this.userFileMap.get(provider).put(fileName, perm);
	}
	
	public char checkPermission(String provider, String fileName){
		char perm = 'N';
		if(this.userFileMap.get(provider) != null && this.userFileMap.get(provider).get(fileName) !=null){
			return this.userFileMap.get(provider).get(fileName);
		}
		if(this.userId.equalsIgnoreCase(provider))
			perm='Y';
		return perm;	
	}
	
	
}
