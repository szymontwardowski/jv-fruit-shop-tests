package core.basesyntax;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.PurchaseOperation;

public class PurchaseOperationTest {
    private PurchaseOperation operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperation();
        Storage.getFruitStorage().clear();
    }

    @Test
    void apply_validOperation_ok() {

        String fruit = "banana";
        Storage.getFruitStorage().put(fruit, 100);
        int purchaseAmount = 40;

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, fruit, purchaseAmount);
        operationHandler.apply(transaction);

        int expectedAmount = 60;
        int actualAmountInStorage = Storage.getFruitStorage().get(fruit);

        Assertions.assertEquals(expectedAmount, actualAmountInStorage,
                "Magazyn powinien zawierać sume 100 - 40 = 60");
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }
}
