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
// ...existing code...

// Gestor de Pokémon con operaciones principales
class PokemonManager {
    private Map<String, Pokemon> pokemonMap;
    private List<Pokemon> userCollection;
    
    public PokemonManager(int mapOption) {
        this.pokemonMap = MapFactory.getMap(mapOption);
        this.userCollection = new ArrayList<>();
    }
    
    // Cargar datos desde un archivo CSV
    public void loadFromFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length < 3) continue;
            Pokemon p = new Pokemon(data[0], data[1], data[2]);
            pokemonMap.put(p.name, p);
        }
        br.close();
    }
    
    // Agregar un Pokémon a la colección del usuario
    public void addPokemonToUser(String name) {
        if (!pokemonMap.containsKey(name)) {
            System.out.println("Error: Pokémon no encontrado.");
            return;
        }
        Pokemon p = pokemonMap.get(name);
        if (userCollection.contains(p)) {
            System.out.println("Este Pokémon ya está en tu colección.");
        } else {
            userCollection.add(p);
            System.out.println("Pokémon agregado a la colección.");
        }
    }
    
    // Mostrar datos de un Pokémon específico
    public void showPokemonData(String name) {
        if (pokemonMap.containsKey(name)) {
            System.out.println(pokemonMap.get(name));
        } else {
            System.out.println("Pokémon no encontrado.");
        }
    }
    
    // Mostrar Pokémon de la colección ordenados por tipo1
    public void showUserCollectionSortedByType() {
        userCollection.stream()
            .sorted(Comparator.comparing(p -> p.type1))
            .forEach(System.out::println);
    }
    
    // Mostrar todos los Pokémon leídos ordenados por tipo1
    public void showAllPokemonSortedByType() {
        pokemonMap.values().stream()
            .sorted(Comparator.comparing(p -> p.type1))
            .forEach(System.out::println);
    }
    
    // Buscar Pokémon por habilidad
    public void showPokemonByAbility(String ability) {
        pokemonMap.values().stream()
            .filter(p -> p.ability.equalsIgnoreCase(ability))
            .forEach(System.out::println);
    }
}