package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperation();
        Storage.fruitStorage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void apply_validTransaction_ok() {
        String fruit = "apple";
        int initialAmount = 10;
        int returnAmount = 5;
        Storage.fruitStorage.put(fruit, initialAmount);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, fruit, returnAmount);
        operationHandler.apply(transaction);

        int expected = initialAmount + returnAmount;
        int actual = Storage.fruitStorage.get(fruit);
        Assertions.assertEquals(expected, actual,
                "Return operation should increase fruit quantity in storage");
    }

    @Test
    void apply_fruitDoesNotExist_ok() {
        String fruit = "kiwi";
        int returnAmount = 20;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, fruit, returnAmount);

        operationHandler.apply(transaction);

        int actual = Storage.fruitStorage.get(fruit);
        Assertions.assertEquals(returnAmount, actual,
                "Return operation should create a new entry if fruit doesn't exist");
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", -10);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception for negative return quantity");
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, null, 10);

        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.apply(transaction);
        }, "Should throw exception when fruit name is null");
    }
}
