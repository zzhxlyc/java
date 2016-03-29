泛型只在编译期有效，编译后通过类型擦除，JVM看不见任何泛型信息

对JVM来说，ArrayList<String>和ArrayList<Integer>的class都是ArrayList.class

