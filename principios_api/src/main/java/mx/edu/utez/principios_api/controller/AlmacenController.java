package mx.edu.utez.principios_api.controller;

import jakarta.validation.Valid;
import mx.edu.utez.principios_api.dto.AlmacenDTO;
import mx.edu.utez.principios_api.dto.OperacionDTO;
import mx.edu.utez.principios_api.model.Almacen;
import mx.edu.utez.principios_api.service.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@CrossOrigin(origins = "*")
public class AlmacenController {

    private static final Logger log = LoggerFactory.getLogger(AlmacenController.class);

    @Autowired
    private AlmacenService almacenService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlmacenDTO> crearAlmacen(@Valid @RequestBody AlmacenDTO almacenDTO) {
        log.info("Solicitud para crear almacén: {}", almacenDTO);
        AlmacenDTO almacenCreado = almacenService.crearAlmacen(almacenDTO);
        return new ResponseEntity<>(almacenCreado, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AlmacenDTO> obtenerAlmacenPorId(@PathVariable Long id) {
        log.info("Solicitud para obtener almacén por ID: {}", id);
        AlmacenDTO almacen = almacenService.obtenerAlmacenPorId(id);
        return ResponseEntity.ok(almacen);
    }
    
    @GetMapping("/clave/{clave}")
    public ResponseEntity<AlmacenDTO> obtenerAlmacenPorClave(@PathVariable String clave) {
        log.info("Solicitud para obtener almacén por clave: {}", clave);
        AlmacenDTO almacen = almacenService.obtenerAlmacenPorClave(clave);
        return ResponseEntity.ok(almacen);
    }
    
    @GetMapping
    public ResponseEntity<List<AlmacenDTO>> obtenerTodosLosAlmacenes() {
        log.info("Solicitud para obtener todos los almacenes");
        List<AlmacenDTO> almacenes = almacenService.obtenerTodosLosAlmacenes();
        return ResponseEntity.ok(almacenes);
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<AlmacenDTO>> obtenerAlmacenesDisponibles() {
        log.info("Solicitud para obtener almacenes disponibles");
        List<AlmacenDTO> almacenes = almacenService.obtenerAlmacenesDisponibles();
        return ResponseEntity.ok(almacenes);
    }
    
    @GetMapping("/ocupados")
    public ResponseEntity<List<AlmacenDTO>> obtenerAlmacenesOcupados() {
        log.info("Solicitud para obtener almacenes ocupados");
        List<AlmacenDTO> almacenes = almacenService.obtenerAlmacenesOcupados();
        return ResponseEntity.ok(almacenes);
    }
    
    @GetMapping("/cede/{cedeId}")
    public ResponseEntity<List<AlmacenDTO>> obtenerAlmacenesPorCede(@PathVariable Long cedeId) {
        log.info("Solicitud para obtener almacenes por cede ID: {}", cedeId);
        List<AlmacenDTO> almacenes = almacenService.obtenerAlmacenesPorCede(cedeId);
        return ResponseEntity.ok(almacenes);
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AlmacenDTO>> obtenerAlmacenesPorCliente(@PathVariable Long clienteId) {
        log.info("Solicitud para obtener almacenes por cliente ID: {}", clienteId);
        List<AlmacenDTO> almacenes = almacenService.obtenerAlmacenesPorCliente(clienteId);
        return ResponseEntity.ok(almacenes);
    }
    
    @GetMapping("/tamaño/{tamaño}")
    public ResponseEntity<List<AlmacenDTO>> obtenerAlmacenesPorTamaño(@PathVariable Almacen.TamanoAlmacen tamaño) {
        log.info("Solicitud para obtener almacenes por tamaño: {}", tamaño);
        List<AlmacenDTO> almacenes = almacenService.obtenerAlmacenesPorTamaño(tamaño);
        return ResponseEntity.ok(almacenes);
    }
    
    @PostMapping("/operacion")
    public ResponseEntity<AlmacenDTO> realizarOperacion(@Valid @RequestBody OperacionDTO operacionDTO) {
        log.info("Solicitud para realizar operación: {}", operacionDTO);
        AlmacenDTO almacenOperado = almacenService.realizarOperacion(operacionDTO);
        return ResponseEntity.ok(almacenOperado);
    }
    
    @PutMapping("/{id}/liberar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlmacenDTO> liberarAlmacen(@PathVariable Long id) {
        log.info("Solicitud para liberar almacén ID: {}", id);
        AlmacenDTO almacenLiberado = almacenService.liberarAlmacen(id);
        return ResponseEntity.ok(almacenLiberado);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlmacenDTO> actualizarAlmacen(@PathVariable Long id, @Valid @RequestBody AlmacenDTO almacenDTO) {
        log.info("Solicitud para actualizar almacén ID: {} con datos: {}", id, almacenDTO);
        AlmacenDTO almacenActualizado = almacenService.actualizarAlmacen(id, almacenDTO);
        return ResponseEntity.ok(almacenActualizado);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarAlmacen(@PathVariable Long id) {
        log.info("Solicitud para eliminar almacén ID: {}", id);
        almacenService.eliminarAlmacen(id);
        return ResponseEntity.noContent().build();
    }
}
