package com.server.DentalCenterLlanos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.DentalCenterLlanos.Model.Usuarios_RolesModel;
import com.server.DentalCenterLlanos.Model.Usuarios_RolesPK;



public interface Usuarios_RolesRepository extends JpaRepository<Usuarios_RolesModel, Usuarios_RolesPK>{

	@Modifying
	@Query(value = "insert into usuarios_roles (usuario, id_rol) values (:usuario, :id);", nativeQuery = true)
	public int add_Usuario_rol(String usuario, int id);

	@Modifying
	@Query(value = "delete from usuarios_roles where usuario=?1", nativeQuery = true)
	public int del_Usuario_rol(String usuario);

	@Modifying
	@Query(value = "delete from usuarios_roles where usuario=?1 and id_rol=?2", nativeQuery = true)
	public int del_Usuario_rol2(String usuario, int id);
	
	
	@Query("SELECT ur FROM Usuarios_RolesModel ur WHERE ur.Usuarios_RolesPK.id_rol = :id_rol")
	List<Usuarios_RolesModel> findByRolIdRol(@Param("id_rol") int id_rol);




}
