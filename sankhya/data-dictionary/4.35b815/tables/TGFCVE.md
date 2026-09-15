# TGFCVE — Controle de Veiculos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleVeiculo | Controle de Veículos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODVEICULO | Veículo | I |  |  | N |
| DHCHEGADA | Data Hora de Retorno | H |  |  | N |
| DHSAIDA | Data Hora de Saída | H |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| KMCHEGADA | Km Chegada | I |  |  | N |
| KMRODADOS | Km Rodados | I |  |  | S |
| KMSAIDA | Km Saída | I |  |  | N |
| NUAPONTA | NUAPONTA | I |  |  | N |
| NUMITEM | Número do ítem | I |  |  | N |
| NUMOS | Número da OS | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| ORDEMCARGA | Ordem de carga | I |  |  | N |
| PLACA | Placa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleVeiculo / [TGFCVE](TGFCVE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ControleVeiculo / [TGFCVE](TGFCVE.md) | Funcionario /  | CODEMP->CODEMP; CODFUNC->CODFUNC | I |
| ControleVeiculo / [TGFCVE](TGFCVE.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| ControleVeiculo / [TGFCVE](TGFCVE.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | ControleVeiculo / [TGFCVE](TGFCVE.md) | NUMOS->NUMOS | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | ControleVeiculo / [TGFCVE](TGFCVE.md) | NUAPONTA->NUAPONTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
