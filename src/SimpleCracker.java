

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class SimpleCracker
	{
	
		public static void main(String[] args) throws FileNotFoundException,
			NoSuchAlgorithmException, IOException {
		
		SimpleCracker sc = new SimpleCracker();

		
		try {

			
			String filename1 = "shadow-simple.txt";
			String filename2 = "common-passwords.txt";

			String[] salt = sc.readLines(filename1);
			String[] compass = sc.readLines(filename2);

			int j;
			int i=0;
			String salt1[] = new String[10];
			int k;
			for (k = 0; k < 10; k++)
			{
				salt1[k] = salt[k].substring(6, 14);
			}
			String concat = null;
			int flag=0;
			for (k = 0; k < salt1.length; k++)
			{
				
				for (i = 0; i < compass.length; i++) 
				{
					
					concat = salt1[k] + compass[i];
					 //System.out.println(concat);

					String MD5_ad1 = SimpleCracker.MD5(concat);
					//System.out.println(MD5_ad1);

					for (j = 0; j <= salt.length-1; j++) 
					{
						
						if (MD5_ad1.equalsIgnoreCase(salt[j].substring(15,47)))
						{
							System.out.println(salt[k].substring(0,5)+":"+compass[i]);
							flag++;
							break;
						}
					}

				}
			}
			if(flag==0)
			{
				System.out.println("No password Match Found");
			}

		} catch (IOException e) {
		}
	}

	public String[] readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);

		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> compass = new ArrayList<String>();
		List<String> salt = new ArrayList<String>();

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			compass.add(line);
			salt.add(line);

		}

		bufferedReader.close();

		return compass.toArray(new String[compass.size()]);

	}

	public static String toHex(byte[] data) {

		BigInteger bi = new BigInteger(1, data);
		return String.format("%0" + (data.length << 1) + "X", bi);
	}

	public static String MD5(String text) throws NoSuchAlgorithmException,UnsupportedEncodingException 
	{
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		md.update(text.getBytes("UTF-8"), 0, text.length());
		md5hash = md.digest();

		return toHex(md5hash);

	}

}
