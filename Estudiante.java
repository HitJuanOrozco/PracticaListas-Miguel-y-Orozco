public abstract class Estudiante {
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String codigoEstudiante;

    public Estudiante(String cedula, String nombre, String apellido, String telefono, String codigoEstudiante) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
        setCodigoEstudiante(codigoEstudiante);
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (!cedula.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("La cedula solo puede contener letras y numeros sin caracteres especiales.");
        }
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (!telefono.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("El telefono solo puede contener letras y numeros sin caracteres especiales.");
        }
        this.telefono = telefono;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        if (!codigoEstudiante.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("El codigo solo puede contener letras y numeros sin caracteres especiales.");
        }
        this.codigoEstudiante = codigoEstudiante;
    }

    public String toString() {
        return "Estudiante [cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido +
               ", telefono=" + telefono + ", codigoEstudiante=" + codigoEstudiante + "]";
    }
}