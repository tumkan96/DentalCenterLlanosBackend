package com.server.DentalCenterLlanos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.DentalCenterLlanos.Model.RolesModulosModel;
import com.server.DentalCenterLlanos.Model.RolesModulosPK;

public interface RolesModulosRepository extends JpaRepository<RolesModulosModel, RolesModulosPK> {

    @Query("SELECT rm FROM RolesModulosModel rm WHERE rm.rolesModulosPK.idRol = :idRol")
    List<RolesModulosModel> findByIdRol(@Param("idRol") Long idRol);

    @Query("SELECT rm FROM RolesModulosModel rm WHERE rm.rolesModulosPK.idModulo = :idModulo")
    List<RolesModulosModel> findByIdModulo(@Param("idModulo") Long idModulo);

    @Modifying
    @Query(value = "INSERT INTO roles_modulos (id_rol, id_modulo) VALUES (:idRol, :idModulo)", nativeQuery = true)
    int insertRolModulo(@Param("idRol") Long idRol, @Param("idModulo") Long idModulo);

    @Modifying
    @Query(value = "DELETE FROM roles_modulos WHERE id_rol = :idRol AND id_modulo = :idModulo", nativeQuery = true)
    int deleteRolModulo(@Param("idRol") Long idRol, @Param("idModulo") Long idModulo);
}
