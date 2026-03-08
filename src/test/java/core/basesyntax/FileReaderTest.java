package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.io.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderTest {
    @TempDir
    public Path tempDir;

    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = tempDir.resolve("input.csv");
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,10"
        );
        Files.write(tempFile, expected);

        List<String> actual = fileReader.input(tempFile.toString());

        Assertions.assertEquals(expected, actual,
                "FileReader should return all lines from the file");
    }

    @Test
    void read_emptyFile_ok() throws IOException {
        Path emptyFile = tempDir.resolve("empty.csv");
        Files.createFile(emptyFile);

        List<String> actual = fileReader.input(emptyFile.toString());

        Assertions.assertTrue(actual.isEmpty(), "Result should be empty for an empty file");
    }

    @Test
    void read_nonExistentFile_notOk() {
        String wrongPath = "non_existent_file.csv";
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.input(wrongPath);
        }, "Should throw RuntimeException when file does not exist");
    }

    @Test
    void read_nullPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.input(null);
        }, "Should throw RuntimeException for null path");
    }
}
