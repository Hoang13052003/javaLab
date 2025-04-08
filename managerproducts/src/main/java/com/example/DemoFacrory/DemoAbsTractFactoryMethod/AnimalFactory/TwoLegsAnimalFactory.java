package com.example.DemoFacrory.DemoAbsTractFactoryMethod.AnimalFactory;

import com.example.DemoFacrory.DemoAbsTractFactoryMethod.Animal.Duck;
import com.example.DemoFacrory.interfacesFactory.Animal;

public class TwoLegsAnimalFactory extends AbstractAnimalFactory {
    @Override
    public Animal createAnimal() {
        return new Duck();
    }

}
