# TCSMET — Modelo de Projeto

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloProjeto | Modelo de Projeto | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Status | S |  |  | N |
| CODMETOD | Modelo | I |  |  | N |
| DESCRMETOD | Descrição | S |  |  | N |
| NOMEMETOD | Nome | S |  |  | N |
| NOTAMODPREVENDA | Mod. Pedido Negociação | I |  |  | N |
| OBSERVACOES | Observações | S |  |  | N |
| QTDHORAS | Quantidade de Horas | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoMetodologia / [TCSGMT](TCSGMT.md) | ModeloProjeto / [TCSMET](TCSMET.md) | CODMETOD->CODMETOD | I |
| ModeloProjeto / [TCSMET](TCSMET.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NOTAMODPREVENDA->NUNOTA | I |
| ModeloProjeto / [TCSMET](TCSMET.md) | ModeloEtapa / [TCSMOD](TCSMOD.md) | CODMETOD->CODMETOD | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | ModeloProjeto / [TCSMET](TCSMET.md) | CODMETOD->CODMETOD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
