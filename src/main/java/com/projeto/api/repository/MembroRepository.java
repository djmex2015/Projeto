package com.projeto.api.repository;

import com.projeto.api.model.MembroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembroRepository extends JpaRepository<MembroEntity, String> {

}
