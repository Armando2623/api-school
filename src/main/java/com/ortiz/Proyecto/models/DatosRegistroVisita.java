package com.ortiz.Proyecto.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroVisita(
              @NotBlank String dniVisitante,
              @NotBlank String nombreVisitante,
              @NotBlank String motivo,
              LocalDateTime horaIngreso,
              @NotBlank Long usuario_id,
              @NotNull EstadoRegistro estadoRegistro) {
}
