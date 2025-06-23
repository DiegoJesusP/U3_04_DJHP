package mx.edu.utez.principios_api.repository;

import mx.edu.utez.principios_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCorreoElectronico(String correoElectronico);
    
    Optional<Cliente> findByTelefono(String telefono);
    
    List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombre);
    
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.almacenes")
    List<Cliente> findAllWithAlmacenes();
    
    boolean existsByCorreoElectronico(String correoElectronico);
    
    boolean existsByTelefono(String telefono);
}
