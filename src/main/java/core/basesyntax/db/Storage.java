package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String, Integer> fruitStorage = new HashMap<>(); // Brak pustej linii wyżej

    public static void clear() {
        fruitStorage.clear();
    }
}
