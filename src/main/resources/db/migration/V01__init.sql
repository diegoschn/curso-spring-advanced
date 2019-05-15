CREATE TABLE empresa(

	id SERIAL not null,
	cnpj varchar(255) not null,
	data_atualizacao timestamp not null,
	data_criacao timestamp not null,
	razao_social varchar(255) not null,
	
	CONSTRAINT empresa_id_pk PRIMARY KEY (id)
);

CREATE TABLE funcionario(

	id SERIAL not null,
	cpf varchar(255) not null,
	data_atualizacao timestamp not null,
	data_criacao timestamp not null,
	email varchar(255) not null,
	perfil varchar(255) not null,
	qtd_horas_almoco float DEFAULT NULL,
	qtd_horas_trabalho_dia float DEFAULT NULL,
	senha varchar(255) not null,
	valor_hora decimal(10,2) not null,
	empresa_id SERIAL not null,
	
	CONSTRAINT funcionario_id_pk PRIMARY KEY (id) 
);

alter table FUNCIONARIO
   add constraint FK_func_reference_empresa foreign key (empresa_id)
      references empresa (id)
      on delete restrict on update restrict;
      
CREATE TABLE lancamento(

	id SERIAL not null,
	data timestamp not null,
	data_atualizacao timestamp not null,
	data_criacao timestamp not null,
	descricao varchar(255) not null,
	localizacao varchar(255) not null,
	tipo varchar(255) not null,
	funcionario_id SERIAL not null,
	CONSTRAINT lancamento_id_pk PRIMARY KEY (id)
);

alter table lancamento
   add constraint fk_lancamento_reference_func foreign key (funcionario_id)
      references lancamento (id)
      on delete restrict on update restrict;
      