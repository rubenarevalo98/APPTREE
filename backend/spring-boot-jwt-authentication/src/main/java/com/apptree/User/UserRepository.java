package com.apptree.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("""
			update User u
			   set u.firstname = :firstname,
			       u.lastname  = :lastname,
			       u.country   = :country
			 where u.id = :id
			""")
	int updateUser(@Param("id") Long id, @Param("firstname") String firstname, @Param("lastname") String lastname,
			@Param("country") String country);
}