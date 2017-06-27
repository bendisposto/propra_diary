package diary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class FetchProjectsFromServer implements FetchProjects {

	private static final String PROJECT_URL = "https://www3.hhu.de/stups/propra/ss17/projects.json";

	@Override
	public List<Project> fetch() {
		String body;
		try {
			body = Unirest.get(PROJECT_URL).asString().getBody();
		} catch (UnirestException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		Gson gson = new Gson();
		Project[] projects = gson.fromJson(body, Project[].class);
		return Arrays.asList(projects);
	}

}
