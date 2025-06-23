package mx.edu.utez.principios_api.service.impl;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.principios_api.dto.ClienteDTO;
import mx.edu.utez.principios_api.exception.DuplicateResourceException;
import mx.edu.utez.principios_api.exception.ResourceNotFoundException;
import mx.edu.utez.principios_api.model.Cliente;
import mx.edu.utez.principios_api.repository.ClienteRepository;
import mx.edu.utez.principios_api.service.ClienteService;
import mx.edu.utez.principios_api.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        log.info("Creando nuevo cliente: {}", clienteDTO);
        
        if (clienteRepository.existsByCorreoElectronico(clienteDTO.getCorreoElectronico())) {
            throw new DuplicateResourceException("Ya existe un cliente con el correo: " + clienteDTO.getCorreoElectronico());
        }
        
        if (clienteRepository.existsByTelefono(clienteDTO.getTelefono())) {
            throw new DuplicateResourceException("Ya existe un cliente con el teléfono: " + clienteDTO.getTelefono());
        }
        
        Cliente cliente = modelMapper.toEntity(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
        log.info("Cliente creado exitosamente con ID: {}", clienteGuardado.getId());
        return modelMapper.toDTO(clienteGuardado);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorId(Long id) {
        log.info("Obteniendo cliente por ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        
        return modelMapper.toDTO(cliente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorCorreo(String correo) {
        log.info("Obteniendo cliente por correo: {}", correo);
        
        Cliente cliente = clienteRepository.findByCorreoElectronico(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con correo: " + correo));
        
        return modelMapper.toDTO(cliente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerTodosLosClientes() {
        log.info("Obteniendo todos los clientes");
        
        return clienteRepository.findAllWithAlmacenes().stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarClientesPorNombre(String nombre) {
        log.info("Buscando clientes por nombre: {}", nombre);
        
        return clienteRepository.findByNombreCompletoContainingIgnoreCase(nombre).stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        log.info("Actualizando cliente con ID: {}", id);
        
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        
        // Validar correo único si cambió
        if (!clienteExistente.getCorreoElectronico().equals(clienteDTO.getCorreoElectronico()) &&
            clienteRepository.existsByCorreoElectronico(clienteDTO.getCorreoElectronico())) {
            throw new DuplicateResourceException("Ya existe un cliente con el correo: " + clienteDTO.getCorreoElectronico());
        }
        
        // Validar teléfono único si cambió
        if (!clienteExistente.getTelefono().equals(clienteDTO.getTelefono()) &&
            clienteRepository.existsByTelefono(clienteDTO.getTelefono())) {
            throw new DuplicateResourceException("Ya existe un cliente con el teléfono: " + clienteDTO.getTelefono());
        }
        
        clienteExistente.setNombreCompleto(clienteDTO.getNombreCompleto());
        clienteExistente.setTelefono(clienteDTO.getTelefono());
        clienteExistente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
        
        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        
        log.info("Cliente actualizado exitosamente con ID: {}", id);
        return modelMapper.toDTO(clienteActualizado);
    }
    
    @Override
    public void eliminarCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
        }
        
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado exitosamente con ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeCliente(Long id) {
        return clienteRepository.existsById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeCorreo(String correo) {
        return clienteRepository.existsByCorreoElectronico(correo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeTelefono(String telefono) {
        return clienteRepository.existsByTelefono(telefono);
    }
}
