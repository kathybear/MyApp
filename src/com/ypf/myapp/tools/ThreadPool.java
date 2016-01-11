package com.ypf.myapp.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool 
{
	    
	    static class SingletonHolder 
	    {     
	    	static ExecutorService threadPool = Executors.newCachedThreadPool(); 
	    }     
	    
	    public static ExecutorService getInstance() 
	    {     
	        return SingletonHolder.threadPool;     
	    }     
  

}
