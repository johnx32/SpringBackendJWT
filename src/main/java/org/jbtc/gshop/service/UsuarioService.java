package org.jbtc.gshop.service;

import java.util.ArrayList;
import java.util.List;

import org.jbtc.gshop.dao.UsuarioDao;
import org.jbtc.gshop.entidad.Rol;
import org.jbtc.gshop.entidad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		if(usuario==null)
			throw new UsernameNotFoundException(username);
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		
		for(Rol rol:usuario.getRoles())
			roles.add(new SimpleGrantedAuthority(rol.getName()));
		
		return new User(usuario.getUsername(),usuario.getPassword(),roles);
	}





}
