package util;


import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SysContextListener implements ServletContextListener{
    private Timer timer=null;
    
    //在这里初始化监听器,在tomcat启动的时候监听器启动,可以在这里实现定时器功能
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("监听器已经初始化!");
        timer=new Timer();
        event.getServletContext().log("Tomcat定时器已经启动.....");
        //调用exportHistoryBean,0表示任务无延迟,5*1000表示每隔5秒执行任务,60*60*1000表示一个小时
        //timer.schedule(new SpecifiedTask(), 0, 5*1000);
        //timer.schedule(new Task(), 0, 10*1000);
        event.getServletContext().log("任务已经添加.....");
    }
    public void contextDestroyed(ServletContextEvent event) {
        //在这里关闭监听器,同时关闭定时器
        timer.cancel();
        event.getServletContext().log("定时器关闭.....");
    }
}