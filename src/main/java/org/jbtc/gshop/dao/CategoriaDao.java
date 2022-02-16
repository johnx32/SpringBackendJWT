package org.jbtc.gshop.dao;

import org.jbtc.gshop.entidad.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaDao extends JpaRepository<Categoria, Long>{

    @Query("SELECT c FROM Categoria c WHERE (:nombre is null or c.nombre like %:nombre%)")
    Page<Categoria> search(String nombre, Pageable pageable);

}
