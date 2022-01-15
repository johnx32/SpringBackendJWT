package org.jbtc.gshop.dao;

import org.jbtc.gshop.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{

	public Usuario findByUsername(String username);

}
