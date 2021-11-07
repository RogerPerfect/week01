package com.example.java01;

import java.io.*;
import java.lang.reflect.Method;

public class NewClassloader extends ClassLoader {

    public static void main(String[] args) throws Exception{

//        Class<?> cc = (Class<?>) new NewClassloader().findClass("Hello").newInstance();
//        Method method = cc.getMethod("hello");
//        method.invoke(cc);

        ClassLoader classLoader = new NewClassloader();
        Class<?> clazz = classLoader.loadClass("Hello");

        Object newHellostance = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(newHellostance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String filename = name + ".xlass";

        String filePath = NewClassloader.class.getClassLoader().getResource(filename).getFile();
        System.out.println(filePath);
        FileInputStream fip = null;
        try {
            fip = new FileInputStream(filePath);
            int arryLength = fip.available();
            byte[] byteArray = new byte[arryLength];
            fip.read(byteArray);
            byte[] byteClass = new byte[byteArray.length];
            for ( int i = 0; i < byteArray.length; i++){
                byteClass[i] = (byte)(255 - byteArray[i]);
            }
            return defineClass(name,byteClass,0,byteClass.length);

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            throw new ClassNotFoundException(name, e);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new ClassNotFoundException(name, e);
        } finally {
            try {
                fip.close();
            } catch (IOException e) {
                //e.printStackTrace();
                throw new ClassNotFoundException(name, e);
            }

        }

    }
}
