package ec.edu.epn.encuentraepn.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Incremento 3: rechazar una reclamación. Pruebas de las reglas de la entidad Reclamacion.
 */
class ReclamacionTest {

    @Test
    @DisplayName("Rechazar una reclamación pendiente la deja en estado RECHAZADA")
    void rechazarUnaReclamacionPendienteLaDejaRechazada() {
        Usuario encontrante = new Usuario("Bruno Salas", "bruno.salas@epn.edu.ec");
        Usuario reclamante = new Usuario("Andrea Paz", "andrea.paz@epn.edu.ec");
        DatosReporte datos = new DatosReporte(TipoReporte.ENCONTRADO, null, "Calculadora científica negra",
                LocalDate.of(2026, 9, 28), -0.21045, -78.48920, List.of("¿Qué iniciales tiene detrás?"));
        Reporte reporte = new Reporte(datos, encontrante, new Categoria("Calculadoras"),
                new Ubicacion(-0.21045, -78.48920, Campus.ZONA_CENTRO));
        Reclamacion reclamacion = new Reclamacion(reclamante, reporte);

        reclamacion.rechazar("Las respuestas no coinciden con el objeto");

        assertEquals(EstadoReclamacion.RECHAZADA, reclamacion.getEstado());
    }
}
