# Carga pontual da Base Instalada

`Resultado_da_Query.xlsx` não é uma extração Genesis válida. A carga requer arquivo
oficial, separado por entidade, com IDs, códigos externos e relacionamentos.

1. Copiar `sql/genesis/02_carga_legado_template.sql` para área segura fora do Git.
2. Validar colunas, tipos, IDs duplicados, séries ambíguas e referências órfãs.
3. Preencher os blocos de origem e executar em homologação numa transação controlada.
4. Abortar integralmente diante de validação crítica.
5. Executar `03_validacao.sql` antes/depois e reconciliar totais por entidade/origem.
6. Repetir com o delta final após congelar os jobs Protheus.

O template faz `MERGE` apenas em `AD_MAH_GEN_MAP` e `AD_MAH_GEN_BASE`; nenhuma carga
pode incluir DML em `TGFEST`. Códigos externos legados são preservados e a chave
Sankhya fica em coluna separada.
