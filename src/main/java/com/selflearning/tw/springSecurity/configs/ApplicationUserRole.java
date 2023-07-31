package com.selflearning.tw.springSecurity.configs;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum ApplicationUserRole {
//    ADMIN(Sets.newHashSet()),
//    MANAGER(Sets.newHashSet()),
//    ACCOUNTING(Sets.newHashSet()),
//    ENGINEER(Sets.newHashSet()),
//    PARTTIME(Sets.newHashSet());
    MANAGER("MANAGER"),
    ARTIST("ARTIST"),
    LEADER("LEADER"),
    MEMBERS("MEMBERS"),
    FAN("FAN");

//    private final Set<ApplicationUserPermission> permissions;
    private final String permissions;

    ApplicationUserRole(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
//        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toSet());
//        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return permissions;

        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
//        permissions.add(new SimpleGrantedAuthority(getPermissions()));
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
