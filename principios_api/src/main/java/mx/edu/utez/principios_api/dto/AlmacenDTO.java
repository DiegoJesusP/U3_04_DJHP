package mx.edu.utez.principios_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import mx.edu.utez.principios_api.model.Almacen;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenDTO {
    private Long id;
    private String clave;
    private LocalDate fechaRegistro;
    
    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio de venta debe ser positivo")
    private Double precioVenta;
    
    @NotNull(message = "El precio de renta es obligatorio")
    @Positive(message = "El precio de renta debe ser positivo")
    private Double precioRenta;
    
    @NotNull(message = "El tamaño es obligatorio")
    private Almacen.TamanoAlmacen tamaño;
    
    @NotNull(message = "La cede es obligatoria")
    private Long cedeId;
    
    private String cedeNombre;
    private Long clienteId;
    private String clienteNombre;
    private Almacen.TipoOperacion tipoOperacion;
    private boolean disponible;

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

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getPrecioRenta() {
        return precioRenta;
    }

    public void setPrecioRenta(Double precioRenta) {
        this.precioRenta = precioRenta;
    }

    public Almacen.TamanoAlmacen getTamaño() {
        return tamaño;
    }

    public void setTamaño(Almacen.TamanoAlmacen tamaño) {
        this.tamaño = tamaño;
    }

    public Long getCedeId() {
        return cedeId;
    }

    public void setCedeId(Long cedeId) {
        this.cedeId = cedeId;
    }

    public String getCedeNombre() {
        return cedeNombre;
    }

    public void setCedeNombre(String cedeNombre) {
        this.cedeNombre = cedeNombre;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Almacen.TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(Almacen.TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
