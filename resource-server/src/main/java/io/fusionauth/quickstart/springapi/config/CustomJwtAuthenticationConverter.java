package io.fusionauth.quickstart.springapi.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String ROLES_CLAIM = "roles";

    private static final String EMAIL_CLAIM = "email";

    private static final String AUD_CLAIM = "aud";

    private final List<String> audiences;

    public CustomJwtAuthenticationConverter(List<String> audiences) {
        this.audiences = audiences;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String email = jwt.getClaimAsString(EMAIL_CLAIM);
        System.out.println("jwt email " + email);
        if (hasAudience(jwt)) {
            Collection<GrantedAuthority> authorities = extractRoles(jwt);
            return new UsernamePasswordAuthenticationToken(email, "n/a", authorities);
        } else {
            return new UsernamePasswordAuthenticationToken(email, "n/a");
        }
    }

    private boolean hasAudience(Jwt jwt) {
        if (!jwt.hasClaim(AUD_CLAIM)) {
            System.out.println("jwt does not have claim " + AUD_CLAIM);
            return false;
        }
        if (jwt.getClaimAsStringList(AUD_CLAIM)
                .stream()
                .anyMatch(audiences::contains)) {
            System.out.println("jwt claim " + AUD_CLAIM + " match " + audiences);
            return true;
        }
        System.out.println("jwt claim " + AUD_CLAIM + " does not match " + audiences);
        return false;
    }

    private List<GrantedAuthority> extractRoles(Jwt jwt) {
        if (!jwt.hasClaim(ROLES_CLAIM)) {
            System.out.println("jwt does not have claim " + ROLES_CLAIM);
            return List.of();
        }
        List<GrantedAuthority> grantedAuthorities = jwt.getClaimAsStringList(ROLES_CLAIM)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        System.out.println("granted authorities " + grantedAuthorities);
        return grantedAuthorities;
    }
}
