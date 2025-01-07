package com.server.DentalCenterLlanos.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.DentalCenterLlanos.Model.UsuariosModel;



public interface UsuariosRepository extends JpaRepository<UsuariosModel, String>{
	UsuariosModel findByUsuarios(String usuarios);
	@Query(value="SELECT *FROM personas ORDER BY usuarios ASC", nativeQuery = true)
	List<UsuariosModel>findAllOrderedByUsuarios();
	
	Optional<UsuariosModel> findByUsuariosAndContrasena(String usuarios, String contrasena);
	 Optional<UsuariosModel> findTopByOrderByUsuariosDesc();
	 
	 @Query(value="SELECT r.nombre FROM roles r " +
             "JOIN usuarios_roles ur ON r.id_rol = ur.id_rol " +
             "JOIN usuarios u ON u.usuarios = ur.usuario " +
             "WHERE u.usuarios = :usuario", nativeQuery = true)
List<String> findRolesByUsuario(String usuario);

}
