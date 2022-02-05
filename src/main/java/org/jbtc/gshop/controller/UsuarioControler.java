package org.jbtc.gshop.controller;

import org.jbtc.gshop.dao.UsuarioDao;
import org.jbtc.gshop.entidad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/usuarios")
@Secured("ROLE_ADMIN")
public class UsuarioControler {

    @Autowired
    UsuarioDao usuarioDao;

    @PostMapping
    public ResponseEntity<String> addUsuario(@RequestBody Usuario usuario){
        //todo: validar que usuario no tenga fecha de creacion hack
        if(usuario!=null) {
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
    public List<Usuario> getUsuarios(){
        List<Usuario> usuarioList = usuarioDao.findAll();
        if(usuarioList!=null)
            return usuarioList;
        else
            return new ArrayList<Usuario>();
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
