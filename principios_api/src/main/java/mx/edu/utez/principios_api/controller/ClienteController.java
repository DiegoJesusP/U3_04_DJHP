package mx.edu.utez.principios_api.controller;

import jakarta.validation.Valid;
import mx.edu.utez.principios_api.dto.ClienteDTO;
import mx.edu.utez.principios_api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;
    
    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("Solicitud para crear cliente: {}", clienteDTO);
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);
        return new ResponseEntity<>(clienteCreado, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable Long id) {
        log.info("Solicitud para obtener cliente por ID: {}", id);
        ClienteDTO cliente = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(cliente);
    }
    
    @GetMapping("/correo/{correo}")
    public ResponseEntity<ClienteDTO> obtenerClientePorCorreo(@PathVariable String correo) {
        log.info("Solicitud para obtener cliente por correo: {}", correo);
        ClienteDTO cliente = clienteService.obtenerClientePorCorreo(correo);
        return ResponseEntity.ok(cliente);
    }
    
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerTodosLosClientes() {
        log.info("Solicitud para obtener todos los clientes");
        List<ClienteDTO> clientes = clienteService.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarClientesPorNombre(@RequestParam String nombre) {
        log.info("Solicitud para buscar clientes por nombre: {}", nombre);
        List<ClienteDTO> clientes = clienteService.buscarClientesPorNombre(nombre);
        return ResponseEntity.ok(clientes);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("Solicitud para actualizar cliente ID: {} con datos: {}", id, clienteDTO);
        ClienteDTO clienteActualizado = clienteService.actualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(clienteActualizado);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        log.info("Solicitud para eliminar cliente ID: {}", id);
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
