package ec.edu.epn.encuentraepn.controlador;

import ec.edu.epn.encuentraepn.dao.ReclamacionDAO;
import ec.edu.epn.encuentraepn.modelo.Campus;
import ec.edu.epn.encuentraepn.modelo.Categoria;
import ec.edu.epn.encuentraepn.modelo.DatosReporte;
import ec.edu.epn.encuentraepn.modelo.EstadoReclamacion;
import ec.edu.epn.encuentraepn.modelo.Reclamacion;
import ec.edu.epn.encuentraepn.modelo.Reporte;
import ec.edu.epn.encuentraepn.modelo.TipoReporte;
import ec.edu.epn.encuentraepn.modelo.Ubicacion;
import ec.edu.epn.encuentraepn.modelo.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Incremento 3: rechazar una reclamación. Prueba del control con el acceso a datos simulado.
 */
@ExtendWith(MockitoExtension.class)
class ControlDevolucionesTest {

    @Mock
    private ReclamacionDAO reclamacionDAO;

    @Test
    @DisplayName("Rechazar una reclamación la guarda con el DAO, sin usar la base de datos")
    void rechazarReclamacionLaGuardaConElDAO() {
        Usuario encontrante = new Usuario("Bruno Salas", "bruno.salas@epn.edu.ec");
        Usuario reclamante = new Usuario("Andrea Paz", "andrea.paz@epn.edu.ec");
        DatosReporte datos = new DatosReporte(TipoReporte.ENCONTRADO, null, "Calculadora científica negra",
                LocalDate.of(2026, 9, 28), -0.21045, -78.48920, List.of("¿Qué iniciales tiene detrás?"));
        Reporte reporte = new Reporte(datos, encontrante, new Categoria("Calculadoras"),
                new Ubicacion(-0.21045, -78.48920, Campus.ZONA_CENTRO));
        Reclamacion reclamacion = new Reclamacion(reclamante, reporte);
        UUID id = UUID.randomUUID();
        when(reclamacionDAO.buscarPorId(id)).thenReturn(reclamacion);
        ControlDevoluciones control = new ControlDevoluciones(reclamacionDAO);

        control.rechazarReclamacion(encontrante, id, "Las respuestas no coinciden con el objeto");

        verify(reclamacionDAO).actualizar(reclamacion);
        assertEquals(EstadoReclamacion.RECHAZADA, reclamacion.getEstado());
    }
}
