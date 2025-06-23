package mx.edu.utez.principios_api.util;

import mx.edu.utez.principios_api.dto.AlmacenDTO;
import mx.edu.utez.principios_api.dto.CedeDTO;
import mx.edu.utez.principios_api.dto.ClienteDTO;
import mx.edu.utez.principios_api.model.Almacen;
import mx.edu.utez.principios_api.model.Cede;
import mx.edu.utez.principios_api.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
    
    // Cede mappings
    public CedeDTO toDTO(Cede cede) {
        if (cede == null) return null;
        
        CedeDTO dto = new CedeDTO();
        dto.setId(cede.getId());
        dto.setClave(cede.getClave());
        dto.setEstado(cede.getEstado());
        dto.setMunicipio(cede.getMunicipio());
        
        if (cede.getAlmacenes() != null) {
            dto.setTotalAlmacenes(cede.getAlmacenes().size());
            dto.setAlmacenesDisponibles((int) cede.getAlmacenes().stream()
                    .filter(Almacen::estaDisponible)
                    .count());
        }
        
        return dto;
    }
    
    public Cede toEntity(CedeDTO dto) {
        if (dto == null) return null;
        
        Cede cede = new Cede();
        cede.setId(dto.getId());
        cede.setClave(dto.getClave());
        cede.setEstado(dto.getEstado());
        cede.setMunicipio(dto.getMunicipio());
        
        return cede;
    }
    
    // Almacen mappings
    public AlmacenDTO toDTO(Almacen almacen) {
        if (almacen == null) return null;
        
        AlmacenDTO dto = new AlmacenDTO();
        dto.setId(almacen.getId());
        dto.setClave(almacen.getClave());
        dto.setFechaRegistro(almacen.getFechaRegistro());
        dto.setPrecioVenta(almacen.getPrecioVenta());
        dto.setPrecioRenta(almacen.getPrecioRenta());
        dto.setTamaño(almacen.getTamaño());
        dto.setTipoOperacion(almacen.getTipoOperacion());
        dto.setDisponible(almacen.estaDisponible());
        
        if (almacen.getCede() != null) {
            dto.setCedeId(almacen.getCede().getId());
            dto.setCedeNombre(almacen.getCede().getEstado() + " - " + almacen.getCede().getMunicipio());
        }
        
        if (almacen.getCliente() != null) {
            dto.setClienteId(almacen.getCliente().getId());
            dto.setClienteNombre(almacen.getCliente().getNombreCompleto());
        }
        
        return dto;
    }
    
    public Almacen toEntity(AlmacenDTO dto) {
        if (dto == null) return null;
        
        Almacen almacen = new Almacen();
        almacen.setId(dto.getId());
        almacen.setClave(dto.getClave());
        almacen.setFechaRegistro(dto.getFechaRegistro());
        almacen.setPrecioVenta(dto.getPrecioVenta());
        almacen.setPrecioRenta(dto.getPrecioRenta());
        almacen.setTamaño(dto.getTamaño());
        almacen.setTipoOperacion(dto.getTipoOperacion());
        
        return almacen;
    }
    
    // Cliente mappings
    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;
        
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombreCompleto(cliente.getNombreCompleto());
        dto.setTelefono(cliente.getTelefono());
        dto.setCorreoElectronico(cliente.getCorreoElectronico());
        
        if (cliente.getAlmacenes() != null) {
            dto.setTotalAlmacenes(cliente.getAlmacenes().size());
        }
        
        return dto;
    }
    
    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreoElectronico(dto.getCorreoElectronico());
        
        return cliente;
    }
}
