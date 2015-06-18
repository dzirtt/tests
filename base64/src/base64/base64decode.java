package base64;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import base64.fileConvert;
import base64.fileConvert.decodeACTIONS;

public class base64decode {

	// parameters for work program
	private static Path OUT;
	private static Path IN;
	private static decodeACTIONS ACTION;
	private static boolean REMOVE = false;	
	
	public static void main(String[] args) {

		// parse arg and init fields
		parseArg(args);

		if (IN == null || OUT == null) 
			ShowErrorAndExit("in and out param is nedeed");
		
		if (!Files.exists(IN)) 
			ShowErrorAndExit("in file not exist");

		try {
			// if output file exist then delete
			Files.deleteIfExists(OUT);
			// read in file, decode\encode, write to out
			switch(ACTION){
			case DECODE: 
				fileConvert.decode(IN, OUT, REMOVE);
				break;
			case ENCODE:
				fileConvert.encode(IN, OUT);
				break;
			}

		} catch (IOException e) {
			ShowErrorAndExit(e.getMessage());
		}

		System.out.print("DONE");

	}

	private static void parseArg(String[] args) {
		String notRecognise = "";
		//parse params
		for (int i = 0; i < args.length; i++) {
			switch (args[i].replaceAll("^-", "")) {
			case "out":
				OUT = FileSystems.getDefault().getPath(args[++i]);
				break;
			case "in":
				IN = FileSystems.getDefault().getPath(args[++i]);
				break;
			case "d":
				ACTION = decodeACTIONS.DECODE;
				break;
			case "e":
				ACTION = decodeACTIONS.ENCODE;
				break;
			case "remove":case "r":
				REMOVE = true;
				break;
			default:
				notRecognise += "|" + args[i];
				break;
			}
		}

		if (!notRecognise.equals("")) {
			ShowErrorAndExit("not recognise parameter " + notRecognise);
		}
	}

	// show error and EXIT
	private static void ShowErrorAndExit(String st) {
		System.out.println(st);
		System.exit(-1);
	}

}
