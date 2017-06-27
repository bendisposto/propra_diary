package diary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Diary {

	private List<DiaryEntry> entries = new ArrayList<>();
	private List<Project> projects;
	private FileStore fileStore;

	public Diary(FetchProjects fetcher, FileStore fileStore) {
		this.fileStore = fileStore;
		projects = fetcher.fetch();
		entries = fileStore.read();
	}

	public synchronized void addEntry(Project project, String subject, String description, LocalDateTime start,
			LocalDateTime end) {
		DiaryEntry e = new DiaryEntry(project, System.getProperty("user.name"), subject, description, start, end);
		entries.add(e);
		store();
	}

	public synchronized void store() {
		fileStore.write(entries);
	}

	public List<Project> getProjects() {
		return projects;
	}

	public synchronized List<DiaryEntry> getEntries() {
		return entries;
	}

	public HashMap<Project, Long> getStatistic() {
		HashMap<Project, Long> stats = new HashMap<Project, Long>();
		for (DiaryEntry entry : entries) {
			Project project = entry.getProject();
			Long soFar = stats.get(project);
			Long duration = (soFar == null ? 0 : soFar) + entry.computeDuration();
			stats.put(project, duration);
		}
		return stats;
	}

}
