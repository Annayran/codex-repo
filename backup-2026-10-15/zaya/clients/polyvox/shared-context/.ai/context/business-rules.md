# Regras de Negócio

## Entidades Principais

- **Ordem de Serviço TLC (`AD_OSTLC`)**: entidade central da integração de OS, identificada por `NROOS`, com campos como `STATUSOS`, `OSPAGA`, `CNPJ`, datas, produto, consumidor, documentos gerados e JSON original.
- **Peças da OS (`AD_OSPECA`)**: peças relacionadas a uma OS, usadas para decidir a forma de pagamento/documento.
- **Parceiro/Posto (`TGFPAR`)**: parceiro localizado por CNPJ; no dashboard aparece associado à taxa de manutenção/valor fixo por OS.
- **Configuração Telecontrol (`AD_TCLCFG`)**: contém URL, token, ambiente, endpoints, modelos de pedido, tabelas de preço e flags.
- **Pedido Telecontrol (`AD_TCLPED`)**: pedidos integrados/exportados, com vínculo a nota (`NUNOTA`) e status de exportação.
- **Peças/produtos Telecontrol (`AD_TCLPCA`)**: cadastro de peça/produto usado em pedidos/faturamento.
- **Notas/Financeiro Sankhya**: `TGFCAB`, `TGFITE`, `TGFVAR`, `Financeiro`, `CabecalhoNota`, `ItemNota`.

## Processos

- **Sincronização de cadastros Telecontrol**: busca/envio de produtos, postos, peças, famílias, linhas, lista básica, pedidos e faturamento.
- **Sincronização de OS**: busca OS na API Telecontrol e persiste/atualiza em `AD_OSTLC`; peças são persistidas em `AD_OSPECA` quando presentes.
- **Processamento documental de OS**:
  - Carrega OS finalizadas e não pagas.
  - Agrupa por CNPJ do posto (`AD_OSTLC.CNPJ`).
  - Se a OS possui peça em `AD_OSPECA`, pode gerar nota de compra agrupada por parceiro.
  - Se a OS não possui peça, gera financeiro direto com provisão desabilitada, conforme comentário do código.
  - Marca OS como paga quando documento/financeiro é gerado com sucesso.
- **Dashboard OS TLC**:
  - Lista apenas OS com `STATUSOS = 'Finalizada'`.
  - Consolida por parceiro/CNPJ.
  - Divide OS em pagas/não pagas, com peça/sem peça.
  - Calcula totais por quantidade de OS multiplicada pelo valor fixo/taxa de manutenção.

## Validações

- OS sem número numérico é ignorada/não persistida em vários fluxos.
- OS já paga (`OSPAGA = 'S'`) é ignorada pelo processamento documental.
- OS com `STATUSOS` diferente de `Finalizada` é ignorada no processamento documental e no dashboard.
- OS sem CNPJ do posto é ignorada no agrupamento documental.
- Parceiro sem valor fixo configurado é ignorado no processamento do grupo.
- Criação de nota de compra pode ser desligada por parâmetro `TLC_GERA_NOTA_COMPRA_OS` ou configuração compatível em `AD_TCLCFG`.

## Cálculos

- Valor por grupo de OS com peça: `valorFixoServico * quantidadeComPecas`, arredondado para 2 casas (`HALF_UP`).
- Valor por grupo de OS sem peça: `valorFixoServico * quantidadeSemPecas`, arredondado para 2 casas (`HALF_UP`).
- Dashboard calcula `VLR_TOTAL`, `VLR_GERADO`, `VLR_PENDENTE`, `VLR_COM_PECA`, `VLR_SEM_PECA` multiplicando `AD_TAXAMANUTENCAO`/valor fixo pela quantidade de OS correspondente.

## Restrições

- Metadata Sankhya pode depender dos nomes totalmente qualificados antigos em `actions/*`.
- Dependências locais do Sankhya são `compileOnly`; o ambiente de execução deve prover esses jars.
- IDs de rotinas Java no dashboard aparecem como configuráveis e com valores padrão mencionados no JSP: `83`, `134` e `136`.
- O processamento documental depende de tabelas customizadas existirem e estarem consistentes (`AD_OSTLC`, `AD_OSPECA`, `AD_TCLCFG`).

## Regras a Confirmar

- Qual campo deve ser considerado fonte oficial do valor fixo: código Java menciona `AD_VLRFIXSER`; dashboard consulta `PAR.AD_TAXAMANUTENCAO` com alias `AD_VLRFIXSER`.
- Quais modelos de nota e parâmetros são obrigatórios para nota de compra de OS com peças.
- Critérios completos para financeiro direto sem peça.
- Se o dashboard deve disparar a versão em `dashboard_os_tlc` ou `Dashboards/dashboard_os_tlc`.
- Fluxo oficial de desfazer geração: campos afetados identificados no JSP incluem `OSPAGA`, `NUFINGER` e `NUNOTAGER`, mas a rotina Java exata deve ser confirmada.
