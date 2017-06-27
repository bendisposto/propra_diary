package diary;

import java.util.Objects;

public class Project {

	private String name;
	private String type;

	public Project(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public boolean isInternal() {
		return "internal".equals(type);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Project) {
			Project that = (Project) obj;
			return Objects.equals(name, that.name) && Objects.equals(type, that.type);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, type);
	}

}
