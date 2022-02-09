package org.jbtc.gshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jbtc.gshop.entidad.Rol;

import java.util.List;

@Data @NoArgsConstructor
public class NewUsuario {

    private String username;
    private String password;
    private String repassword;
    private List<Rol> roles;

}
