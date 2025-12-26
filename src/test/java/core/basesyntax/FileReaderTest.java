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
    private Path tempDir;

    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = tempDir.resolve("input.csv").toAbsolutePath();
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,10"
        );

        Files.write(tempFile, expected);

        List<String> actual = fileReader.input(tempFile.toString());

        Assertions.assertEquals(expected, actual,
                "FileReader powinien zwrócić wszystkie linie z pliku");
    }

    @Test
    void read_nonExistentFile_notOk() {
        String wrongPath = "non_existent_file.csv";
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.input(wrongPath);
        }, "Powinien zostać rzucony RuntimeException, gdy plik nie istnieje");
    }
}

