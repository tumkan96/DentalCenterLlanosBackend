package com.server.DentalCenterLlanos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.DentalCenterLlanos.Model.UsuariosRolesModel;
import com.server.DentalCenterLlanos.Model.UsuariosRolesPK;

public interface UsuariosRolesRepository extends JpaRepository<UsuariosRolesModel, UsuariosRolesPK> {

    @Modifying
    @Query(value = "INSERT INTO usuarios_roles (usuario, id_rol) VALUES (:usuario, :idRol)", nativeQuery = true)
    int addUsuarioRol(@Param("usuario") String usuario, @Param("idRol") Long idRol);

    @Modifying
    @Query(value = "DELETE FROM usuarios_roles WHERE usuario = :usuario", nativeQuery = true)
    int deleteUsuarioRoles(@Param("usuario") String usuario);

    @Modifying
    @Query(value = "DELETE FROM usuarios_roles WHERE usuario = :usuario AND id_rol = :idRol", nativeQuery = true)
    int deleteUsuarioRol(@Param("usuario") String usuario, @Param("idRol") Long idRol);

    @Query(value = "SELECT * FROM usuarios_roles WHERE id_rol = :idRol", nativeQuery = true)
    List<UsuariosRolesModel> findByRolIdRol(@Param("idRol") Long idRol);

    @Query(value = "SELECT * FROM usuarios_roles WHERE usuario = :usuario", nativeQuery = true)
    List<UsuariosRolesModel> findByUsuario(@Param("usuario") String usuario);

}
