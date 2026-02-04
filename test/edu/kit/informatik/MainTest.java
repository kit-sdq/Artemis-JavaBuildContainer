package edu.kit.informatik;

import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import edu.kit.kastel.sdq.artemis.api.EnableTestingFramework;
import edu.kit.kastel.sdq.artemis.api.generators.DependencyTestGenerator;
import edu.kit.kastel.sdq.artemis.api.generators.PipelineTestGenerator;
import edu.kit.kastel.sdq.artemis.api.generators.ProtocolTestGenerator;
import edu.kit.kastel.sdq.artemis.api.generators.TestGenerator;

@EnableTestingFramework
public class MainTest {
	@TestFactory
	public Stream<DynamicTest> generateTests() throws Exception {
		TestGenerator tests = new PipelineTestGenerator().addStage(new DependencyTestGenerator("Dependencies").addWhitelist("java.lang.invoke")
				.addWhitelist(Matchers.matchesRegex("java\\.lang\\.[A-Z].*")).addWhitelist(Matchers.matchesRegex("java\\.io\\.[A-Z].*"))
				.addWhitelist(Matchers.matchesRegex("java\\.util\\.[A-Z].*")).addWhitelist(Matchers.matchesRegex("java\\.util\\.regex\\.[A-Z].*"))
				.addWhitelist(Matchers.matchesRegex("java\\.util\\.function\\.[A-Z].*")).addWhitelist(Matchers.matchesRegex("java\\.util\\.stream\\.[A-Z].*"))
				.addWhitelist("java.util.Objects") // Needed for compiler handling inline constants
				.addBlacklist("")).addStage(new ProtocolTestGenerator("test/protocols", false));

		return tests.get();
	}
}
