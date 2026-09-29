package org.example.aptboardapi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.domain.auth.dto.SignUpRequest;
import org.example.aptboardapi.domain.user.entity.User;
import org.example.aptboardapi.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpRequest request){
        if(userRepository.existsByUsername(request.username())){
            throw new BusinessException(ErrorCode.IS_EXIST_USER);
        }

        String encodesPassword = passwordEncoder.encode(request.password());

        User user = User.create(request.username(), request.nickname(), encodesPassword);

        userRepository.save(user);
    }
}
