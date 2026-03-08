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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Storage.fruitStorage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void process_multipleTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5)
        );

        shopService.process(transactions);

        Assertions.assertEquals(130, Storage.fruitStorage.get("apple"),
                "Final amount of apples should be 100 + 50 - 30 + 10 = 130");
        Assertions.assertEquals(15, Storage.fruitStorage.get("banana"),
                "Final amount of bananas should be 20 - 5 = 15");
    }

    @Test
    void process_emptyList_ok() {
        shopService.process(List.of());
        Assertions.assertTrue(Storage.fruitStorage.isEmpty(),
                "Storage should be empty after processing empty list");
    }

    @Test
    void process_invalidTransaction_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50) // More than available
        );

        Assertions.assertThrows(RuntimeException.class, () -> {
            shopService.process(transactions);
        }, "Should throw exception when any transaction in the list is invalid");
    }

    @Test
    void process_nullInput_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            shopService.process(null);
        }, "Should throw exception when transaction list is null");
    }
}
