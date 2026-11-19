package br.edu.atitus.bompradev.scheduling.dtos;

import java.util.UUID;

public record ServicoCatalogo(UUID id, UUID providerId, Integer duracaoMinutos) {}
