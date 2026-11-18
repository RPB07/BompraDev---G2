package br.edu.atitus.bompradev.catalog.repositorios;

import br.edu.atitus.bompradev.catalog.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
