import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DirectorioTelefonico {
    private Map<String, String> contacts = new HashMap<>();
    private static final String FILENAME = "addressbook.csv";

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contacts.put(parts[0], parts[1]);
                }
            }
            System.out.println("Contactos cargados desde el archivo.");
        } catch (IOException e) {
            System.err.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
            System.out.println("Cambios guardados en el archivo.");
        } catch (IOException e) {
            System.err.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String phoneNumber, String name) {
        contacts.put(phoneNumber, name);
        System.out.println("Nuevo contacto creado: " + phoneNumber + " : " + name);
    }

    public void delete(String phoneNumber) {
        if (contacts.containsKey(phoneNumber)) {
            contacts.remove(phoneNumber);
            System.out.println("Contacto eliminado: " + phoneNumber);
        } else {
            System.out.println("No se encontró el contacto con el número: " + phoneNumber);
        }
    }

    public static void main(String[] args) {
        DirectorioTelefonico addressBook = new DirectorioTelefonico();
        addressBook.load();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenú:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar cambios");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después de nextInt()

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Ingrese el número de teléfono: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(phoneNumber, name);
                    break;
                case 3:
                    System.out.print("Ingrese el número de teléfono a eliminar: ");
                    String numberToDelete = scanner.nextLine();
                    addressBook.delete(numberToDelete);
                    break;
                case 4:
                    addressBook.save();
                    break;
                case 5:
                    System.out.println("Saliendo de la aplicación.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
