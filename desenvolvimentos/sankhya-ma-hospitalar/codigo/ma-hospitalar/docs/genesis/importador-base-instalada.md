# Importador da Base Instalada Genesis

## Finalidade

Importar as oito abas da planilha da Base Instalada para as entidades adicionais
da tela `[ZAYA.IT] - Base Instalada Genesis`. A rotina nao grava em tabelas
padrao Sankhya e nao chama a API Genesis.

## Componentes

- `AcaoImportarBaseGenesis`: recebe e valida os lotes enviados pelo JSP.
- `ImportacaoBaseGenesisService`: controla limite, mapeamento e operacoes.
- `BaseGenesisImportRepository`: inclui ou atualiza por `IDBASE + IDGENESIS`.
- `importarBaseGenesis.jsp`: seleciona o arquivo, valida abas e exibe progresso.
- `acao-abrir-importador.js`: abre o componente a partir da tela `GENBASE`.

## Cadastro no Sankhya

1. Publicar `MAHospitalar.jar` e reiniciar/recarregar o modulo conforme o
   procedimento do ambiente.
2. Na instancia `GENBASE`, cadastrar uma acao Java para a classe
   `br.com.zaya.mahospitalar.genesis.action.AcaoImportarBaseGenesis`.
3. Cadastrar os parametros `IDBASE` e `LINHA_INICIAL` (numero), `OPERACAO`,
   `ABA`, `DADOS_JSON` e `ARQUIVO` (texto). `DADOS_JSON` deve aceitar o maior tamanho permitido pela
   acao na versao instalada.
4. Publicar o ZIP `MAHospitalar-Genesis-Importador-JSP.zip` no Gadget HTML5 ja
   cadastrado.
5. Abrir o Gadget diretamente. Nesta versao de homologacao, o JSP usa
   `IDBASE=1` e o codigo de acao Java `123`.

## Fluxo

1. O usuario abre o Gadget, que trabalha com a `AD_GENBASE.IDBASE=1`.
2. O modal le localmente as abas do Excel.
3. O navegador envia lotes sequenciais de ate 250 linhas.
4. O Java usa uma allowlist de oito abas e campos conhecidos.
5. Uma linha nova recebe `STATUSCONC=PENDENTE`; uma linha existente preserva os
   vinculos quando a planilha nao possui `CODPARC`.
6. Quando a aba de clientes possui `CODPARC`, o valor da planilha e considerado
   a fonte do vinculo: numero inteiro positivo gera `CONCILIADO`; `#N/A` ou vazio
   limpa o vinculo e gera `PENDENTE`, sempre com mensagem e data de rastreio.
7. Ao concluir todos os lotes, a base recebe `STATUS=IMPORTADA`.

## Abas e destinos

| Aba | Entidade |
|---|---|
| `prorpiedade_equipamento` | `GENDOMINIO`, tipo `PROPRIEDADE` |
| `situacao_equipamento` | `GENDOMINIO`, tipo `SITUACAO` |
| `Cliente` ou `Clientes` | `GENCLIENTE` |
| `Setor` | `GENSECTOR` |
| `Material` | `GENMATERIAL` |
| `Empresa` | `GENEMPRESA` |
| `Modelo` | `GENMODELO` |
| `ItemMaterial` | `GENITEMMAT` |

Na planilha validada em 2026-08-27, a leitura encontrou 120.241 registros:
8 propriedades, 9 situacoes, 24.324 clientes, 24.326 setores, 308 materiais,
71 empresas, 6.102 modelos e 65.093 itens-materiais.

Na planilha revisada em 2026-09-08, a aba `Clientes` possui 24.413 registros:
21.871 com `CODPARC` calculado e 2.542 sem correspondencia (`#N/A`). O
importador aceita ambos os resultados sem interromper o restante da carga.

## Homologacao

1. Usar uma base de teste vazia e uma planilha reduzida.
2. Conferir os totais por aba e os logs `[MAH-GENESIS-IMPORT]`.
3. Repetir a mesma importacao e confirmar que houve atualizacao, sem duplicacao.
4. Interromper durante uma aba e retomar; registros ja gravados devem ser
   atualizados de forma idempotente.
5. Validar setores sem cliente, modelos sem fabricante/material e itens sem
   modelo antes de definir a base como ativa.
6. Conferir clientes com `VINCULO_IMPORTADO_PLANILHA` e validar que `#N/A`
   resultou em `CODPARC` nulo e `STATUSCONC=PENDENTE`.
7. Executar `sql/genesis/conciliacao-clientes-idexterno/06_validar_importacao_planilha_clientes.sql`
   e confirmar que nao existem `CODPARC` orfaos ou divergencias de codigo externo.

## Rollback

Excluir a carga somente pela tela adicional e seguindo a ordem de detalhes
documentada no procedimento de implantacao. Nao habilitar exclusao em cascata e
nao executar a carga diretamente em producao sem homologacao.
