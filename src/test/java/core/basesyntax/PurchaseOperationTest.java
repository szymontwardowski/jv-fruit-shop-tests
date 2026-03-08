package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.PurchaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private PurchaseOperation operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperation();
        Storage.fruitStorage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void apply_validOperation_ok() {
        String fruit = "banana";
        Storage.fruitStorage.put(fruit, 100);
        int purchaseAmount = 40;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, fruit, purchaseAmount);

        operationHandler.apply(transaction);

        int expectedAmount = 60;
        int actualAmountInStorage = Storage.fruitStorage.get(fruit);
        Assertions.assertEquals(expectedAmount, actualAmountInStorage,
                "Storage should contain 60 bananas after purchasing 40 out of 100");
    }

    @Test
    void apply_purchaseMoreThanAvailable_notOk() {
        Storage.fruitStorage.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 15);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception when purchasing more than available in storage");
    }

    @Test
    void apply_fruitDoesNotExist_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "dragonfruit", 5);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception when fruit does not exist in storage");
    }

    @Test
    void apply_negativeQuantity_notOk() {
        Storage.fruitStorage.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", -5);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception for negative purchase quantity");
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, null, 10);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception when fruit name is null");
    }
}
