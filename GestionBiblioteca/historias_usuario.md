# Historias de Usuario 
---

## STORY-001
**Tipo:** Historia de Usuario  
**Resumen:** Registro de nuevos usuarios

**Descripción:**  
Como usuario visitante  
Quiero registrarme en la plataforma  
Para acceder a funcionalidades como préstamo y reservas.

**Criterios de Aceptación:**  
- Dado que el visitante accede al formulario de registro  
  Cuando completa sus datos y envía el formulario  
  Entonces el sistema debe crear una cuenta y notificar al usuario.

---

## STORY-002  
**Tipo:** Historia de Usuario  
**Resumen:** Registro de devoluciones

**Descripción:**  
Como bibliotecario  
Quiero registrar la devolución de libros  
Para actualizar el estado del libro y liberar su disponibilidad para otros usuarios.

**Criterios de Aceptación:**  
- Dado que el bibliotecario escanea el código del libro  
  Cuando el estado es actualizado como "devuelto"  
  Entonces el libro queda disponible para nuevos préstamos.

---

## STORY-003  
**Tipo:** Historia de Usuario  
**Resumen:** Reporte de libros más prestados

**Descripción:**  
Como administrador  
Quiero generar reportes de libros más prestados en un período específico  
Para analizar las tendencias de uso y mejorar la colección.

**Criterios de Aceptación:**  
- Dado un rango de fechas  
  Cuando el administrador genera el reporte  
  Entonces debe mostrarse una lista ordenada por cantidad de préstamos.

---

## STORY-004  
**Tipo:** Historia de Usuario  
**Resumen:** Notificación de disponibilidad

**Descripción:**  
Como usuario registrado  
Quiero recibir notificaciones cuando un libro reservado esté disponible  
Para poder recogerlo a tiempo y no perder la reserva.

**Criterios de Aceptación:**  
- Dado que el libro reservado ha sido devuelto  
  Cuando está disponible para el siguiente en la lista  
  Entonces el sistema debe enviar una notificación al usuario.

---

## STORY-005  
**Tipo:** Historia de Usuario  
**Resumen:** Gestión de multas

**Descripción:**  
Como bibliotecario  
Quiero poder gestionar multas por devoluciones tardías  
Para incentivar la devolución puntual de los libros.

**Criterios de Aceptación:**  
- Dado un libro devuelto fuera de plazo  
  Cuando se registra la devolución  
  Entonces el sistema debe calcular e imponer la multa correspondiente.

---
