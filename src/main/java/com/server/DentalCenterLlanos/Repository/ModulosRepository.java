package com.server.DentalCenterLlanos.Repository;

import com.server.DentalCenterLlanos.Model.ModulosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModulosRepository extends JpaRepository<ModulosModel, Long> {
}