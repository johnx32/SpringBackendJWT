package org.jbtc.gshop.controller;

import org.jbtc.gshop.repository.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/producto")
public class ProductoController {

	//@Autowired
	//@Qualifier("algo")
	//ProductoDao productoDao;
	@Autowired
	ProductoRepo productoRepo;
	
	@GetMapping("hola")
	public String verProductos() {
		return "holas";
	}
	
}
