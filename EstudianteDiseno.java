public class EstudianteDiseno extends Estudiante {
    private String modalidad;
    private int asignaturas;

    public EstudianteDiseno(String cedula, String nombre, String apellido, String telefono,
                            String codigoEstudiante, String modalidad, int asignaturas) {
        super(cedula, nombre, apellido, telefono, codigoEstudiante);
        setModalidad(modalidad);
        setAsignaturas(asignaturas);
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        if (!modalidad.equalsIgnoreCase("virtual") && !modalidad.equalsIgnoreCase("presencial")) {
            throw new IllegalArgumentException("La modalidad debe ser 'virtual' o 'presencial'.");
        }
        this.modalidad = modalidad;
    }

    public int getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(int asignaturas) {
        if (asignaturas <= 0) {
            throw new IllegalArgumentException("El número de asignaturas debe ser un entero positivo.");
        }
        this.asignaturas = asignaturas;
    }

    public String toString() {
        return super.toString() + ", EstudianteDiseno [modalidad=" + modalidad + ", asignaturas=" + asignaturas + "]";
    }
}