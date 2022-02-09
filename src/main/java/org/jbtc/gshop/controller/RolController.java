package org.jbtc.gshop.controller;

import org.jbtc.gshop.dao.RolDao;
import org.jbtc.gshop.entidad.Rol;
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
@RequestMapping("api/roles")
@Secured("ROLE_ADMIN")
public class RolController {

    @Autowired
    RolDao rolDao;

    @PostMapping
    public ResponseEntity<String> addRol(@RequestBody Rol rol){
        if(rol!=null){
            rolDao.save(rol);
            return new ResponseEntity<>("", HttpStatus.OK);
        }else
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "{id}")
    public Rol getRolById(@PathVariable Long id){
        Rol r = rolDao.findById(id).get();
        if(r!=null)
            return r;
        else
            return new Rol("");
    }

    @GetMapping
    public List<Rol> getRoles(){
        List<Rol> rolList = rolDao.findAll();
        if(rolList!=null)
            return rolList;
        else
            return new ArrayList<Rol>();
    }

    @PutMapping
    public ResponseEntity<String> updateRol(@RequestBody Rol rol){
        if(rol!=null) {
            if (rol.getId() != null && rol.getId() > 0)
                rol.setUpdated_at(new Date());
            if(rolDao.save(rol)!=null)
                return new ResponseEntity<>("", HttpStatus.OK);
            else return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
        }else return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deleteRol(Rol rol){
        try {
            rolDao.delete(rol);
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }
}
