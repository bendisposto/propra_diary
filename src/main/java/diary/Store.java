package diary;

import java.util.List;

public interface Store {

	void write(List<DiaryEntry> entries);

	List<DiaryEntry> read();

}