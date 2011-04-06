package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.TimerTask;

import javax.servlet.ServletContext;

public class SpecifiedTask extends TimerTask {
    private static boolean isRunning = false;
    private ServletContext context;
    public SpecifiedTask(ServletContext context){
        this.context=context;
    }
    @Override
    public void run() {
        if(!isRunning){
                isRunning=true;
                System.out.println("生成目录...........");
                readHtml();
        }
    }
    
    private void readHtml(){
    	InputStream is = null;
		try {
			is = context.getResourceAsStream("/topic/git-quick-starts.html");
		
			String str;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while( (str = br.readLine()) != null){
				System.out.println(str);
				if(str.indexOf("title")>-1)
					break;
			}
		} catch (Exception e1) {
//			e1.printStackTrace();
		}
    	
    	
    	
    }
}