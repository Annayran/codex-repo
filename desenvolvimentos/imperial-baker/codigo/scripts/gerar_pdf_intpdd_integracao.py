from pathlib import Path

from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import mm
from reportlab.platypus import (
    BaseDocTemplate,
    Frame,
    PageTemplate,
    Paragraph,
    Spacer,
    Table,
    TableStyle,
    PageBreak,
)


ROOT = Path(__file__).resolve().parents[1]
OUT_DIR = ROOT / "output" / "pdf"
OUT_DIR.mkdir(parents=True, exist_ok=True)
PDF_PATH = OUT_DIR / "ZY-IB-2026-PARC-INTPDD-001.pdf"

PAGE_W, PAGE_H = A4
GREEN = colors.HexColor("#00B875")
DARK = colors.HexColor("#333333")
TEXT = colors.HexColor("#3B3B3B")
MUTED = colors.HexColor("#777777")
GRID = colors.HexColor("#D7D7D7")
HEADER_BG = colors.HexColor("#ECECEC")
CALLOUT_BG = colors.HexColor("#EEF9F5")

styles = getSampleStyleSheet()

styles.add(ParagraphStyle(
    "Brand",
    parent=styles["Normal"],
    fontName="Helvetica-Bold",
    fontSize=10,
    leading=14,
    textColor=GREEN,
    alignment=TA_CENTER,
    spaceAfter=6,
))
styles.add(ParagraphStyle(
    "DocType",
    parent=styles["Normal"],
    fontName="Helvetica-Bold",
    fontSize=10,
    leading=16,
    textColor=GREEN,
    alignment=TA_CENTER,
    spaceAfter=28,
    letterSpacing=4,
))
styles.add(ParagraphStyle(
    "CoverTitle",
    parent=styles["Title"],
    fontName="Helvetica-Bold",
    fontSize=22,
    leading=27,
    textColor=DARK,
    alignment=TA_CENTER,
    spaceAfter=8,
))
styles.add(ParagraphStyle(
    "CoverSubtitle",
    parent=styles["Normal"],
    fontName="Helvetica",
    fontSize=10.5,
    leading=15,
    textColor=DARK,
    alignment=TA_CENTER,
    spaceAfter=9,
))
styles.add(ParagraphStyle(
    "SectionWhite",
    parent=styles["Normal"],
    fontName="Helvetica-Bold",
    fontSize=10,
    leading=12,
    textColor=colors.white,
    alignment=TA_LEFT,
))
styles.add(ParagraphStyle(
    "Body",
    parent=styles["BodyText"],
    fontName="Helvetica",
    fontSize=10,
    leading=14.8,
    textColor=TEXT,
    spaceAfter=8,
))
styles.add(ParagraphStyle(
    "BodySmall",
    parent=styles["BodyText"],
    fontName="Helvetica",
    fontSize=8,
    leading=10.5,
    textColor=TEXT,
))
styles.add(ParagraphStyle(
    "Cell",
    parent=styles["BodyText"],
    fontName="Helvetica",
    fontSize=7.7,
    leading=9.8,
    textColor=TEXT,
))
styles.add(ParagraphStyle(
    "CellBold",
    parent=styles["BodyText"],
    fontName="Helvetica-Bold",
    fontSize=7.7,
    leading=9.8,
    textColor=TEXT,
))
styles.add(ParagraphStyle(
    "MetaLabel",
    parent=styles["BodyText"],
    fontName="Helvetica-Bold",
    fontSize=8,
    leading=10,
    textColor=GREEN,
))
styles.add(ParagraphStyle(
    "Footer",
    parent=styles["BodyText"],
    fontName="Helvetica",
    fontSize=7,
    leading=9,
    textColor=MUTED,
))


def p(text, style="Body"):
    return Paragraph(text, styles[style])


def c(text, bold=False):
    return Paragraph(text, styles["CellBold" if bold else "Cell"])


def section(title):
    return Table(
        [[p(title.upper(), "SectionWhite")]],
        colWidths=[172 * mm],
        rowHeights=[13 * mm],
        style=TableStyle([
            ("BACKGROUND", (0, 0), (-1, -1), GREEN),
            ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
            ("LEFTPADDING", (0, 0), (-1, -1), 12),
            ("RIGHTPADDING", (0, 0), (-1, -1), 12),
        ]),
    )


def table(data, widths, header=True, meta=False):
    rows = []
    for r, row in enumerate(data):
        out = []
        for idx, value in enumerate(row):
            if meta and idx == 0:
                out.append(Paragraph(value, styles["MetaLabel"]))
            else:
                out.append(c(value, bold=(header and r == 0)))
        rows.append(out)

    ts = [
        ("GRID", (0, 0), (-1, -1), 0.4, GRID),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("LEFTPADDING", (0, 0), (-1, -1), 7),
        ("RIGHTPADDING", (0, 0), (-1, -1), 7),
        ("TOPPADDING", (0, 0), (-1, -1), 6),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
    ]
    if header and not meta:
        ts.append(("BACKGROUND", (0, 0), (-1, 0), HEADER_BG))
    if meta:
        ts.append(("BACKGROUND", (1, 0), (1, 0), HEADER_BG))
        rows[0][1].style = styles["CellBold"]
    return Table(rows, colWidths=widths, repeatRows=1 if header and not meta else 0, style=TableStyle(ts))


def callout(text):
    return Table(
        [[p(text, "BodySmall")]],
        colWidths=[172 * mm],
        style=TableStyle([
            ("BACKGROUND", (0, 0), (-1, -1), CALLOUT_BG),
            ("LINEBEFORE", (0, 0), (0, -1), 2, GREEN),
            ("LEFTPADDING", (0, 0), (-1, -1), 14),
            ("RIGHTPADDING", (0, 0), (-1, -1), 10),
            ("TOPPADDING", (0, 0), (-1, -1), 10),
            ("BOTTOMPADDING", (0, 0), (-1, -1), 10),
        ]),
    )


class Doc(BaseDocTemplate):
    def __init__(self, filename):
        super().__init__(
            filename,
            pagesize=A4,
            leftMargin=20 * mm,
            rightMargin=20 * mm,
            topMargin=28 * mm,
            bottomMargin=24 * mm,
        )
        frame = Frame(self.leftMargin, self.bottomMargin, self.width, self.height, id="normal")
        self.addPageTemplates([
            PageTemplate(id="all", frames=[frame], onPage=draw_page),
        ])


def draw_page(canvas, doc):
    canvas.saveState()
    if doc.page == 1:
        canvas.setFillColor(GREEN)
        canvas.rect(0, PAGE_H - 11 * mm, PAGE_W, 4 * mm, fill=1, stroke=0)
        canvas.rect(0, 7 * mm, PAGE_W, 4 * mm, fill=1, stroke=0)
        canvas.setFont("Helvetica", 9)
        canvas.setFillColor(DARK)
        canvas.drawCentredString(PAGE_W / 2, 26 * mm, "Zaya IT - Gestao de Negocios | Documento de Uso Interno")
    else:
        canvas.setStrokeColor(GRID)
        canvas.line(20 * mm, 18 * mm, PAGE_W - 20 * mm, 18 * mm)
        canvas.setFillColor(MUTED)
        canvas.setFont("Helvetica", 7)
        canvas.drawString(20 * mm, 12 * mm, "Zaya IT - Gestao de Negocios | ZY-IB-2026-PARC-INTPDD-001 | Confidencial")
        canvas.drawRightString(PAGE_W - 20 * mm, 12 * mm, f"Pagina {doc.page}")
    canvas.restoreState()


def cover():
    return [
        Spacer(1, 54 * mm),
        p("ZAYA IT", "Brand"),
        p("G E S T A O   D E   N E G O C I O S", "Brand"),
        Spacer(1, 13 * mm),
        p("I N T P D D  -  D O C U M E N T O   D E   I N T E G R A C A O<br/>E   D E S E N V O L V I M E N T O", "DocType"),
        p("Integracao cadastral de parceiros", "CoverTitle"),
        p("Aproveitamento do fluxo JavaScript existente com atualizacao da TGFPAR no Sankhya", "CoverSubtitle"),
        p("Consulta ReceitaWS para atualizacao cadastral de parceiros", "CoverSubtitle"),
        Spacer(1, 10 * mm),
        Table(
            [[p("PROPOSTA TECNICA - PLANEJAMENTO EM 2 AGENDAS DE EXECUCAO", "SectionWhite")]],
            colWidths=[172 * mm],
            rowHeights=[13 * mm],
            style=TableStyle([
                ("BACKGROUND", (0, 0), (-1, -1), GREEN),
                ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
                ("LEFTPADDING", (0, 0), (-1, -1), 12),
            ]),
        ),
        Spacer(1, 9 * mm),
        table([
            ["Documento", "ZY-IB-2026-PARC-INTPDD-001"],
            ["Projeto", "Integracao cadastral de parceiros via ReceitaWS - Imperial Baker"],
            ["Versao", "v1.0"],
            ["Data", "07 de julho de 2026"],
            ["Responsavel Tecnico", "Zaya IT - Gestao de Negocios"],
            ["Tipo de Documento", "Documento Tecnico de Integracao e Desenvolvimento"],
            ["Tecnologia", "Sankhya / JavaScript / API ReceitaWS / TGFPAR"],
            ["Status", "Proposta para validacao"],
        ], [52 * mm, 120 * mm], header=False, meta=True),
    ]


def story():
    s = cover()
    s.append(PageBreak())

    s += [
        section("Objetivo deste documento"),
        Spacer(1, 9 * mm),
        p("Este documento formaliza o escopo tecnico para utilizar e adequar o processo atualmente existente em JavaScript no ambiente Sankhya da Imperial Baker."),
        p("O processo contempla consulta de CNPJ na ReceitaWS e atualizacao dos dados cadastrais e operacionais do parceiro na TGFPAR. Neste escopo, nao sera realizada validacao Sintegra, Simples Nacional ou inscricao estadual."),
        callout("<b>Planejamento recomendado:</b> execucao organizada em 2 agendas, considerando o reaproveitamento do desenvolvimento JavaScript da ReceitaWS ja existente e a necessidade de validacao do fluxo cadastral no Sankhya."),
        Spacer(1, 10 * mm),
        section("Solicitacao funcional"),
        Spacer(1, 9 * mm),
        p("Foi solicitado utilizar o que ja existe no processo atual em JavaScript, mantendo apenas a consulta ReceitaWS e atualizando o cadastro do parceiro com os dados retornados pela Receita."),
        table([
            ["#", "Necessidade solicitada", "Resultado esperado"],
            ["1", "Consultar dados cadastrais do CNPJ", "Preencher razao social, nome fantasia, endereco, CEP, cidade, UF, telefone e email do parceiro."],
            ["2", "Resolver endereco, bairro e cidade", "Localizar CODEND, CODBAI e CODCID a partir das tabelas internas do Sankhya."],
            ["3", "Preservar perfis do parceiro", "Manter CLIENTE, FORNECEDOR e TRANSPORTADORA quando ja existirem; assumir CLIENTE=S quando nenhum perfil estiver definido."],
            ["4", "Adequar o JavaScript existente", "Reaproveitar o desenvolvimento atual, ajustando pontos necessarios para execucao e manutencao no Sankhya."],
        ], [10 * mm, 62 * mm, 100 * mm]),
    ]
    s.append(PageBreak())

    s += [
        section("APIs e retornos considerados"),
        Spacer(1, 9 * mm),
        p("Abaixo estao os endpoints, parametros e principais campos de retorno identificados no fluxo atual."),
        table([
            ["API", "Envio", "Retornos utilizados", "Uso no Sankhya"],
            ["ReceitaWS", "GET /v1/cnpj/{cnpj}", "status, code, message, nome_empresarial, nome, razao_social, fantasia, cep, email, telefone, numero, complemento, logradouro, bairro, municipio, uf", "Atualizacao cadastral do parceiro, endereco, contato, cidade, bairro e CEP."],
        ], [34 * mm, 43 * mm, 58 * mm, 37 * mm]),
        Spacer(1, 10 * mm),
        section("Atualizacoes previstas na TGFPAR"),
        Spacer(1, 9 * mm),
        table([
            ["Grupo", "Campos envolvidos", "Observacao"],
            ["Identificacao", "CODPARC, CGC_CPF, TIPPESSOA, RAZAOSOCIAL, NOMEPARC", "TIPPESSOA deve ser J para CNPJ. CODPARC pode ser preservado a partir do formulario AD_PARCPEND."],
            ["Endereco", "CEP, CODEND, NUMEND, COMPLEMENTO, CODBAI, CODCID", "CODEND, CODBAI e CODCID dependem de resolucao nas tabelas internas TSIEND, TSIBAI, TSICID e TSIUFS."],
            ["Contato", "EMAIL, EMAILNFE, TELEFONE", "Telefone deve manter apenas digitos e ser limitado a 11 posicoes."],
            ["Perfil", "CLIENTE, FORNECEDOR, TRANSPORTADORA", "Se nenhum perfil estiver definido, o fluxo atual assume CLIENTE=S. Transportadora tambem deve ser FORNECEDOR=S."],
            ["Observacao fiscal", "Nao aplicavel neste escopo", "Nao sera realizada validacao Sintegra, Simples Nacional, inscricao estadual, SIMPLES, CLASSIFICMS ou IDENTINSCESTAD."],
        ], [31 * mm, 61 * mm, 80 * mm]),
    ]
    s.append(PageBreak())

    s += [
        section("Regras de negocio identificadas"),
        Spacer(1, 9 * mm),
        table([
            ["Regra", "Descricao"],
            ["CNPJ", "Remover caracteres nao numericos e completar com zeros a esquerda ate 14 digitos quando necessario."],
            ["Proxy", "Ler PROXYHOST e PROXYPORT da TSIPAR e configurar proxy HTTP/HTTPS antes das chamadas externas."],
            ["ReceitaWS", "Consumir endpoint /v1/cnpj/{cnpj} e tratar retorno JSON, mesmo que a configuracao de envio cite XML."],
            ["Razao social", "Usar nome_empresarial, nome ou razao_social, normalizando para caixa alta e limitando o tamanho conforme campo Sankhya."],
            ["Nome fantasia", "Usar fantasia quando houver; caso contrario, usar a razao social."],
            ["Endereco", "Resolver CODEND, CODBAI e CODCID por logradouro, bairro, municipio e UF retornados pela ReceitaWS."],
            ["Contato", "Preencher EMAIL, EMAILNFE e TELEFONE quando ainda nao estiverem cadastrados, preservando informacoes existentes."],
            ["Perfil", "Preservar CLIENTE, FORNECEDOR e TRANSPORTADORA do cadastro existente; se nenhum estiver marcado, assumir CLIENTE=S."],
        ], [46 * mm, 126 * mm]),
        Spacer(1, 10 * mm),
        section("Planejamento das agendas de execucao"),
        Spacer(1, 9 * mm),
        table([
            ["Agenda", "Foco", "Atividades previstas"],
            ["Agenda 1", "Adequacao do fluxo existente", "Conferir o JavaScript ja existente, validar pontos de entrada, ajustar consulta ReceitaWS, revisar tratamento do CNPJ, proxy, retorno JSON e mapeamento dos campos cadastrais."],
            ["Agenda 2", "Validacao e alinhamento final", "Validar atualizacao da TGFPAR, revisar preservacao de dados existentes, testar CNPJ valido e retorno incompleto/divergente, registrar pendencias e alinhar liberacao do escopo."],
        ], [47 * mm, 25 * mm, 100 * mm]),
    ]
    s.append(PageBreak())

    s += [
        section("Escopo aprovado e evolucoes futuras"),
        Spacer(1, 9 * mm),
        table([
            ["Cenario", "Situacao", "Entregavel", "Risco"],
            ["Escopo atual", "Planejado em 2 agendas", "Reaproveitamento do JavaScript existente, consulta ReceitaWS, atualizacao TGFPAR e validacao do fluxo cadastral.", "Baixo a medio. Adequado porque o desenvolvimento base ja existe."],
            ["Evolucao futura", "Fora do escopo", "Novas regras, historico de consultas, telas auxiliares ou validacoes fiscais futuras.", "Deve ser avaliado separadamente se solicitado."],
        ], [34 * mm, 20 * mm, 76 * mm, 42 * mm]),
        Spacer(1, 10 * mm),
        callout("<b>Posicionamento recomendado:</b> como sera utilizado o desenvolvimento JavaScript ja existente e nao havera validacao Sintegra, Simples Nacional ou inscricao estadual, recomenda-se executar o escopo em 2 agendas."),
        Spacer(1, 10 * mm),
        section("Riscos e mitigacoes"),
        Spacer(1, 9 * mm),
        table([
            ["Risco", "Impacto", "Mitigacao"],
            ["ReceitaWS retornar JSON enquanto o cadastro estiver configurado como XML", "Falha de parsing ou integracao.", "Tratar resposta como JSON/texto no JavaScript existente e ajustar configuracao da chamada."],
            ["Instabilidade ou limite da ReceitaWS", "Cadastro incompleto ou falha em lote.", "Usar timeout, log, retry controlado e reprocessamento manual."],
            ["Cidade, bairro ou endereco nao localizados", "Cadastro pode ficar sem CODCID, CODBAI ou CODEND.", "Registrar divergencia e permitir correcao manual/tabela de equivalencia."],
            ["CNPJ invalido ou sem retorno", "Parceiro nao sera atualizado.", "Validar CNPJ com 14 digitos e apresentar mensagem clara de falha."],
            ["Campos obrigatorios do parceiro", "Falha ao salvar TGFPAR.", "Validar campos obrigatorios antes de persistir e preservar valores existentes quando possivel."],
            ["Sobrescrita de campos preenchidos manualmente", "Perda de informacao operacional.", "Definir quais campos podem ser sobrescritos e quais devem ser preservados."],
        ], [48 * mm, 55 * mm, 69 * mm]),
    ]
    s.append(PageBreak())

    s += [
        section("Requisitos a confirmar"),
        Spacer(1, 9 * mm),
        table([
            ["#", "Pergunta", "Motivo"],
            ["1", "A rotina sera manual, automatica no cadastro ou em lote?", "Define transacao, performance, logs e experiencia de uso."],
            ["2", "Deve existir historico das consultas ReceitaWS?", "Importante para auditoria e suporte."],
            ["3", "Quais campos podem sobrescrever valores ja cadastrados?", "Reduz risco de alterar informacoes mantidas manualmente."],
            ["4", "Quando a cidade/endereco nao for localizado, deve bloquear ou apenas registrar divergencia?", "Define comportamento em casos comuns de diferenca cadastral."],
            ["5", "EMAIL, EMAILNFE e TELEFONE devem ser preenchidos apenas quando vazios?", "Confirma a regra atual de preservacao de contato."],
            ["6", "Se nenhum perfil estiver marcado, deve manter a regra de CLIENTE=S?", "Confirma comportamento ja existente no JS."],
            ["7", "Sintegra, Simples Nacional e IE ficam oficialmente fora deste escopo?", "Evita expectativa de atualizacao fiscal nesta entrega."],
        ], [10 * mm, 78 * mm, 84 * mm]),
        Spacer(1, 10 * mm),
        section("Conclusao"),
        Spacer(1, 9 * mm),
        p("O fluxo JavaScript existente reduz o esforco de entendimento funcional e permite concentrar a atividade em adequacao, teste e organizacao do processo. Como o recorte remove Sintegra, Simples Nacional, inscricao estadual e migracao de tecnologia, o escopo fica restrito a ReceitaWS e atualizacao cadastral da TGFPAR."),
        p("Dessa forma, a recomendacao tecnica e organizar a entrega em 2 agendas de execucao, considerando que o desenvolvimento base ja esta pronto em JavaScript e sera reaproveitado."),
        callout("<b>Resumo executivo:</b> escopo planejado em 2 agendas de execucao, utilizando o desenvolvimento JavaScript existente e mantendo o escopo restrito a ReceitaWS e atualizacao cadastral da TGFPAR."),
    ]

    return s


def main():
    doc = Doc(str(PDF_PATH))
    doc.build(story())
    print(PDF_PATH)


if __name__ == "__main__":
    main()
