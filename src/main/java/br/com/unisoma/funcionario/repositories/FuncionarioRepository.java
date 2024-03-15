package br.com.unisoma.funcionario.repositories;

import br.com.unisoma.funcionario.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Funcionario findByCpf(String cpf);

    Funcionario findByPhone(String phone);
}
