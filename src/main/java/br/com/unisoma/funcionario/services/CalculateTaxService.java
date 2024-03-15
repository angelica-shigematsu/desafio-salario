package br.com.unisoma.funcionario.services;

import br.com.unisoma.funcionario.models.Funcionario;
import br.com.unisoma.funcionario.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculateTaxService {
    @Autowired
    private FuncionarioRepository repository;
    public Object printValueTax(String cpf) {
        String message = "";
        if (cpf.isEmpty()) return "Campo vazio";

        Funcionario funcionario = this.repository.findByCpf(cpf);

        if (funcionario == null) return "Não foi possível achar esse cpf";

        double amountTax = this.calculateTax(funcionario.getSalary());

        if (amountTax == 0.0) message = "Isento";

        if (amountTax > 0.0) message = "R$ " + amountTax;

        Map<String,String> showData = new HashMap<>();
        showData.put( "CPF: ", cpf);
        showData.put( "Imposto: ", message);

        return showData.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public double calculateTax(double salary) {

        double valueTax = 0.0;

        double valueExceed = 0.0;

        double valueFloor = 0.0;

        if (salary <= 2000) {
            valueTax = 0.0;
        } else if (salary <= 3000){
            valueTax = (salary - 2000) * 0.08;
        } else if (salary <= 4500) {
            valueFloor = (3000 - 2000) * 0.08;
            valueExceed = (salary - 3000) * 0.18;
            valueTax = valueFloor + valueExceed;
        } else {
            valueFloor = (4500 - 2000) * 0.18;
            valueExceed = (salary - 4500) * 0.28;
            valueTax = valueFloor + valueExceed;
        }

        return valueTax;
    }
}
