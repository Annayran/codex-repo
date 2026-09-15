# TIMMFD — Registro MFD

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimMFD | MFD | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| MFDCONTRATOADM | Contrato Administração | I |  |  | N |
| MFDCONTRATOCLA | Contrato Venda Imóvel | I |  |  | N |
| MFDCONTRATOLOC | Contrato Locação | I |  |  | N |
| MFDCONTRATOLTV | Contrato Venda Lote | I |  |  | N |
| MFDCORRESPBANC | Corresp. Bancário | I |  |  | N |
| MFDDESCRICAO | Descrição | S |  |  | N |
| MFDDHALTER | Dh. Alteração | H |  |  | N |
| MFDDHINCLUSAO | Dh. Inclusão | H |  |  | N |
| MFDMODNOTA | Nota Modelo | I |  |  | N |
| MFDNUNOTA | Nro. Nota | I |  |  | N |
| MFDPARCEIRO | Parceiro | I |  |  | N |
| MFDPRODUTO | Produto | S |  |  | N |
| MFDSEQUENCE | Código | I |  |  | N |
| MFDUSUALTEROU | Usuário Alteração | I |  |  | N |
| MFDUSUINCLUIU | Usuário Inclusão | I |  |  | N |
| MFDVLRNOTA | Vlr. Nota | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimMFD / [TIMMFD](TIMMFD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | MFDNUNOTA->NUNOTA | I |
| TimMFD / [TIMMFD](TIMMFD.md) | Parceiro / [TGFPAR](TGFPAR.md) | MFDPARCEIRO->CODPARC | I |
| TimMFD / [TIMMFD](TIMMFD.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | MFDCORRESPBANC->CODPARC | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimContratoAdm / [TIMADM](TIMADM.md) | MFDCONTRATOADM->ADMNUCONTRATO | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | MFDCONTRATOCLA->CLACODIGO | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | MFDCONTRATOLTV->LTVCODIGO | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimLocacao / [TIMLOC](TIMLOC.md) | MFDCONTRATOLOC->LOCCODIGO | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimModeloNota / [TIMCNF](TIMCNF.md) | MFDMODNOTA->CNFNUNOTA | I |
| TimMFD / [TIMMFD](TIMMFD.md) | Usuario / [TSIUSU](TSIUSU.md) | MFDUSUINCLUIU->CODUSU | I |
| TimMFD / [TIMMFD](TIMMFD.md) | Usuario2 / [TSIUSU](TSIUSU.md) | MFDUSUALTEROU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
