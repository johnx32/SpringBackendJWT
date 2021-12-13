package org.jbtc.gshop.dao;

import org.jbtc.gshop.entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository<Producto, Long>{

}
