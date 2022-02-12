package org.jbtc.gshop.dao;

import org.jbtc.gshop.entidad.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{

	Usuario findByUsername(String username);

	@Query("SELECT u FROM Usuario u WHERE (:name is null or u.username like %:name%)")
	Page<Usuario> search(String name, Pageable pageable);

}
