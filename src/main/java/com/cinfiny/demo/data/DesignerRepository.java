package com.cinfiny.demo.data;

import com.cinfiny.demo.models.Designer;
import org.springframework.data.repository.CrudRepository;

public interface DesignerRepository extends CrudRepository<Designer, Integer> {

    Designer findByEmployeeNumber(String employeeNumber);

    Designer findByEmail(String email);
}
