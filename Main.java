import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    MetodosDispositivos metDispositivos = new MetodosDispositivos();
    MetodosEstudiantes metEstudiantes = new MetodosEstudiantes();

    List<Portatil> listaPortatiles = new ArrayList<>();
    List<Tableta> listaTabletas = new ArrayList<>();

    cargarDispositivos(listaPortatiles, listaTabletas);

    List<EstudianteIngenieria> listaEstIngenieria = new ArrayList<>();
    List<EstudianteDiseno> listaEstDiseno = new ArrayList<>();

    // Cargar estudiantes desde archivo
    cargarEstudiantes("estudiantes_ingenieria.txt", listaEstIngenieria, new ArrayList<>());
    cargarEstudiantes("estudiantes_diseno.txt", new ArrayList<>(), listaEstDiseno);

    boolean salir = false;

    while (!salir) {
        System.out.println("\n========= MENÚ PRINCIPAL =========");
        System.out.println("1. Gestionar Estudiantes de Ingeniería");
        System.out.println("2. Gestionar Estudiantes de Diseño");
        System.out.println("3. Imprimir Inventario Total");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");

        int opcion = -1;  // Inicializamos con un valor no válido

        while (true) {
            try {
                opcion = Integer.parseInt(sc.nextLine()); // Usamos `nextLine()` y luego convertimos a int
                break; // Salimos del bucle si la conversión es exitosa
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido (del 1 al 4). Inténtelo nuevamente.");
                System.out.print("Seleccione una opción: ");
            }
        }

        switch (opcion) {
            case 1:
                menuEstudiantesIngenieria(sc, metEstudiantes, listaEstIngenieria, listaPortatiles, listaTabletas);
                break;
            case 2:
                menuEstudiantesDiseno(sc, metEstudiantes, listaEstDiseno, listaPortatiles, listaTabletas);
                break;
            case 3:
                metDispositivos.imprimirInventarioTotal(listaPortatiles, listaTabletas); 
                break;    
            case 4:
                salir = true;
                System.out.println("Gracias por usar el sistema.");
                break;
            default:
                System.out.println("Opción no válida. Por favor, ingrese un número entre 1 y 3.");
        }
    }
    sc.close();
}
    public static void cargarEstudiantes(String archivo, List<EstudianteIngenieria> listaIngenieria, List<EstudianteDiseno> listaDiseno) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String tipoEstudiante = data[0].trim();
                String cedula = data[1].trim();
                String nombre = data[2].trim();
                String apellido = data[3].trim();
                String telefono = data[4].trim();
                String serialDispositivo = data[5].trim();

                if (tipoEstudiante.equalsIgnoreCase("Ingenieria")) {
                    int semestre = Integer.parseInt(data[6].trim());
                    double promedio = Double.parseDouble(data[7].trim());
                    EstudianteIngenieria estudiante = new EstudianteIngenieria(cedula, nombre, apellido, telefono, serialDispositivo, semestre, promedio);
                    listaIngenieria.add(estudiante);
                } else if (tipoEstudiante.equalsIgnoreCase("Diseno")) {
                    String modalidad = data[6].trim();
                    int asignaturas = Integer.parseInt(data[7].trim());
                    EstudianteDiseno estudiante = new EstudianteDiseno(cedula, nombre, apellido, telefono, serialDispositivo, modalidad, asignaturas);
                    listaDiseno.add(estudiante);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de estudiantes: " + e.getMessage());
        }
    }
    
    public static void menuEstudiantesIngenieria(Scanner sc, MetodosEstudiantes metEstudiantes,
                                             List<EstudianteIngenieria> listaEstIngenieria,
                                             List<Portatil> listaPortatiles,
                                             List<Tableta> listaTabletas) {
    boolean salirIngenieria = false;
    
    while (!salirIngenieria) {
        System.out.println("\n--- Estudiantes de Ingeniería ---");
        System.out.println("1. Registrar préstamo de equipo");
        System.out.println("2. Modificar préstamo de equipo");
        System.out.println("3. Devolución de equipo");
        System.out.println("4. Buscar equipo");
        System.out.println("5. Volver al menú principal");
        System.out.print("Seleccione una opción: ");

        int opcion = -1;

        while (true) {
            try {
                opcion = Integer.parseInt(sc.nextLine()); 
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido (1-5). Inténtelo nuevamente.");
                System.out.print("Seleccione una opción: ");
            }
        }

        switch (opcion) {
            case 1:
                metEstudiantes.registrarPrestamoIngenieria(sc, listaEstIngenieria, listaPortatiles, listaTabletas);
                break;
            case 2:
                metEstudiantes.modificarPrestamoIngenieria(sc, listaEstIngenieria, listaPortatiles, listaTabletas);
                break;
            case 3:
                metEstudiantes.devolverEquipoIngenieria(sc, listaEstIngenieria, listaPortatiles, listaTabletas);
                break;
            case 4:
                metEstudiantes.buscarEquipoIngenieria(sc, listaEstIngenieria, listaPortatiles, listaTabletas);
                break;
            case 5:
                salirIngenieria = true;
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, ingrese un número entre 1 y 5.");
        }
    }
}

    public static void menuEstudiantesDiseno(Scanner sc, MetodosEstudiantes metEstudiantes,
                                         List<EstudianteDiseno> listaEstDiseno,
                                         List<Portatil> listaPortatiles,
                                         List<Tableta> listaTabletas) {
    boolean salirDiseno = false;
    
    while (!salirDiseno) {
        System.out.println("\n--- Estudiantes de Diseño ---");
        System.out.println("1. Registrar préstamo de equipo");
        System.out.println("2. Modificar préstamo de equipo");
        System.out.println("3. Devolución de equipo");
        System.out.println("4. Buscar equipo");
        System.out.println("5. Volver al menú principal");
        System.out.print("Seleccione una opción: ");

        int opcion = -1;  // Inicializamos con un valor no válido

        while (true) {
            try {
                opcion = Integer.parseInt(sc.nextLine()); 
                break; // Salimos del bucle si la conversión es exitosa
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido (1-5). Inténtelo nuevamente.");
                System.out.print("Seleccione una opción: ");
            }
        }

        switch (opcion) {
            case 1:
                metEstudiantes.registrarPrestamoDiseno(sc, listaEstDiseno, listaPortatiles, listaTabletas);
                break;
            case 2:
                metEstudiantes.modificarPrestamoDiseno(sc, listaEstDiseno, listaPortatiles, listaTabletas);
                break;
            case 3:
                metEstudiantes.devolverEquipoDiseno(sc, listaEstDiseno, listaPortatiles, listaTabletas);
                break;
            case 4:
                metEstudiantes.buscarEquipoDiseno(sc, listaEstDiseno, listaPortatiles, listaTabletas);
                break;
            case 5:
                salirDiseno = true;
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, ingrese un número entre 1 y 5.");
        }
    }
}


    public static void cargarDispositivos(List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
        try (BufferedReader br = new BufferedReader(new FileReader("dispositivos.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String tipo = data[0];
                String serial = data[1];
                String marca = data[2];
                double tamaño = Double.parseDouble(data[3]);
                double precio = Double.parseDouble(data[4]);
                double peso = Double.parseDouble(data[5]);
                int almacenamiento = Integer.parseInt(data[6]);

                if (tipo.equals("Portatil")) {
Portatil portatil = new Portatil(serial, marca, tamaño, precio, peso, almacenamiento, 
                                 Portatil.SO_WINDOWS, Portatil.PROC_INTEL);
                    listaPortatiles.add(portatil);
                } else if (tipo.equals("Tableta")) {
                    Tableta tableta = new Tableta(serial, marca, tamaño, precio, peso, almacenamiento);
                    listaTabletas.add(tableta);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de dispositivos: " + e.getMessage());
        }
    }
}