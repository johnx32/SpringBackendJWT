package org.jbtc.gshop.dao;

import org.jbtc.gshop.entidad.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoDao extends JpaRepository<Producto, Long>{

    @Query("SELECT p FROM Producto p WHERE (:titulo is null or p.titulo like %:titulo%)")
    Page<Producto> search(String titulo, Pageable pageable);

}
