package ec.edu.epn.encuentraepn;

import ec.edu.epn.encuentraepn.modelo.Campus;
import ec.edu.epn.encuentraepn.modelo.Categoria;
import ec.edu.epn.encuentraepn.modelo.DatosReporte;
import ec.edu.epn.encuentraepn.modelo.Reclamacion;
import ec.edu.epn.encuentraepn.modelo.Reporte;
import ec.edu.epn.encuentraepn.modelo.TipoReporte;
import ec.edu.epn.encuentraepn.modelo.Ubicacion;
import ec.edu.epn.encuentraepn.modelo.Usuario;

import java.time.LocalDate;
import java.util.List;

/**
 * Datos de ejemplo de las pruebas unitarias, tomados de los casos de prueba de la Tarea 1:
 * Bruno Salas encontró una calculadora y Andrea Paz la reclama.
 */
public final class DatosDePrueba {

    public static final String MOTIVO = "Las respuestas no coinciden con el objeto";

    private DatosDePrueba() {
    }

    /**
     * Reclamación recién registrada (PENDIENTE) sobre un objeto encontrado.
     */
    public static Reclamacion reclamacionPendiente() {
        return reclamacionPendiente(new Usuario("Bruno Salas", "bruno.salas@epn.edu.ec"));
    }

    /**
     * Reclamación recién registrada (PENDIENTE) sobre un objeto que registró el encontrante indicado.
     */
    public static Reclamacion reclamacionPendiente(Usuario encontrante) {
        DatosReporte datos = new DatosReporte(TipoReporte.ENCONTRADO, null, "Calculadora científica negra",
                LocalDate.of(2026, 9, 28), -0.21045, -78.48920, List.of("¿Qué iniciales tiene detrás?"));
        Reporte reporte = new Reporte(datos, encontrante, new Categoria("Calculadoras"),
                new Ubicacion(-0.21045, -78.48920, Campus.ZONA_CENTRO));
        return new Reclamacion(new Usuario("Andrea Paz", "andrea.paz@epn.edu.ec"), reporte);
    }
}
