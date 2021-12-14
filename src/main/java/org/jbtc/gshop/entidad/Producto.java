package org.jbtc.gshop.entidad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String img_url;
	String titulo;
	String descripcion;
	double precio;
	int count_ventas;
	int stock;
	Date created_at;
	Date updated_at;
	//Long id_categoria;
	@ManyToOne(fetch = FetchType.LAZY)
	private Categoria categoria;
}
