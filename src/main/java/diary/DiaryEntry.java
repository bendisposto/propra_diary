package diary;

import java.time.Duration;
import java.time.LocalDateTime;

public class DiaryEntry {

	private long id;
	private final String username;
	private final String subject;
	private final String description;
	private final LocalDateTime start;
	private final LocalDateTime end;
	private final Project project;

	public DiaryEntry(Project project, String username, String subject, String description, LocalDateTime start,
			LocalDateTime end) {
		this.project = project;
		this.username = username;
		this.subject = subject;
		this.description = description;
		this.start = start;
		this.end = end;
	}

	public Project getProject() {
		return project;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long computeDuration() {
		Duration duration = Duration.between(start, end);
		return duration.getSeconds() / 60;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getSubject() {
		return subject;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

}
