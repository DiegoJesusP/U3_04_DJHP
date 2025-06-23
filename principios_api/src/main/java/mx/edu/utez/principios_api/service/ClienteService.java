package mx.edu.utez.principios_api.service;

import mx.edu.utez.principios_api.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    ClienteDTO crearCliente(ClienteDTO clienteDTO);
    ClienteDTO obtenerClientePorId(Long id);
    ClienteDTO obtenerClientePorCorreo(String correo);
    List<ClienteDTO> obtenerTodosLosClientes();
    List<ClienteDTO> buscarClientesPorNombre(String nombre);
    ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO);
    void eliminarCliente(Long id);
    boolean existeCliente(Long id);
    boolean existeCorreo(String correo);
    boolean existeTelefono(String telefono);
}
