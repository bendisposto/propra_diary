package diary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Diary {

	private List<DiaryEntry> entries = new ArrayList<>();
	private List<Project> projects;
	private final Store store;

	/**
	 * FetchProject is an object that retrieves a list of Projects (typically
	 * from the internet). Store is an object that reads and writes the entries
	 * to a storage (typically a file).
	 */
	public Diary(FetchProjects fetcher, Store store) {
		projects = fetcher.fetch();
		this.store = store;
		entries = store.read();
	}

	/**
	 * Creates a fresh entry and stores it in the storage
	 */
	public synchronized void addEntry(Project project, String subject, String description, LocalDateTime start,
			LocalDateTime end) {
		DiaryEntry e = new DiaryEntry(project, System.getProperty("user.name"), subject, description, start, end);
		entries.add(e);
		store();
	}

	/**
	 * Saves the entries into a storage (typically a file). Will be called
	 * automatically by addEntry. Use this method to store the entries after one
	 * entry has been modified in the edit view
	 */
	public synchronized void store() {
		store.write(entries);
	}

	/**
	 * Returns a list of all projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * Returns a list of all entries
	 */
	public synchronized List<DiaryEntry> getEntries() {
		return entries;
	}

	/**
	 * Computes the data required for the statistics view. Returns a map where
	 * the projects are the keys and the accumulated duration is the value
	 */
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
