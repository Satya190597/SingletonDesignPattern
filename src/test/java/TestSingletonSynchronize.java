import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TestSingletonSynchronize {
    @Test
    public void testWithSingleThread() {
        Set<OracleDBSynchronize> instances = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            instances.add(OracleDBSynchronize.getInstance());
        }

        Assertions.assertEquals(1, instances.size());
    }

    @Test
    public void testWithMultiThread() throws InterruptedException {
        Set<OracleDBSynchronize> instances = Collections.synchronizedSet(new HashSet<>());

        // Create Runnable Class.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                instances.add(OracleDBSynchronize.getInstance());
            }
        };

        // Create 1000 Threads.
        final int numberOfThreads = 1000;
        Thread[] threads = new Thread[numberOfThreads];

        for (int index = 0; index < numberOfThreads; index++) {
            threads[index] = new Thread(runnable);
            threads[index].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Assertions.assertEquals(1, instances.size());

    }
}
