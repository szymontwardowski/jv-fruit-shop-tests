package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class OperationStrategyTest {
    private OperationStrategy strategy;

    @BeforeEach
    void SetUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        strategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_balanceOperation_ok() {

        FruitTransaction.Operation type = FruitTransaction.Operation.BALANCE;

        OperationHandler handler = strategy.getHandler(type);

        Assertions.assertTrue(handler instanceof BalanceOperation, "Dla typu BALANCE powinniśmy otrzymać obiekt klasy BalanceOperation");
    }
}
