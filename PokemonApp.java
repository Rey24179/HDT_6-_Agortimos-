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
        br.readLine(); // Saltar la primera línea (encabezados)
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length < 8) continue;
            String name = data[0];
            String type1 = data[2];
            String ability = data[7].split(" ")[0]; // Tomar la primera habilidad
            Pokemon p = new Pokemon(name, type1, ability);
            pokemonMap.put(name, p);
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

// Programa principal
public class PokemonApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione la implementación de Map: 1) HashMap 2) TreeMap 3) LinkedHashMap");
        int option = scanner.nextInt();
        PokemonManager manager = new PokemonManager(option);
        
        try {
            manager.loadFromFile("pokemon_data_pokeapi.csv");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
            return;
        }
        
        boolean running = true;
        while (running) {
            System.out.println("\nOpciones: 1) Agregar Pokémon 2) Mostrar Pokémon 3) Colección ordenada 4) Todos ordenados 5) Buscar por habilidad 6) Salir");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (choice) {
                case 1:
                    System.out.print("Nombre del Pokémon: ");
                    manager.addPokemonToUser(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Nombre del Pokémon: ");
                    manager.showPokemonData(scanner.nextLine());
                    break;
                case 3:
                    manager.showUserCollectionSortedByType();
                    break;
                case 4:
                    manager.showAllPokemonSortedByType();
                    break;
                case 5:
                    System.out.print("Habilidad: ");
                    manager.showPokemonByAbility(scanner.nextLine());
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }
}
