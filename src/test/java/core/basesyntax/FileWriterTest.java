package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.io.FileWriterImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @TempDir
    Path tempDir;

    @Test
    void output_toFile_ok() throws IOException {
        File tempFile = tempDir.resolve("test_report.csv").toFile();
        String path = tempFile.getAbsolutePath();

        String content = "fruit,quantity" + System.lineSeparator() + "apple,10";

        fileWriter.output(content, path);

        Assertions.assertTrue(tempFile.exists(), "Plik powinien zostać utworzony");
        String actualContent = Files.readString(tempFile.toPath()).trim();
        Assertions.assertEquals(content, actualContent);
    }
}
