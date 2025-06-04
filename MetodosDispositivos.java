import java.util.List;

public class MetodosDispositivos {

    /**
     * Imprime el inventario total de dispositivos, mostrando primero portátiles y luego tabletas.
     */
    public void imprimirInventarioTotal(List<Portatil> listaPortatiles, List<Tableta> listaTabletas) {
        System.out.println("\n======= INVENTARIO DE DISPOSITIVOS =======");

        System.out.println("\n--- Portátiles ---");
        if (listaPortatiles.isEmpty()) {
            System.out.println("No hay portátiles en el inventario.");
        } else {
            for (Portatil p : listaPortatiles) {
                System.out.println(p);
            }
        }

        System.out.println("\n--- Tabletas ---");
        if (listaTabletas.isEmpty()) {
            System.out.println("No hay tabletas en el inventario.");
        } else {
            for (Tableta t : listaTabletas) {
                System.out.println(t);
            }
        }
    }
    
    // Métodos auxiliares para la búsqueda en las listas.
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
}
