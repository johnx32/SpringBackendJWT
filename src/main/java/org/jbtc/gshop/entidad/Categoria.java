package org.jbtc.gshop.entidad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String nombre;
	Date created_at;
	Date updated_at;


}
