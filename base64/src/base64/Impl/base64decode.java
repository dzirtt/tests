package base64.Impl;

import base64.convertManager;

public class base64decode {

	
	public static void main(String[] args) {
		//status of program work
		int status = -1;
		
		convertManager convertmanager = new convertManagerImpl();
		
		try {
			status = convertmanager.convert(args);
		} catch (Exception e) {
			ShowMsg(e.getMessage());
			status = -1;
		}

		if(status == 0)
			ShowMsg("DONE");
		
		System.exit(status);

	}

	// show error and EXIT
	private static void ShowMsg(String st) {
		System.out.println(st);
	}

}
