public class EstudianteIngenieria extends Estudiante {
    private int semestre;
    private double promedio;

    public EstudianteIngenieria(String cedula, String nombre, String apellido, String telefono,
                                String codigoEstudiante, int semestre, double promedio) {
        super(cedula, nombre, apellido, telefono, codigoEstudiante);
        setSemestre(semestre);
        setPromedio(promedio);
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        if (semestre <= 0) {
            throw new IllegalArgumentException("El semestre debe ser un entero positivo.");
        }
        this.semestre = semestre;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        if (promedio < 0 || promedio > 5.0) {
            throw new IllegalArgumentException("El promedio debe estar entre 0.0 y 5.0.");
        }
        this.promedio = promedio;
    }

    public String toString() {
        return super.toString() + ", EstudianteIngenieria [semestre=" + semestre + ", promedio=" + promedio + "]";
    }
}