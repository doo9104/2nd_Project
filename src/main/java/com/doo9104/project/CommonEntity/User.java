package com.doo9104.project.CommonEntity;

import com.doo9104.project.Shop.domain.entity.Address;
import com.doo9104.project.Shop.domain.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends TimeEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(length = 300, nullable = false, unique = true)
    private String userid;

    @Column(length = 300, nullable = false)
    private String nickname;

    @JsonIgnore // 비밀번호는 client에 전달이 안되도록 한다.(jackson annotation)
    @Column(length = 300, nullable = false)
    private String password;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    // 이메일 인증 여부
    @Enumerated(EnumType.STRING)
    private IsUse isUse;

    @Column(length = 50)
    private String authkey;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<Order>();

    @Embedded
    private Address address;


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }



    //////////////////////////////////////////////////////////
    //getter .. setter..
    //////////////////////////////////////////////////////////
    public void accountActive(IsUse isUse,String authkey) {
        this.isUse = isUse.inverse();
        this.authkey = null;
    }


    @Override
    public String getUsername() {
        return userid;
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