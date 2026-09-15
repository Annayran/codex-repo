# TRSECG — TRSECG

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 9
- Instâncias DWF: 5
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CargosEtapasProcesso | Etapas Processo de Seleção | S | S |
| EmpresasEtapasProcesso | Etapas Processo de Seleção | S | S |
| EtapasCargoEmpresa | Etapas Cargo Empresa | S | N |
| EtapasSelecaoCargo | Cargos | S | S |
| EtapasSelecaoEmpresa | Empresas | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCARGO | Cargo | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODETAPA | Etapa | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| PESO | Peso | I |  |  | N |
| PONTUACAOMINIMA | Pontuação Mínima (%) | I |  |  | N |
| SEQETAPA | Seq. Etapa | I |  |  | N |
| VLRETAPA | Valor da Etapa | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CargosEtapasProcesso / [TRSECG](TRSECG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CargosEtapasProcesso / [TRSECG](TRSECG.md) | EtapasProcessoSelecao / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| EmpresasEtapasProcesso / [TRSECG](TRSECG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EmpresasEtapasProcesso / [TRSECG](TRSECG.md) | EtapasProcessoSelecao / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| EtapasCargoEmpresa / [TRSECG](TRSECG.md) | EtapasProcessoSelecao / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| EtapasProcessoSelecao / [TRSETA](TRSETA.md) | EtapasSelecaoCargo / [TRSECG](TRSECG.md) | CODETAPA->CODETAPA | I |
| EtapasProcessoSelecao / [TRSETA](TRSETA.md) | EtapasSelecaoEmpresa / [TRSECG](TRSECG.md) | CODETAPA->CODETAPA | I |
| EtapasSelecaoCargo / [TRSECG](TRSECG.md) | Cargo /  | CODCARGO->CODCARGO | I |
| EtapasSelecaoCargo / [TRSECG](TRSECG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EtapasSelecaoEmpresa / [TRSECG](TRSECG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
