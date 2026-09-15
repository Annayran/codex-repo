# TFCPER — Perfil do Currículo

- Módulo técnico: [TFC](../modules/TFC.md)
- Campos: 2
- Instâncias DWF: 3
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CurriculoPerfilAreaProf | Perfil Área Profissional | N | N |
| CurriculoPerfilHierarquico | Perfil Nível Hierárquico | N | N |
| CurriculoPerfilRequisito | Perfil Requisitos | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPERFIL | Cód. Perfil | I |  |  | N |
| NUCURRICULO | Cód. Currículo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Curriculo / [TFCBCO](TFCBCO.md) | CurriculoPerfilAreaProf / [TFCPER](TFCPER.md) | NUCURRICULO->NUCURRICULO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | CurriculoPerfilHierarquico / [TFCPER](TFCPER.md) | NUCURRICULO->NUCURRICULO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | CurriculoPerfilRequisito / [TFCPER](TFCPER.md) | NUCURRICULO->NUCURRICULO | I |
| CurriculoPerfilAreaProf / [TFCPER](TFCPER.md) | PerfilAreaProf /  | CODPERFIL->CODPERFIL | I |
| CurriculoPerfilHierarquico / [TFCPER](TFCPER.md) | PerfilNivelHierarquico /  | CODPERFIL->CODPERFIL | I |
| CurriculoPerfilRequisito / [TFCPER](TFCPER.md) | PerfilRequisito /  | CODPERFIL->CODPERFIL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
