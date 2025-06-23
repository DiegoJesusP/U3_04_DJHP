package mx.edu.utez.principios_api.service;

import mx.edu.utez.principios_api.dto.CedeDTO;

import java.util.List;

public interface CedeService {
    CedeDTO crearCede(CedeDTO cedeDTO);
    CedeDTO obtenerCedePorId(Long id);
    CedeDTO obtenerCedePorClave(String clave);
    List<CedeDTO> obtenerTodasLasCedes();
    List<CedeDTO> buscarCedesPorEstado(String estado);
    List<CedeDTO> buscarCedesPorMunicipio(String municipio);
    CedeDTO actualizarCede(Long id, CedeDTO cedeDTO);
    void eliminarCede(Long id);
    boolean existeCede(Long id);
}
