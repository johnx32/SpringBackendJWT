package org.jbtc.gshop.controller;

import org.jbtc.gshop.dao.UsuarioDao;
import org.jbtc.gshop.entidad.Usuario;
import org.jbtc.gshop.model.NewUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/usuarios")
@Secured("ROLE_ADMIN")
public class UsuarioControler {

    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> addUsuario(@RequestBody NewUsuario nusuario){
        //todo: validar que usuario no tenga fecha de creacion hack
        if(nusuario!=null) {
            if(!nusuario.getPassword().equals(nusuario.getRepassword())) {
                System.out.println("password incorrectas");
                return new ResponseEntity<>("password y repassword son diferentes", HttpStatus.BAD_REQUEST);
            }
            Usuario u = usuarioDao.findByUsername(nusuario.getUsername());
            if(u!=null) return new ResponseEntity<>("Usuario ya existe", HttpStatus.BAD_REQUEST);

            Usuario usuario = new Usuario();
            usuario.setUsername(nusuario.getUsername());
            usuario.setPassword(passwordEncoder.encode(nusuario.getPassword()));
            usuario.setRoles(nusuario.getRoles());
            usuario.setCreated_at(new Date());

            System.out.println("nuevo usuario: "+usuario);

            usuarioDao.save(usuario);
            return new ResponseEntity<>("", HttpStatus.OK);
        }else
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "{id}")
    public Usuario getUsuarioById(@PathVariable Long id){
        Usuario u = usuarioDao.findById(id).get();
        if(u!=null)
            return u;
        else
            return new Usuario("","");
    }

    @GetMapping
    public Page<Usuario> getUsuarios(@Param("name") String name, Pageable page){
        System.out.println("pagina: "+page.getPageNumber());
        return usuarioDao.search(name,page);
    }

    @PutMapping
    public ResponseEntity<String> updateUsuario(@RequestBody Usuario usuario){
        if(usuario!=null) {
            if (usuario.getId() != null && usuario.getId() > 0)
                usuario.setUpdated_at(new Date());
            if(usuarioDao.save(usuario)!=null)
                return new ResponseEntity<>("", HttpStatus.OK);
            else return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
        }else return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deleteUsuario(Usuario usuario){
        try {
            usuarioDao.delete(usuario);
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }

}
