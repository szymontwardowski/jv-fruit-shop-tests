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

        Storage.getFruitStorage().clear();
    }

    @Test
    void handle_validTransaction_ok() {

        String fruit = "apple";
        int amount = 100;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, amount);

        operationHandler.apply(transaction);

        int actualAmount = Storage.getFruitStorage().get(fruit);
        Assertions.assertEquals(amount, actualAmount, "Magazyn powinien zawierać 100 jabłek po operacji BALANCE");
    }
}
