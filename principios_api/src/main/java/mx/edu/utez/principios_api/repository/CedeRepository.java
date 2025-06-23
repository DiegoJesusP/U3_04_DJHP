package mx.edu.utez.principios_api.repository;

import mx.edu.utez.principios_api.model.Cede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CedeRepository extends JpaRepository<Cede, Long> {
    
    Optional<Cede> findByClave(String clave);
    
    List<Cede> findByEstadoContainingIgnoreCase(String estado);
    
    List<Cede> findByMunicipioContainingIgnoreCase(String municipio);
    
    @Query("SELECT c FROM Cede c LEFT JOIN FETCH c.almacenes")
    List<Cede> findAllWithAlmacenes();
    
    boolean existsByClave(String clave);
}
