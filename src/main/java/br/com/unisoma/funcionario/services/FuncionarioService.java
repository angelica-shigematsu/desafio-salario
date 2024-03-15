package br.com.unisoma.funcionario.services;

import br.com.unisoma.funcionario.dto.FuncionarioDTO;
import br.com.unisoma.funcionario.models.Funcionario;
import br.com.unisoma.funcionario.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public Funcionario create(FuncionarioDTO funcionarioDTO) {
        if (funcionarioDTO.getCpf().length() < 14) return null;

        if (funcionarioDTO.getSalary() < 0.0) return null;

        Funcionario dataFuncionario = this.repository.findByCpf(funcionarioDTO.getCpf());

        if (dataFuncionario != null) return null;

        Funcionario hasPhone = this.repository.findByPhone(funcionarioDTO.getPhone());

        if (hasPhone != null) return null;

        Funcionario funcionario = convertEntity(funcionarioDTO);
        return this.repository.save(funcionario);
    }

    private Funcionario convertEntity(FuncionarioDTO dto) {
        Funcionario funcionario= new Funcionario();
        funcionario.setFullname(dto.getFullname());
        funcionario.setCpf(dto.getCpf());
        funcionario.setBirthDate(dto.getBirthDate());
        funcionario.setPhone(dto.getPhone());
        funcionario.setAddress(dto.getAddress());
        funcionario.setSalary(dto.getSalary());
        return funcionario;
    }
}
