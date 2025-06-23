package mx.edu.utez.principios_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import mx.edu.utez.principios_api.model.Almacen;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperacionDTO {
    @NotNull(message = "El ID del almacén es obligatorio")
    private Long almacenId;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;
    
    @NotNull(message = "El tipo de operación es obligatorio")
    private Almacen.TipoOperacion tipoOperacion;

    //

    public Long getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Long almacenId) {
        this.almacenId = almacenId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Almacen.TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(Almacen.TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
}
