package diary;

import static java.nio.file.StandardOpenOption.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

public class FileStore {

	private static final Path PATH = Paths.get(System.getProperty("user.home"), "diary.json");

	public void write(List<DiaryEntry> entries) {
		Gson gson = new Gson();
		String json = gson.toJson(entries);
		try {
			Files.write(PATH, json.getBytes(), TRUNCATE_EXISTING, CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<DiaryEntry> read() {
		String json = "";
		try {
			json = new String(Files.readAllBytes(PATH));
		} catch (IOException e) {
			return Collections.emptyList();
		}
		Gson gson = new Gson();
		DiaryEntry[] entries = gson.fromJson(json, DiaryEntry[].class);

		return new ArrayList<>(Arrays.asList(entries));
	}

}
