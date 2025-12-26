package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopServiceTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(strategy);
        Storage.getFruitStorage().clear();
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );

        shopService.process(transactions);

        int expected = 130; // 100 + 50 - 30 + 10
        int actual = Storage.getFruitStorage().get("apple");
        Assertions.assertEquals(expected, actual);
    }
}


