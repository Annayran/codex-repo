# TIMPPR — Prospect

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 29
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimProspect | Prospect | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| PPRBAIRRO | Bairro | I |  |  | N |
| PPRCEP | CEP | S |  |  | N |
| PPRCIDADE | Cidade | I |  |  | N |
| PPRCODEND | Cód. Endereço | I |  |  | N |
| PPRCODIGO | Código | I |  |  | N |
| PPRCOMPLEMENTO | Complemento | S |  |  | N |
| PPRCPFCNPJ | CPF/CNPJ | S |  |  | N |
| PPRDATADENASCIMENTO | Data de nascimento | D |  |  | N |
| PPRDHALTER | Dh. Alteração | H |  |  | N |
| PPRDHINCLUSAO | Dh. Inclusão | H |  |  | N |
| PPREMAIL | E-Mail | S |  |  | N |
| PPRENDERECO | Endereço | S |  |  | N |
| PPRFACINI | FAC Inicial | I |  |  | N |
| PPRNOME | Nome | S |  |  | N |
| PPROBSERVACAO | Observação | S |  |  | N |
| PPRPOUPANCA | Valor em poupança | F |  |  | N |
| PPRPROFCONJUGE | Profissão do conjuge | I |  |  | N |
| PPRPROFISSAO | Profissão | I |  |  | N |
| PPRRAMAL | Ramal | S |  |  | N |
| PPRRENDA | Renda | F |  |  | N |
| PPRRENDACNJFORMAL | Renda do conjuge é formal | S |  |  | N |
| PPRRENDACONJUGE | Renda do conjuge | F |  |  | N |
| PPRRENDAFORMAL | Renda formal | S |  |  | N |
| PPRSEXO | Sexo | S |  |  | N |
| PPRTELEFONES | Telefone | S |  |  | N |
| PPRTIPO | Tipo Pessoa | S |  |  | N |
| PPRTIPOCLIENTE | Cliente potencial | S |  |  | N |
| PPRUSUALTER | Usuário Alteração | I |  |  | N |
| PPRUSUARIO | Usuário Inclusão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimProspect / [TIMPPR](TIMPPR.md) | FACPROSPECT->PPRCODIGO | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Bairro / [TSIBAI](TSIBAI.md) | PPRBAIRRO->CODBAI | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Cidade / [TSICID](TSICID.md) | PPRCIDADE->CODCID | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Endereco / [TSIEND](TSIEND.md) | PPRCODEND->CODEND | I |
| TimProspect / [TIMPPR](TIMPPR.md) | TimProfissaoConjuge / [TIMPRF](TIMPRF.md) | PPRPROFCONJUGE->PRFCODIGO | I |
| TimProspect / [TIMPPR](TIMPPR.md) | TimProfissaoRamoAtividade / [TIMPRF](TIMPRF.md) | PPRPROFISSAO->PRFCODIGO | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Usuario / [TSIUSU](TSIUSU.md) | PPRUSUARIO->CODUSU | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | PPRUSUALTER->CODUSU | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimProspect / [TIMPPR](TIMPPR.md) | FACPROSPECT->PPRCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
