package adventofcode.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.InputStream;
import java.util.List;

import org.springframework.util.StreamUtils;


public class FileReaderUtil {

	public static String[] readRowsAsArray(String filePath) {
		return readFile(filePath).split("\n");
	}

	public static String readFile(String filePath) {
		try (InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
			return StreamUtils.copyToString(file, UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(
					"Failed to load json file \"" + filePath + "\" from src/test/resources folder", e);
		}
	}
}
