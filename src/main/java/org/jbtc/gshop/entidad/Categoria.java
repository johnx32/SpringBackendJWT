package org.jbtc.gshop.entidad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String nombre;
	//@DateTimeFormat
	Date created_at;
	//@DateTimeFormat
	Date updated_at;
	
	public Categoria() {
	}
	public Categoria(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	public Categoria(String nombre) {
		this.nombre = nombre;
	}

	@PrePersist
	private void antesDePersistir(){
		this.created_at = new Date();
	}
	
	@PreUpdate
	private void despuesDePersistir() {
		this.created_at = new Date();
	}
	
}
