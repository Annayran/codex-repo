# TCSMSA — Serviço por Modelo de Etapa

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ServicoModEtapa | Serviço por Modelo de Etapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETOD | Metodologia | I |  |  | N |
| CODPROD | Serviço | I |  |  | N |
| COMPLEMENTAR | Complementar? | S |  |  | N |
| NUMMODELO | Número do Modelo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ModeloEtapa / [TCSMOD](TCSMOD.md) | ServicoModEtapa / [TCSMSA](TCSMSA.md) | CODMETOD->CODMETOD; NUMMODELO->NUMMODELO | I |
| ServicoModEtapa / [TCSMSA](TCSMSA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ServicoModEtapa / [TCSMSA](TCSMSA.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
