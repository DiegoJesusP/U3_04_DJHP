package mx.edu.utez.principios_api.service;

import mx.edu.utez.principios_api.dto.AlmacenDTO;
import mx.edu.utez.principios_api.dto.OperacionDTO;
import mx.edu.utez.principios_api.model.Almacen;

import java.util.List;

public interface AlmacenService {
    AlmacenDTO crearAlmacen(AlmacenDTO almacenDTO);
    AlmacenDTO obtenerAlmacenPorId(Long id);
    AlmacenDTO obtenerAlmacenPorClave(String clave);
    List<AlmacenDTO> obtenerTodosLosAlmacenes();
    List<AlmacenDTO> obtenerAlmacenesDisponibles();
    List<AlmacenDTO> obtenerAlmacenesOcupados();
    List<AlmacenDTO> obtenerAlmacenesPorCede(Long cedeId);
    List<AlmacenDTO> obtenerAlmacenesPorCliente(Long clienteId);
    List<AlmacenDTO> obtenerAlmacenesPorTamaño(Almacen.TamanoAlmacen tamaño);
    AlmacenDTO realizarOperacion(OperacionDTO operacionDTO);
    AlmacenDTO liberarAlmacen(Long almacenId);
    AlmacenDTO actualizarAlmacen(Long id, AlmacenDTO almacenDTO);
    void eliminarAlmacen(Long id);
    boolean existeAlmacen(Long id);
    boolean estaDisponible(Long id);
}
