import java.util.*;
import java.io.*;

// Clase que representa un Pokémon
class Pokemon {
    String name;
    String type1;
    String ability;
    
    public Pokemon(String name, String type1, String ability) {
        this.name = name;
        this.type1 = type1;
        this.ability = ability;
    }
    
    @Override
    public String toString() {
        return name + " | Type1: " + type1 + " | Ability: " + ability;
    }
}

// Fábrica para seleccionar la implementación de Map
class MapFactory {
    public static Map<String, Pokemon> getMap(int option) {
        switch (option) {
            case 1: return new HashMap<>();
            case 2: return new TreeMap<>();
            case 3: return new LinkedHashMap<>();
            default: throw new IllegalArgumentException("Opción inválida");
        }
    }
}