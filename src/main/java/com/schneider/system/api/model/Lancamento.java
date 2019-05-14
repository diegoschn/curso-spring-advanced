package com.schneider.system.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	
	private Funcionario funcionario;

}
