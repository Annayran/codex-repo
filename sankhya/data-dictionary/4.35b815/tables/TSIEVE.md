# TSIEVE — Eventos para Cálculo de Frete

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventoVeiculo | Eventos para Cálculo de Frete | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | Cód. Evento | I |  |  | N |
| CODSERV | Serviço | I |  |  | N |
| DESCREVENTO | Descrição | S |  |  | N |
| GRUPO | Grupo | S |  |  | N |
| SINAL | Sinal | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoVeiculo / [TSIEVE](TSIEVE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| EventoVeiculoCadastro / [TGFEVEVEI](TGFEVEVEI.md) | EventoVeiculo / [TSIEVE](TSIEVE.md) | CODEVENTO->CODEVENTO | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | EventoVeiculo / [TSIEVE](TSIEVE.md) | CODEVENTO->CODEVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
