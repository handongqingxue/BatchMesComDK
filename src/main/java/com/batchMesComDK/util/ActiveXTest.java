package com.batchMesComDK.util;

import com.jacob.com.*;
import com.jacob.activeX.*;

//http://www.blogjava.net/aiaiwoo/articles/337329.html
public class ActiveXTest {
	public static void main(String[] args){
		printVersion("BatchViewHMI.BacthesList");
		//printVersion("Word.Application");
		//printVersion("Word.Application");
		//printVersion("Word.Application");
	}
	
	public static void printVersion(String name) {
		//ActiveXComponent xl = new ActiveXComponent("Word.Application");
		ActiveXComponent xl = new ActiveXComponent(name);
		Object xlo = xl.getObject();
		try{
			System.out.println("version="+xl.getProperty("Version"));
			// for (int i=0; i<100; i++)
			System.out.println("version="+Dispatch.get(xl, 1));
			// System.out.println("version="+Dispatch.get(xlo,"Version");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			xl.invoke("Quit", new Variant[] {});
		}
	}
}
