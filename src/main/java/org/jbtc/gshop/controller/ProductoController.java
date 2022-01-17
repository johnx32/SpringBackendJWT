package org.jbtc.gshop.controller;

import org.jbtc.gshop.repository.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Secured("ROLE_ADMIN")
	public String verProductos() {
		return "holas";
	}
	
}
