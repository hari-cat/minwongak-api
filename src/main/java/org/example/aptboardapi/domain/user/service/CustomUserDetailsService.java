package org.example.aptboardapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.domain.user.CustomUserDetails;
import org.example.aptboardapi.domain.user.entity.User;
import org.example.aptboardapi.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}
