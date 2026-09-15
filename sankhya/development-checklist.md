# Checklist de desenvolvimento Sankhya

Marque cada item como verificado, não aplicável ou **Pendente de validação pela equipe**.

- [ ] Processo funcional e documentos de origem/destino identificados.
- [ ] Limite e responsabilidade da transação definidos.
- [ ] Uso de `JapeSession` compatível com o padrão do projeto.
- [ ] Rollback possível em erro crítico.
- [ ] Exceções não são engolidas e recursos são fechados.
- [ ] Processamento duplicado e idempotência avaliados.
- [ ] TOP identificada.
- [ ] Versão da TOP confirmada.
- [ ] `TIPMOV` confirmado.
- [ ] Necessidade e vínculo em `TGFVAR` avaliados.
- [ ] Impacto em estoque avaliado.
- [ ] Impacto financeiro avaliado.
- [ ] Impacto fiscal avaliado.
- [ ] Confirmação/liberação avaliada.
- [ ] Cancelamento e reversão avaliados.
- [ ] Banco possui deploy, validação e rollback.
- [ ] Dashboard/JSP preserva aliases, parâmetros e permissões.
- [ ] JavaScript não contém regra crítica exclusiva nem segredo.
- [ ] Publicação, validação pós-deploy e retorno documentados.