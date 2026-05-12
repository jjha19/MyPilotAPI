package com.MyPilot.api.auth;

import com.MyPilot.api.model.Conductor;
import com.MyPilot.api.model.Viajero;
import com.MyPilot.api.repository.ConductorRepository;
import com.MyPilot.api.repository.ViajeroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ViajeroRepository viajeroRepository;
    private final ConductorRepository conductorRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            ViajeroRepository viajeroRepository,
            ConductorRepository conductorRepository,
            JwtUtils jwtUtils,
            PasswordEncoder passwordEncoder) {
        this.viajeroRepository = viajeroRepository;
        this.conductorRepository = conductorRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request == null || request.email() == null || request.password() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Viajero> viajeroOpt = viajeroRepository.findByCorreo(request.email());
        if (viajeroOpt.isPresent()) {
            Viajero viajero = viajeroOpt.get();
            if (viajero.getPassword() == null || !passwordEncoder.matches(request.password(), viajero.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String token = jwtUtils.generateToken(viajero.getCorreo(), "VIAJERO");
            return ResponseEntity.ok(new LoginResponse(
                    token,
                    viajero.getId(),
                    viajero.getNombre(),
                    viajero.getApellido(),
                    "VIAJERO"
            ));
        }

        Optional<Conductor> conductorOpt = conductorRepository.findByCorreo(request.email());
        if (conductorOpt.isPresent()) {
            Conductor conductor = conductorOpt.get();
            if (conductor.getPassword() == null || !passwordEncoder.matches(request.password(), conductor.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String token = jwtUtils.generateToken(conductor.getCorreo(), "CONDUCTOR");
            return ResponseEntity.ok(new LoginResponse(
                    token,
                    conductor.getId(),
                    conductor.getNombre(),
                    conductor.getApellido(),
                    "CONDUCTOR"
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
