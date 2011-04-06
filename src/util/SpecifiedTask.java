package util;

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
                System.out.println("定时打印...........");
                //SendMail.sendMail();//定时发送邮件
        }
    }

}