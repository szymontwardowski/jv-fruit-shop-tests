package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.io.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterTest {
    @TempDir
    private Path tempDir;

    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void output_toFile_ok() throws IOException {
        File tempFile = tempDir.resolve("test_report.csv").toFile();
        String path = tempFile.getAbsolutePath();
        String content = "fruit,quantity" + System.lineSeparator() + "apple,10";

        fileWriter.output(content, path);

        Assertions.assertTrue(tempFile.exists(), "File should be created");
        String actualContent = Files.readString(tempFile.toPath());
        Assertions.assertEquals(content, actualContent, "Content should match");
    }

    @Test
    void output_invalidPath_notOk() {
        String invalidPath = "invalid/path/to/file.csv";
        String content = "some content";
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriter.output(content, invalidPath), "Should throw for invalid path");
    }

    @Test
    void output_nullPathOrContent_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriter.output(null, "some/path.csv"), "Should throw for null content");

        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriter.output("some content", null), "Should throw for null path");
    }
}
