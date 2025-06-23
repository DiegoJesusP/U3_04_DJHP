package mx.edu.utez.principios_api.service.impl;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.principios_api.dto.AlmacenDTO;
import mx.edu.utez.principios_api.dto.OperacionDTO;
import mx.edu.utez.principios_api.exception.BusinessException;
import mx.edu.utez.principios_api.exception.ResourceNotFoundException;
import mx.edu.utez.principios_api.model.Almacen;
import mx.edu.utez.principios_api.model.Cede;
import mx.edu.utez.principios_api.model.Cliente;
import mx.edu.utez.principios_api.repository.AlmacenRepository;
import mx.edu.utez.principios_api.repository.CedeRepository;
import mx.edu.utez.principios_api.repository.ClienteRepository;
import mx.edu.utez.principios_api.service.AlmacenService;
import mx.edu.utez.principios_api.util.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlmacenServiceImpl implements AlmacenService {

    private static final Logger log = LoggerFactory.getLogger(AlmacenServiceImpl.class);

    private final AlmacenRepository almacenRepository;
    private final CedeRepository cedeRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    public AlmacenServiceImpl(
            AlmacenRepository almacenRepository,
            CedeRepository cedeRepository,
            ClienteRepository clienteRepository,
            ModelMapper modelMapper
    ) {
        this.almacenRepository = almacenRepository;
        this.cedeRepository = cedeRepository;
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AlmacenDTO crearAlmacen(AlmacenDTO almacenDTO) {
        log.info("Creando nuevo almacén: {}", almacenDTO);

        Cede cede = cedeRepository.findById(almacenDTO.getCedeId())
                .orElseThrow(() -> new ResourceNotFoundException("Cede no encontrada con ID: " + almacenDTO.getCedeId()));

        Almacen almacen = modelMapper.toEntity(almacenDTO);
        almacen.setCede(cede);

        // Guardar sin clave primero
        Almacen almacenGuardado = almacenRepository.save(almacen);

        // Generar clave y volver a guardar
        String clave = String.format("%s-A%d", cede.getClave(), almacenGuardado.getId());
        almacenGuardado.setClave(clave);
        almacenGuardado = almacenRepository.save(almacenGuardado);

        log.info("Almacén creado exitosamente con ID: {}", almacenGuardado.getId());
        return modelMapper.toDTO(almacenGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public AlmacenDTO obtenerAlmacenPorId(Long id) {
        log.info("Obteniendo almacén por ID: {}", id);
        
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Almacén no encontrado con ID: " + id));
        
        return modelMapper.toDTO(almacen);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AlmacenDTO obtenerAlmacenPorClave(String clave) {
        log.info("Obteniendo almacén por clave: {}", clave);
        
        Almacen almacen = almacenRepository.findByClave(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Almacén no encontrado con clave: " + clave));
        
        return modelMapper.toDTO(almacen);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AlmacenDTO> obtenerTodosLosAlmacenes() {
        log.info("Obteniendo todos los almacenes");
        
        return almacenRepository.findAllWithDetails().stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AlmacenDTO> obtenerAlmacenesDisponibles() {
        log.info("Obteniendo almacenes disponibles");
        
        return almacenRepository.findAvailableAlmacenesWithCede().stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AlmacenDTO> obtenerAlmacenesOcupados() {
        log.info("Obteniendo almacenes ocupados");
        
        return almacenRepository.findByClienteIsNotNull().stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AlmacenDTO> obtenerAlmacenesPorCede(Long cedeId) {
        log.info("Obteniendo almacenes por cede ID: {}", cedeId);
        
        return almacenRepository.findByCedeId(cedeId).stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AlmacenDTO> obtenerAlmacenesPorCliente(Long clienteId) {
        log.info("Obteniendo almacenes por cliente ID: {}", clienteId);
        
        return almacenRepository.findByClienteId(clienteId).stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AlmacenDTO> obtenerAlmacenesPorTamaño(Almacen.TamanoAlmacen tamaño) {
        log.info("Obteniendo almacenes por tamaño: {}", tamaño);
        
        return almacenRepository.findByTamaño(tamaño).stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public AlmacenDTO realizarOperacion(OperacionDTO operacionDTO) {
        log.info("Realizando operación: {}", operacionDTO);
        
        Almacen almacen = almacenRepository.findById(operacionDTO.getAlmacenId())
                .orElseThrow(() -> new ResourceNotFoundException("Almacén no encontrado con ID: " + operacionDTO.getAlmacenId()));
        
        if (!almacen.estaDisponible()) {
            throw new BusinessException("El almacén ya está ocupado");
        }
        
        Cliente cliente = clienteRepository.findById(operacionDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + operacionDTO.getClienteId()));
        
        almacen.setCliente(cliente);
        almacen.setTipoOperacion(operacionDTO.getTipoOperacion());
        
        Almacen almacenActualizado = almacenRepository.save(almacen);
        
        log.info("Operación realizada exitosamente para almacén ID: {}", operacionDTO.getAlmacenId());
        return modelMapper.toDTO(almacenActualizado);
    }
    
    @Override
    public AlmacenDTO liberarAlmacen(Long almacenId) {
        log.info("Liberando almacén ID: {}", almacenId);
        
        Almacen almacen = almacenRepository.findById(almacenId)
                .orElseThrow(() -> new ResourceNotFoundException("Almacén no encontrado con ID: " + almacenId));
        
        if (almacen.estaDisponible()) {
            throw new BusinessException("El almacén ya está disponible");
        }
        
        almacen.setCliente(null);
        almacen.setTipoOperacion(null);
        
        Almacen almacenLiberado = almacenRepository.save(almacen);
        
        log.info("Almacén liberado exitosamente ID: {}", almacenId);
        return modelMapper.toDTO(almacenLiberado);
    }
    
    @Override
    public AlmacenDTO actualizarAlmacen(Long id, AlmacenDTO almacenDTO) {
        log.info("Actualizando almacén con ID: {}", id);
        
        Almacen almacenExistente = almacenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Almacén no encontrado con ID: " + id));
        
        almacenExistente.setPrecioVenta(almacenDTO.getPrecioVenta());
        almacenExistente.setPrecioRenta(almacenDTO.getPrecioRenta());
        almacenExistente.setTamaño(almacenDTO.getTamaño());
        
        Almacen almacenActualizado = almacenRepository.save(almacenExistente);
        
        log.info("Almacén actualizado exitosamente con ID: {}", id);
        return modelMapper.toDTO(almacenActualizado);
    }
    
    @Override
    public void eliminarAlmacen(Long id) {
        log.info("Eliminando almacén con ID: {}", id);
        
        if (!almacenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Almacén no encontrado con ID: " + id);
        }
        
        almacenRepository.deleteById(id);
        log.info("Almacén eliminado exitosamente con ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeAlmacen(Long id) {
        return almacenRepository.existsById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean estaDisponible(Long id) {
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Almacén no encontrado con ID: " + id));
        return almacen.estaDisponible();
    }
}
