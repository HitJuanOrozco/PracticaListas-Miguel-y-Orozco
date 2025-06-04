import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MetodosEstudiantes {

    public void registrarPrestamoIngenieria(Scanner sc, List<EstudianteIngenieria> listaIng,
                                  List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
    String cedula;
    
    while (true) {
        System.out.print("Ingrese la cédula del estudiante de Ingeniería: ");
        cedula = sc.nextLine().trim();
        
        if (cedula.matches("\\d+")) {
            break; 
        } else {
            System.out.println("Error: La cédula solo debe contener números. Inténtelo de nuevo.");
        }
    }

    double promedio = 0.0;
    boolean entradaValida = false;

    EstudianteIngenieria estudiante = buscarEstudianteIngenieria(cedula, listaIng);

    if (estudiante == null) {
        System.out.println("Estudiante no encontrado. ¿Desea registrar un nuevo estudiante? (S/N)");
        String respuesta = sc.nextLine().trim().toUpperCase();

        if (respuesta.equals("S")) {
            System.out.print("Ingrese el nombre: ");
String nombre;
while (true) {
    nombre = sc.nextLine().trim();
    
    if (nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {  
        break; // Si el nombre es válido, salimos del bucle
    } else {
        System.out.println("Error: El nombre solo puede contener letras y espacios. Inténtelo de nuevo.");
        System.out.print("Ingrese el nombre: ");
    }
}

System.out.print("Ingrese el apellido: ");
String apellido;
while (true) {
    apellido = sc.nextLine().trim();
    
    if (apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {  
        break; 
    } else {
        System.out.println("Error: El apellido solo puede contener letras y espacios. Inténtelo de nuevo.");
        System.out.print("Ingrese el apellido: ");
    }
}
            String telefono;
            while (true) {
                System.out.print("Ingrese el teléfono (mínimo 7 dígitos): ");
                telefono = sc.nextLine().trim();

                if (telefono.matches("\\d{7,15}")) {  
                    break; 
                } else {
                    System.out.println("Error: El teléfono solo debe contener números y tener entre 7 y 15 dígitos. Inténtelo de nuevo.");
                }
            }

            System.out.print("Ingrese el número de semestre: ");
            
            int semestre;
            while (true) {
                try {
                    semestre = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido para el semestre.");
                }
            }

            while (true) {
    System.out.print("Ingrese el promedio académico: ");
    String promedioStr = sc.nextLine().trim();

    // Reemplazar comas por puntos en caso de formato incorrecto
    promedioStr = promedioStr.replace(",", ".");

    try {
        promedio = Double.parseDouble(promedioStr);
        
        // Validar que el promedio esté en el rango correcto (ejemplo: 0.0 a 5.0)
        if (promedio >= 0.0 && promedio <= 5.0) {
            System.out.println("Promedio ingresado correctamente: " + promedio);
            break; // Salimos del bucle porque el promedio es válido
        } else {
            System.out.println("Error: El promedio debe estar entre 0.0 y 5.0. Inténtelo nuevamente.");
        }
    } catch (NumberFormatException e) {
        System.out.println("Error: Entrada inválida. Debe ingresar un número decimal correctamente.");
    }
}
            estudiante = new EstudianteIngenieria(cedula, nombre, apellido, telefono, "0", semestre, promedio);
            listaIng.add(estudiante);
            System.out.println("Estudiante registrado correctamente.");
        } else {
            System.out.println("Volviendo al menú...");
            return;
        }
    }

    if (!estudiante.getCodigoEstudiante().equals("0")) {
        System.out.println("El estudiante ya tiene un préstamo registrado con el equipo: " + estudiante.getCodigoEstudiante());
        return;
    }

    // Mostrar los portátiles disponibles
    System.out.println("Portátiles disponibles: ");
    boolean hayDisponibles = false;
    for (Portatil p : listaPortatiles) {
        if (!estaAsignadoIngenieria(p.getSerial(), listaIng)) {
            System.out.println(p);
            hayDisponibles = true;
        }
    }
    if (!hayDisponibles) {
        System.out.println("No hay portátiles disponibles para préstamo.");
        return;
    }

    System.out.print("Ingrese el serial del portátil a prestar: ");
    String serialPrestamo = sc.nextLine();
    Portatil portatil = buscarPortatil(serialPrestamo, listaPortatiles);
    if (portatil == null) {
        System.out.println("Portátil no encontrado.");
        return;
    }
    if (estaAsignadoIngenieria(serialPrestamo, listaIng)) {
        System.out.println("El portátil ya está asignado a un estudiante de Ingeniería.");
        return;
    }

    estudiante.setCodigoEstudiante(serialPrestamo);
    System.out.println("Préstamo registrado exitosamente para el estudiante " + estudiante.getNombre());

    try {
        File ingenFile = new File("estudiantes_ingenieria.txt");

        // Verificar si el archivo ya tiene contenido (para anteponer un salto de línea)
        boolean needNewLine = ingenFile.exists() && ingenFile.length() > 0;

        FileWriter fw = new FileWriter(ingenFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);

        if (needNewLine) { 
            out.write(System.lineSeparator());  // 👈 Si el archivo ya tiene datos, añadir un salto de línea antes
        }

        // Escribir el nuevo registro con System.lineSeparator() al final
        out.write("Ingenieria," + estudiante.getCedula() + "," + estudiante.getNombre() + "," +
                estudiante.getApellido() + "," + estudiante.getTelefono() + "," + "0" + "," +
                estudiante.getSemestre() + "," + estudiante.getPromedio() + System.lineSeparator());

        out.flush();
        out.close();

        System.out.println("Los datos del prestamo se han guardado correctamente en el archivo de Ingeniería.");
    } catch (IOException e) {
        System.out.println("Error al guardar el estudiante en el archivo: " + e.getMessage());
    }

   try {
    File inputFile = new File("estudiantes_ingenieria.txt");
    File tempFile = new File("temp.txt");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;
    boolean serialUpdated = false;

    while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue;

        String[] data = line.split(",");

        if (data.length >= 8 && data[1].equals(estudiante.getCedula())) { 
            data[5] = serialPrestamo; // **Reemplazar "0" con el serial asignado**
            line = String.join(",", data);
            serialUpdated = true;
        }

        writer.write(line + System.lineSeparator());
    }

    reader.close();
    writer.close();

    if (!serialUpdated) {
        System.out.println("No se encontró el estudiante para actualizar el serial.");
    } else {
        System.out.println("Volviendo al menú");
    }

    inputFile.delete();
    tempFile.renameTo(inputFile);

} catch (IOException e) {
    System.out.println("Error al actualizar el archivo de estudiantes.");
}

}

   public void registrarPrestamoDiseno(Scanner sc, List<EstudianteDiseno> listaDiseno,
                                  List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
    String cedula;

    while (true) {
        System.out.print("Ingrese la cédula del estudiante de Diseño (solo números): ");
        cedula = sc.nextLine().trim();
        
        if (cedula.matches("\\d+")) { 
            break;
        } else {
            System.out.println("Error: La cédula solo debe contener números. Inténtelo nuevamente.");
        }
    }

    EstudianteDiseno estudiante = buscarEstudianteDiseno(cedula, listaDiseno);

    if (estudiante == null) {
        System.out.println("Estudiante no encontrado. ¿Desea registrar un nuevo estudiante? (S/N)");
        String respuesta = sc.nextLine().trim().toUpperCase();

        if (respuesta.equals("S")) {
            String nombre, apellido, telefono, modalidad;
            int asignaturas;

            while (true) {
                System.out.print("Ingrese el nombre: ");
                nombre = sc.nextLine().trim();
                if (nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    break;
                } else {
                    System.out.println("Error: El nombre solo puede contener letras y espacios.");
                }
            }

            while (true) {
                System.out.print("Ingrese el apellido: ");
                apellido = sc.nextLine().trim();
                if (apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    break;
                } else {
                    System.out.println("Error: El apellido solo puede contener letras y espacios.");
                }
            }

            while (true) {
                System.out.print("Ingrese el teléfono (solo números, mínimo 7 dígitos): ");
                telefono = sc.nextLine().trim();
                if (telefono.matches("\\d{7,15}")) {
                    break;
                } else {
                    System.out.println("Error: El teléfono debe tener entre 7 y 15 dígitos. Inténtelo nuevamente.");
                }
            }

            while (true) {
                System.out.print("Ingrese la modalidad de estudio (Virtual/Presencial): ");
                modalidad = sc.nextLine().trim().toLowerCase();
                if (modalidad.equals("virtual") || modalidad.equals("presencial")) {
                    break;
                } else {
                    System.out.println("Error: Debe ingresar 'Virtual' o 'Presencial'.");
                }
            }

            while (true) {
                System.out.print("Ingrese la cantidad de asignaturas: ");
                try {
                    asignaturas = Integer.parseInt(sc.nextLine());
                    if (asignaturas > 0) {
                        break;
                    } else {
                        System.out.println("Error: La cantidad de asignaturas debe ser un número positivo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debe ingresar un número entero válido.");
                }
            }

            estudiante = new EstudianteDiseno(cedula, nombre, apellido, telefono, "0", modalidad, asignaturas);
            listaDiseno.add(estudiante);
            System.out.println("Estudiante registrado correctamente.");
        } else {
            System.out.println("Volviendo al menú...");
            return;
        }
    }

    if (!estudiante.getCodigoEstudiante().equals("0")) {
        System.out.println("El estudiante ya tiene un préstamo registrado con el equipo: " + estudiante.getCodigoEstudiante());
        return;
    }

    // Mostrar las tabletas disponibles
    System.out.println("Tabletas disponibles: ");
    boolean hayDisponibles = false;
    for (Tableta t : listaTabletas) {
        if (!estaAsignadoDiseno(t.getSerial(), listaDiseno)) {
            System.out.println(t);
            hayDisponibles = true;
        }
    }
    if (!hayDisponibles) {
        System.out.println("No hay tabletas disponibles para préstamo.");
        return;
    }

    System.out.print("Ingrese el serial de la tableta a prestar: ");
    String serialPrestamo = sc.nextLine();
    Tableta tableta = buscarTableta(serialPrestamo, listaTabletas);
    if (tableta == null) {
        System.out.println("Tableta no encontrada.");
        return;
    }
    if (estaAsignadoDiseno(serialPrestamo, listaDiseno)) {
        System.out.println("La tableta ya está asignada a un estudiante de Diseño.");
        return;
    }

    estudiante.setCodigoEstudiante(serialPrestamo);
    System.out.println("Préstamo registrado exitosamente para el estudiante " + estudiante.getNombre());

    try {
        File disenoFile = new File("estudiantes_diseno.txt");

        // Verificar si el archivo ya tiene contenido (para anteponer un salto de línea)
        boolean needNewLine = disenoFile.exists() && disenoFile.length() > 0;

        FileWriter fw = new FileWriter(disenoFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);

        if (needNewLine) { 
            out.write(System.lineSeparator());  // 👈 Si el archivo ya tiene datos, añadir un salto de línea antes
        }

        // Escribir el nuevo registro con System.lineSeparator() al final
        out.write("Diseno," + estudiante.getCedula() + "," + estudiante.getNombre() + "," +
                estudiante.getApellido() + "," + estudiante.getTelefono() + "," + "0" + "," +
                estudiante.getModalidad() + "," + estudiante.getAsignaturas() + System.lineSeparator());

        out.flush();
        out.close();

        System.out.println("Estudiante guardado correctamente en el archivo de Diseño.");
    } catch (IOException e) {
        System.out.println("Error al guardar el estudiante en el archivo: " + e.getMessage());
    }

    try {
        File inputFile = new File("estudiantes_diseno.txt");
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean serialUpdated = false;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] data = line.split(",");

            if (data.length >= 8 && data[1].equals(estudiante.getCedula())) { 
                data[5] = serialPrestamo; // **Reemplazar "0" con el serial asignado**
                line = String.join(",", data);
                serialUpdated = true;
            }

            writer.write(line + System.lineSeparator());
        }

        reader.close();
        writer.close();

        if (!serialUpdated) {
            System.out.println("No se encontró el estudiante para actualizar el serial.");
        } else {
            System.out.println("Volviendo al menú");
        }

    inputFile.delete();
    tempFile.renameTo(inputFile);

} catch (IOException e) {
    System.out.println("Error al actualizar el archivo de estudiantes.");
}

}
    public void modificarPrestamoIngenieria(Scanner sc, List<EstudianteIngenieria> listaIng,
                                  List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
    int opcion = -1;

    while (true) {
        try {
            System.out.println("Modificar préstamo por: 1. Cédula  2. Serial del equipo");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            if (opcion == 1 || opcion == 2) {
                break;
            } else {
                System.out.println("Error: Debe ingresar 1 o 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    EstudianteIngenieria estudiante = null;

    if (opcion == 1) {
        String cedula;
        while (true) {
            System.out.print("Ingrese la cédula del estudiante: ");
            cedula = sc.nextLine().trim();
            if (cedula.matches("\\d+")) {
                estudiante = buscarEstudianteIngenieria(cedula, listaIng);
                break;
            } else {
                System.out.println("Error: La cédula solo debe contener números.");
            }
        }
    } else if (opcion == 2) {
        String serial;
        while (true) {
            System.out.print("Ingrese el serial del portátil: ");
            serial = sc.nextLine().trim();
            if (!serial.isEmpty()) {
                for (EstudianteIngenieria e : listaIng) {
                    if (e.getCodigoEstudiante().equals(serial)) {
                        estudiante = e;
                        break;
                    }
                }
                break;
            } else {
                System.out.println("Error: El serial no puede estar vacío.");
            }
        }
    }

    if (estudiante == null) {
        System.out.println("Registro no encontrado.");
        return;
    }

    System.out.println("Registro actual del estudiante:");
    System.out.println(estudiante);
    Portatil portatil = buscarPortatil(estudiante.getCodigoEstudiante(), listaPortatiles);
    System.out.println("Equipo asociado:");
    System.out.println(portatil);

    int opcionMod = -1;

    while (true) {
        try {
            System.out.println("¿Qué desea modificar?");
            System.out.println("1. Datos del estudiante");
            System.out.println("2. Datos del equipo");
            System.out.print("Seleccione una opción: ");
            opcionMod = Integer.parseInt(sc.nextLine());
            if (opcionMod == 1 || opcionMod == 2) {
                break;
            } else {
                System.out.println("Error: Debe ingresar 1 o 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    if (opcionMod == 1) {
        boolean salir = false;
        while (!salir) {
            System.out.println("Datos actuales: " + estudiante);
            System.out.println("Seleccione el dato a modificar:");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Teléfono");
            System.out.println("4. Semestre");
            System.out.println("5. Promedio");
            System.out.println("6. Salir");
            System.out.print("Opción: ");

            int sel;
            while (true) {
                try {
                    sel = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debe ingresar un número válido.");
                }
            }

            switch (sel) {
                case 1:
                    String nuevoNombre;
                    while (true) {
                        System.out.print("Ingrese el nuevo nombre: ");
                        nuevoNombre = sc.nextLine().trim();
                        if (nuevoNombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                            estudiante.setNombre(nuevoNombre);
                            break;
                        } else {
                            System.out.println("Error: El nombre solo puede contener letras y espacios.");
                        }
                    }
                    break;
                case 2:
                    String nuevoApellido;
                    while (true) {
                        System.out.print("Ingrese el nuevo apellido: ");
                        nuevoApellido = sc.nextLine().trim();
                        if (nuevoApellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                            estudiante.setApellido(nuevoApellido);
                            break;
                        } else {
                            System.out.println("Error: El apellido solo puede contener letras y espacios.");
                        }
                    }
                    break;
                case 3:
                    String nuevoTelefono;
                    while (true) {
                        System.out.print("Ingrese el nuevo teléfono: ");
                        nuevoTelefono = sc.nextLine().trim();
                        if (nuevoTelefono.matches("\\d{7,15}")) {
                            estudiante.setTelefono(nuevoTelefono);
                            break;
                        } else {
                            System.out.println("Error: El teléfono debe tener entre 7 y 15 dígitos.");
                        }
                    }
                    break;
                case 4:
                    int nuevoSemestre;
                    while (true) {
                        try {
                            System.out.print("Ingrese el nuevo semestre (entre 1 y 10): ");
                            nuevoSemestre = Integer.parseInt(sc.nextLine());
                            if (nuevoSemestre >= 1 && nuevoSemestre <= 10) {
                                estudiante.setSemestre(nuevoSemestre);
                                break;
                            } else {
                                System.out.println("Error: El semestre debe estar entre 1 y 10.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️ Error: Debe ingresar un número entero válido.");
                        }
                    }
                    break;
                case 5:
                    double nuevoPromedio;
                    while (true) {
                        try {
                            System.out.print("Ingrese el nuevo promedio (entre 0.0 y 5.0): ");
                            nuevoPromedio = Double.parseDouble(sc.nextLine());
                            if (nuevoPromedio >= 0.0 && nuevoPromedio <= 5.0) {
                                estudiante.setPromedio(nuevoPromedio);
                                break;
                            } else {
                                System.out.println("Error: El promedio debe estar entre 0.0 y 5.0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Debe ingresar un número decimal válido.");
                        }
                    }
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
try {
    File inputFile = new File("estudiantes_ingenieria.txt");
    File tempFile = new File("temp.txt");

    if (!inputFile.exists() || inputFile.length() == 0) {
        System.out.println("El archivo no existe o está vacío.");
        return;
    }

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;
    boolean cambiosGuardados = false;

    while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue;

        String[] data = line.split(",");

        if (data.length >= 8 && data[1].trim().equals(estudiante.getCedula())) {  
            System.out.println("Actualizando datos...");

            data[2] = estudiante.getNombre();
            data[3] = estudiante.getApellido();
            data[4] = estudiante.getTelefono();
            data[5] = estudiante.getCodigoEstudiante();
            data[6] = String.valueOf(estudiante.getSemestre());
            data[7] = String.valueOf(estudiante.getPromedio());

            line = String.join(",", data);
            cambiosGuardados = true;
        }

        writer.write(line.trim() + System.lineSeparator()); // Eliminamos espacios extra en cada línea antes de escribir
    }

    reader.close();
    writer.close();

    if (cambiosGuardados) {
        System.out.println("Actualizando la data...");
        boolean deleted = inputFile.delete();
        boolean renamed = tempFile.renameTo(inputFile);
        System.out.println(deleted && renamed ? "¡Data actualizada!" : "Error al actualizar la data.");
    } else {
        System.out.println("No se encontró el estudiante en el archivo.");
    }

} catch (IOException e) {
    System.out.println("Error al actualizar el archivo de estudiantes: " + e.getMessage());
}

    }

    if (opcionMod == 2) {
    if (portatil == null) {
        System.out.println("No se encontró el equipo asociado.");
        return;
    }

    boolean salirEquipo = false;
    while (!salirEquipo) {
        System.out.println("Datos actuales del equipo:");
        System.out.println(portatil);
        System.out.println("Seleccione el dato a modificar:");
        System.out.println("1. Marca");
        System.out.println("2. Tamaño");
        System.out.println("3. Precio");
        System.out.println("4. Peso");
        System.out.println("5. Almacenamiento");
        System.out.println("6. Sistema Operativo");
        System.out.println("7. Procesador");
        System.out.println("8. Salir");
        System.out.print("Opción: ");

        int selEq;
        while (true) {
            try {
                selEq = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }

        switch (selEq) {
            case 1:
                String nuevaMarca;
                while (true) {
                    System.out.print("Ingrese la nueva marca: ");
                    nuevaMarca = sc.nextLine().trim();
                    if (nuevaMarca.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                        portatil.setMarca(nuevaMarca);
                        break;
                    } else {
                        System.out.println("Error: La marca solo puede contener letras y espacios.");
                    }
                }
                break;
            case 2:
        double nuevoTamano;
        while (true) {
            try {
                System.out.print("Ingrese el nuevo tamaño (pulgadas): ");
                nuevoTamano = Double.parseDouble(sc.nextLine());
                if (nuevoTamano > 0) {
                    portatil.setTamano(nuevoTamano);
                    break;
                } else {
                    System.out.println("Error: El tamaño debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
        break;
    case 3:
        double nuevoPrecio;
        while (true) {
            try {
                System.out.print("Ingrese el nuevo precio: ");
                nuevoPrecio = Double.parseDouble(sc.nextLine());
                if (nuevoPrecio > 0) {
                    portatil.setPrecio(nuevoPrecio);
                    break;
                } else {
                    System.out.println("Error: El precio debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
        break;
    case 4:
        double nuevoPeso;
        while (true) {
            try {
                System.out.print("Ingrese el nuevo peso (kg): ");
                nuevoPeso = Double.parseDouble(sc.nextLine());
                if (nuevoPeso > 0) {
                    portatil.setPeso(nuevoPeso);
                    break;
                } else {
                    System.out.println("Error: El peso debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
        break;
    case 5:
        int nuevoAlmacenamiento;
        while (true) {
            try {
                System.out.print("Ingrese el nuevo almacenamiento (GB): ");
                nuevoAlmacenamiento = Integer.parseInt(sc.nextLine());
                if (nuevoAlmacenamiento > 0) {
                    portatil.setAlmacenamiento(nuevoAlmacenamiento);
                    break;
                } else {
                    System.out.println("Error: El almacenamiento debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
        break;
    case 6:
        int nuevoSO;
        while (true) {
            try {
                System.out.println("Seleccione el nuevo sistema operativo:");
                System.out.println("1. Windows");
                System.out.println("2. MacOS");
                System.out.println("3. Linux");
                System.out.print("Ingrese una opción (1-3): ");
                nuevoSO = Integer.parseInt(sc.nextLine());
                if (nuevoSO >= 1 && nuevoSO <= 3) {
                    portatil.setSistemaOperativo(nuevoSO);
                    break;
                } else {
                    System.out.println("Error: Debe ingresar un número entre 1 y 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
        break;
    case 7:
        int nuevoProc;
        while (true) {
            try {
                System.out.println("Seleccione el nuevo procesador:");
                System.out.println("1. Intel");
                System.out.println("2. AMD");
                System.out.println("3. ARM");
                System.out.print("Ingrese una opción (1-3): ");
                nuevoProc = Integer.parseInt(sc.nextLine());
                if (nuevoProc >= 1 && nuevoProc <= 3) {
                    portatil.setProcesador(nuevoProc);
                    break;
                } else {
                    System.out.println("Error: Debe ingresar un número entre 1 y 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
        break;
            case 8:
                salirEquipo = true;
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }
    try {
    File inputFile = new File("dispositivos.txt");
    File tempFile = new File("temp.txt");

    if (!inputFile.exists() || inputFile.length() == 0) {
        System.out.println("El archivo no existe o está vacío.");
        return;
    }

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;
    boolean cambiosGuardados = false;

    System.out.println("Buscando dispositivo con serial: " + portatil.getSerial());

    while ((line = reader.readLine()) != null) {
    if (line.trim().isEmpty()) continue;

    String[] data = line.split(",");

        if (data.length >= 7 && data[1].trim().equalsIgnoreCase(portatil.getSerial().trim())) {  
    System.out.println("Dispositivo encontrado. Actualizando datos...");

    data[2] = portatil.getMarca();
    data[3] = String.valueOf(portatil.getTamano());
    data[4] = String.valueOf(portatil.getPrecio());
    data[5] = String.valueOf(portatil.getPeso());
    data[6] = String.valueOf(portatil.getAlmacenamiento());

    if (data.length > 7) {
        data[7] = String.valueOf(portatil.getSistemaOperativo());
    }
    if (data.length > 8) {
        data[8] = String.valueOf(portatil.getProcesador());
    }

    line = String.join(",", data);
    cambiosGuardados = true;
    System.out.println("Nueva línea guardada: " + line);
}

    writer.write(line.trim() + System.lineSeparator());
}

    reader.close();
    writer.close();

    if (cambiosGuardados) {
        System.out.println("🛠️ Intentando reemplazar el archivo...");
        boolean deleted = inputFile.delete();
        boolean renamed = tempFile.renameTo(inputFile);
        System.out.println(deleted && renamed ? "Cambios guardados correctamente en el archivo." : "Error al renombrar el archivo.");
    } else {
        System.out.println("No se encontró el dispositivo en el archivo.");
    }

} catch (IOException e) {
    System.out.println("Error al actualizar el archivo de dispositivos: " + e.getMessage());
}
}
    System.out.println("Modificación completada.");
}
    public void modificarPrestamoDiseno(Scanner sc, List<EstudianteDiseno> listaDiseno,
                                  List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
    int opcion = -1;

    while (true) {
        try {
            System.out.println("Modificar préstamo por: 1. Cédula  2. Serial del equipo");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            if (opcion == 1 || opcion == 2) {
                break;
            } else {
                System.out.println("Error: Debe ingresar 1 o 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    EstudianteDiseno estudiante = null;

    if (opcion == 1) {
        String cedula;
        while (true) {
            System.out.print("Ingrese la cédula del estudiante: ");
            cedula = sc.nextLine().trim();
            if (cedula.matches("\\d+")) {
                estudiante = buscarEstudianteDiseno(cedula, listaDiseno);
                break;
            } else {
                System.out.println("Error: La cédula solo debe contener números.");
            }
        }
    } else {
        String serial;
        while (true) {
            System.out.print("Ingrese el serial de la tableta: ");
            serial = sc.nextLine().trim();
            if (!serial.isEmpty()) {
                for (EstudianteDiseno e : listaDiseno) {
                    if (e.getCodigoEstudiante().equals(serial)) {
                        estudiante = e;
                        break;
                    }
                }
                break;
            } else {
                System.out.println("Error: El serial no puede estar vacío.");
            }
        }
    }

    if (estudiante == null) {
        System.out.println("Registro no encontrado.");
        return;
    }

    System.out.println("Registro actual del estudiante:");
    System.out.println(estudiante);
    Tableta tableta = buscarTableta(estudiante.getCodigoEstudiante(), listaTabletas);
    System.out.println("Equipo asociado:");
    System.out.println(tableta);

    int opcionMod = -1;

    while (true) {
        try {
            System.out.println("¿Qué desea modificar?");
            System.out.println("1. Datos del estudiante");
            System.out.println("2. Datos del equipo");
            System.out.print("Seleccione una opción: ");
            opcionMod = Integer.parseInt(sc.nextLine());
            if (opcionMod == 1 || opcionMod == 2) {
                break;
            } else {
                System.out.println("Error: Debe ingresar 1 o 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    if (opcionMod == 1) {
        boolean salir = false;
        while (!salir) {
            System.out.println("Datos actuales: " + estudiante);
            System.out.println("Seleccione el dato a modificar:");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Teléfono");
            System.out.println("4. Modalidad");
            System.out.println("5. Asignaturas");
            System.out.println("6. Salir");
            System.out.print("Opción: ");

            int sel;
            while (true) {
                try {
                    sel = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debe ingresar un número válido.");
                }
            }

            switch (sel) {
                case 1:
                    System.out.print("Ingrese el nuevo nombre: ");
                    estudiante.setNombre(validarTexto(sc));
                    break;
                case 2:
                    System.out.print("Ingrese el nuevo apellido: ");
                    estudiante.setApellido(validarTexto(sc));
                    break;
                case 3:
                    System.out.print("Ingrese el nuevo teléfono: ");
                    estudiante.setTelefono(validarTelefono(sc));
                    break;
                case 4:
                    System.out.print("Ingrese la nueva modalidad (Virtual/Presencial): ");
                    estudiante.setModalidad(validarModalidad(sc));
                    break;
                case 5:
                    System.out.print("Ingrese la nueva cantidad de asignaturas: ");
                    estudiante.setAsignaturas(validarNumeroEntero(sc, 1, 20));
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
try {
    File inputFile = new File("estudiantes_diseno.txt");
    File tempFile = new File("temp.txt");

    if (!inputFile.exists() || inputFile.length() == 0) {
        System.out.println("El archivo no existe o está vacío.");
        return;
    }

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;
    boolean cambiosGuardados = false;

    System.out.println("Buscando estudiante con cédula: " + estudiante.getCedula());

    while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue;

        String[] data = line.split(",");

        if (data.length >= 8 && data[1].trim().equals(estudiante.getCedula())) {  
            System.out.println("Actualizando datos...");

            data[2] = estudiante.getNombre();
            data[3] = estudiante.getApellido();
            data[4] = estudiante.getTelefono();
            data[5] = estudiante.getCodigoEstudiante();
            data[6] = String.valueOf(estudiante.getModalidad());
            data[7] = String.valueOf(estudiante.getAsignaturas());

            line = String.join(",", data);
            cambiosGuardados = true;

        }

        writer.write(line.trim() + System.lineSeparator());
    }

    reader.close();
    writer.close();

    if (cambiosGuardados) {
        System.out.println("Intentando reemplazar el archivo...");
        boolean deleted = inputFile.delete();
        boolean renamed = tempFile.renameTo(inputFile);
        System.out.println(deleted && renamed ? "Cambios guardados correctamente en el archivo." : "Error al renombrar el archivo.");
    } else {
        System.out.println("No se encontró el estudiante en el archivo.");
    }

} catch (IOException e) {
    System.out.println("Error al actualizar el archivo de estudiantes: " + e.getMessage());

}
    } else if (opcionMod == 2) {
        boolean salirEquipo = false;
        while (!salirEquipo) {
            System.out.println("Datos actuales del equipo:");
            System.out.println(tableta);
            System.out.println("Seleccione el dato a modificar:");
            System.out.println("1. Marca");
            System.out.println("2. Tamaño");
            System.out.println("3. Precio");
            System.out.println("4. Almacenamiento");
            System.out.println("5. Salir");
            System.out.print("Opción: ");

            int selEq = validarNumeroEntero(sc, 1, 5);

            switch (selEq) {
                case 1:
                    System.out.print("Ingrese la nueva marca: ");
                    tableta.setMarca(validarTexto(sc));
                    break;
                case 2:
                    System.out.print("Ingrese el nuevo tamaño (pulgadas): ");
                    tableta.setTamano(validarNumeroDecimal(sc, 1.0, 20.0));
                    break;
                case 3:
                    System.out.print("Ingrese el nuevo precio: ");
                    tableta.setPrecio(validarNumeroDecimal(sc, 100.0, 5000.0));
                    break;
                case 4:
                    System.out.println("Introduzca el nuevo almacenamiento (GB):");
                    tableta.setAlmacenamiento(validarNumeroEntero(sc, 1, 1000));
                    break;
                case 5:
                    salirEquipo = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        try {
    File inputFile = new File("dispositivos.txt");
    File tempFile = new File("temp.txt");

    if (!inputFile.exists() || inputFile.length() == 0) {
        System.out.println("El archivo no existe o está vacío.");
        return;
    }

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;
    boolean cambiosGuardados = false;

    System.out.println("Buscando dispositivo con serial: " + tableta.getSerial());

    while ((line = reader.readLine()) != null) {
    if (line.trim().isEmpty()) continue;

    String[] data = line.split(",");

        if (data.length >= 7 && data[1].trim().equalsIgnoreCase(tableta.getSerial().trim())) {  
    System.out.println("Dispositivo encontrado. Actualizando datos...");

    data[2] = tableta.getMarca();
    data[3] = String.valueOf(tableta.getTamano());
    data[4] = String.valueOf(tableta.getPrecio());
    data[5] = String.valueOf(tableta.getPeso());
    data[6] = String.valueOf(tableta.getAlmacenamiento());

    line = String.join(",", data);
    cambiosGuardados = true;
    System.out.println("Nueva línea guardada: " + line);
}

    writer.write(line.trim() + System.lineSeparator());
}

    reader.close();
    writer.close();

    if (cambiosGuardados) {
        System.out.println("🛠️ Intentando reemplazar el archivo...");
        boolean deleted = inputFile.delete();
        boolean renamed = tempFile.renameTo(inputFile);
        System.out.println(deleted && renamed ? "Cambios guardados correctamente en el archivo." : "Error al renombrar el archivo.");
    } else {
        System.out.println("No se encontró el dispositivo en el archivo.");
    }

} catch (IOException e) {
    System.out.println("Error al actualizar el archivo de dispositivos: " + e.getMessage());
}
}
    System.out.println("Modificación completada.");
}
    public void devolverEquipoIngenieria(Scanner sc, List<EstudianteIngenieria> listaIng,
                                 List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
    int opcion = -1;

    while (true) {
        try {
            System.out.println("Devolver equipo por: 1. Cédula  2. Serial del equipo");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            if (opcion == 1 || opcion == 2) {
                break;
            } else {
                System.out.println("Error: Debe ingresar 1 o 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    EstudianteIngenieria estudiante = null;

    if (opcion == 1) {
        String cedula;
        while (true) {
            System.out.print("Ingrese la cédula del estudiante: ");
            cedula = sc.nextLine().trim();
            if (cedula.matches("\\d+")) {
                estudiante = buscarEstudianteIngenieria(cedula, listaIng);
                break;
            } else {
                System.out.println("Error: La cédula solo debe contener números.");
            }
        }
    } else {
        String serial;
        while (true) {
            System.out.print("Ingrese el serial del portátil: ");
            serial = sc.nextLine().trim();
            if (!serial.isEmpty()) {
                for (EstudianteIngenieria e : listaIng) {
                    if (e.getCodigoEstudiante().equals(serial)) {
                        estudiante = e;
                        break;
                    }
                }
                break;
            } else {
                System.out.println("Error: El serial no puede estar vacío.");
            }
        }
    }

    if (estudiante == null) {
        System.out.println("No se encontró un estudiante con el criterio ingresado.");
        return;
    }

    if (estudiante.getCodigoEstudiante().equals("0")) {
        System.out.println("Este estudiante no tiene un préstamo registrado.");
        return;
    }

    boolean equipoEncontrado = false;
    for (Portatil p : listaPortatiles) {
        if (p.getSerial().equals(estudiante.getCodigoEstudiante())) {
            equipoEncontrado = true;
            break;
        }
    }

    if (!equipoEncontrado) {
        System.out.println("Error: El equipo que intenta devolver no está registrado en el sistema.");
        return;
    }

    estudiante.setCodigoEstudiante("0");
    System.out.println("Devolución de equipo completada para el estudiante " + estudiante.getNombre());

    try {
    File inputFile = new File("estudiantes_ingenieria.txt");
    File tempFile = new File("temp.txt");

    if (!inputFile.exists() || inputFile.length() == 0) {
        System.out.println("El archivo no existe o está vacío.");
        return;
    }

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;
    boolean cambiosGuardados = false;

    while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue;

        String[] data = line.split(",");
  

        if (data.length >= 8 && data[1].trim().equals(estudiante.getCedula())) {  
            System.out.println("Actualizando datos...");

            data[5] = "0"; // Se actualiza el equipo asignado a "0"

            line = String.join(",", data);
            cambiosGuardados = true;

        }

        writer.write(line.trim() + System.lineSeparator());
    }

    reader.close();
    writer.close();

    if (cambiosGuardados) {
        System.out.println("Registrando devolución...");
        boolean deleted = inputFile.delete();
        boolean renamed = tempFile.renameTo(inputFile);
        System.out.println(deleted && renamed ? "Devolución Completada" : "Error al renombrar el archivo.");
    } else {
        System.out.println("No se encontró el estudiante en el archivo.");
    }

} catch (IOException e) {
    System.out.println("Error al actualizar el archivo de estudiantes: " + e.getMessage());
}

}

    public void devolverEquipoDiseno(Scanner sc, List<EstudianteDiseno> listaDiseno,
                                 List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
    int opcion = -1;

    while (true) {
        try {
            System.out.println("Devolver equipo por: 1. Cédula  2. Serial del equipo");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            if (opcion == 1 || opcion == 2) {
                break;
            } else {
                System.out.println("Error: Debe ingresar 1 o 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    EstudianteDiseno estudiante = null;

    if (opcion == 1) {
        String cedula;
        while (true) {
            System.out.print("Ingrese la cédula del estudiante: ");
            cedula = sc.nextLine().trim();
            if (cedula.matches("\\d+")) {
                estudiante = buscarEstudianteDiseno(cedula, listaDiseno);
                break;
            } else {
                System.out.println("Error: La cédula solo debe contener números.");
            }
        }
    } else {
        String serial;
        while (true) {
            System.out.print("Ingrese el serial de la tableta: ");
            serial = sc.nextLine().trim();
            if (!serial.isEmpty()) {
                for (EstudianteDiseno e : listaDiseno) {
                    if (e.getCodigoEstudiante().equals(serial)) {
                        estudiante = e;
                        break;
                    }
                }
                break;
            } else {
                System.out.println("Error: El serial no puede estar vacío.");
            }
        }
    }

    if (estudiante == null) {
        System.out.println("No se encontró un estudiante con el criterio ingresado.");
        return;
    }

    if (estudiante.getCodigoEstudiante().equals("0")) {
        System.out.println("Este estudiante no tiene un préstamo registrado.");
        return;
    }

    boolean equipoEncontrado = false;
    for (Tableta t : listaTabletas) {
        if (t.getSerial().equals(estudiante.getCodigoEstudiante())) {
            equipoEncontrado = true;
            break;
        }
    }

    if (!equipoEncontrado) {
        System.out.println("Error: La tableta que intenta devolver no está registrada en el sistema.");
        return;
    }

    estudiante.setCodigoEstudiante("0");
    System.out.println("Devolución de equipo completada para el estudiante " + estudiante.getNombre());

    try {
    File inputFile = new File("estudiantes_diseno.txt");
    File tempFile = new File("temp.txt");

    if (!inputFile.exists() || inputFile.length() == 0) {
        System.out.println("El archivo no existe o está vacío.");
        return;
    }

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;
    boolean cambiosGuardados = false;

    while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue;

        String[] data = line.split(",");
  

        if (data.length >= 8 && data[1].trim().equals(estudiante.getCedula())) {  
            System.out.println("Actualizando datos...");

            data[5] = "0"; // Se actualiza el equipo asignado a "0"

            line = String.join(",", data);
            cambiosGuardados = true;

        }

        writer.write(line.trim() + System.lineSeparator());
    }

    reader.close();
    writer.close();

    if (cambiosGuardados) {
        System.out.println("Registrando devolución...");
        boolean deleted = inputFile.delete();
        boolean renamed = tempFile.renameTo(inputFile);
        System.out.println(deleted && renamed ? "Devolución Completada" : "Error al renombrar el archivo.");
    } else {
        System.out.println("No se encontró el estudiante en el archivo.");
    }

} catch (IOException e) {
    System.out.println("Error al actualizar el archivo de estudiantes: " + e.getMessage());
}

}
    public void buscarEquipoIngenieria(Scanner sc, List<EstudianteIngenieria> listaIng,
                             List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
    int opcion = -1;

    while (true) {
        try {
            System.out.println("Buscar equipo por: 1. Cédula  2. Serial del equipo");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            if (opcion == 1 || opcion == 2) {
                break;
            } else {
                System.out.println("Error: Debe ingresar 1 o 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    EstudianteIngenieria estudiante = null;

    if (opcion == 1) {
        String cedula;
        while (true) {
            System.out.print("Ingrese la cédula del estudiante: ");
            cedula = sc.nextLine().trim();
            if (cedula.matches("\\d+")) {
                estudiante = buscarEstudianteIngenieria(cedula, listaIng);
                break;
            } else {
                System.out.println("Error: La cédula solo debe contener números.");
            }
        }
    } else {
        String serial;
        while (true) {
            System.out.print("Ingrese el serial del portátil: ");
            serial = sc.nextLine().trim();
            if (!serial.isEmpty()) {
                for (EstudianteIngenieria e : listaIng) {
                    if (e.getCodigoEstudiante().equals(serial)) {
                        estudiante = e;
                        break;
                    }
                }
                break;
            } else {
                System.out.println("Error: El serial no puede estar vacío.");
            }
        }
    }

    if (estudiante == null) {
        System.out.println("No se encontró un estudiante con el criterio ingresado.");
        return;
    }

    if (estudiante.getCodigoEstudiante().equals("0")) {
        System.out.println("Este estudiante no tiene un equipo asignado.");
        return;
    }

    Portatil portatil = buscarPortatil(estudiante.getCodigoEstudiante(), listaPortatiles);
    if (portatil != null) {
        System.out.println("Registro encontrado:");
        System.out.println(estudiante);
        System.out.println("Equipo asignado:");
        System.out.println(portatil);
    } else {
        System.out.println("No se encontró el equipo asignado.");
    }
}
    public void buscarEquipoDiseno(Scanner sc, List<EstudianteDiseno> listaDiseno,
                             List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
    int opcion = -1;

    while (true) {
        try {
            System.out.println("Buscar equipo por: 1. Cédula  2. Serial del equipo");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            if (opcion == 1 || opcion == 2) {
                break;
            } else {
                System.out.println("Error: Debe ingresar 1 o 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    EstudianteDiseno estudiante = null;

    if (opcion == 1) {
        String cedula;
        while (true) {
            System.out.print("Ingrese la cédula del estudiante: ");
            cedula = sc.nextLine().trim();
            if (cedula.matches("\\d+")) {
                estudiante = buscarEstudianteDiseno(cedula, listaDiseno);
                break;
            } else {
                System.out.println("Error: La cédula solo debe contener números.");
            }
        }
    } else {
        String serial;
        while (true) {
            System.out.print("Ingrese el serial de la tableta: ");
            serial = sc.nextLine().trim();
            if (!serial.isEmpty()) {
                for (EstudianteDiseno e : listaDiseno) {
                    if (e.getCodigoEstudiante().equals(serial)) {
                        estudiante = e;
                        break;
                    }
                }
                break;
            } else {
                System.out.println("Error: El serial no puede estar vacío.");
            }
        }
    }

    if (estudiante == null) {
        System.out.println("No se encontró un estudiante con el criterio ingresado.");
        return;
    }

    if (estudiante.getCodigoEstudiante().equals("0")) {
        System.out.println("Este estudiante no tiene un equipo asignado.");
        return;
    }

    Tableta tableta = buscarTableta(estudiante.getCodigoEstudiante(), listaTabletas);
    if (tableta != null) {
        System.out.println("Registro encontrado:");
        System.out.println(estudiante);
        System.out.println("Equipo asignado:");
        System.out.println(tableta);
    } else {
        System.out.println("No se encontró el equipo asignado.");
    }
}
    /*/////
     * MÉTODOS AUXILIARES
     *///
    public EstudianteIngenieria buscarEstudianteIngenieria(String cedula, List<EstudianteIngenieria> listaIng) {
        for (EstudianteIngenieria e : listaIng) {
            if (e.getCedula().equals(cedula)) {
                return e;
            }
        }
        return null;
    }
    
    public EstudianteDiseno buscarEstudianteDiseno(String cedula, List<EstudianteDiseno> listaDiseno) {
        for (EstudianteDiseno e : listaDiseno) {
            if (e.getCedula().equals(cedula)) {
                return e;
            }
        }
        return null;
    }
    
    public Portatil buscarPortatil(String serial, List<Portatil> listaPortatiles) {
        for (Portatil p : listaPortatiles) {
            if (p.getSerial().equals(serial)) {
                return p;
            }
        }
        return null;
    }
    
    public Tableta buscarTableta(String serial, List<Tableta> listaTabletas) {
        for (Tableta t : listaTabletas) {
            if (t.getSerial().equals(serial)) {
                return t;
            }
        }
        return null;
    }
    
    public boolean estaAsignadoIngenieria(String serial, List<EstudianteIngenieria> listaIng) {
        for (EstudianteIngenieria e : listaIng) {
            if (e.getCodigoEstudiante().equals(serial)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean estaAsignadoDiseno(String serial, List<EstudianteDiseno> listaDiseno) {
        for (EstudianteDiseno e : listaDiseno) {
            if (e.getCodigoEstudiante().equals(serial)) {
                return true;
            }
        }
        return false;
    }
public static String validarTexto(Scanner sc) {
    String texto;
    while (true) {
        texto = sc.nextLine().trim();
        if (texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {  // ✅ Permite solo letras y espacios
            return texto;
        } else {
            System.out.print("⚠️ Error: Solo se permiten letras y espacios. Inténtelo nuevamente: ");
        }
    }
}

public static String validarTelefono(Scanner sc) {
    String telefono;
    while (true) {
        System.out.print("Ingrese un número de teléfono válido: ");
        telefono = sc.nextLine().trim();
        if (telefono.matches("\\d{7,15}")) {  // ✅ Solo números, entre 7 y 15 dígitos
            return telefono;
        } else {
            System.out.println("⚠️ Error: El teléfono debe contener solo números y tener entre 7 y 15 dígitos.");
        }
    }
}

public static String validarModalidad(Scanner sc) {
    String modalidad;
    while (true) {
        System.out.print("Ingrese la modalidad (Virtual/Presencial): ");
        modalidad = sc.nextLine().trim().toLowerCase();
        if (modalidad.equals("virtual") || modalidad.equals("presencial")) {  
            return modalidad;
        } else {
            System.out.println("⚠️ Error: Debe ingresar 'Virtual' o 'Presencial'.");
        }
    }
}

public static int validarNumeroEntero(Scanner sc, int min, int max) {
    int numero;
    while (true) {
        try {
            System.out.print("Ingrese un número entre " + min + " y " + max + ": ");
            numero = Integer.parseInt(sc.nextLine());
            if (numero >= min && numero <= max) {
                return numero;
            } else {
                System.out.println("⚠️ Error: Debe ingresar un número entre " + min + " y " + max + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Error: Debe ingresar un número válido.");
        }
    }
}

public static double validarNumeroDecimal(Scanner sc, double min, double max) {
    double numero;
    while (true) {
        try {
            System.out.print("Ingrese un número entre " + min + " y " + max + ": ");
            numero = Double.parseDouble(sc.nextLine());
            if (numero >= min && numero <= max) {
                return numero;
            } else {
                System.out.println("⚠️ Error: Debe ingresar un número entre " + min + " y " + max + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Error: Debe ingresar un número válido.");
        }
    }
}

}
