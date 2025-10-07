package com.server.DentalCenterLlanos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.DentalCenterLlanos.Model.OdontologosModel;

@Repository
public interface OdontologosRepository extends JpaRepository<OdontologosModel, Long> {
}