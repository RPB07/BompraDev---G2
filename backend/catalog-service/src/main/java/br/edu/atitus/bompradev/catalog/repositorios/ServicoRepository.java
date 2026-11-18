package br.edu.atitus.bompradev.catalog.repositorios;

import br.edu.atitus.bompradev.catalog.entidades.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServicoRepository extends JpaRepository<Servico, UUID> {
}
