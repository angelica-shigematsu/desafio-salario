package br.com.unisoma.funcionario.services;

import br.com.unisoma.funcionario.dto.FuncionarioDTO;
import br.com.unisoma.funcionario.models.Funcionario;
import br.com.unisoma.funcionario.repositories.FuncionarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class FuncionarioServiceTest {
    @Test
    void shoulBeShowTaxToBePay() {

        FuncionarioService service = new FuncionarioService();

        double salary = 3002.0;
        double valueTotalTax = service.calculateTax(salary);

        assertEquals(80.36, valueTotalTax);
    }


}