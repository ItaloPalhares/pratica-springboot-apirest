package med.voll.apiRest.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import med.voll.apiRest.domain.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    UserDetails findByLogin(String login);


}
