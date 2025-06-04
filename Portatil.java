public class Portatil extends DispositivoElectronico {
    private int almacenamiento; // en GB
    private int sistemaOperativo;
    private int procesador;

    public static final int SO_WINDOWS = 0;
    public static final int SO_MACOS = 1;
    public static final int SO_LINUX = 2;

    public static final int PROC_INTEL = 0;
    public static final int PROC_AMD = 1;
    public static final int PROC_ARM = 2;

    public Portatil(String serial, String marca, double tamano, double precio, double peso,
                    int almacenamiento, int sistemaOperativo, int procesador) {
        super(serial, marca, tamano, precio, peso);
        setAlmacenamiento(almacenamiento);
        setSistemaOperativo(sistemaOperativo);
        setProcesador(procesador);
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

    public int getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(int sistemaOperativo) {
        if (sistemaOperativo < 0 || sistemaOperativo > 2) {
            throw new IllegalArgumentException("Sistema operativo inválido.");
        }
        this.sistemaOperativo = sistemaOperativo;
    }

    public int getProcesador() {
        return procesador;
    }

    public void setProcesador(int procesador) {
        if (procesador < 0 || procesador > 2) {
            throw new IllegalArgumentException("Procesador inválido.");
        }
        this.procesador = procesador;
    }

    private String sistemaOperativoToString() {
        switch (sistemaOperativo) {
            case SO_WINDOWS: return "Windows";
            case SO_MACOS: return "MacOS";
            case SO_LINUX: return "Linux";
            default: return "Desconocido";
        }
    }

    private String procesadorToString() {
        switch (procesador) {
            case PROC_INTEL: return "Intel";
            case PROC_AMD: return "AMD";
            case PROC_ARM: return "ARM";
            default: return "Desconocido";
        }
    }

    public String toString() {
        return "Portatil [serial=" + getSerial() +
               ", marca=" + getMarca() +
               ", tamaño=" + getTamano() + " pulgadas" +
               ", precio=$" + getPrecio() +
               ", peso=" + getPeso() + " kg" +
               ", almacenamiento=" + almacenamiento + " GB" +
               ", sistemaOperativo=" + sistemaOperativoToString() +
               ", procesador=" + procesadorToString() + "]";
    }
}