package CW.chatbot.entities;

import CW.chatbot.commons.constants.Role;
import CW.chatbot.commons.constants.Status;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS")
public class Member implements UserDetails { // 사용자 정보
//    @Id @GeneratedValue
//    @Column(name = "no", updatable = false, unique = true, nullable = false)
//    private Long no;
    @Id
    @Column(name = "userId", unique = true, nullable = false)
    private String id;
    @Column(length = 50, nullable = false)
    private String password;
    @Column(name = "userName", nullable = false)
    private String username;
   // @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    //private Set<Role> roles;
    //private Set<Role> role = Set.of(Role.ADMIN); // 기본값으로 ADMIN 설정
    @CreationTimestamp
    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;
    @UpdateTimestamp
    @Column(name = "modifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    //private Set<Status> statuses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .collect(Collectors.toSet());
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
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
        return this.status == Status.Active; // 계정이 활성화 상태일 때만 true 반환
    }
}
