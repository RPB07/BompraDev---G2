package br.edu.atitus.bompradev.auth.servicos;

import br.edu.atitus.bompradev.auth.dtos.LoginRequest;
import br.edu.atitus.bompradev.auth.entidades.TipoUsuario;
import br.edu.atitus.bompradev.auth.entidades.Usuario;
import br.edu.atitus.bompradev.auth.repositorios.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {
    @Mock
    private UsuarioRepository repositorio;
    @Mock
    private JwtEncoder jwtEncoder;
    private BCryptPasswordEncoder encoder;
    private AutenticacaoService autenticacao;
    private Usuario usuario;

    @BeforeEach
    void preparar() {
        encoder = new BCryptPasswordEncoder(4);
        autenticacao = new AutenticacaoService(repositorio, encoder, jwtEncoder);
        usuario = new Usuario("Pessoa", "pessoa@exemplo.com", encoder.encode("senha123"), TipoUsuario.PROVIDER);
        ReflectionTestUtils.setField(usuario, "id", UUID.randomUUID());
    }

    @Test
    void autenticaUsuarioEDevolveToken() {
        when(repositorio.findByEmailIgnoreCase("pessoa@exemplo.com")).thenReturn(Optional.of(usuario));
        when(jwtEncoder.encode(any())).thenReturn(Jwt.withTokenValue("token").header("alg", "HS256").subject("usuario").build());

        var resposta = autenticacao.entrar(new LoginRequest("PESSOA@exemplo.com", "senha123"));

        assertEquals("token", resposta.token());
        assertEquals("Bearer", resposta.tipoToken());
        assertEquals(3600, resposta.expiraEmSegundos());
        assertEquals(TipoUsuario.PROVIDER, resposta.usuario().tipo());
    }

    @Test
    void rejeitaSenhaIncorretaSemEmitirToken() {
        when(repositorio.findByEmailIgnoreCase("pessoa@exemplo.com")).thenReturn(Optional.of(usuario));
        var erro = assertThrows(ResponseStatusException.class,
                () -> autenticacao.entrar(new LoginRequest("pessoa@exemplo.com", "senhaerrada")));
        assertEquals(HttpStatus.UNAUTHORIZED, erro.getStatusCode());
        verifyNoInteractions(jwtEncoder);
    }

    @Test
    void rejeitaUsuarioInexistenteOuInativo() {
        when(repositorio.findByEmailIgnoreCase("pessoa@exemplo.com"))
                .thenReturn(Optional.empty(), Optional.of(usuario));
        assertEquals(HttpStatus.UNAUTHORIZED, assertThrows(ResponseStatusException.class,
                () -> autenticacao.entrar(new LoginRequest("pessoa@exemplo.com", "senha123"))).getStatusCode());
        ReflectionTestUtils.setField(usuario, "ativo", false);
        assertEquals(HttpStatus.UNAUTHORIZED, assertThrows(ResponseStatusException.class,
                () -> autenticacao.entrar(new LoginRequest("pessoa@exemplo.com", "senha123"))).getStatusCode());
        verifyNoInteractions(jwtEncoder);
    }

    @Test
    void consultaUsuarioAtivoPeloIdentificador() {
        when(repositorio.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        assertEquals(usuario.getId(), autenticacao.obterUsuario(usuario.getId().toString()).id());
        assertEquals(HttpStatus.UNAUTHORIZED, assertThrows(ResponseStatusException.class,
                () -> autenticacao.obterUsuario("identificador-invalido")).getStatusCode());
    }
}
