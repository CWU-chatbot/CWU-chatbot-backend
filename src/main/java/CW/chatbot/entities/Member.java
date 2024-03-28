package CW.chatbot.entities;

import CW.chatbot.commons.constants.Role;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails { // 사용자 정보
    @Id
    @Column(length=40, updatable = false, nullable = false, unique = true)
    private String id;
    @Column(length = 50, nullable = false)
    private String password;
    @Column(length = 40, nullable = false)
    private String username;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    //private Set<Role> role = Set.of(Role.ADMIN); // 기본값으로 ADMIN 설정
    @Column(insertable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    } // "ROLE_" 접두사는 Spring Security에서 권한을 표현하는 표준 방식 -> 권한을 명확하게 지정할 수 있음

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
