INSERT INTO t_das_enderecos (logradouro, bairro, cep, complemento, cidade, uf, numero)
VALUES ('Logradouro', 'Bairro', 'Cep', 'Complemento', 'Cidade', 'Uf', 123);

INSERT INTO t_das_clinicas (nome, cnpj, telefone, email, razaoSocial, dataCadastro, senha, fotoClinica, role, endereco_id)
VALUES (
  'Clinica Admin',
  '00.111.222-3333-44',
  '+55 00 12345-6789',
  'admin@email.com',
  'Clinica Admin Ltda',
  CURRENT_TIMESTAMP,
  '$2a$10$e.Hxz3cb/fttvOxON4zs.OdtctfqrRtUUp.MQ7xyikrRyAX5wmgnO', -- senha: admin
  'foto',
  'ADMIN',
  1
);
