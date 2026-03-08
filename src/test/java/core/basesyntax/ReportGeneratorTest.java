package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.fruitStorage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void getReport_validStorage_ok() {
        Storage.fruitStorage.put("apple", 20);
        Storage.fruitStorage.put("banana", 10);
        String actualReport = reportGenerator.getReport();

        String expectedHeader = "fruit,quantity";
        Assertions.assertTrue(actualReport.startsWith(expectedHeader),
                "Report should start with a header");
        Assertions.assertTrue(actualReport.contains("apple,20"),
                "Report should contain information about apples");
        Assertions.assertTrue(actualReport.contains("banana,10"),
                "Report should contain information about bananas");
    }

    @Test
    void getReport_emptyStorage_ok() {
        String actualReport = reportGenerator.getReport();
        String expectedHeader = "fruit,quantity";

        // Zazwyczaj przy pustym magazynie zwracamy sam nagłówek
        Assertions.assertEquals(expectedHeader, actualReport.trim(),
                "Report should only contain the header when storage is empty");
    }
}
