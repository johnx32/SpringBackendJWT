package org.jbtc.gshop.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data @NoArgsConstructor
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

	public Producto(String img_url, String titulo, String descripcion, double precio, int count_ventas, int stock) {
		this.img_url = img_url;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.count_ventas = count_ventas;
		this.stock = stock;
	}
}
