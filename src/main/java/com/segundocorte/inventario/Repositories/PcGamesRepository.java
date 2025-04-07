package com.segundocorte.inventario.Repositories;

import com.segundocorte.inventario.Entities.PcGamesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PcGamesRepository extends JpaRepository<PcGamesEntity, UUID>{

    Page<PcGamesEntity> findAllByGameNameContaining(String GameName, Pageable pageable);

    @Override
    Page<PcGamesEntity> findAll(Pageable pageable);
}
