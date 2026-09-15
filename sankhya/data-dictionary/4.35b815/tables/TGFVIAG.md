# TGFVIAG — Viagem

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Viagem | Viagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| CODVEIPRIN | Veículo de tração | I |  |  | N |
| CODVEIREB1 | Reboque 1 | I |  |  | N |
| CODVEIREB2 | Reboque 2 | I |  |  | N |
| CODVEIREB3 | Reboque 3 | I |  |  | N |
| CONTEMDOCTERC | Contém documentos de terceiros? | S |  |  | N |
| DHALTER | Data de Alteração | H |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SERIE | Série | S |  |  | N |
| STATUSDOC | Status Doc. | S |  |  | N |
| TIPAMB | Tip. Amb. MDF-e | S |  |  | N |
| TIPMODALMDFE | Tipo Modal MDFe | S |  |  | N |
| USACIOTCTEVINC | Usar CIOTs dos CT-e vinculados ao MDF-e? | S |  |  | N |
| USATOMSERVCONTR | Usar tomadores de serv. como contratantes? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| ManifestoCompPgtoFrete / [TGFMDFCOMPGFRE](TGFMDFCOMPGFRE.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| ManifestoPrazoPgtoFrete / [TGFMDFPRAPGFRE](TGFMDFPRAPGFRE.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| MotoristaMDF / [TGFMME](TGFMME.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | MotoristaMDF / [TGFMME](TGFMME.md) | NUVIAG->NUVIAG | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEIPRIN->CODVEICULO | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | VeiculoReboque1 / [TGFVEI](TGFVEI.md) | CODVEIREB1->CODVEICULO | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | VeiculoReboque2 / [TGFVEI](TGFVEI.md) | CODVEIREB2->CODVEICULO | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | VeiculoReboque3 / [TGFVEI](TGFVEI.md) | CODVEIREB3->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
