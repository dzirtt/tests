package base64;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;


public class convert {
	
	public enum decodeACTIONS {
		DECODE, ENCODE
	};
	private static final char[] base64Chars = { 'A', 'B', 'C', 'D', 'E', 'F',
	'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
	'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
	'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
	't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
	'6', '7', '8', '9', '+', '/' };

	public static void convert(Path IN, Path OUT, decodeACTIONS ACTION, boolean REMOVE) throws FileNotFoundException, IOException, IllegalArgumentException {

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
					throw new IllegalArgumentException("contains non Base64 characters\n Base64 chars is:"
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

	public static void decode(Path IN, Path OUT, boolean REMOVE) throws FileNotFoundException, IllegalArgumentException, IOException{
		convert(IN,OUT,decodeACTIONS.DECODE, REMOVE);
		
	}
	
	public static void encode(Path IN, Path OUT) throws FileNotFoundException, IllegalArgumentException, IOException{
		convert(IN,OUT,decodeACTIONS.ENCODE,false);
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

}
