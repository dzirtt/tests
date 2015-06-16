package base64;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class base64decode {

	
	//parameters for work program
	private static Path OUT;
	private static Path IN;
	private static decodeACTIONS ACTION;
	private static boolean REMOVE = false;

	private enum decodeACTIONS {
		DECODE, ENCODE
	};

	private static final char[] base64Chars = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };

	

	public static void main(String[] args) {

		// parse arg and init fields
		parseArg(args);

		if (IN == null || OUT == null) {
			ShowErrorAndExit("in and out param is nedeed");
		}

		if (!Files.exists(IN)) {
			ShowErrorAndExit("in file not exist");
		}

		try {
			// if output file exist then delete
			Files.deleteIfExists(OUT);
			// read in file, decode\encode, write to out
			convert();
		} catch (IOException e) {
			ShowErrorAndExit(e.getMessage());
		}

	}

	private static void convert() throws FileNotFoundException, IOException,
			IllegalArgumentException {

		BufferedInputStream inStream = new BufferedInputStream(
				Files.newInputStream(IN, StandardOpenOption.READ));
		BufferedOutputStream outStream = new BufferedOutputStream(
				Files.newOutputStream(OUT, StandardOpenOption.CREATE));
		byte[] tmp;

		tmp = new byte[inStream.available()];
		inStream.read(tmp);
		inStream.close();

		if (ACTION == decodeACTIONS.DECODE) {
			byte[] nonBase = searchNonBase64Chars(tmp);
			if (nonBase.length != 0) {
				if (REMOVE) {
					tmp = removeNonBase64Chars(tmp, nonBase);
				} else {
					ShowErrorAndExit("contains non Base64 characters\n Base64 chars is:"
							+ Arrays.toString(base64Chars));
				}
			}
			tmp = Base64.getDecoder().decode(tmp);
		} else {
			tmp = Base64.getEncoder().encode(tmp);
		}

		outStream.write(tmp);
		outStream.flush();
		outStream.close();

	}

	private static byte[] removeNonBase64Chars(byte[] from, byte[] what) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		boolean skip = false;

		for (byte b_in : from) {
			for (byte b2_ex : what) {
				if (b_in == b2_ex) {
					skip = true;
					break;
				}
			}

			if (!skip) 
				out.write(b_in);
			
			skip = false;
		}

		return out.toByteArray();
	}

	private static byte[] searchNonBase64Chars(byte[] arr) {
		ArrayList<Byte> base = new ArrayList<>();

		for (char c : base64Chars) {
			base.add((byte) c);
		}

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();

		// collect all non base54 chars
		for (byte b : arr) {
			if (!base.contains(b)) {
				tmp.write(b);
			}
		}

		return tmp.toByteArray();

	}

	/**
	 * @param args
	 *            cmd parameters
	 * @param params
	 *            array with parse params
	 */
	private static void parseArg(String[] args) {
		String notRecognise = "";
		// parse params
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
			case "remove":
			case "r":
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
