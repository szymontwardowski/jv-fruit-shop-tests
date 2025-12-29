package core.basesyntax;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.ReturnOperation;

public class ReturnOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperation();
        Storage.getFruitStorage().clear();
    }

    @Test
    void apply_validTransaction_ok() {
        String fruit = "apple";
        int initialAmount = 10;
        int returnAmount = 5;
        Storage.getFruitStorage().put(fruit, initialAmount);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, fruit, returnAmount);
        operationHandler.apply(transaction);

        int expected = initialAmount + returnAmount;
        int actual = Storage.getFruitStorage().get(fruit);
        Assertions.assertEquals(expected, actual,
                "Return operation should increase fruit quantity in storage");
    }
}
