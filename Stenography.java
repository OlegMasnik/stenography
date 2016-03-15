import java.io.*;

public class Stenography {

	final static long HEADSIZE = 120;
	static int textSize = 0;

	public static void main(String[] args) throws IOException {
		encode();
		decode();
		System.out.println("Finish");
	}

	private static void encode() throws IOException {
		System.out.println("Encrypt");
		FileInputStream in = null;
		FileInputStream msg = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream("image1.bmp");
			msg = new FileInputStream("lab4.txt");
			out = new FileOutputStream("image1X.bmp");
			int c, mb;
			byte clearBit1 = (byte) 0xFE; // 254; // 11111110

			for (int i = 1; i <= HEADSIZE; i++) out.write(in.read());

			while ((mb = msg.read()) != -1) {
				for (int bit = 7; bit >= 0; bit--) {
					c = in.read() & clearBit1;
					c = (c | ((mb >> bit) & 1));
					out.write(c);
				}
				textSize++;
			}
			for (int bit = 7; bit >= 0; bit--) {
				out.write(in.read());
			}
			while ((c = in.read()) != -1) out.write(c);
		} finally {
			if (in != null)
				in.close();
			if (msg != null)
				msg.close();
			if (out != null)
				out.close();
		}
	}

	private static void decode() throws IOException {
		System.out.println("Decrypt");
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			System.out.println(textSize);
			in = new FileInputStream("image1x.bmp");
			out = new FileOutputStream("lab4res.txt");
			for (int i = 1; i <= HEADSIZE; i++)
				in.read();

			byte[] result = new byte[textSize];
			for (int i = 0; i < textSize; i++) {
				for (int bit = 0; bit <= 7; bit++) {
					result[i] = (byte) ((result[i] << 1) | (in.read() & 1));
				}
				out.write(result[i]);
			}
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}
}
