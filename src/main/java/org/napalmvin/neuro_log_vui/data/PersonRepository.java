/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.data;

import java.util.List;
import org.napalmvin.neuro_log_vui.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository<T extends Person> extends JpaRepository<T, Long> {

	List<T> findByLastNameStartsWithIgnoreCase(String lastName);
}