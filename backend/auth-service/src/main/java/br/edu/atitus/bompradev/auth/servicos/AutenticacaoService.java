package br.edu.atitus.bompradev.auth.servicos;

import br.edu.atitus.bompradev.auth.configs.ConfigJwt;
import br.edu.atitus.bompradev.auth.dtos.LoginRequest;
import br.edu.atitus.bompradev.auth.dtos.LoginResponse;
import br.edu.atitus.bompradev.auth.dtos.UsuarioResponse;
import br.edu.atitus.bompradev.auth.entidades.Usuario;
import br.edu.atitus.bompradev.auth.repositorios.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Service
public class AutenticacaoService {
    private static final long DURACAO_TOKEN_SEGUNDOS = 3600;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final String senhaDeVerificacao;

    public AutenticacaoService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.senhaDeVerificacao = passwordEncoder.encode("credencial-inexistente");
    }

    public LoginResponse entrar(LoginRequest dados) {
        if (dados.senha().getBytes(StandardCharsets.UTF_8).length > 72) {
            throw credenciaisInvalidas();
        }
        String email = dados.email().strip().toLowerCase(Locale.ROOT);
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).orElse(null);
        String hash = usuario == null ? senhaDeVerificacao : usuario.getSenhaHash();
        boolean senhaCorreta = passwordEncoder.matches(dados.senha(), hash);
        if (usuario == null || !usuario.isAtivo() || !senhaCorreta) {
            throw credenciaisInvalidas();
        }

        Instant agora = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ConfigJwt.EMISSOR)
                .subject(usuario.getId().toString())
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(DURACAO_TOKEN_SEGUNDOS))
                .claim("tipo", usuario.getTipo().name())
                .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
        return new LoginResponse(token, "Bearer", DURACAO_TOKEN_SEGUNDOS, UsuarioResponse.de(usuario));
    }

    public UsuarioResponse obterUsuario(String identificador) {
        try {
            Usuario usuario = usuarioRepository.findById(UUID.fromString(identificador))
                    .filter(Usuario::isAtivo)
                    .orElseThrow(AutenticacaoService::credenciaisInvalidas);
            return UsuarioResponse.de(usuario);
        } catch (IllegalArgumentException e) {
            throw credenciaisInvalidas();
        }
    }

    private static ResponseStatusException credenciaisInvalidas() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
    }
}
