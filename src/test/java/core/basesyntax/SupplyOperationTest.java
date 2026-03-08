package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private SupplyOperation operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperation();
        Storage.fruitStorage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void apply_validOperation_ok() {
        String fruit = "apple";
        Storage.fruitStorage.put(fruit, 10);
        int supplyAmount = 20;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, supplyAmount);

        operationHandler.apply(transaction);

        int expectedAmount = 30;
        int actualAmountInStorage = Storage.fruitStorage.get(fruit);
        Assertions.assertEquals(expectedAmount, actualAmountInStorage,
                "Storage should contain 30 apples after supply of 20 to existing 10");
    }

    @Test
    void apply_fruitDoesNotExist_ok() {
        String fruit = "orange";
        int supplyAmount = 50;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, supplyAmount);

        operationHandler.apply(transaction);

        int actualAmountInStorage = Storage.fruitStorage.get(fruit);
        Assertions.assertEquals(supplyAmount, actualAmountInStorage,
                "Supply should create a new entry if the fruit does not exist in storage");
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", -10);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception for negative supply quantity");
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, null, 10);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception when fruit name is null");
    }
}
