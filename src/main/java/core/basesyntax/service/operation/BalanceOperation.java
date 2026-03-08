package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new RuntimeException("Fruit name cannot be null");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
        Storage.fruitStorage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
