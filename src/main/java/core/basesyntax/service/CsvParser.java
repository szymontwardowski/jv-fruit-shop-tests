package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    public List<FruitTransaction> parse(List<String> rawLines) {
        if (rawLines == null) {
            throw new RuntimeException("Input lines cannot be null");
        }
        return rawLines.stream()
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private FruitTransaction parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new RuntimeException("Invalid data format in line: " + line);
        }

        try {
            char operationCode = parts[OPERATION_INDEX].trim().charAt(0);
            String fruit = parts[FRUIT_INDEX].trim();
            int quantity = Integer.parseInt(parts[QUANTITY_INDEX].trim());

            if (quantity < 0) {
                throw new RuntimeException("Quantity cannot be negative: " + line);
            }

            FruitTransaction.Operation operation = FruitTransaction
                    .Operation.getByCode(operationCode);

            return new FruitTransaction(operation, fruit, quantity);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error parsing line to FruitTransaction: " + line, e);
        }
    }
}

