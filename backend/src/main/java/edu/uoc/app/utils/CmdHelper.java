package edu.uoc.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class CmdHelper {

    private CmdHelper() {
    }

	public static void executeCommand(String command, boolean printOut) {
		Process p = null;
		String[] cmd = { "/bin/sh", "-c", command };

		try {
			p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			System.err.println("Error on exec() method");
			e.printStackTrace();
		}

		try {
			if (printOut) {
				printInConsole(p.getInputStream(), System.out);
			}
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void printInConsole(InputStream in, OutputStream out) throws IOException {
		while (true) {
			int c = in.read();
			if (c == -1) {
				break;
			}
			out.write((char) c);
		}
	}


}
