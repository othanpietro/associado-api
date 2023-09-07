create table associado(uuid varchar(36),
 documento varchar(14), 
 tipo_pessoa varchar(2), 
 nome varchar(50));
 
 
 ALTER TABLE boleto
ADD PRIMARY KEY (uuid);
 create table boleto(
 uuid varchar(36),
 valor decimal(10,2),
 uuid_associado varchar(36),
 FOREIGN KEY (uuid_associado) REFERENCES associado(uuid),
 documento_pagador varchar(14),
 nome_pagador varchar(50),
 nome_fantasia_pagador varchar(50),
 situacao varchar(50),
 vencimento date,
 indentificador int
 );
