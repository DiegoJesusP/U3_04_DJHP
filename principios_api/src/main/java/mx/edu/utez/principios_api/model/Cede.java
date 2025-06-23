package mx.edu.utez.principios_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "cedes")
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class Cede {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String clave;
    
    @NotBlank(message = "El estado es obligatorio")
    @Column(nullable = false)
    private String estado;
    
    @NotBlank(message = "El municipio es obligatorio")
    @Column(nullable = false)
    private String municipio;
    
    @OneToMany(mappedBy = "cede", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Almacen> almacenes;

    @PostPersist
    private void generarClave() {
        if (this.clave == null) {
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            String aleatorio = String.format("%04d", new Random().nextInt(10000));
            this.clave = String.format("C%d-%s-%s", this.id, fecha, aleatorio);
        }
    }
    
    public Cede(String estado, String municipio) {
        this.estado = estado;
        this.municipio = municipio;
    }

    //getter and setter

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

    public List<Almacen> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<Almacen> almacenes) {
        this.almacenes = almacenes;
    }

    //

    public Cede() {
    }
}
