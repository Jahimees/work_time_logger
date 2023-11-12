package com.example.worktime.service;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Собственная реализация аутентификации пользователя
 */
@Service
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AccountDataService accountDataService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Производит аутентификацию пользователя. Сверяет имя пользователя, а также хэшированные пароли
     *
     * @param authentication аутентификация из {@link org.springframework.security.core.context.SecurityContextHolder}
     * @return объект аутентификации с данными аутентифицированного пользователя
     * @throws AuthenticationException возникает при неверно введенных данных
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Account> accountOptional = accountDataService.findByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new BadCredentialsException("Unknown user with username " + username);
        }

        Account account = accountOptional.get();

        if (!bCryptPasswordEncoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }

        CustomPrincipal principal = new CustomPrincipal(account.getIdAccount(), account.getRole());

        return new UsernamePasswordAuthenticationToken(
                principal, password, account.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
