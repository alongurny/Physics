package run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Count {

	static int count = 0;

	public static void main(String[] args) {
		try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\Alon\\git\\Physics\\Physics"))) {
			paths.forEach(path -> {
				File f = path.toFile();
				if (f.isFile() && !f.getName().startsWith("Count") && f.getName().endsWith(".java")) {
					try (BufferedReader in = new BufferedReader(new FileReader(f))) {
						while (in.readLine() != null) {
							count++;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(count);
	}
}
