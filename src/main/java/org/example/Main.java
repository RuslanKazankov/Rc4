package org.example;

public class Main {
    public static void main(String[] args) {
        Rc4 rc4 = new Rc4();
        byte[] key = { 7, 5, 7, 1};
        byte[] text = { 3, 5, 5, 1, 2, 5, 6};
        rc4.init(key);
        rc4.text = text;
        rc4.writeByteArrayToFile(rc4.code(), "cifra.txt");
    }
}