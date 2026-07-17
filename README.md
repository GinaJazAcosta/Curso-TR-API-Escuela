# 📚 Contrato de API - Sistema Escuela

## Descripción

Este documento define el **contrato de la API REST** del Sistema Escuela.

Aquí se especifican los datos que cada endpoint recibe (**Request**), la información que devuelve (**Response**) y las reglas de negocio que deben cumplirse durante la implementación.

Este contrato funciona como guía técnica y como referencia para validar el correcto funcionamiento de cada módulo.

---

# 📑 Índice

- Reglas generales
- Alumnos
- Maestros
- Aulas
- Cursos
- Grupos
- Horarios
- Inscripciones
- Calificaciones

---

# ⚠️ Reglas Generales

Todos los módulos deben cumplir las siguientes reglas:

| Código | Descripción |
|---------|-------------|
| **400 Bad Request** | `IllegalArgumentException`: errores de validación o reglas de negocio. |
| **404 Not Found** | `NoSuchElementException`: entidad no encontrada. |
| **409 Conflict** | Intento de eliminar una entidad padre que posee relaciones hijas. |

### Validaciones generales

- Todos los IDs enviados en los requests deben existir.
- Si una entidad relacionada no existe debe responder **404 Not Found**.
- Todas las reglas de unicidad deben respetarse tanto al crear como al actualizar registros.

---

# 👨‍🎓 Módulo: Alumnos

## Objetivo

Administrar la información de los alumnos de la institución.

## Funcionalidades

- Registrar alumnos.
- Consultar uno o varios alumnos.
- Actualizar información.
- Eliminar alumnos sin inscripciones.
- Mostrar materias inscritas.
- Mostrar calificaciones.
- Calcular promedio.

---

## POST /alumnos

### Request

```json
{
  "nombre": "Carlos",
  "apellidoPaterno": "González",
  "apellidoMaterno": "Ramírez"
}
```

### Reglas

- La matrícula se genera automáticamente.
- El correo institucional se genera automáticamente.
- Si cambia el nombre o apellidos se regeneran ambos.

### Response

```json
{
  "id": 1,
  "nombre": "Juan Pérez López",
  "email": "juan.perez@alumnos.com",
  "matricula": "A2025001",
  "fechaIngreso": "10/01/2025",
  "calificaciones": [
    {
      "curso": "Matemáticas I",
      "periodo": "2025-1",
      "calificacion": null
    },
    {
      "curso": "Bases de Datos",
      "periodo": "2025-1",
      "calificacion": 8
    }
  ],
  "promedio": 8.00
}
```

### Reglas para el promedio

- Solo se consideran calificaciones diferentes de `null`.
- Las materias sin calificación no afectan el promedio.
- Si no existen calificaciones el promedio será **0.00**.

---

# 👨‍🏫 Módulo: Maestros

## Objetivo

Administrar los docentes y los cursos que imparten.

## Funcionalidades

- Registrar maestros.
- Consultar maestros.
- Actualizar información.
- Eliminar maestros sin grupos asignados.
- Consultar cursos impartidos.

---

## POST /maestros

### Request

```json
{
  "nombre": "Ana",
  "apellidoPaterno": "Hernández",
  "apellidoMaterno": "Sánchez",
  "email": "ana.her@correo.com",
  "telefono": "5512345678"
}
```

### Reglas

- El email debe ser único.
- El teléfono debe ser único.
- Al actualizar se excluye el propio registro de la validación.

### Response

```json
{
  "id": 1,
  "nombre": "Laura Martínez Martínez",
  "email": "laura.martinez@escuela.com",
  "telefono": "5551010789",
  "cursos": [
    {
      "nombre": "Matemáticas I",
      "descripcion": "Fundamentos matemáticos para nivel básico",
      "creditos": 6
    }
  ]
}
```

---

# 🏫 Módulo: Aulas

## Objetivo

Administrar las aulas disponibles para impartir clases.

## Funcionalidades

- Registrar aulas.
- Consultar aulas.
- Actualizar aulas.
- Eliminar aulas sin grupos asignados.

---

## POST /aulas

### Request

```json
{
  "nombre": "Aula 101",
  "capacidad": 30
}
```

### Reglas

- El nombre debe ser único.

### Response

```json
{
  "id": 1,
  "nombre": "Aula 101",
  "capacidad": 30
}
```

---

# 📖 Módulo: Cursos

## Objetivo

Administrar el catálogo de cursos.

## Funcionalidades

- Registrar cursos.
- Consultar cursos.
- Actualizar cursos.
- Eliminar cursos sin grupos asociados.

---

## POST /cursos

### Request

```json
{
  "nombre": "Matemáticas I",
  "descripcion": "Fundamentos matemáticos para nivel básico",
  "creditos": 6
}
```

### Reglas

- El nombre del curso debe ser único.

### Response

```json
{
  "id": 1,
  "nombre": "Matemáticas I",
  "descripcion": "Fundamentos matemáticos para nivel básico",
  "creditos": 6
}
```

---

# 👥 Módulo: Grupos

## Objetivo

Administrar los grupos académicos.

Cada grupo representa la relación entre:

- Curso
- Maestro
- Aula
- Periodo

---

## POST /grupos

### Request

```json
{
  "idCurso": 2,
  "idMaestro": 7,
  "idAula": 3,
  "periodo": "2026-01"
}
```

### Reglas

La combinación:

- Curso
- Maestro
- Aula
- Periodo

debe ser única.

### Response

```json
{
  "id": 1,
  "curso": {
    "nombre": "Matemáticas I",
    "descripcion": "Fundamentos matemáticos para nivel básico",
    "creditos": 6
  },
  "maestro": {
    "nombre": "Laura Martínez Martínez",
    "email": "laura.martinez@escuela.com",
    "telefono": "5551010789"
  },
  "aula": {
    "nombre": "Aula 101",
    "capacidad": 30
  },
  "horarios": [
    "Lunes 08:00 - 10:00",
    "Miércoles 08:00 - 10:00"
  ],
  "periodo": "2026-01"
}
```

---

# 🕒 Módulo: Horarios

## Objetivo

Administrar los horarios asignados a los grupos.

---

## POST /horarios

### Request

```json
{
  "idGrupo": 1,
  "dia": "Lunes",
  "horaInicio": "08:00",
  "horaFin": "10:00"
}
```

### Reglas

- Formato **HH:mm**
- La hora final debe ser mayor que la inicial.
- No puede existir traslape de horarios por grupo.
- No puede existir traslape de horarios por aula.

### Response

```json
{
  "id": 1,
  "grupo": {
    "curso": "Matemáticas I",
    "maestro": "Laura Martínez Martínez",
    "aula": "Aula 101",
    "periodo": "2025-1"
  },
  "horario": "Lunes 08:00 - 10:00"
}
```

---

# 📝 Módulo: Inscripciones

## Objetivo

Administrar la inscripción de alumnos a grupos.

---

## POST /inscripciones

### Request

```json
{
  "idAlumno": 10,
  "idGrupo": 5
}
```

### Reglas

La combinación **Alumno + Grupo** debe ser única.

### Response

```json
{
  "id": 1,
  "alumno": {
    "nombre": "Juan Pérez López",
    "matricula": "A2025001",
    "email": "juan.perez@alumnos.com",
    "fechaIngreso": "10/01/2025"
  },
  "grupo": {
    "curso": "Matemáticas I",
    "maestro": "Laura Martínez Martínez",
    "aula": "Aula 101",
    "periodo": "2025-1"
  },
  "calificacion": null,
  "fechaInscripcion": "11/02/2026"
}
```

---

# 📊 Módulo: Calificaciones

## Objetivo

Administrar las calificaciones de los alumnos.

---

## POST /calificaciones

### Request

```json
{
  "idInscripcion": 15,
  "calificacion": 8.5
}
```

### Reglas

- Solo puede existir una calificación por inscripción.

### Response

```json
{
  "id": 2,
  "inscripcion": {
    "alumno": {
      "nombre": "María Gómez Ramos",
      "matricula": "A2025002",
      "email": "maria.gomez@alumnos.com",
      "fechaIngreso": "11/01/2025"
    },
    "grupo": {
      "curso": "Matemáticas I",
      "maestro": "Laura Martínez Martínez",
      "aula": "Aula 101",
      "periodo": "2025-1"
    },
    "fechaInscripcion": "11/02/2026"
  },
  "calificacion": 9,
  "fechaRegistro": "11/02/2026"
}
```
