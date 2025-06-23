package mx.edu.utez.principios_api.repository;

import mx.edu.utez.principios_api.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
    
    Optional<Almacen> findByClave(String clave);
    
    List<Almacen> findByClienteIsNull();
    
    List<Almacen> findByClienteIsNotNull();
    
    List<Almacen> findByCedeId(Long cedeId);
    
    List<Almacen> findByClienteId(Long clienteId);
    
    List<Almacen> findByTamaño(Almacen.TamanoAlmacen tamaño);
    
    @Query("SELECT a FROM Almacen a JOIN FETCH a.cede WHERE a.cliente IS NULL")
    List<Almacen> findAvailableAlmacenesWithCede();
    
    @Query("SELECT a FROM Almacen a JOIN FETCH a.cede LEFT JOIN FETCH a.cliente")
    List<Almacen> findAllWithDetails();
    
    @Query("SELECT COUNT(a) FROM Almacen a WHERE a.cede.id = :cedeId AND a.cliente IS NULL")
    long countAvailableByCedeId(@Param("cedeId") Long cedeId);
    
    boolean existsByClave(String clave);
}
