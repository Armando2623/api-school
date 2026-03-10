package com.ortiz.Proyecto.models;

import java.time.LocalDateTime;

public record DatosListaVisitas(
         String nombreVisitante,
         String motivo,
         LocalDateTime horaIngreso,
         Long usuario_id,
         EstadoRegistro estadoRegistro
) {
public DatosListaVisitas(RegistroVisita registro){
this(
registro.getNombreVisitante(),
registro.getMotivo(),
registro.getHoraIngreso(),
registro.getId(),
registro.getEstadoRegistro()
);
}
}
