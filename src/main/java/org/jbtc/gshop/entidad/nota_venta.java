package org.jbtc.gshop.entidad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class nota_venta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	double precio_total;
	String direccion;
	Date created_at;
	Date updated_at;
	Long id_cliente;
}
