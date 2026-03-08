package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        Integer currentQuantity = Storage.fruitStorage.get(fruit);
        if (currentQuantity == null || currentQuantity < transaction.getQuantity()) {
            throw new RuntimeException("Not enough fruits in storage or fruit does not exist");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
        Storage.fruitStorage.put(fruit, currentQuantity - transaction.getQuantity());
    }
}
