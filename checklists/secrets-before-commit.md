# Checklist de secrets antes do commit

- [ ] `.gitignore` cobre `.env`, chaves, certificados, logs, caches e telemetria.
- [ ] O status Git foi revisado arquivo por arquivo.
- [ ] A busca de possíveis segredos reportou somente caminhos, nunca valores.
- [ ] Não há credencial, token, senha, connection string real ou URL interna sensível.
- [ ] Não há exportação, dump, log, arquivo de produção ou dado pessoal de cliente.
- [ ] Exemplos usam valores fictícios e seguros.
- [ ] Arquivos binários e dependências locais têm origem e necessidade justificadas.
- [ ] O remote foi conferido e nenhum push será feito sem autorização explícita.
