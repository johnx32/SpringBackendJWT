package org.jbtc.gshop.entidad;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	@JsonIgnore
	private String password;
	@ManyToMany(
		//mappedBy = "Usuario",
		fetch = FetchType.LAZY
	)
	//@JoinColumn(name="id_usuario")
	@JoinTable(name = "usuario_roles"
		,joinColumns = @JoinColumn(name = "usuario_id")
		,inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Rol> roles;
	@JsonIgnore
	private Date created_at;
	@JsonIgnore
	private Date updated_at;

	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
