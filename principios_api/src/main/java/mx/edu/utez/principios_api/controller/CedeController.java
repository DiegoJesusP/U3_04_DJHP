package mx.edu.utez.principios_api.controller;

import jakarta.validation.Valid;
import mx.edu.utez.principios_api.dto.CedeDTO;
import mx.edu.utez.principios_api.service.CedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/cedes")
@CrossOrigin(origins = "*")
public class CedeController {

    private static final Logger log = LoggerFactory.getLogger(CedeController.class);

    @Autowired
    private CedeService cedeService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CedeDTO> crearCede(@Valid @RequestBody CedeDTO cedeDTO) {
        log.info("Solicitud para crear cede: {}", cedeDTO);
        CedeDTO cedeCreada = cedeService.crearCede(cedeDTO);
        return new ResponseEntity<>(cedeCreada, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CedeDTO> obtenerCedePorId(@PathVariable Long id) {
        log.info("Solicitud para obtener cede por ID: {}", id);
        CedeDTO cede = cedeService.obtenerCedePorId(id);
        return ResponseEntity.ok(cede);
    }
    
    @GetMapping("/clave/{clave}")
    public ResponseEntity<CedeDTO> obtenerCedePorClave(@PathVariable String clave) {
        log.info("Solicitud para obtener cede por clave: {}", clave);
        CedeDTO cede = cedeService.obtenerCedePorClave(clave);
        return ResponseEntity.ok(cede);
    }
    
    @GetMapping
    public ResponseEntity<List<CedeDTO>> obtenerTodasLasCedes() {
        log.info("Solicitud para obtener todas las cedes");
        List<CedeDTO> cedes = cedeService.obtenerTodasLasCedes();
        return ResponseEntity.ok(cedes);
    }
    
    @GetMapping("/buscar/estado")
    public ResponseEntity<List<CedeDTO>> buscarCedesPorEstado(@RequestParam String estado) {
        log.info("Solicitud para buscar cedes por estado: {}", estado);
        List<CedeDTO> cedes = cedeService.buscarCedesPorEstado(estado);
        return ResponseEntity.ok(cedes);
    }
    
    @GetMapping("/buscar/municipio")
    public ResponseEntity<List<CedeDTO>> buscarCedesPorMunicipio(@RequestParam String municipio) {
        log.info("Solicitud para buscar cedes por municipio: {}", municipio);
        List<CedeDTO> cedes = cedeService.buscarCedesPorMunicipio(municipio);
        return ResponseEntity.ok(cedes);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CedeDTO> actualizarCede(@PathVariable Long id, @Valid @RequestBody CedeDTO cedeDTO) {
        log.info("Solicitud para actualizar cede ID: {} con datos: {}", id, cedeDTO);
        CedeDTO cedeActualizada = cedeService.actualizarCede(id, cedeDTO);
        return ResponseEntity.ok(cedeActualizada);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCede(@PathVariable Long id) {
        log.info("Solicitud para eliminar cede ID: {}", id);
        cedeService.eliminarCede(id);
        return ResponseEntity.noContent().build();
    }
}
