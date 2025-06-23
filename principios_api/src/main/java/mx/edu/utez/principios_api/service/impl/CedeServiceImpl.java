package mx.edu.utez.principios_api.service.impl;

import mx.edu.utez.principios_api.dto.CedeDTO;
import mx.edu.utez.principios_api.exception.ResourceNotFoundException;
import mx.edu.utez.principios_api.model.Cede;
import mx.edu.utez.principios_api.repository.CedeRepository;
import mx.edu.utez.principios_api.service.CedeService;
import mx.edu.utez.principios_api.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class CedeServiceImpl implements CedeService {

    private static final Logger log = LoggerFactory.getLogger(CedeServiceImpl.class);

    @Autowired
    private CedeRepository cedeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CedeDTO crearCede(CedeDTO cedeDTO) {
        log.info("Creando nueva cede: {}", cedeDTO);

        // Convertir DTO a entidad
        Cede cede = modelMapper.toEntity(cedeDTO);

        // Guardar inicialmente (para obtener el ID)
        Cede cedeGuardada = cedeRepository.save(cede);

        // Generar clave con el formato solicitado
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String aleatorio = String.format("%04d", new Random().nextInt(10000));
        String clave = String.format("C%d-%s-%s", cedeGuardada.getId(), fecha, aleatorio);
        cedeGuardada.setClave(clave);

        // Volver a guardar con la clave ya asignada
        cedeGuardada = cedeRepository.save(cedeGuardada);

        log.info("Cede creada exitosamente con clave: {}", cedeGuardada.getClave());

        return modelMapper.toDTO(cedeGuardada);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CedeDTO obtenerCedePorId(Long id) {
        log.info("Obteniendo cede por ID: {}", id);
        
        Cede cede = cedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cede no encontrada con ID: " + id));
        
        return modelMapper.toDTO(cede);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CedeDTO obtenerCedePorClave(String clave) {
        log.info("Obteniendo cede por clave: {}", clave);
        
        Cede cede = cedeRepository.findByClave(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Cede no encontrada con clave: " + clave));
        
        return modelMapper.toDTO(cede);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CedeDTO> obtenerTodasLasCedes() {
        log.info("Obteniendo todas las cedes");
        
        return cedeRepository.findAllWithAlmacenes().stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CedeDTO> buscarCedesPorEstado(String estado) {
        log.info("Buscando cedes por estado: {}", estado);
        
        return cedeRepository.findByEstadoContainingIgnoreCase(estado).stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CedeDTO> buscarCedesPorMunicipio(String municipio) {
        log.info("Buscando cedes por municipio: {}", municipio);
        
        return cedeRepository.findByMunicipioContainingIgnoreCase(municipio).stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CedeDTO actualizarCede(Long id, CedeDTO cedeDTO) {
        log.info("Actualizando cede con ID: {}", id);
        
        Cede cedeExistente = cedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cede no encontrada con ID: " + id));
        
        cedeExistente.setEstado(cedeDTO.getEstado());
        cedeExistente.setMunicipio(cedeDTO.getMunicipio());
        
        Cede cedeActualizada = cedeRepository.save(cedeExistente);
        
        log.info("Cede actualizada exitosamente con ID: {}", id);
        return modelMapper.toDTO(cedeActualizada);
    }
    
    @Override
    public void eliminarCede(Long id) {
        log.info("Eliminando cede con ID: {}", id);
        
        if (!cedeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cede no encontrada con ID: " + id);
        }
        
        cedeRepository.deleteById(id);
        log.info("Cede eliminada exitosamente con ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeCede(Long id) {
        return cedeRepository.existsById(id);
    }
}
