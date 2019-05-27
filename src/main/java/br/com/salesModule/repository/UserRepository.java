package br.com.salesModule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.salesModule.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	 Optional<User> findByCpf(String cpf);
}