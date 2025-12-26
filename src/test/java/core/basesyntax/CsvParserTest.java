package core.basesyntax;

import core.basesyntax.service.CsvParser;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvParserTest {
    private CsvParser csvParser;

    @BeforeEach
    void setUp() {
        csvParser = new CsvParser();
    }

    @Test
    void parse_validInput_ok() {
        List<String> input = List.of("type,fruit,quantity", "b,banana,20", "s,apple,100");
        List<FruitTransaction> result = csvParser.parse(input);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("banana", result.get(0).getFruit());
        Assertions.assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void parse_invalidData_notOk() {
        List<String> input = List.of("type,fruit,quantity", "b,banana");
        Assertions.assertThrows(RuntimeException.class, () -> csvParser.parse(input));
    }
}
