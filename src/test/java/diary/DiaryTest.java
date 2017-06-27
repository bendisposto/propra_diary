package diary;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class DiaryTest {

	@Test
	public void testProjects() {
		FileStore fileStore = mock(FileStore.class);
		when(fileStore.read()).thenReturn(new ArrayList<DiaryEntry>());
		FetchProjects fetchProjects = () -> Arrays.asList(new Project("a", "internal"), new Project("b", "customer"));

		Diary d = new Diary(fetchProjects, fileStore);
		assertEquals(2, d.getProjects().size());
		assertEquals("a", d.getProjects().get(0).getName());
		assertEquals(true, d.getProjects().get(0).isInternal());
		assertEquals("b", d.getProjects().get(1).getName());
		assertEquals(false, d.getProjects().get(1).isInternal());
	}

	@Test
	public void addingEntry() {
		FetchProjects fetchProjects = () -> Arrays.asList(new Project("leTest", "internal"));
		FileStore fileStore = mock(FileStore.class);
		when(fileStore.read()).thenReturn(new ArrayList<DiaryEntry>());
		Diary d = new Diary(fetchProjects, fileStore);
		d.addEntry(d.getProjects().get(0), "hallo", "ein test!", LocalDateTime.of(2017, 06, 27, 11, 29),
				LocalDateTime.of(2017, 06, 27, 11, 35));
		d.addEntry(d.getProjects().get(0), "aha!", "ein zweiter Eintrag!", LocalDateTime.of(2017, 06, 27, 11, 36),
				LocalDateTime.of(2017, 06, 27, 11, 39));
		assertEquals(2, d.getEntries().size());
		assertEquals(6, d.getEntries().get(0).computeDuration());
		assertEquals(3, d.getEntries().get(1).computeDuration());
		assertEquals("ein test!", d.getEntries().get(0).getDescription());
	}

	@Test
	public void addingEntryExisting() {
		FetchProjects fetchProjects = () -> Arrays.asList(new Project("leTest", "internal"));
		FileStore fileStore = mock(FileStore.class);
		ArrayList<DiaryEntry> preExisting = new ArrayList<>();
		preExisting.add(new DiaryEntry(fetchProjects.fetch().get(0),
				"mopp",
				"ein Eintrag",
				"vom fiesen mopp",
				LocalDateTime.of(2017, 06, 27, 12, 00),
				LocalDateTime.of(2017, 06, 27, 12, 35)));
		when(fileStore.read()).thenReturn(preExisting);
		Diary d = new Diary(fetchProjects, fileStore);
		d.addEntry(d.getProjects().get(0), "hallo", "ein test!", LocalDateTime.of(2017, 06, 27, 11, 29),
				LocalDateTime.of(2017, 06, 27, 11, 35));
		assertEquals(2, d.getEntries().size());

	}

}
