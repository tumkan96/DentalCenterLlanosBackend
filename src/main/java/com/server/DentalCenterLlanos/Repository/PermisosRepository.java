package com.server.DentalCenterLlanos.Repository;

import com.server.DentalCenterLlanos.Model.PermisosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisosRepository extends JpaRepository<PermisosModel, Long> {
}