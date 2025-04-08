package com.example.DemoFacrory.DemoFactoryMethod.AnimalFactory;

import java.util.Random;

import com.example.DemoFacrory.DemoFactoryMethod.Animal.Cat;
import com.example.DemoFacrory.DemoFactoryMethod.Animal.Dog;
import com.example.DemoFacrory.DemoFactoryMethod.Animal.Duck;
import com.example.DemoFacrory.interfacesFactory.Animal;
import com.example.DemoFacrory.interfacesFactory.AnimalFactory;

public class RadomCreateAnimal implements AnimalFactory {
    @Override
    public Animal createAnimal() {
        Random random = new Random();

        int index = random.nextInt(3) + 1;

        if (index == 1) {
            return new Cat();
        } else if (index == 2) {
            return new Dog();
        } else {
            return new Duck();
        }

    }
}
