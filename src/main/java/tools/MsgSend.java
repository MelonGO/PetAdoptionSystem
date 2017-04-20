package tools;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import redis.clients.jedis.Jedis;

public class MsgSend {
	
	Jedis js;
	
	public MsgSend()
	{
		js=new Jedis("localhost",6379);
	}
	
	public Boolean hasMsg(String username)
	{
		boolean hasMsg=js.exists("msg_"+username);
		/*if(hasMsg){
			System.out.println(hasMsg);
			System.out.println("msg_"+username);
		}*/
		return hasMsg;
	}
	
	
	
	public void sendMsg(String username,String msgContent)
	{
		Date cdate=new Date();
		DateFormat  df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		String date = df.format(cdate);
		
		
		String key="msg_"+username;
		String value=date+"_"+msgContent;
		
		System.out.println(key);
		System.out.println(value);
		js.rpush(key, value);
		
		
	}

}
