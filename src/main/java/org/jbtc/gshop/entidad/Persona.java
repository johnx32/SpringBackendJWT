package org.jbtc.gshop.entidad;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Persona {
	@Id
	Long id;
	String name;

}
