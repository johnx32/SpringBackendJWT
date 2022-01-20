package org.jbtc.gshop.controller;

import java.util.Date;
import java.util.List;

import org.jbtc.gshop.dao.CategoriaDao;
import org.jbtc.gshop.entidad.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaDao categoriaDao;

	@Secured("ROLE_ADMIN")
	@PostMapping
	public void insertCategoria(@RequestBody Categoria categoria) {
		if(categoria!=null && categoria.getId()>0) {
			categoria.setCreated_at(new Date());
		}
		System.out.println("categoria recivida: "+categoria);
		categoriaDao.save(categoria);
	}

	//@CrossOrigin(origins = "http://localhost:5500")
	//@CrossOrigin(origins = "*")
	@GetMapping(value = "{id}")
	public Categoria getCategoriaById(@PathVariable Long id) {
		Categoria c;
		try {
			c = categoriaDao.findById(id).get();
		}catch(Exception e) {
			c = new Categoria(0L,"vacio");
		}
		return c;
	}

	@GetMapping
	public List<Categoria> getCategorias(){
		return categoriaDao.findAll();
	}

	@Secured("ROLE_ADMIN")
	@PutMapping
	public void updateCategoria(@RequestBody Categoria categoria) {
		if(categoria!=null && categoria.getId()!=null && categoria.getId()>0){
			categoria.setUpdated_at(new Date());
		}
		System.out.println("categoria actualizada: "+categoria);
		/*Categoria c;
		try {
			c = categoriaDao.findById(id).get();
		}catch(Exception e) {
			c = new Categoria(0L,"vacio");
		}*/
		categoriaDao.save(categoria);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping(value = "{id}")
	public void deleteCategoria(Categoria c) {
		categoriaDao.delete(c);
	}
	
}
