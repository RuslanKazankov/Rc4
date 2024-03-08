package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rc4 {
    public byte[] text; //текст для шифрования/расшифрования

    private byte[] S = new byte[8];
    private int i = 0;
    private int j = 0;

    //просто для удобства обмена
    private void swap(byte[] array, int ind1, int ind2)
    {
        byte temp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = temp;
    }

    //инициализация, алгоритм ключевого расписания
    public void init(byte[] key)
    {
        for (i = 0; i < 8; i++)
        {
            S[i] = (byte)i;
        }
        j = 0;
        for (i = 0; i < 8; i++)
        {
            j = (j + S[i] + key[i % key.length]) & 0x07;
            swap(S, i, j);
        }
        i = j = 0;
    }

    //генератор псевдослучайной последовательности
    public byte kword()
    {
        i = (i + 1) & 0x07;
        j = (j + S[i]) & 0x07;
        swap(S, i, j);
        byte K = S[(S[i] + S[j]) & 0x07];
        return K;
    }

    //функция шифрования/расшифрования
    public byte[] code()
    {
        byte[] data = Arrays.copyOf(text, text.length);
        byte[] res = new byte[data.length];
        for (int i = 0; i < data.length; i++)
        {
            res[i] = (byte)(data[i] ^ kword());
        }
        for (var el : S){
            System.out.print(el);
        }
        return res;
    }

    //бинарная запись в файл
    public void writeByteArrayToFile(byte[] buffer, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(buffer);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //бинарное чтение из файла
    public byte[] readByteArrayFromFile(String fileName) {
        List<Byte> byteList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            int data;
            while ((data = fis.read()) != -1) {
                byteList.add((byte) data);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return convertListToByteArray(byteList);
    }

    public static byte[] convertListToByteArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }
}
