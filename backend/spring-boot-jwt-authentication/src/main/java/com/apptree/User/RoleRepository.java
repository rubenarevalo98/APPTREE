package com.apptree.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);

	boolean existsByName(String name);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("""
			update Role r
			   set r.name = :name,
			       r.description = :description
			 where r.id = :id
			""")
	int updateRole(@Param("id") Long id, @Param("name") String name, @Param("description") String description);
}