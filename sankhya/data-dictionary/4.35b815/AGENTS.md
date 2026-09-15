# Consulta ao dicionário Sankhya 4.35b815

## Obrigatória

- Antes de consultar o XML mgeDD, use README.md, TABLE_INDEX.md e a página
  da tabela em tables/.
- Confirme que o projeto alvo utiliza versão compatível com 4.35b815.
- Trate relações deste mapa como relações DWF, não como foreign keys físicas
  comprovadas.
- Diferencie campos comprovados nesta exportação de hipóteses funcionais.
- Não execute DML ou importe metadados com base somente neste mapa.
- Não inclua usuário, domínio, scripts integrais, URLs internas ou credenciais.
- Para objetos AD_, confirme a existência e o significado no ambiente alvo.

## Ordem de consulta

1. Pesquise a tabela em TABLE_INDEX.md.
2. Abra tables/<TABELA>.md.
3. Confirme campos, tipo e tamanho.
4. Verifique instâncias e relações DWF.
5. Use os CSVs de catalog/ somente para filtros em massa.
6. Registre como pendência tudo que exigir constraint física, regra funcional ou
   validação em outra versão.