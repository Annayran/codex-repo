# TCSESA — Serviço por Etapa de Projeto (ServicoEtapa)

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ServicoEtapa | ServicoEtapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Serviço | I |  |  | N |
| COMPLEMENTAR | Complementar? | S |  |  | N |
| NUFAP | Número da Fap | I |  |  | N |
| NUMETAPA | Número da Etapa | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaProjeto / [TCSFET](TCSFET.md) | ServicoEtapa / [TCSESA](TCSESA.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| ServicoEtapa / [TCSESA](TCSESA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
