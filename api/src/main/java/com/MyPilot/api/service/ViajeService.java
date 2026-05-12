package com.MyPilot.api.service;

import com.MyPilot.api.model.Viaje;
import com.MyPilot.api.model.ViajeEstado;
import com.MyPilot.api.repository.ConductorRepository;
import com.MyPilot.api.repository.ViajeRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final ConductorRepository conductorRepository;
    private final ConductorQueueService conductorQueueService;
    private final SimpMessagingTemplate messagingTemplate;

    public ViajeService(
            ViajeRepository viajeRepository,
            ConductorRepository conductorRepository,
            ConductorQueueService conductorQueueService,
            SimpMessagingTemplate messagingTemplate) {
        this.viajeRepository = viajeRepository;
        this.conductorRepository = conductorRepository;
        this.conductorQueueService = conductorQueueService;
        this.messagingTemplate = messagingTemplate;
    }

    public record SolicitudViajeEvent(
            Long viajeId,
            Long viajeroId,
            String origenDireccion,
            Double origenLat,
            Double origenLng,
            String destinoDireccion,
            Double destinoLat,
            Double destinoLng
    ) {
    }

    public record ConductorAsignadoEvent(
            Long id,
            Long viajeroId,
            Long conductorId,
            ViajeEstado estado,
            LocalDateTime creadoEn,
            LocalDateTime actualizadoEn,
            String origenDireccion,
            Double origenLat,
            Double origenLng,
            String destinoDireccion,
            Double destinoLat,
            Double destinoLng,
            String conductorNombre,
            Double conductorLat,
            Double conductorLng
    ) {
    }

    public List<Viaje> obtenerTodos() {
        return viajeRepository.findAll();
    }

    public List<Viaje> obtenerPorViajeroIdOrdenado(Long viajeroId) {
        return viajeRepository.findByViajeroIdOrderByCreadoEnDesc(viajeroId);
    }

    public Optional<Viaje> obtenerPorId(Long id) {
        return viajeRepository.findById(id);
    }

    public Viaje crearViaje(
            Long viajeroId,
            String origenDireccion,
            Double origenLat,
            Double origenLng,
            String destinoDireccion,
            Double destinoLat,
            Double destinoLng) {
        Viaje viaje = new Viaje();
        viaje.setViajeroId(viajeroId);
        viaje.setEstado(ViajeEstado.SOLICITADO);
        viaje.setOrigenDireccion(origenDireccion);
        viaje.setOrigenLat(origenLat);
        viaje.setOrigenLng(origenLng);
        viaje.setDestinoDireccion(destinoDireccion);
        viaje.setDestinoLat(destinoLat);
        viaje.setDestinoLng(destinoLng);
        Viaje guardado = viajeRepository.save(viaje);

        SolicitudViajeEvent event = new SolicitudViajeEvent(
                guardado.getId(),
                guardado.getViajeroId(),
                guardado.getOrigenDireccion(),
                guardado.getOrigenLat(),
                guardado.getOrigenLng(),
                guardado.getDestinoDireccion(),
                guardado.getDestinoLat(),
                guardado.getDestinoLng()
        );

        conductorQueueService.obtenerPrimeros(10)
                .forEach(conductor -> messagingTemplate.convertAndSend(
                        "/topic/conductores/" + conductor.conductorId() + "/solicitud",
                        event
                ));

        return guardado;
    }

    public Viaje cambiarEstado(Long id, ViajeEstado nuevoEstado) {
        return viajeRepository.findById(id)
                .map(viaje -> {
                    if (!esTransicionValida(viaje.getEstado(), nuevoEstado)) {
                        throw new IllegalStateException("Transición no válida de " + viaje.getEstado() + " a " + nuevoEstado);
                    }
                    viaje.setEstado(nuevoEstado);
                    return viajeRepository.save(viaje);
                })
                .orElse(null);
    }

    public Viaje asignarConductor(Long viajeId, Long conductorId) {
        Optional<Viaje> viajeOpt = viajeRepository.findById(viajeId);
        if (viajeOpt.isEmpty()) {
            return null;
        }

        Optional<ConductorQueueService.ConductorEnCola> conductorOpt =
                conductorQueueService.obtenerPorId(conductorId);
        if (conductorOpt.isEmpty()) {
            return null;
        }

        Viaje viaje = viajeOpt.get();
        ConductorQueueService.ConductorEnCola conductor = conductorOpt.get();
        viaje.setConductorId(conductorId);
        viaje.setEstado(ViajeEstado.CONDUCTOR_EN_CAMINO);
        Viaje actualizado = viajeRepository.save(viaje);

        conductorQueueService.eliminar(conductorId);

        ConductorAsignadoEvent event = new ConductorAsignadoEvent(
                actualizado.getId(),
                actualizado.getViajeroId(),
                actualizado.getConductorId(),
                actualizado.getEstado(),
                actualizado.getCreadoEn(),
                actualizado.getActualizadoEn(),
                actualizado.getOrigenDireccion(),
                actualizado.getOrigenLat(),
                actualizado.getOrigenLng(),
                actualizado.getDestinoDireccion(),
                actualizado.getDestinoLat(),
                actualizado.getDestinoLng(),
                conductor.nombre(),
                conductor.ubicacionLat(),
                conductor.ubicacionLng()
        );

        messagingTemplate.convertAndSend("/topic/viajes/" + viajeId, event);

        return actualizado;
    }

    private boolean esTransicionValida(ViajeEstado actual, ViajeEstado nuevoEstado) {
        return (actual == ViajeEstado.SOLICITADO && nuevoEstado == ViajeEstado.CONDUCTOR_EN_CAMINO)
                || (actual == ViajeEstado.CONDUCTOR_EN_CAMINO && nuevoEstado == ViajeEstado.EN_CURSO)
                || (actual == ViajeEstado.EN_CURSO && nuevoEstado == ViajeEstado.FINALIZADO)
                || (actual == ViajeEstado.SOLICITADO && nuevoEstado == ViajeEstado.CANCELADO)
                || (actual == ViajeEstado.CONDUCTOR_EN_CAMINO && nuevoEstado == ViajeEstado.CANCELADO);
    }
}
