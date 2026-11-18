package br.edu.atitus.bompradev.auth.configs;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ConfigJwtTest {
    @Test
    void validaAssinaturaEmissorEExpiracao() {
        var configuracao = new ConfigJwt();
        var chave = configuracao.chaveJwt("chave-para-testes-com-mais-de-32-bytes");
        var encoder = configuracao.jwtEncoder(chave);
        var decoder = configuracao.jwtDecoder(chave);
        var agora = Instant.now();
        var claims = JwtClaimsSet.builder().issuer(ConfigJwt.EMISSOR).subject("usuario")
                .issuedAt(agora).expiresAt(agora.plusSeconds(3600)).claim("tipo", "USER").build();
        var token = encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims));

        assertEquals("usuario", decoder.decode(token.getTokenValue()).getSubject());
        var outraChave = configuracao.chaveJwt("outra-chave-para-testes-com-mais-de-32-bytes");
        assertThrows(JwtException.class, () -> configuracao.jwtDecoder(outraChave).decode(token.getTokenValue()));
        var expirado = JwtClaimsSet.builder().issuer(ConfigJwt.EMISSOR).subject("usuario")
                .issuedAt(agora.minusSeconds(180)).expiresAt(agora.minusSeconds(120)).build();
        var tokenExpirado = encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), expirado));
        assertThrows(JwtException.class, () -> decoder.decode(tokenExpirado.getTokenValue()));
        var emissorInvalido = JwtClaimsSet.builder().issuer("emissor-externo").subject("usuario")
                .issuedAt(agora).expiresAt(agora.plusSeconds(3600)).build();
        var tokenEmissorInvalido = encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), emissorInvalido));
        assertThrows(JwtException.class, () -> decoder.decode(tokenEmissorInvalido.getTokenValue()));
        assertThrows(IllegalArgumentException.class, () -> configuracao.chaveJwt("curta"));
    }
}
