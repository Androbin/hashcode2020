import java.io.*;
import java.util.*;

public final class Main {
	private Main() {
	}

	@SuppressWarnings("resource")
	public static void main(final String[] args) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
		final BufferedWriter writer = new BufferedWriter(new FileWriter(new File(args[1])));
		final Input input = Parser.parse(reader.lines());
		final List<Output> output = Solver.solveLibScoreBySignupAndShipAmount(input.libraries, input.days);
		System.out.println("score: " + Solver.score(output, input.days));
		System.out.println("sane: " + SolverSanity.sanity(input, output));
		writer.write(Parser.toOutput(output));
		writer.flush();
		writer.close();
	}
}