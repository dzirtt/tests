package base64.Impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import base64.convertManager;
import base64.Impl.fileConvert.decodeACTIONS;

public class convertManagerImpl implements convertManager {

	private Path OUT;
	private Path IN;
	private decodeACTIONS ACTION;
	private boolean REMOVE = false;	
	
	private void parseArg(String[] args) throws IllegalArgumentException {
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
			throw new IllegalArgumentException("not recognise parameter " + notRecognise);
		}
	}

	@Override
	public int convert(String[] arg) throws IllegalArgumentException, IOException {

		parseArg(arg);

		if (IN == null || OUT == null)
			throw new IllegalArgumentException("in and out param is nedeed");
		
		if (!Files.exists(IN)) 
			throw new FileNotFoundException("IN file not exist");

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
		
		return 0;
	}

}
