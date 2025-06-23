package mx.edu.utez.principios_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "almacenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Almacen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true)
    private String clave;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;
    
    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio de venta debe ser positivo")
    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;
    
    @NotNull(message = "El precio de renta es obligatorio")
    @Positive(message = "El precio de renta debe ser positivo")
    @Column(name = "precio_renta", nullable = false)
    private Double precioRenta;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TamanoAlmacen tamaño;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cede_id", nullable = false)
    private Cede cede;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacion")
    private TipoOperacion tipoOperacion;
    
    @PrePersist
    private void inicializar() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDate.now();
        }
    }

    @PostPersist
    private void generarClave() {
        if (this.cede != null && this.id != null) {
            this.clave = String.format("%s-A%d", this.cede.getClave(), this.id);
        }
    }
    
    public boolean estaDisponible() {
        return this.cliente == null;
    }
    
    public enum TamanoAlmacen {
        G, M, P
    }
    
    public enum TipoOperacion {
        COMPRA, RENTA
    }

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

    public TamanoAlmacen getTamaño() {
        return tamaño;
    }

    public void setTamaño(TamanoAlmacen tamaño) {
        this.tamaño = tamaño;
    }

    public Cede getCede() {
        return cede;
    }

    public void setCede(Cede cede) {
        this.cede = cede;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
}
