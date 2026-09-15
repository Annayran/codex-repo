# TIMIMP — Proprietários dos imóveis

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimProprietario | Proprietários do Imóvel | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IMPCODTIPTIT | Tipo Título p/ Repasse | I |  |  | N |
| IMPCODUSUINC | Usuário Inclusão | I |  |  | N |
| IMPCONTACORRENTE | Conta Corrente p/ repasse | I |  |  | N |
| IMPCONTRATO | Contrato | I |  |  | N |
| IMPDHALTER | Dh. Alteração | H |  |  | N |
| IMPDHINC | Dh. Inclusão | H |  |  | N |
| IMPIMOVEL | Cod. Imóvel | I |  |  | N |
| IMPPERCENTUAL | Posse (%) | F |  |  | N |
| IMPPERCENTUALRETIDO | Retido (%) | F |  |  | N |
| IMPPROCURADOR | Procurador | I |  |  | N |
| IMPPROPRIETARIO | Proprietário | I |  |  | N |
| IMPUSUARIO | Usuário Alteração | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimBeneficiario / [TIMIRB](TIMIRB.md) | TimProprietario / [TIMIMP](TIMIMP.md) | IRBCONTRATO->IMPCONTRATO; IRBPROPIMOVEL->IMPPROPRIETARIO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimProprietario / [TIMIMP](TIMIMP.md) | ADMIMOVEL->IMPIMOVEL; ADMNUCONTRATO->IMPCONTRATO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimProprietario / [TIMIMP](TIMIMP.md) | IMVCODIGO->IMPIMOVEL | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimProprietario / [TIMIMP](TIMIMP.md) | LOCCONTRATOADM->IMPCONTRATO; LOCIMOVEL->IMPIMOVEL | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | Parceiro / [TGFPAR](TGFPAR.md) | IMPPROPRIETARIO->CODPARC | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | ParceiroOrigem / [TGFPAR](TGFPAR.md) | IMPPROCURADOR->CODPARC | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | TimBeneficiario / [TIMIRB](TIMIRB.md) | IMPCONTRATO->IRBCONTRATO; IMPIMOVEL->IRBIMOVEL; IMPPROPRIETARIO->IRBPROPIMOVEL | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | IMPCONTACORRENTE->CBLCODIGO; IMPPROPRIETARIO->CBLLOCADOR | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | IMPCODTIPTIT->CODTIPTIT | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | Usuario / [TSIUSU](TSIUSU.md) | IMPUSUARIO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
