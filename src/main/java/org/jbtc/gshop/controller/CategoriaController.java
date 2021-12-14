package org.jbtc.gshop.controller;

import org.jbtc.gshop.dao.CategoriaDao;
import org.jbtc.gshop.entidad.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaDao categoriaDao;
	
	@GetMapping(value = "{id}")
	public Categoria getCategoriaById(@PathVariable Long id) {
		Categoria c = categoriaDao.findById(id).get();
		return c;
	}
	
	@PostMapping
	public void insertCategoria(@RequestBody Categoria categoria) {
		categoriaDao.save(categoria);
	}
	
	@PutMapping
	public void updateCategoria() {
		
	}
	
	@DeleteMapping
	public void deleteCategoria() {
		
	}
	
	

}
