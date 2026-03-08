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

    @Test
    void getByCode_validCode_ok() {
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getByCode('b'));
        Assertions.assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getByCode('p'));
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getByCode('s'));
        Assertions.assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getByCode('r'));
    }

    @Test
    void getByCode_invalidCode_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.getByCode('z');
        }, "Should throw exception for unknown operation code 'z'");
    }

    @Test
    void fruitTransaction_negativeQuantity_ok() {
        // Zazwyczaj dopuszczamy tworzenie obiektu, ale warto sprawdzić czy dane się zgadzają
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", -10);
        Assertions.assertEquals(-10, transaction.getQuantity());
    }
}
