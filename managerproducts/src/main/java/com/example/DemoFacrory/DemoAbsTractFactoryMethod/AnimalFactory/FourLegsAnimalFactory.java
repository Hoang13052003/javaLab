package com.example.DemoFacrory.DemoAbsTractFactoryMethod.AnimalFactory;

import java.util.Random;

import com.example.DemoFacrory.DemoAbsTractFactoryMethod.Animal.Cat;
import com.example.DemoFacrory.DemoAbsTractFactoryMethod.Animal.Dog;
import com.example.DemoFacrory.interfacesFactory.Animal;

public class FourLegsAnimalFactory extends AbstractAnimalFactory {
    @Override
    public Animal createAnimal() {
        Random random = new Random();

        int index = random.nextInt(2) + 1;

        if (index == 1) {
            return new Cat();
        } else {
            return new Dog();
        }

    }
}
