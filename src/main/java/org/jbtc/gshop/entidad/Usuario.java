package org.jbtc.gshop.entidad;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
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
	@JoinColumn(name="id_usuario")
	private List<Rol> roles;
	private Date created_at;
	private Date updated_at;

	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
