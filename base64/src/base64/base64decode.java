package base64;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class base64decode {
	
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

	private static void convert() throws FileNotFoundException, IOException {

		BufferedInputStream inStream = new BufferedInputStream(
				Files.newInputStream(IN, StandardOpenOption.READ));
		BufferedOutputStream outStream = new BufferedOutputStream(
				Files.newOutputStream(OUT, StandardOpenOption.CREATE));
		byte[] tmp;

		tmp = new byte[inStream.available()];
		inStream.read(tmp);
		inStream.close();

		if (ACTION == decodeACTIONS.DECODE) {
			tmp = Base64.getDecoder().decode(tmp);
		} else {
			tmp = Base64.getEncoder().encode(tmp);
		}

		outStream.write(tmp);
		outStream.flush();
		outStream.close();

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


	private static Path OUT;
	private static Path IN;
	private static decodeACTIONS ACTION;

	private enum decodeACTIONS {
		DECODE, ENCODE
	}
			
}
