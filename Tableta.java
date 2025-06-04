public class Tableta extends DispositivoElectronico {
    private int almacenamiento; // en GB

    public Tableta(String serial, String marca, double tamano, double precio, double peso, int almacenamiento) {
        super(serial, marca, tamano, precio, peso);
        setAlmacenamiento(almacenamiento);
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        if (almacenamiento <= 0) {
            throw new IllegalArgumentException("El almacenamiento debe ser un número positivo.");
        }
        this.almacenamiento = almacenamiento;
    }

    @Override
    public String toString() {
        return "Tableta [serial=" + getSerial() +
               ", marca=" + getMarca() +
               ", tamano=" + getTamano() + " pulgadas" +
               ", precio=$" + getPrecio() +
               ", peso=" + getPeso() + " kg" +
               ", almacenamiento=" + almacenamiento + " GB]";
    }
}