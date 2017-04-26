package tools;

import java.io.IOException;

public class RunRedis {
		public RunRedis(){
			String path = "src/main/resources/redis/redis-server.exe";
			try {
			   Runtime runtime = Runtime.getRuntime();
			   Process process = runtime.exec(path);
			} catch (IOException e) {
			   e.printStackTrace();
			}
		}

}
