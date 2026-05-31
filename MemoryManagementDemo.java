import java.util.ArrayList;
import java.util.List;

public class MemoryManagementDemo {

    // This static list will hold references to objects, preventing them from being garbage collected.
    // It simulates a simple memory leak scenario, as static references are root references.
    private static final List<Object> LEAKY_LIST = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Java Heap Memory Management Demo");
        System.out.println("---------------------------------");

        long initialMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.printf("Initial Used Memory: %.2f MB%n", bytesToMegabytes(initialMemory));

        // --- Scenario 1: Objects created and immediately eligible for GC ---
        System.out.println("\n--- Scenario 1: Transient Objects on Heap ---");
        System.out.println("Creating 100,000 String objects that will quickly become eligible for GC.");
        for (int i = 0; i < 100_000; i++) {
            // Each 'tempObject' is created on the Heap.
            // After each loop iteration, the reference to the previous 'tempObject' is lost,
            // making the object eligible for Garbage Collection (GC).
            String tempObject = new String("Transient Data " + i);
            // We don't store 'tempObject' anywhere persistent.
        }
        System.out.println("Finished creating transient objects. GC is likely to clean these up soon.");
        System.gc(); // Hint to the JVM to run garbage collection
        Thread.sleep(100); // Give GC a moment to potentially run

        long afterTransientMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.printf("Used Memory after transient objects and GC: %.2f MB%n", bytesToMegabytes(afterTransientMemory));


        // --- Scenario 2: Simulating a Memory Leak ---
        System.out.println("\n--- Scenario 2: Simulating a Memory Leak ---");
        System.out.println("Creating 50,000 objects and adding them to a static list (simulated leak).");
        for (int i = 0; i < 50_000; i++) {
            // Each 'leakedObject' (a byte array) is created on the Heap.
            // A reference to it is stored in LEAKY_LIST, preventing it from being garbage collected.
            // This simulates a common memory leak pattern where objects are retained unnecessarily.
            LEAKY_LIST.add(new byte[1024]); // Each object is 1KB
        }
        System.out.println("Finished adding objects to the leaky list.");
        System.gc(); // Hint to the JVM to run garbage collection
        Thread.sleep(100); // Give GC a moment

        long afterLeakMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.printf("Used Memory after simulated leak and GC: %.2f MB%n", bytesToMegabytes(afterLeakMemory));
        System.out.println("Notice the memory usage increase due to objects held in LEAKY_LIST.");


        // --- Scenario 3: Clearing the 'leak' ---
        System.out.println("\n--- Scenario 3: Clearing the 'leak' ---");
        System.out.println("Clearing the static leaky list, making its objects eligible for GC.");
        LEAKY_LIST.clear(); // Removing all references from the static list
        System.gc(); // Hint to the JVM to run garbage collection
        Thread.sleep(100); // Give GC a moment

        long afterClearMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.printf("Used Memory after clearing leak and GC: %.2f MB%n", bytesToMegabytes(afterClearMemory));
        System.out.println("Memory usage should now be lower as leaked objects are collected.");

        System.out.println("\nDemo complete.");
    }

    private static double bytesToMegabytes(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }
}