package org.jbtc.gshop.repository;

import java.util.List;

import org.jbtc.gshop.dao.ProductoDao;
import org.jbtc.gshop.entidad.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductoRepo {
	
	@Autowired
	ProductoDao productoDao;
	
	//@PersistenceContext
	//EntityManager entityManager; //entityManager.createQuery("").getResultList()
	
	public List<Producto> getProductos(){
		return productoDao.findAll();
	}

}
