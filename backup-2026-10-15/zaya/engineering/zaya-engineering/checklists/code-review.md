# Checklist de revisão de código

- [ ] A revisão está limitada ao `git diff` atual.
- [ ] Contexto adicional foi consultado somente quando necessário para entender o diff.
- [ ] Escopo corresponde à Issue ou OS.
- [ ] Mudança está pequena e compreensível.
- [ ] Não há credenciais ou dados de clientes.
- [ ] Compatibilidade Java e dependências foram preservadas.
- [ ] Exceções, recursos, logs e valores nulos foram tratados.
- [ ] Testes e evidências são proporcionais ao risco.
- [ ] Documentação e rollback estão adequados.
- [ ] Não há refatoração paralela sem justificativa.

Prompt recomendado:

> Revise exclusivamente o git diff atual. Consulte contexto adicional somente se
> necessário. Não procure melhorias fora das linhas afetadas.
