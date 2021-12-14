package org.jbtc.gshop.entidad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class detalle_venta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	double precio;
	Date created_at;
	Date updated_at;
	Long id_producto;
	Long id_notav;
}
