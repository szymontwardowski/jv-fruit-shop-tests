package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.getFruitStorage().clear();
    }

    @Test
    void getReport_validStorage_ok() {

        Storage.getFruitStorage().put("apple", 20);
        Storage.getFruitStorage().put("banana", 10);

        String actualReport = reportGenerator.getReport();


        String expectedHeader = "fruit,quantity";
        Assertions.assertTrue(actualReport.contains(expectedHeader), "Raport powinien zawierać nagłówek");
        Assertions.assertTrue(actualReport.contains("apple,20"), "Raport powinien zawierać dane o jabłkach");
        Assertions.assertTrue(actualReport.contains("banana,10"), "Raport powinien zawierać dane o bananach");
    }
}
