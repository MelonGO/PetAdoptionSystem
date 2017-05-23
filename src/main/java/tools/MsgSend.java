package tools;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class MsgSend {
	
	Jedis js;
	
	public MsgSend()
	{
		js=new Jedis("localhost",6379);
	}
	
	public int hasMsg(String username)
	{ 		
		int result=new Long(js.scard(username)).intValue();
		return result;
	}
	
	public Set<String> getMsgByTargeUsername(String targetUsername){
		Set<String> s = js.smembers(targetUsername);
		/*for (String str : s) {  
		      String[] splits=str.split("_");
		      String date=splits[0];
		      String msgContent=splits[1];
		      String username=splits[2];
		      String readed=splits[3];
		      String type=splits[4];
		} */
		return s;
	}
	
	public void delMsgByTargeUsername(String targetUsername){
		Set<String> s = js.smembers(targetUsername);
		for (String str : s) {  
			js.srem(targetUsername,str);  	      
		}
	}
	
	
	
	public void sendMsg(String username,String msgContent,String targetUsername,String t)
	{
		Date cdate=new Date();
		DateFormat  df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String date = df.format(cdate);
		String readed="0";
		String type=t;		
		String key=targetUsername;	
		String value=date+"_"+msgContent+"_"+username+"_"+readed+"_"+type;
		Set<String> s = js.smembers(targetUsername);
		s.add(value);
		js.sadd(key,value);  	
	}

}
