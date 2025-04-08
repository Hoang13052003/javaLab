package com.example.DemoFacrory.DemoFactoryMethod.AnimalFactory;

import com.example.DemoFacrory.DemoFactoryMethod.Animal.Cat;
import com.example.DemoFacrory.DemoFactoryMethod.Animal.Dog;
import com.example.DemoFacrory.DemoFactoryMethod.Animal.Duck;
import com.example.DemoFacrory.interfacesFactory.Animal;
import com.example.DemoFacrory.interfacesFactory.AnimalFactory;

public class BasicCreateAnimal implements AnimalFactory {
    private int index = 0;

    @Override
    public Animal createAnimal() {
        if (index == 0) {
            index++;
            return new Cat();
        }

        if (index == 1) {
            index++;
            return new Dog();
        }

        if (index == 2) {
            index = 0;
            return new Duck();
        }

        return null;
    }

}
