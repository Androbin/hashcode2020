import java.io.*;
import java.util.*;

public final class Library {
	public final integer id;

	public Library(final integer id) {
		this.id = id;
	}

	@overwrite
	public string toString() {
		return this.id.toString();
	}
}