package org.example.aptboardapi.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.common.response.SessionUserResponse;
import org.example.aptboardapi.domain.auth.dto.LoginRequest;
import org.example.aptboardapi.domain.auth.dto.SignUpRequest;
import org.example.aptboardapi.domain.auth.service.AuthService;
import org.example.aptboardapi.domain.user.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignUpRequest request){
        authService.signup(request);
    }

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse){
        try{
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    );

            Authentication authentication =
                    authenticationManager.authenticate(token);

            SecurityContext context =
                    SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);

            securityContextRepository.saveContext(
                    context,
                    httpRequest,
                    httpResponse
            );
        } catch (Exception e){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal CustomUserDetails principal) {
        if(principal.getUserId() == null || principal.getUsername().isEmpty() || principal.getNickname().isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new SessionUserResponse(principal.getUserId(), principal.getUsername(), principal.getNickname()));
    }

}
