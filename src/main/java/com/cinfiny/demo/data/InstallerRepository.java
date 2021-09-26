package com.cinfiny.demo.data;

import com.cinfiny.demo.models.Installer;
import org.springframework.data.repository.CrudRepository;

public interface InstallerRepository extends CrudRepository<Installer, Integer> {

    Installer findByEmployee(String employeeNumber);

    Installer findByEmail(String email);

}
