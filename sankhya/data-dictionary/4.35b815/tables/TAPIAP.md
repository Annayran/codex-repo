# TAPIAP — Item Apontamento

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemApontamento | Item Apontamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSERV | Serviço | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| IDOBJETO | ID Objeto | S |  |  | N |
| NOMETAB | Nome da Tabela | S |  |  | N |
| NUAPONTA | Nro Único Apontamento | I |  |  | N |
| NUMITEM | Número Item | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| QTDSERV | Quantidade Serviço | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaturamentoItemApontamento / [TAPARM](TAPARM.md) | ItemApontamento / [TAPIAP](TAPIAP.md) | NUAPONTA->NUAPONTA; NUMITEM->NUMITEM | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | ColetaAmostra / [TAPCAM](TAPCAM.md) | NUAPONTA->NUAPONTA; NUMITEM->NUMITEM | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | EquipamentoApontamento / [TAPEQT](TAPEQT.md) | NUAPONTA->NUAPONTA; NUMITEM->NUMITEM | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | EquipeApontamento / [TAPEAP](TAPEAP.md) | NUAPONTA->NUAPONTA; NUMITEM->NUMITEM | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | MovimentoApontamento / [TAPMAP](TAPMAP.md) | NUAPONTA->NUAPONTA | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | ItemApontamento / [TAPIAP](TAPIAP.md) | NUAPONTA->NUAPONTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
