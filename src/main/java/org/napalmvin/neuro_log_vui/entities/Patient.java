/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PATIENTS")
public class Patient extends Person{

    public Patient() {
    }
    

}
