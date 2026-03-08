package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
        String fruit = transaction.getFruit();
        Integer currentQuantity = Storage.fruitStorage.getOrDefault(fruit, 0);
        Storage.fruitStorage.put(fruit, currentQuantity + transaction.getQuantity());
    }
}
