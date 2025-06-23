package mx.edu.utez.principios_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CedeDTO {
    private Long id;
    private String clave;
    
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
    
    @NotBlank(message = "El municipio es obligatorio")
    private String municipio;
    
    private int totalAlmacenes;
    private int almacenesDisponibles;

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getTotalAlmacenes() {
        return totalAlmacenes;
    }

    public void setTotalAlmacenes(int totalAlmacenes) {
        this.totalAlmacenes = totalAlmacenes;
    }

    public int getAlmacenesDisponibles() {
        return almacenesDisponibles;
    }

    public void setAlmacenesDisponibles(int almacenesDisponibles) {
        this.almacenesDisponibles = almacenesDisponibles;
    }
}
