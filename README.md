# GR05_1BP2_622_26B: EncuentraEPN

Sistema web georreferenciado de objetos perdidos y encontrados en el campus de la Escuela Politécnica Nacional.

Práctica 2 de Metodologías Ágiles (ISWD622, EPN, 2026-B), Grupo 5: desarrollo dirigido por pruebas (TDD) de un incremento nuevo sobre la aplicación de la Tarea 3.

## Casos de uso implementados

| Incremento | Caso de uso | Control (Servlet) | Vistas (JSP) |
|---|---|---|---|
| 1 | CU-01 Registrar objeto perdido o encontrado | `ControlRegistroReporte` | `formularioReporte.jsp` |
| 1 | CU-02 Consultar y filtrar reportes en el mapa | `ControlConsultaReportes` | `mapaReportes.jsp`, `detalleReporte.jsp` |
| 2 | CU-03 Reclamar un objeto encontrado | `ControlReclamaciones` | `formularioReclamacion.jsp` |
| 2 | CU-04 Gestionar devolución del objeto | `ControlDevoluciones` | `panelDevolucion.jsp`, `vistaAcuerdoEntrega.jsp` |
| 3 | Rechazar una reclamación (Práctica 2, desarrollado con TDD) | `ControlDevoluciones` | `panelDevolucion.jsp`, `inicio.jsp` |

## Trazabilidad de los modelos al código

El código conserva los nombres de los diagramas de la Tarea 1:

| En los modelos | En el código |
|---|---|
| Clases «entity» (`Reporte`, `Reclamacion`, `AcuerdoEntrega`…) | Entidades JPA del paquete `modelo`, con los mismos atributos y operaciones |
| Clases «control» (`ControlRegistroReporte`…) | Servlets del paquete `controlador`, con las mismas operaciones |
| Clases «boundary» (`FormularioReporte`, `MapaReportes`…) | Páginas JSP de `WEB-INF/vistas` con el mismo nombre |
| Mensajes de los diagramas de secuencia (SEC-01 a SEC-04) | Comentarios `// SEC-0n · número de mensaje` junto a la línea que realiza cada mensaje |

Los diagramas de diseño, ya adaptados a Java Web, están en [`docs/modelos`](docs/modelos): las fuentes PlantUML en `src` y las imágenes en `img`.

## Tecnologías

| Capa | Tecnología |
|---|---|
| Vista | JSP con JSTL; mapa con Leaflet y OpenStreetMap |
| Controlador | Servlets (Jakarta Servlet 6) |
| Persistencia (ORM) | JPA con Hibernate |
| Base de datos | H2 embebida (archivo en `~/gr05_1bp2/`) |
| Servidor | Apache Tomcat 10.1 |
| Construcción | Maven |
| Pruebas unitarias | JUnit 5 y Mockito |

No se usan frameworks de aplicación.

## Estructura

```
src/main/java/ec/edu/epn/encuentraepn/
├── modelo/        entidades JPA, enumeraciones y datos de apoyo (Campus, DatosReporte, FiltrosReporte, DatosPublicos)
├── dao/           JPAUtil, GenericDAO<T> y un DAO por entidad raíz
├── controlador/   un Servlet por clase de control, más la identificación y la página de inicio
└── web/           filtros, sesión del usuario y carga inicial de categorías
src/main/webapp/
├── WEB-INF/vistas/   una JSP por clase de interfaz
├── css/, js/         estilos y mapa
└── lib/leaflet/      librería del mapa
src/test/java/        pruebas unitarias (JUnit 5 y Mockito)
docs/modelos/         diagramas de clases y de secuencia del diseño
pruebas/              casos de prueba automatizados en el navegador (CP-01A a CP-05)
```

## Cómo ejecutar

Solo se necesita Java 17 o superior. Maven y Tomcat se descargan automáticamente la primera vez.

```bash
# Windows (PowerShell o símbolo del sistema)
.\mvnw.cmd package cargo:run

# Linux o macOS
./mvnw package cargo:run
```

Luego abrir <http://localhost:8080/GR05_1BP2_622_26B/>. Para detener el servidor, presionar `Ctrl + C`.

Para probar el flujo completo se necesitan dos usuarios, por ejemplo en dos navegadores: uno registra un objeto encontrado y el otro lo reclama. La identificación pide solo el nombre y un correo `@epn.edu.ec`.

## Casos de prueba

`pruebas/casos-de-prueba.js` ejecuta en un navegador los cinco casos de prueba del flujo básico definidos en la Tarea 1 y el caso CP-05 del Incremento 3 (rechazar una reclamación), con dos usuarios en sesiones separadas, y comprueba el resultado esperado de cada paso. Con la aplicación en ejecución y la base de datos vacía:

```bash
cd pruebas
npm install puppeteer-core
node casos-de-prueba.js capturas
```

## Incremento 3: rechazar una reclamación (TDD)

Quien registró un objeto encontrado puede rechazar una reclamación pendiente indicando el motivo. La reclamación queda RECHAZADA, el reclamante ve el motivo en «Mis reclamaciones» y el reporte sigue activo para otras reclamaciones.

Se desarrolló con TDD: cada prueba tiene tres commits, `TDD n · Rojo`, `TDD n · Verde` y `TDD n · Refactor`.

| N.º | Tipo | Prueba | Qué comprueba |
|---|---|---|---|
| 1 | Unitaria | `ReclamacionTest` | Al rechazar una reclamación pendiente, queda RECHAZADA |
| 2 | Unitaria | `ReclamacionTest` | Se guardan el motivo, sin espacios sobrantes, y la fecha del rechazo |
| 3 | Unitaria | `ReclamacionTest` | Una reclamación ya aceptada no se puede rechazar |
| 4 | Unitaria | `ReclamacionTest` | Un motivo de más de 200 caracteres no se acepta |
| 5 | Con mocks (Mockito) | `ControlDevolucionesTest` | El control guarda el rechazo con un DAO simulado, sin base de datos |
| 6 | Con parámetros | `ReclamacionTest` | Un motivo vacío o en blanco no se acepta (un caso por fila) |

## Origen del código

El punto de partida es la aplicación de la Tarea 3 después de las refactorizaciones de la Práctica 1: repositorio [GR05_1BT3_622_26B](https://github.com/JoelAlexanderProg/GR05_1BT3_622_26B), commit [`5a1696a`](https://github.com/JoelAlexanderProg/GR05_1BT3_622_26B/commit/5a1696a).

## Cómo ejecutar las pruebas unitarias

Solo se necesita Java 17 o superior. Desde la carpeta del proyecto:

    .\mvnw.cmd test      (Windows)
    ./mvnw test          (Linux o macOS)

Maven ejecuta todas las pruebas de `src/test/java` y al final muestra cuántas pasaron. Si alguna falla, indica cuál y por qué.

## Integrantes

- Lenin Alejandro Jerez Chimbo
- Dylan Isaí Maldonado Morales
- Joel Alexander Places Lucero
- Jeimy Jhair Sánchez Tixe
- Francisco Gabriel Villalba Portilla
