package org.jbtc.gshop.entidad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	@OneToMany(
			//mappedBy = "Usuario",
			fetch = FetchType.LAZY
	)
	//@JoinColumn(name="rol_id")
	private List<Rol> roles;
	

}
