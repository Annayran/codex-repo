# TCSMOD — Modelo Etapa

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloEtapa | Modelo Etapa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETOD | Código | I |  |  | N |
| CODPROD | Servico | I |  |  | N |
| DESCRICAO | Descrição do modelo | S |  |  | N |
| EXIGEPRODUTOS | Exige Prod. na Negociação | S |  |  | N |
| NUMMODELO | Número do Modelo | I |  |  | N |
| NUMMODELOPAI | Número do modelo pai | I |  |  | N |
| NUMSEQ | Sequência | I |  |  | N |
| OBRIGATORIA | OBRIGATORIA | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| SUPLEMENTA | Suplementa | S |  |  | N |
| SUPLEMENTADO | Suplementado | S |  |  | N |
| TIPVALOR | Tipo do Valor | S |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRSUPLEMENTA | Valor Suplementa | F |  |  | N |
| VLRSUPLEMENTADO | Valor Suplementado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | ModeloEtapa / [TCSMOD](TCSMOD.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | ModeloEtapaPredecessora / [TCSMPD](TCSMPD.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | RecursoModEtapa / [TCSMFU](TCSMFU.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | ServicoModEtapa / [TCSMSA](TCSMSA.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| ModeloEtapaPredecessora / [TCSMPD](TCSMPD.md) | ModeloEtapa / [TCSMOD](TCSMOD.md) | CODMETOD->CODMETOD; NUMMODELOPRED->NUMMODELO | I |
| ModeloProjeto / [TCSMET](TCSMET.md) | ModeloEtapa / [TCSMOD](TCSMOD.md) | CODMETOD->CODMETOD | I |
| RecursoModEtapa / [TCSMFU](TCSMFU.md) | ModeloEtapa / [TCSMOD](TCSMOD.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
