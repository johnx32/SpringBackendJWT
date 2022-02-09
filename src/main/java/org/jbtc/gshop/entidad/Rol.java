package org.jbtc.gshop.entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data @NoArgsConstructor
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	//Long id_usuario;
	@JsonIgnore
	private Date created_at;
	@JsonIgnore
	private Date updated_at;

	public Rol(String name) {
		this.name = name;
	}
}
