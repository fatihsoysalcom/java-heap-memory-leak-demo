# Java Heap Memory Leak Demo

This Java example demonstrates fundamental concepts of JVM Heap memory management and Garbage Collection. It illustrates how objects are created on the Heap, how they become eligible for garbage collection when references are lost, and simulates a simple memory leak by holding unnecessary references in a static list. The example also shows how clearing these references allows the Garbage Collector to reclaim memory.

## Language

`java`

## How to Run

1. Save the code as `MemoryManagementDemo.java`.
2. Compile: `javac MemoryManagementDemo.java`
3. Run: `java MemoryManagementDemo`

## Original Article

This example accompanies the Turkish article: [Java Bellek Yönetimi Açıklandı](https://fatihsoysal.com/blog/?p=42197).

## License

MIT — see [LICENSE](LICENSE).
