package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        strategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_validOperations_ok() {
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseOperation);
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperation);
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.RETURN)
                instanceof ReturnOperation);
    }

    @Test
    void getHandler_unknownOperation_notOk() {
        // Tworzymy strategię z pustą mapą, aby zasymulować brak handlera
        OperationStrategy emptyStrategy = new OperationStrategyImpl(new HashMap<>());
        Assertions.assertThrows(RuntimeException.class, () -> {
            emptyStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        }, "Should throw exception when handler is missing in the map");
    }

    @Test
    void getHandler_nullOperation_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            strategy.getHandler(null);
        }, "Should throw exception when operation type is null");
    }
}
