package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvParser;
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
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
    }

    @Test
    void parse_emptyList_ok() {
        List<String> input = List.of();
        List<FruitTransaction> result = csvParser.parse(input);
        Assertions.assertTrue(result.isEmpty(), "Result should be empty for empty input list");
    }

    @Test
    void parse_onlyHeader_ok() {
        List<String> input = List.of("type,fruit,quantity");
        List<FruitTransaction> result = csvParser.parse(input);
        Assertions.assertTrue(result.isEmpty(), "Result should be empty when only header is present");
    }

    @Test
    void parse_invalidOperationSymbol_notOk() {
        List<String> input = List.of("type,fruit,quantity", "x,banana,10");
        Assertions.assertThrows(RuntimeException.class, () ->
                csvParser.parse(input), "Should throw exception for unknown symbol");
    }

    @Test
    void parse_malformedLine_notOk() {
        List<String> input = List.of("type,fruit,quantity", "b,banana");
        Assertions.assertThrows(RuntimeException.class, () -> csvParser.parse(input),
                "Should throw exception for line with missing quantity");
    }

    @Test
    void parse_nullInput_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> csvParser.parse(null),
                "Should throw exception when input list is null");
    }

    @Test
    void parse_negativeQuantityInLine_notOk() {
        List<String> input = List.of("type,fruit,quantity", "b,banana,-5");
        Assertions.assertThrows(RuntimeException.class, () -> csvParser.parse(input),
                "Should throw exception for negative quantity in CSV line");
    }
}
