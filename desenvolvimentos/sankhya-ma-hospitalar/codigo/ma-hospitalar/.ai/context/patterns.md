# Padrões do Projeto

- Dependências em sentido `action -> service -> repository/client`.
- SQL com parâmetros; não concatenar entrada externa.
- Recursos JDBC sempre em try-with-resources.
- Não chamar API em evento transacional de documento.
- Identidade do equipamento: `CODPROD|SERIE` normalizada.
- Idempotência: tipo + chave + hash do payload.
- Segredos nunca em código, banco, log, dashboard ou documentação.
- Dashboard apenas analítico; regras e POST permanecem no Java.
