package com.example.DemoFacrory.DemoAbsTractFactoryMethod.AnimalFactory;

import com.example.DemoFacrory.interfacesFactory.AnimalFactory;
import com.example.DemoFacrory.interfacesFactory.Animal;

public abstract class AbstractAnimalFactory implements AnimalFactory {
    public abstract Animal createAnimal();
}
