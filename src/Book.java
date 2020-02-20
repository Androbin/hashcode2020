import java.io.*;
import java.util.*;

public final class Book {
	public final Integer id;
	public final Integer score;

	public Book(final Integer id, final Integer score) {
		this.id = id;
		this.score = score;
	}

	@Override
	public String toString() {
		return this.id.toString();
	}
}