# Metadados da Base Instalada Genesis

Pacote preparado para importar as modelagens da tela
`[ZAYA.IT] - Base Instalada Genesis` no Sankhya.

## Conteudo

- `AD_GENBASE`: registro mestre da carga.
- `AD_GENDOMINIO`: propriedades e situacoes Genesis.
- `AD_GENCLIENTE`: clientes da base instalada.
- `AD_GENSECTOR`: setores vinculados aos clientes.
- `AD_GENMATERIAL`: materiais Genesis.
- `AD_GENEMPRESA`: fabricantes e fornecedores.
- `AD_GENMODELO`: modelos dos equipamentos.
- `AD_GENITEMMAT`: equipamentos ou itens instalados.

`AD_GENBASE` possui relacionamentos de detalhe por `IDBASE` para as outras sete
instancias. O ZIP de importacao contem somente `metadata.xml`, seguindo os
arquivos de referencia analisados.

## Compatibilidade

- Estrutura de referencia: Sankhya `4.36b126`.
- Charset: `ISO-8859-1`.
- Banco indicado na referencia: Oracle 19c.
- Dados de conexao, usuario, scripts, actions e credenciais nao foram copiados.
- Nomes fisicos e tipos seguem `docs/genesis/modelagem-base-instalada-dwf.md`.
- `OBSERVACAO` e `MENSAGEMCONC` usam `CLOB`, conforme o padrao de texto longo
  comprovado no metadado `AD_GENMOVEST`.

## Referencias analisadas

```json
{
  "Metadados_AD_TGFSEQENT.zip": {
    "size": 1351,
    "sha256": "0C02BD01903E2FD7899F2CB3DB7BC9A61F2EEACF61AB678D9B8580D778990D35",
    "systemVersion": "4.36b124"
  },
  "metadata.xml": {
    "size": 55287,
    "sha256": "81C693C9E0FEF0609C7BFB0520D8ADAA6E8577CDD4C41E559CB8416FAA899F36",
    "systemVersion": "4.36b126"
  }
}
```

## Publicacao segura

1. Fazer backup restauravel do ambiente.
2. Confirmar se nenhuma instancia ou tabela `AD_GEN*` conflitante ja existe.
3. Importar primeiro em homologacao.
4. Conferir as oito instancias e os sete detalhes de `AD_GENBASE`.
5. Validar tipos, tamanhos, chaves e permissao de acesso no Dicionario de Dados.
6. Somente depois executar os scripts de carga do `IDBASE=1`.

Este pacote nao foi importado automaticamente e nao executa DML.
