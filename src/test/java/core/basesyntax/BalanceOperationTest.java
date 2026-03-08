package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private BalanceOperation operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperation();
    }

    @AfterEach
    void teardown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void apply_validTransaction_ok() {
        String fruit = "apple";
        int amount = 100;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, amount);

        operationHandler.apply(transaction);

        int actualAmount = Storage.fruitStorage.get(fruit);
        Assertions.assertEquals(amount, actualAmount,
                "Storage should contain 100 apples after BALANCE operation");
    }

    @Test
    void apply_existingFruitOverride_ok() {
        Storage.fruitStorage.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 150);

        operationHandler.apply(transaction);

        int actualAmount = Storage.fruitStorage.get("apple");
        Assertions.assertEquals(150, actualAmount,
                "BALANCE should override existing value in storage");
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -10);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception for negative quantity in BALANCE");
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, null, 10);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception when fruit name is null");
    }
}
