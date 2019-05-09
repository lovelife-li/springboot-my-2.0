package com.springboot.demo.hutool;

import cn.hutool.core.clone.*;
import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.clone.Cloneable;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ObjectUtil;

import java.io.FileInputStream;

/**
 * @author ldb
 * @date 2019-05-07 14:30
 */
public class Test01 {


    /**
     * 猫猫类，使用实现Cloneable方式
     * @author Looly
     *
     */
    private static class Cat implements Cloneable<Cat> {
        private String name = "miaomiao";
        private int age = 2;

        @Override
        public Cat clone() {
            try {
                return (Cat) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new CloneRuntimeException(e);
            }
        }
    }

    private static class Dog extends CloneSupport<Dog> {
        private String name = "wangwang";
        private int age = 3;
    }

    public static void main(String[] args) throws Exception{
        Cat cat = new Cat();
        Cat clone = cat.clone();
        System.out.println(cat+","+clone);

        Dog dog = new Dog();
        Dog clone1 = dog.clone();
        System.out.println(dog+","+clone1);

        Dog dog2 = ObjectUtil.clone(dog);


        int a = 1;
        //aStr为"1"
        String aStr = Convert.toStr(a);
        System.out.println(aStr);

        long[] b = {1,2,3,4,5};
        //bStr为："[1, 2, 3, 4, 5]"
        String bStr = Convert.toStr(b);

        System.out.println(FileTypeUtil.getType(new FileInputStream("D:/b.gif")));

    }
}

