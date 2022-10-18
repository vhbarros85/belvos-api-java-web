package com.vhbarros.belvo.FinaceAPIIntegration.repository;

import com.vhbarros.belvo.FinaceAPIIntegration.model.Link;
import com.vhbarros.belvo.FinaceAPIIntegration.model.Role;
import com.vhbarros.belvo.FinaceAPIIntegration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LinkRepository extends JpaRepository<Link, String> {
    Set<Link> findByUser(User user);

    List<Link> getAllByUserId(int id);
}
