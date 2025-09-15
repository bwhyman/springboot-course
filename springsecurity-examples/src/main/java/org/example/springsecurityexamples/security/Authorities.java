package org.example.springsecurityexamples.security;

import org.springframework.stereotype.Component;

@Component("auth")
public final class Authorities {
    public static final String MANAGER_READ = "manager_read";
    public static final String MANAGER_CREATE = "manager_create";
    public static final String MANAGER_UPDATE = "manager_update";
    public static final String MANAGER_DELETE = "manager_delete";
}
