public class DispositivoElectronico {
    private String serial;
    private String marca;
    private double tamano;  // En pulgadas, sin tilde para evitar problemas
    private double precio;
    private double peso;    // En kg

    public DispositivoElectronico(String serial, String marca, double tamano, double precio, double peso) {
        setSerial(serial);
        setMarca(marca);
        setTamano(tamano);
        setPrecio(precio);
        setPeso(peso);
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        if (!serial.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("El serial solo puede contener letras y números sin caracteres especiales.");
        }
        this.serial = serial;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (!marca.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            throw new IllegalArgumentException("La marca solo puede contener letras y espacios.");
        }
        this.marca = marca;
    }

    public double getTamano() {
        return tamano;
    }

    public void setTamano(double tamano) {
        if (tamano <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser un número positivo.");
        }
        this.tamano = tamano;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser un número positivo.");
        }
        this.precio = precio;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser un número positivo.");
        }
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "DispositivoElectronico [serial=" + serial + ", marca=" + marca + ", tamano=" + tamano +
               ", precio=" + precio + ", peso=" + peso + "]";
    }
}