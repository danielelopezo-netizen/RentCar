# 🚗 RentCar - Sistema Integral de Gestión de Alquiler de Vehículos

Proyecto desarrollado en **Java** con interfaz gráfica en **JavaFX**, aplicando la arquitectura **Modelo-Vista-Controlador (MVC)**, buenas prácticas de Programación Orientada a Objetos, principios **SOLID** y **Patrones Creacionales**.

---

## 📋 Descripción del Sistema

RentCar es una solución de software diseñada para automatizar y optimizar la administración de la empresa de alquiler de vehículos. El sistema permite:
- **Gestión de Clientes:** Registro completo de clientes (nombre, documento, teléfono, correo, edad, fecha de registro).
- **Consulta Especial de Clientes (Número Perfecto):** Búsqueda de clientes por su número de teléfono y verificación matemática de si dicho número corresponde a un **número perfecto** (un número cuya suma de divisores propios es igual a sí mismo, como 6, 28, 496).
- **Gestión de Flota de Vehículos:** Registro y visualización de vehículos con placa, marca, modelo, año, tipo y tarifa diaria.
- **Modalidades de Alquiler:** Catálogo de modalidades (*Económica*, *Ejecutiva* y *Premium*) con sus beneficios (kilometraje incluido, seguros, asistencia) y especificaciones premium (tipo de cobertura, conductores adicionales permitidos, servicios VIP).
- **Servicios Adicionales:** Catálogo de servicios opcionales (GPS, silla para bebé, conductor adicional, seguro complementario) que se asocian a las reservas.
- **Gestión de Reservas:** Creación guiada de reservas asociando cliente, vehículo, modalidad, servicios adicionales, periodo de fechas y posibles descuentos.
- **Reporte de Ingresos por Periodo:** Consulta y cálculo acumulado de los ingresos generados por las reservas realizadas en un rango de fechas determinado.

---

## 🏛️ Arquitectura del Software: Modelo - Vista - Controlador (MVC)

El sistema implementa una estricta separación de responsabilidades a través del patrón arquitectural **MVC**:

```
 ┌─────────────────────────────────────────────────────────────┐
 │                      VISTA (View)                           │
 │   - inicio.fxml (Definición jerárquica de la interfaz)      │
 │   - styles.css (Estilos visuales modernos)                  │
 └──────────────────────────────▲──────────────────────────────┘
                                │ Eventos y enlace de datos
 ┌──────────────────────────────┴──────────────────────────────┐
 │                   CONTROLADOR (Controller)                  │
 │   - InicioController.java                                   │
 │     * Manejo de eventos de botones                          │
 │     * Validaciones de formato y diálogos de alerta          │
 │     * Sincronización entre la vista y el modelo             │
 └──────────────────────────────▲──────────────────────────────┘
                                │ Manipulación e invocación
 ┌──────────────────────────────┴──────────────────────────────┐
 │                     MODELO (Model & Util)                   │
 │   - Empresa, Cliente, Vehiculo, Reserva                     │
 │   - ModalidadAlquiler (Económica, Ejecutiva, Premium)       │
 │   - ServicioAdicional, EstadoModalidad                      │
 │   - NumeroPerfectoUtil (Cálculo matemático puro)            │
 └─────────────────────────────────────────────────────────────┘
```

---

## 🧩 Principios SOLID Aplicados

1. **S - Single Responsibility Principle (Principio de Responsabilidad Única):**
   - La lógica matemática del número perfecto fue aislada en [`NumeroPerfectoUtil`](file:///src/main/java/util/NumeroPerfectoUtil.java), permitiendo que la entidad [`Cliente`](file:///src/main/java/model/Cliente.java) se concentre exclusivamente en los datos del cliente.
   - El controlador [`InicioController`](file:///src/main/java/controller/InicioController.java) solo gestiona la interacción UI, delegando toda la lógica de negocio a la clase [`Empresa`](file:///src/main/java/model/Empresa.java).

2. **O - Open/Closed Principle (Principio Abierto/Cerrado):**
   - La jerarquía de [`ModalidadAlquiler`](file:///src/main/java/model/ModalidadAlquiler.java) es abstracta y abierta a la adición de nuevas modalidades (ej. Ecológica, Blindada, Carga) sin necesidad de modificar el cálculo de tarifas general ni la clase `Reserva`.

3. **L - Liskov Substitution Principle (Principio de Sustitución de Liskov):**
   - Cualquier subclase (`ModalidadEconomica`, `ModalidadEjecutiva`, `ModalidadPremium`) puede sustituir a `ModalidadAlquiler` polimórficamente sin alterar la correctitud del programa.

4. **I - Interface Segregation Principle (Principio de Segregación de Interfaces):**
   - Clases con interfaces y métodos cohesivos y específicos; los clientes no dependen de métodos que no utilizan.

5. **D - Dependency Inversion Principle (Principio de Inversión de Dependencias):**
   - La clase `Reserva` depende de la abstracción `ModalidadAlquiler` y no de implementaciones concretas, garantizando bajo acoplamiento.

---

## ⚙️ Patrones Creacionales Implementados

### 1. Patrón Singleton
- **Clase:** [`Empresa`](file:///src/main/java/model/Empresa.java)
- **Propósito:** Garantiza que exista una única instancia global de la empresa durante el ciclo de vida de la aplicación, manteniendo sincronizados en memoria todos los clientes, vehículos, modalidades, servicios y reservas para todos los módulos de la interfaz gráfica.
- **Acceso:** `Empresa.getInstance()`.

### 2. Patrón Factory Method
- **Clase:** [`ModalidadFactory`](file:///src/main/java/model/ModalidadFactory.java)
- **Propósito:** Centraliza la creación e instanciación de las distintas modalidades de alquiler (`ModalidadEconomica`, `ModalidadEjecutiva`, `ModalidadPremium`), encapsulando las particularidades de cada subclase (como atributos exclusivos de cobertura y conductores adicionales de las modalidades Premium).

### 3. Patrón Builder
- **Clase:** [`ReservaBuilder`](file:///src/main/java/model/ReservaBuilder.java) (y método `Reserva.builder()`)
- **Propósito:** Permite la construcción fluida, controlada y por pasos de una `Reserva`. Valida la coherencia de datos (cliente y vehículo obligatorios, fechas válidas, duración mínima de la modalidad, servicios adicionales seleccionados) antes de construir el objeto inmutable.

---

## 💻 Requisitos y Ejecución

### Requisitos del Entorno:
- **Java JDK:** 17 o superior (compatible con JDK 25).
- **Maven:** Incluido a través de `mvnw` (Maven Wrapper).

### Cómo Ejecutar la Aplicación:

1. **Compilar el proyecto:**
   ```bash
   ./mvnw compile
   ```

2. **Iniciar la aplicación gráfica (JavaFX):**
   ```bash
   ./mvnw javafx:run
   ```

---

## 👥 Datos Semilla Pre-cargados para Demostración

La aplicación incluye datos iniciales listos para probar de inmediato:
- **Clientes:**
  - *Carlos Mendoza* (Teléfono: `28` - **¡Número Perfecto!**)
  - *Andrés Gómez* (Teléfono: `6` - **¡Número Perfecto!**)
  - *María González* (Teléfono: `3004567890` - No perfecto)
- **Vehículos:** Diversos modelos (Toyota Corolla, Mazda CX-5, BMW Serie 3, Renault Kwid).
- **Modalidades:** Económica, Ejecutiva y Premium con beneficios detallados.
- **Servicios:** Navegador GPS, Silla para Bebé, Conductor Adicional, Seguro Complementario.
- **Reservas de prueba:** Con fechas recientes para comprobar de inmediato el cálculo de ingresos por periodo.
