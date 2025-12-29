package core.basesyntax;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.SupplyOperation;

public class SupplyOperationTest {
    private SupplyOperation operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperation();
        Storage.getFruitStorage().clear();
    }

    @Test
    void apply_validOperation_ok() {
        String fruit = "apple";
        Storage.getFruitStorage().put(fruit, 10);

        int supplyAmount = 20;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, supplyAmount);

        operationHandler.apply(transaction);
        int expectedAmount = 30;
        int actualAmountInStorage = Storage.getFruitStorage().get(fruit);

        Assertions.assertEquals(expectedAmount, actualAmountInStorage,
                "Magazyn powinien zawierać 30 jabłek");
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }
}
