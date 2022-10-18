package com.vhbarros.belvo.FinaceAPIIntegration.repository;

import com.vhbarros.belvo.FinaceAPIIntegration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}