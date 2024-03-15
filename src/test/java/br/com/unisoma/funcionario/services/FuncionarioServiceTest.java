package br.com.unisoma.funcionario.services;
import org.junit.jupiter.api.Test;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class FuncionarioServiceTest {
    @Test
    void shoulBeShowTaxToBePay() {

        CalculateTaxService service = new CalculateTaxService();

        double salary = 3002.0;
        double valueTotalTax = service.calculateTax(salary);

        assertEquals(80.36, valueTotalTax);
    }
}