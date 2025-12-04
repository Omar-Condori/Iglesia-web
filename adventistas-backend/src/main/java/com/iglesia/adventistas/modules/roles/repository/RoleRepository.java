package com.iglesia.adventistas.modules.roles.repository;

import com.iglesia.adventistas.modules.roles.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findBySlug(String slug);

    Set<Role> findByIdIn(Set<Long> ids);
}