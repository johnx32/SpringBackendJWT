package org.jbtc.gshop.controller;

import org.jbtc.gshop.dao.ProductoDao;
import org.jbtc.gshop.entidad.Producto;
import org.jbtc.gshop.entidad.Rol;
import org.jbtc.gshop.repository.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

	//@Autowired
	//@Qualifier("algo")
	//ProductoDao productoDao;
	@Autowired
	//ProductoRepo productoRepo;
	ProductoDao productoDao;

	//@CrossOrigin(origins = "http://localhost:5500/")
	@GetMapping("hola")
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	//@Secured("ROLE_ADMIN")
	public String verProductos() {
		return "holas";
	}

	@PostMapping
	public ResponseEntity<String> addProducto(@RequestBody Producto producto){
		System.out.println("nuevo producto = "+producto);
		if(producto!=null){
			productoDao.save(producto);
			return new ResponseEntity<>("", HttpStatus.OK);
		}else
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "{id}")
	public Producto getProductoById(@PathVariable Long id){
		Producto p = productoDao.findById(id).get();
		if(p!=null)
			return p;
		else
			return new Producto("","","",0,0,0);
	}

	@GetMapping
	public Page<Producto> getProductos(@Param("titulo") String titulo, Pageable page){
		return productoDao.search(titulo,page);
	}

	@PutMapping
	public ResponseEntity<String> updateProducto(@RequestBody Producto producto){
		if(producto!=null) {
			if (producto.getId() != null && producto.getId() > 0)
				producto.setUpdated_at(new Date());
			if(productoDao.save(producto)!=null)
				return new ResponseEntity<>("", HttpStatus.OK);
			else return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
		}else return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<String> deleteProducto(Producto producto){
		try {
			productoDao.delete(producto);
			return new ResponseEntity<>("", HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}
	}
	
}
