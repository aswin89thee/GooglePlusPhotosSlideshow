package com.photoviewer.app;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gdata.client.photos.PicasawebService;
import com.photoviewer.test.PicasawebCommandLine;


public class SlideshowMain {
	
	private static final int NORMAL = 1;
	private static final int PASSWORD = 2;
	
	// Input stream for reading user input.
	  private static final BufferedReader IN
	      = new BufferedReader(new InputStreamReader(System.in));

	public SlideshowMain(PicasawebService service, String uname, String passwd) {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String uname, passwd;
		PicasawebService service = new PicasawebService("exampleClient");
		try {
			if (args.length == 2) {
				uname = args[0];
				passwd = args[1];
			} else if (args.length != 0) {
				println("Syntax: SlideshowMain <username> <password>");
				return;
			} else {
				uname = getString("Username",NORMAL);
				passwd = getString("Password",PASSWORD);
			}
			
			SlideshowWebClient webClient = new SlideshowWebClient(service, uname, passwd);
			webClient.start();
			
		} catch (Exception ee) {
			ee.printStackTrace();
			println("Exiting...");
		}

	}

	private static String getString(String name, int type) throws ExitException,
	IOException {
		print("Please enter ");
		print(name);
		println(":");
		System.out.flush();
		String result = "";
		if(type == PASSWORD)
		{
			result = new String(System.console().readPassword());
		}
		else {
			result = IN.readLine();
		}
		result = result.trim();
		if (result.length() == 0) {
			return "-1";
		}
		return result;
	}

	private static void print(String str) {
		System.out.print(str);
	}

	private static void println(String str) {
		System.out.println(str);
	}

	private static class ExitException extends Exception {
		// Empty, just used to exit quickly.
	}

}
