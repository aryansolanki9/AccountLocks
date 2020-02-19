package Utilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;
import com.ibm.broker.plugin.MbGlobalMapSessionPolicy;
import com.ibm.broker.plugin.MbXMLNSC;

public class CacheUtil {
	/**
	 * Method to get a value from Global Cache using map name and key
	 */
	
	public static void writeToCache(String mapName, String key, String value)
	{
		try
		{
			MbGlobalMap map = MbGlobalMap.getGlobalMap(mapName,new MbGlobalMapSessionPolicy(30));
			if(map.containsKey(key))
			   map.update(key, value);
			else
			   map.put(key, value);
			}
		catch(MbException mbe)
		{
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
		}
	}

	public static String readfromCache(String mapName, String key)
	{
		try
		{  
		   MbGlobalMap map = MbGlobalMap.getGlobalMap(mapName);
		   String value = (String)map.get(key);
		   return value;
		}
		catch(MbException mbe)
		{
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
			return "";
		}
	}
		   
	
	public static String getValue(String strMapName, String strKey) {
		
		String strValue = null;
		MbGlobalMap globalMap = null;
		
		try
		{
			globalMap = MbGlobalMap.getGlobalMap(strMapName);
			strValue = (String) globalMap.get(strKey);
		}
		catch(MbException mbe)
		{
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
		}
		
		return strValue;
	}
	
	public static void getValues(String strMapName,MbElement outputRoot) 
	{
		String strKey = "";
		String strValue = "";
		MbGlobalMap globalMap = null;
		int i = 0;
		try
		{
			globalMap = MbGlobalMap.getGlobalMap(strMapName);
			HashMap<String,String> map =  (HashMap<String,String>)globalMap.get(strMapName);
			Set<String> allKeys = map.keySet();
			Iterator<String> iter = allKeys.iterator();
			while(iter.hasNext())
			{
				strKey = iter.next();
				strValue = map.get(strKey);
				MbElement book1 = outputRoot.createElementAsLastChild(MbElement.TYPE_NAME, "Entry", strValue); 
				book1.createElementAsFirstChild(MbXMLNSC.ATTRIBUTE, "key", strKey); 
			}
			String [] key = new String[5];
		}
		catch(MbException mbe)
		{
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
		}
	}
	
	/**
	 * Method to add all the key-value pairs for a map in Global Cache
	 */	
	
	public static Boolean addMap(MbElement elmMap) {
		
		String strValue = null;
		String strKey = null;
		String strMapName = null;
		MbGlobalMap globalMap = null;
		HashMap<String,String> map = null;
		try
		{
			elmMap = elmMap.getFirstChild();
			strMapName = elmMap.getValueAsString();
			
			globalMap = MbGlobalMap.getGlobalMap(strMapName);
			map = (HashMap<String,String>)globalMap.get(strMapName);
			MbElement elmEntry = elmMap.getNextSibling();
			
			while (elmEntry != null) {
				
				strKey = elmEntry.getFirstChild().getValueAsString();
				strValue = elmEntry.getValueAsString();
				
				if(globalMap.containsKey(strKey)) {
					globalMap.update(strKey,strValue);
					map.put(strKey,strValue);
				} else {
					globalMap.put(strKey, strValue);
					if (map == null)
						map = new HashMap<String,String>();
					map.put(strKey, strValue);
				}
				
				elmEntry = elmEntry.getNextSibling();
			}
			globalMap.remove(strMapName);	 
				globalMap.put(strMapName, map);	
		}
		catch(MbException mbe) {
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
			return Boolean.FALSE;
		}		

		
		return Boolean.TRUE;
	}	
	
	/**
	 * Method to get a add a key-value pair to a map in Global Cache
	 */	
	
	public static Boolean addUpdateKey(String strMapName, String strKey, String strValue) {
		
		MbGlobalMap globalMap = null;
		
		try
		{
			// Get an existing Map, or create the dynamic map if it doesn't exist
			globalMap = MbGlobalMap.getGlobalMap(strMapName);
			
			// If key is not present, add the key-value pair to the map
			// If key exists, refresh the value of the key
			if(globalMap.containsKey(strKey)) {
				globalMap.update(strKey,strValue);
			} else {
				globalMap.put(strKey, strValue);
			}
		}
		catch(MbException mbe) {
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
			return Boolean.FALSE;
		}		
		
		return Boolean.TRUE;
	}	

}
