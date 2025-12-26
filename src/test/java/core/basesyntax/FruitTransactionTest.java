package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void fruitTransaction_createObject_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        String fruit = "banana";
        int quantity = 50;

        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);

        Assertions.assertEquals(operation, transaction.getOperation());
        Assertions.assertEquals(fruit, transaction.getFruit());
        Assertions.assertEquals(quantity, transaction.getQuantity());
    }
}
