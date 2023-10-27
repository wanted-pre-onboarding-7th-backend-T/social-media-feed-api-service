package com.wanted.common.security.vo;

import io.jsonwebtoken.Claims;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class Principal extends User {
    private final Long id;

    public Principal(com.wanted.user.entity.User user,List<SimpleGrantedAuthority> authorities) {
        super(user.getUserName(), user.getPassword(), authorities);
        this.id = user.getId();
    }

    public Principal(Claims claims) {
        super(claims.getSubject(), "", (Collection<? extends GrantedAuthority>)claims.get("authorities"));
        this.id = claims.get("id",Long.class);
    }
}
