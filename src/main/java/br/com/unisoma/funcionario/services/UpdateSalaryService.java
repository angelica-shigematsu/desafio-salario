package br.com.unisoma.funcionario.services;

import br.com.unisoma.funcionario.models.Funcionario;
import br.com.unisoma.funcionario.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UpdateSalaryService {
    @Autowired
    FuncionarioRepository repository;

    public Object updateSalary(String cpf) {
        String message = "";
        double newSalary, oldSalary, reajustSalary = 0;
        String salary = "";
        try {
            if (cpf.isEmpty()) return message = "Campo vazio";

            Funcionario funcionario = this.repository.findByCpf(cpf);

            if (funcionario == null) return message = "Não foi possível achar esse cpf";

            int percentual = this.findPercentual(funcionario.getSalary());

            newSalary = this.calculateSalary(funcionario.getSalary(), percentual);

            oldSalary = funcionario.getSalary();

            reajustSalary = newSalary - oldSalary;

            DecimalFormat df =  new DecimalFormat();

            df.setMaximumFractionDigits(2);

            salary = df.format(reajustSalary);

            Optional<Object> updatedSalary = this.repository.findById(funcionario.getId()).map(data -> {
                data.setSalary(newSalary);
                return this.repository.save(data);
            });

            if (updatedSalary == null) return message = "Não foi possível atualizar";

            return this.printValues(cpf, newSalary, salary, percentual);

        }catch(Error error) {
            return error;
        }
    }

    public Object printValues(String cpf, double newSalary, String salary, int percentual) {
        Map<String,String> showData = new HashMap<>();

        showData.put( "CPF: ",cpf);
        showData.put( "Novo salário: ", Double.toString(newSalary));
        showData.put( "Reajuste de ganho: ", salary);
        showData.put( "Em percetual: ", Double.toString(percentual) + " %");

        return showData.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private int findPercentual(double salary) {
        int percent sual = 0;
        if (salary <= 400.0) {
            percentual = 15;
        } else if (salary <= 800.0) {
            percentual= 12;
        } else if (salary <= 1200.0) {
            percentual = 10;
        } else if (salary <= 2000.0) {
            percentual = 7;
        } else {
            percentual = 4;
        }

        return percentual;
    }
    private double calculateSalary(double salary, int percentual) {
        return salary *= (1 + (percentual/100.0));
    }
}
