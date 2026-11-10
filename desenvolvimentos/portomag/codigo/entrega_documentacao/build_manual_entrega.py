from __future__ import annotations

from pathlib import Path

from docx import Document
from docx.enum.section import WD_SECTION
from docx.enum.table import WD_ALIGN_VERTICAL
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Inches, Pt, RGBColor
from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import inch
from reportlab.platypus import (
    Image,
    KeepTogether,
    PageBreak,
    Paragraph,
    SimpleDocTemplate,
    Spacer,
    Table,
    TableStyle,
)


ROOT = Path(__file__).resolve().parent
ASSETS = ROOT / "assets"
OUT = ROOT / "out"
OUT.mkdir(parents=True, exist_ok=True)

DOCX_PATH = OUT / "Manual_Entrega_Dashboard_Consolidado_Fornecedores.docx"
PDF_PATH = OUT / "Manual_Entrega_Dashboard_Consolidado_Fornecedores.pdf"

NAVY = "071A3A"
INK = "0B1736"
MUTED = "64748B"
GREEN = "22C55E"
GREEN_DARK = "16A34A"
GREEN_LIGHT = "DCFCE7"
RED = "EF4444"
RED_LIGHT = "FEE2E2"
ORANGE = "F97316"
ORANGE_LIGHT = "FFEDD5"
BLUE_GRAY = "E8EEF5"
SOFT = "F6F8FB"
BORDER = "D8E0EA"
WHITE = "FFFFFF"

META_ROWS = [
    ("Documento", "ZY-QFORN-2026-ENTREGA-MANUAL-001"),
    ("Projeto", "Dashboard Consolidado de Fornecedores"),
    ("Cliente", "PortoMag"),
    ("Versao", "v1.0"),
    ("Data", "30 de junho de 2026"),
    ("Responsavel tecnico", "Zaya IT - Gestao de Negocios"),
    ("Tecnologia", "Sankhya - Dashboard HTML5/JSP, Flow/BPMN e Oracle SQL"),
    ("Status", "Documento final de entrega e apoio ao usuario"),
]

DELIVERABLES = [
    ("Dashboard consolidado", "Tela operacional de qualidade de fornecedores com KPIs, graficos, filtros, abas e historico consolidado."),
    ("Flow de inspecao", "Processo INSPECAO_FORNECEDOR no grupo FORNECEDORES, baseado no formulario AD_RECLFORN."),
    ("Flow de homologacao", "Processo HOMOLOGACAO_FORNECEDOR no grupo FORNECEDORES, baseado no formulario AD_HOMOLFORN."),
    ("Pacotes de instalacao", "Arquivos projeto_2_v2.zip e projeto_3_v2.zip exportados do Sankhya em 29/06/2026."),
    ("Documentacao de apoio", "Manual do usuario, criterios de aceite, checklist operacional e termo de entrega/assinatura."),
]

KPI_ROWS = [
    ("Score de qualidade", "Percentual principal do periodo filtrado. Considera os itens avaliados e separa conformidade de ocorrencias."),
    ("Itens avaliados", "Quantidade de registros considerados no periodo e nos filtros aplicados."),
    ("% Conforme", "Itens classificados como conformes sobre o total avaliado."),
    ("% Atraso", "Itens entregues com atraso sobre o total avaliado."),
    ("Distribuicao consolidada", "Grafico de rosca por status: conforme, fora da especificacao, desvio, atraso e nao entregue."),
    ("Historico consolidado", "Grafico temporal para acompanhar evolucao das ocorrencias por data."),
]

FLOW_INSPECAO = [
    ("Processo", "INSPECAO_FORNECEDOR"),
    ("Formulario", "AD_RECLFORN - Reclamacao de Fornecedor"),
    ("Campos principais", "Fornecedor, tipo ocorrencia, data, descricao, gravidade, responsavel, tratativa, status, encerramento, nota fiscal, anexo, certificado, especificado, encontrado e instrumento."),
    ("Tipos de ocorrencia", "Atraso de entrega, entrega parcial, produto avariado, problema fiscal, divergencia de qualidade, outras ocorrencias e entrega normal sem ocorrencias."),
    ("Status", "Aberta, em tratativa e encerrada."),
    ("Areas envolvidas", "Compras/Suprimentos, Qualidade, Fiscal e Gestor."),
]

FLOW_HOMOLOGACAO = [
    ("Processo", "HOMOLOGACAO_FORNECEDOR"),
    ("Formulario", "AD_HOMOLFORN - Homologacao de Fornecedor"),
    ("Campos principais", "Fornecedor, documentos, observacao, visita tecnica, status, data de validade, score, exige homologacao, certificacao, referencia tecnica, classificacao e motivo de ajuste."),
    ("Status", "Pendente, homologado, reprovado e ajustes."),
    ("Areas envolvidas", "Compras/Suprimentos, Documentacao, Validador e Aprovador."),
    ("Atualizacao", "Ao aprovar, o processo registra homologacao e atualiza informacoes do parceiro existente."),
]

USER_STEPS = [
    ("Acessar o dashboard", "Entrar no Sankhya, abrir a tela personalizada [ZAYA.IT] - Qualidade de Fornecedores e aguardar o carregamento dos indicadores."),
    ("Atualizar a leitura", "Usar o botao Atualizar quando precisar recarregar os dados do periodo, filtros ou registros recem-lancados."),
    ("Ler os KPIs", "Conferir score de qualidade, itens avaliados, percentual conforme e percentual de atraso antes de analisar a tabela."),
    ("Filtrar e buscar", "Usar data inicial, data final e campo de busca para localizar fornecedor, codigo, tipo de ocorrencia, status ou responsavel."),
    ("Alternar as abas", "Usar Consolidado para resumo por fornecedor e Historico para a lista detalhada dos registros avaliados no Flow."),
    ("Investigar ocorrencias", "Na aba Historico, revisar situacao Flow, tipo, data, gravidade, status e responsavel para direcionar tratativas."),
    ("Registrar evidencias", "Quando aplicavel, complementar o Flow com anexo, certificado, nota fiscal, especificado, encontrado e instrumento."),
    ("Encerrar tratativa", "Atualizar status, tratativa e data de encerramento somente depois da validacao operacional da area responsavel."),
]

ACCEPTANCE = [
    ("Dashboard acessivel", "Tela abre no Sankhya para os usuarios autorizados e exibe o cabecalho do projeto."),
    ("Indicadores carregam", "Cards de score, itens avaliados, % conforme e % atraso carregam sem erro."),
    ("Graficos visiveis", "Distribuicao consolidada e historico consolidado aparecem com dados do periodo."),
    ("Filtros funcionam", "Periodo, busca e botao Limpar alteram a lista exibida sem quebrar a tela."),
    ("Abas funcionam", "Consolidado e Historico exibem as quantidades esperadas e alternam corretamente."),
    ("Flow de inspecao", "Registros de reclamacao de fornecedor podem ser criados e tratados pelas areas previstas."),
    ("Flow de homologacao", "Registros de homologacao podem ser avaliados, aprovados, reprovados ou enviados para ajuste."),
    ("Assinatura de aceite", "Cliente valida o funcionamento e assina o termo de entrega deste documento."),
]


def rgb(hex_color: str) -> RGBColor:
    return RGBColor(int(hex_color[0:2], 16), int(hex_color[2:4], 16), int(hex_color[4:6], 16))


def hx(hex_color: str):
    return colors.HexColor("#" + hex_color.lstrip("#"))


def shade_cell(cell, fill: str) -> None:
    tc_pr = cell._tc.get_or_add_tcPr()
    shd = tc_pr.find(qn("w:shd"))
    if shd is None:
        shd = OxmlElement("w:shd")
        tc_pr.append(shd)
    shd.set(qn("w:fill"), fill)


def set_cell_border(cell, color: str = BORDER, size: str = "8") -> None:
    tc = cell._tc
    tc_pr = tc.get_or_add_tcPr()
    borders = tc_pr.first_child_found_in("w:tcBorders")
    if borders is None:
        borders = OxmlElement("w:tcBorders")
        tc_pr.append(borders)
    for edge in ("top", "left", "bottom", "right"):
        tag = "w:{}".format(edge)
        element = borders.find(qn(tag))
        if element is None:
            element = OxmlElement(tag)
            borders.append(element)
        element.set(qn("w:val"), "single")
        element.set(qn("w:sz"), size)
        element.set(qn("w:space"), "0")
        element.set(qn("w:color"), color)


def set_repeat_table_header(row) -> None:
    tr_pr = row._tr.get_or_add_trPr()
    tbl_header = OxmlElement("w:tblHeader")
    tbl_header.set(qn("w:val"), "true")
    tr_pr.append(tbl_header)


def set_cell_text(cell, text: str, bold: bool = False, color: str = INK, size: float = 9.5) -> None:
    cell.text = ""
    p = cell.paragraphs[0]
    p.paragraph_format.space_after = Pt(0)
    run = p.add_run(text)
    run.bold = bold
    run.font.name = "Arial"
    run._element.rPr.rFonts.set(qn("w:ascii"), "Arial")
    run._element.rPr.rFonts.set(qn("w:hAnsi"), "Arial")
    run.font.size = Pt(size)
    run.font.color.rgb = rgb(color)


def add_docx_table(doc: Document, headers: list[str], rows: list[tuple[str, ...]], widths: list[float]) -> None:
    table = doc.add_table(rows=1, cols=len(headers))
    table.autofit = False
    set_repeat_table_header(table.rows[0])
    for i, header in enumerate(headers):
        cell = table.rows[0].cells[i]
        cell.width = Inches(widths[i])
        shade_cell(cell, NAVY)
        set_cell_border(cell, NAVY)
        set_cell_text(cell, header.upper(), True, WHITE, 8.7)
        cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
    for row in rows:
        cells = table.add_row().cells
        for i, value in enumerate(row):
            cells[i].width = Inches(widths[i])
            shade_cell(cells[i], WHITE)
            set_cell_border(cells[i], BORDER, "4")
            set_cell_text(cells[i], value, False, INK, 9)
            cells[i].vertical_alignment = WD_ALIGN_VERTICAL.CENTER
    doc.add_paragraph()


def add_heading(doc: Document, text: str, level: int = 1) -> None:
    p = doc.add_paragraph(style=f"Heading {level}")
    run = p.add_run(text)
    run.font.name = "Arial"
    run._element.rPr.rFonts.set(qn("w:ascii"), "Arial")
    run._element.rPr.rFonts.set(qn("w:hAnsi"), "Arial")


def add_body(doc: Document, text: str, bold_lead: str | None = None) -> None:
    p = doc.add_paragraph(style="Normal")
    if bold_lead:
        r = p.add_run(bold_lead)
        r.bold = True
        r.font.color.rgb = rgb(INK)
        p.add_run(" ")
    p.add_run(text)


def add_bullet(doc: Document, text: str) -> None:
    p = doc.add_paragraph(style="List Bullet")
    p.add_run(text)


def add_callout(doc: Document, title: str, text: str, fill: str = GREEN_LIGHT) -> None:
    table = doc.add_table(rows=1, cols=1)
    table.autofit = False
    cell = table.rows[0].cells[0]
    cell.width = Inches(6.5)
    shade_cell(cell, fill)
    set_cell_border(cell, BORDER, "6")
    p = cell.paragraphs[0]
    p.paragraph_format.space_after = Pt(2)
    r = p.add_run(title.upper())
    r.bold = True
    r.font.name = "Arial"
    r.font.size = Pt(9)
    r.font.color.rgb = rgb(GREEN_DARK if fill == GREEN_LIGHT else INK)
    p2 = cell.add_paragraph()
    p2.paragraph_format.space_after = Pt(0)
    r2 = p2.add_run(text)
    r2.font.name = "Arial"
    r2.font.size = Pt(9.5)
    r2.font.color.rgb = rgb(INK)
    doc.add_paragraph()


def setup_docx_styles(doc: Document) -> None:
    section = doc.sections[0]
    section.page_width = Inches(8.5)
    section.page_height = Inches(11)
    section.top_margin = Inches(0.75)
    section.bottom_margin = Inches(0.72)
    section.left_margin = Inches(0.8)
    section.right_margin = Inches(0.8)
    section.header_distance = Inches(0.35)
    section.footer_distance = Inches(0.35)

    styles = doc.styles
    normal = styles["Normal"]
    normal.font.name = "Arial"
    normal._element.rPr.rFonts.set(qn("w:ascii"), "Arial")
    normal._element.rPr.rFonts.set(qn("w:hAnsi"), "Arial")
    normal.font.size = Pt(10.2)
    normal.font.color.rgb = rgb(INK)
    normal.paragraph_format.space_after = Pt(6)
    normal.paragraph_format.line_spacing = 1.18

    for name, size, color, before, after in [
        ("Heading 1", 16, NAVY, 16, 8),
        ("Heading 2", 12.5, NAVY, 12, 6),
        ("Heading 3", 11, INK, 8, 4),
    ]:
        style = styles[name]
        style.font.name = "Arial"
        style._element.rPr.rFonts.set(qn("w:ascii"), "Arial")
        style._element.rPr.rFonts.set(qn("w:hAnsi"), "Arial")
        style.font.size = Pt(size)
        style.font.bold = True
        style.font.color.rgb = rgb(color)
        style.paragraph_format.space_before = Pt(before)
        style.paragraph_format.space_after = Pt(after)

    for name in ("List Bullet", "List Number"):
        style = styles[name]
        style.font.name = "Arial"
        style._element.rPr.rFonts.set(qn("w:ascii"), "Arial")
        style._element.rPr.rFonts.set(qn("w:hAnsi"), "Arial")
        style.font.size = Pt(10)
        style.paragraph_format.space_after = Pt(4)
        style.paragraph_format.left_indent = Inches(0.38)


def set_header_footer(doc: Document) -> None:
    section = doc.sections[0]
    header = section.header.paragraphs[0]
    header.alignment = WD_ALIGN_PARAGRAPH.LEFT
    run = header.add_run("Zaya IT - Qualidade de Fornecedores | Documento de entrega")
    run.font.name = "Arial"
    run.font.size = Pt(8.5)
    run.font.color.rgb = rgb(MUTED)

    footer = section.footer.paragraphs[0]
    footer.alignment = WD_ALIGN_PARAGRAPH.CENTER
    r = footer.add_run("Zaya IT - Gestao de Negocios | PortoMag | Confidencial")
    r.font.name = "Arial"
    r.font.size = Pt(8)
    r.font.color.rgb = rgb(MUTED)


def build_docx() -> None:
    doc = Document()
    setup_docx_styles(doc)
    set_header_footer(doc)

    # Cover
    cover = doc.add_table(rows=1, cols=1)
    cover.autofit = False
    cell = cover.rows[0].cells[0]
    cell.width = Inches(6.5)
    shade_cell(cell, NAVY)
    set_cell_border(cell, NAVY)
    p = cell.paragraphs[0]
    p.paragraph_format.space_after = Pt(10)
    r = p.add_run("ZAYA IT")
    r.bold = True
    r.font.name = "Arial"
    r.font.size = Pt(18)
    r.font.color.rgb = rgb(WHITE)
    p2 = cell.add_paragraph()
    p2.paragraph_format.space_after = Pt(0)
    rr = p2.add_run("GESTAO DE NEGOCIOS")
    rr.font.name = "Arial"
    rr.font.size = Pt(8.5)
    rr.font.color.rgb = rgb(GREEN)

    doc.add_paragraph()
    title = doc.add_paragraph()
    title.alignment = WD_ALIGN_PARAGRAPH.LEFT
    tr = title.add_run("MANUAL DO USUARIO E DOCUMENTO DE ENTREGA")
    tr.bold = True
    tr.font.name = "Arial"
    tr.font.size = Pt(21)
    tr.font.color.rgb = rgb(NAVY)
    subtitle = doc.add_paragraph()
    subtitle.paragraph_format.space_after = Pt(14)
    sr = subtitle.add_run("Dashboard Consolidado de Fornecedores - Qualidade, Reclamacoes e Homologacao")
    sr.font.name = "Arial"
    sr.font.size = Pt(12.5)
    sr.font.color.rgb = rgb(MUTED)

    add_docx_table(doc, ["Campo", "Informacao"], META_ROWS, [1.65, 4.85])
    add_callout(
        doc,
        "Finalidade",
        "Este material consolida a entrega tecnica, o apoio de uso diario e os criterios de aceite para assinatura do dashboard e dos flows de fornecedores.",
    )

    doc.add_page_break()

    add_heading(doc, "1. Objetivo do documento")
    add_body(
        doc,
        "Formalizar a entrega do Dashboard Consolidado de Fornecedores e orientar o uso operacional da tela e dos flows associados. O documento tambem funciona como manual de apoio para consulta, treinamento e aceite final da solucao.",
    )
    add_body(
        doc,
        "A documentacao foi montada com base no design system do PDF de referencia, nos prints do dashboard e nos pacotes de Flow exportados do Sankhya.",
    )

    add_heading(doc, "2. Escopo entregue")
    add_docx_table(doc, ["Item", "Descricao"], DELIVERABLES, [1.8, 4.7])
    add_callout(
        doc,
        "Nota de seguranca",
        "Detalhes sensiveis de conexao, banco de dados e ambiente foram omitidos da documentacao de entrega. O foco deste documento e funcional, operacional e de aceite.",
        BLUE_GRAY,
    )

    add_heading(doc, "3. Visao geral do dashboard")
    add_body(
        doc,
        "A tela [ZAYA.IT] - Qualidade de Fornecedores apresenta uma leitura consolidada de reclamacoes, conformidade e historico por fornecedor. O usuario consegue acompanhar indicadores de qualidade, consultar ocorrencias e usar filtros por periodo, fornecedor, codigo, tipo, status e responsavel.",
    )
    img1 = ASSETS / "dashboard-visao-geral.png"
    doc.add_picture(str(img1), width=Inches(6.5))
    doc.paragraphs[-1].alignment = WD_ALIGN_PARAGRAPH.CENTER
    cap = doc.add_paragraph("Figura 1 - Visao geral do Dashboard Consolidado de Fornecedores.")
    cap.alignment = WD_ALIGN_PARAGRAPH.CENTER
    cap.runs[0].font.size = Pt(8.5)
    cap.runs[0].font.color.rgb = rgb(MUTED)

    add_heading(doc, "3.1 Indicadores e leitura operacional", 2)
    add_docx_table(doc, ["Indicador", "Como interpretar"], KPI_ROWS, [1.75, 4.75])
    add_callout(
        doc,
        "Regra aplicada",
        "Conforme e aceite por desvio contam como atendimento de qualidade. Atraso, fora da especificacao e nao entregue aparecem separados para auditoria. A nota de homologacao permanece fora deste calculo.",
    )

    doc.add_page_break()
    add_heading(doc, "4. Manual do usuario")
    add_body(doc, "Use os passos abaixo como roteiro de operacao diaria e tambem como apoio em treinamento de novos usuarios.")
    add_docx_table(doc, ["Passo", "Instrucao"], USER_STEPS, [1.7, 4.8])

    add_heading(doc, "4.1 Aba Historico")
    add_body(
        doc,
        "A aba Historico detalha os registros avaliados no Flow. Ela mostra codigo, fornecedor, situacao Flow, tipo, data, gravidade, status e responsavel. Use essa visao para acompanhar tratativas em aberto, comparar recorrencia por fornecedor e apoiar decisoes de qualidade.",
    )
    img2 = ASSETS / "dashboard-historico.png"
    doc.add_picture(str(img2), width=Inches(6.5))
    doc.paragraphs[-1].alignment = WD_ALIGN_PARAGRAPH.CENTER
    cap = doc.add_paragraph("Figura 2 - Aba Historico com registros de ocorrencias por fornecedor.")
    cap.alignment = WD_ALIGN_PARAGRAPH.CENTER
    cap.runs[0].font.size = Pt(8.5)
    cap.runs[0].font.color.rgb = rgb(MUTED)

    add_heading(doc, "5. Flow de inspecao de fornecedor")
    add_docx_table(doc, ["Elemento", "Detalhe"], FLOW_INSPECAO, [1.7, 4.8])
    for text in [
        "Registrar a ocorrencia com fornecedor, tipo, data, descricao e gravidade.",
        "Direcionar a tratativa para Compras/Suprimentos, Qualidade ou Fiscal conforme o tipo informado.",
        "Validar a tratativa pelo gestor antes do encerramento.",
        "Manter anexos e evidencias quando o caso exigir comprovacao.",
    ]:
        add_bullet(doc, text)

    add_heading(doc, "6. Flow de homologacao de fornecedor")
    add_docx_table(doc, ["Elemento", "Detalhe"], FLOW_HOMOLOGACAO, [1.7, 4.8])
    for text in [
        "Abrir registro de homologacao para o fornecedor avaliado.",
        "Anexar documentos e informar certificacao, referencia tecnica, visita, score e validade quando aplicavel.",
        "Enviar para validacao e, conforme os criterios, aprovar, reprovar ou solicitar ajustes.",
        "Quando aprovado, registrar a homologacao e atualizar o parceiro existente.",
    ]:
        add_bullet(doc, text)

    add_heading(doc, "7. Regras de uso e boas praticas")
    for text in [
        "Sempre conferir o periodo antes de interpretar score ou historico.",
        "Usar a busca para reduzir a tabela antes de investigar fornecedores com muitos registros.",
        "Nao encerrar ocorrencia sem tratativa registrada quando houver divergencia, atraso ou problema fiscal.",
        "Guardar evidencias nos campos de anexo/documentacao quando o caso impactar homologacao ou auditoria.",
        "Revisar status pendente, em tratativa e encerrada para evitar backlog operacional.",
    ]:
        add_bullet(doc, text)

    add_heading(doc, "8. Criterios de aceite")
    add_docx_table(doc, ["Criterio", "Evidencia esperada"], ACCEPTANCE, [1.65, 4.85])

    add_heading(doc, "9. Checklist de operacao assistida")
    checklist = [
        ("Acesso validado", "Usuario consegue abrir a tela no menu Sankhya."),
        ("Dados validados", "Indicadores conferidos com registros do Flow no periodo selecionado."),
        ("Filtros testados", "Busca, periodo e limpar filtros testados com sucesso."),
        ("Flow inspecao", "Registro criado e movimentado ate tratativa/encerramento."),
        ("Flow homologacao", "Registro criado e avaliado com status final."),
        ("Usuarios-chave treinados", "Compras/Suprimentos, Qualidade, Fiscal e Gestor receberam orientacao de uso."),
    ]
    add_docx_table(doc, ["Item", "Validacao"], checklist, [1.8, 4.7])

    doc.add_page_break()
    add_heading(doc, "10. Termo de entrega e aceite")
    add_body(
        doc,
        "Declaramos que o Dashboard Consolidado de Fornecedores e os flows associados foram entregues para validacao e uso operacional, contemplando o escopo descrito neste documento. A assinatura abaixo registra o recebimento e o aceite funcional da solucao.",
    )
    add_docx_table(
        doc,
        ["Parte", "Nome", "Assinatura", "Data"],
        [
            ("Cliente - Responsavel", "", "", ""),
            ("Cliente - Usuario chave", "", "", ""),
            ("Zaya IT - Responsavel tecnico", "", "", ""),
        ],
        [1.55, 1.7, 2.0, 1.25],
    )
    add_heading(doc, "11. Controle de versao")
    add_docx_table(
        doc,
        ["Versao", "Data", "Autor", "Alteracoes"],
        [("v1.0", "30/06/2026", "Zaya IT - Gestao de Negocios", "Criacao do manual do usuario, documento de entrega, criterios de aceite e termo de assinatura.")],
        [0.8, 1.1, 1.9, 2.7],
    )

    doc.save(DOCX_PATH)


def pdf_styles():
    styles = getSampleStyleSheet()
    return {
        "title": ParagraphStyle("title", parent=styles["Title"], fontName="Helvetica-Bold", fontSize=22, leading=27, textColor=hx(NAVY), spaceAfter=12),
        "subtitle": ParagraphStyle("subtitle", parent=styles["Normal"], fontName="Helvetica", fontSize=11, leading=15, textColor=hx(MUTED), spaceAfter=14),
        "h1": ParagraphStyle("h1", parent=styles["Heading1"], fontName="Helvetica-Bold", fontSize=15, leading=19, textColor=hx(NAVY), spaceBefore=12, spaceAfter=8),
        "h2": ParagraphStyle("h2", parent=styles["Heading2"], fontName="Helvetica-Bold", fontSize=12, leading=15, textColor=hx(NAVY), spaceBefore=10, spaceAfter=6),
        "body": ParagraphStyle("body", parent=styles["BodyText"], fontName="Helvetica", fontSize=9.2, leading=12.5, textColor=hx(INK), spaceAfter=6),
        "small": ParagraphStyle("small", parent=styles["BodyText"], fontName="Helvetica", fontSize=7.8, leading=10, textColor=hx(MUTED), alignment=TA_CENTER, spaceAfter=8),
        "cover_white": ParagraphStyle("cover_white", parent=styles["BodyText"], fontName="Helvetica-Bold", fontSize=18, leading=22, textColor=colors.white),
        "cover_green": ParagraphStyle("cover_green", parent=styles["BodyText"], fontName="Helvetica-Bold", fontSize=8.5, leading=11, textColor=hx(GREEN)),
        "callout_title": ParagraphStyle("callout_title", parent=styles["BodyText"], fontName="Helvetica-Bold", fontSize=8.2, leading=10, textColor=hx(GREEN_DARK), spaceAfter=2),
    }


def make_pdf_table(headers, rows, col_widths, header_fill=NAVY):
    data = [[Paragraph(f"<b>{h.upper()}</b>", PDF_STYLES["body"]) for h in headers]]
    for row in rows:
        data.append([Paragraph(str(cell), PDF_STYLES["body"]) for cell in row])
    table = Table(data, colWidths=[w * inch for w in col_widths], repeatRows=1, hAlign="LEFT")
    table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), hx(header_fill)),
                ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
                ("FONTNAME", (0, 0), (-1, 0), "Helvetica-Bold"),
                ("FONTSIZE", (0, 0), (-1, 0), 7.7),
                ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
                ("GRID", (0, 0), (-1, -1), 0.35, hx(BORDER)),
                ("LEFTPADDING", (0, 0), (-1, -1), 6),
                ("RIGHTPADDING", (0, 0), (-1, -1), 6),
                ("TOPPADDING", (0, 0), (-1, -1), 6),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
                ("BACKGROUND", (0, 1), (-1, -1), colors.white),
            ]
        )
    )
    return table


def callout_pdf(title: str, text: str, fill=GREEN_LIGHT):
    data = [[Paragraph(f"<b>{title.upper()}</b><br/>{text}", PDF_STYLES["body"])]]
    table = Table(data, colWidths=[6.45 * inch], hAlign="LEFT")
    table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, -1), hx(fill)),
                ("BOX", (0, 0), (-1, -1), 0.5, hx(BORDER)),
                ("LEFTPADDING", (0, 0), (-1, -1), 9),
                ("RIGHTPADDING", (0, 0), (-1, -1), 9),
                ("TOPPADDING", (0, 0), (-1, -1), 8),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 8),
            ]
        )
    )
    return table


def header_footer(canvas, doc):
    canvas.saveState()
    canvas.setFont("Helvetica", 7.5)
    canvas.setFillColor(hx(MUTED))
    canvas.drawString(0.8 * inch, 0.42 * inch, "Zaya IT - Gestao de Negocios | PortoMag | Confidencial")
    canvas.drawRightString(7.7 * inch, 0.42 * inch, f"Pagina {doc.page}")
    canvas.restoreState()


def build_pdf() -> None:
    story = []

    masthead = Table(
        [[Paragraph("ZAYA IT", PDF_STYLES["cover_white"]), Paragraph("GESTAO DE NEGOCIOS", PDF_STYLES["cover_green"])]],
        colWidths=[1.35 * inch, 5.1 * inch],
    )
    masthead.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, -1), hx(NAVY)),
                ("LEFTPADDING", (0, 0), (-1, -1), 12),
                ("RIGHTPADDING", (0, 0), (-1, -1), 12),
                ("TOPPADDING", (0, 0), (-1, -1), 12),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 12),
                ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
            ]
        )
    )
    story.extend(
        [
            masthead,
            Spacer(1, 0.28 * inch),
            Paragraph("MANUAL DO USUARIO E DOCUMENTO DE ENTREGA", PDF_STYLES["title"]),
            Paragraph("Dashboard Consolidado de Fornecedores - Qualidade, Reclamacoes e Homologacao", PDF_STYLES["subtitle"]),
            make_pdf_table(["Campo", "Informacao"], META_ROWS, [1.55, 4.9]),
            Spacer(1, 0.12 * inch),
            callout_pdf("Finalidade", "Este material consolida a entrega tecnica, o apoio de uso diario e os criterios de aceite para assinatura do dashboard e dos flows de fornecedores."),
            PageBreak(),
            Paragraph("1. Objetivo do documento", PDF_STYLES["h1"]),
            Paragraph("Formalizar a entrega do Dashboard Consolidado de Fornecedores e orientar o uso operacional da tela e dos flows associados. O documento tambem funciona como manual de apoio para consulta, treinamento e aceite final da solucao.", PDF_STYLES["body"]),
            Paragraph("A documentacao foi montada com base no design system do PDF de referencia, nos prints do dashboard e nos pacotes de Flow exportados do Sankhya.", PDF_STYLES["body"]),
            Paragraph("2. Escopo entregue", PDF_STYLES["h1"]),
            make_pdf_table(["Item", "Descricao"], DELIVERABLES, [1.7, 4.75]),
            Spacer(1, 0.1 * inch),
            callout_pdf("Nota de seguranca", "Detalhes sensiveis de conexao, banco de dados e ambiente foram omitidos da documentacao de entrega. O foco deste documento e funcional, operacional e de aceite.", BLUE_GRAY),
            Paragraph("3. Visao geral do dashboard", PDF_STYLES["h1"]),
            Paragraph("A tela [ZAYA.IT] - Qualidade de Fornecedores apresenta uma leitura consolidada de reclamacoes, conformidade e historico por fornecedor. O usuario consegue acompanhar indicadores de qualidade, consultar ocorrencias e usar filtros por periodo, fornecedor, codigo, tipo, status e responsavel.", PDF_STYLES["body"]),
            Image(str(ASSETS / "dashboard-visao-geral.png"), width=6.45 * inch, height=3.02 * inch),
            Paragraph("Figura 1 - Visao geral do Dashboard Consolidado de Fornecedores.", PDF_STYLES["small"]),
            Paragraph("3.1 Indicadores e leitura operacional", PDF_STYLES["h2"]),
            make_pdf_table(["Indicador", "Como interpretar"], KPI_ROWS, [1.65, 4.8]),
            Spacer(1, 0.1 * inch),
            callout_pdf("Regra aplicada", "Conforme e aceite por desvio contam como atendimento de qualidade. Atraso, fora da especificacao e nao entregue aparecem separados para auditoria. A nota de homologacao permanece fora deste calculo."),
            PageBreak(),
            Paragraph("4. Manual do usuario", PDF_STYLES["h1"]),
            Paragraph("Use os passos abaixo como roteiro de operacao diaria e tambem como apoio em treinamento de novos usuarios.", PDF_STYLES["body"]),
            make_pdf_table(["Passo", "Instrucao"], USER_STEPS, [1.55, 4.9]),
            Paragraph("4.1 Aba Historico", PDF_STYLES["h2"]),
            Paragraph("A aba Historico detalha os registros avaliados no Flow. Ela mostra codigo, fornecedor, situacao Flow, tipo, data, gravidade, status e responsavel. Use essa visao para acompanhar tratativas em aberto, comparar recorrencia por fornecedor e apoiar decisoes de qualidade.", PDF_STYLES["body"]),
            Image(str(ASSETS / "dashboard-historico.png"), width=6.45 * inch, height=2.86 * inch),
            Paragraph("Figura 2 - Aba Historico com registros de ocorrencias por fornecedor.", PDF_STYLES["small"]),
            PageBreak(),
            Paragraph("5. Flow de inspecao de fornecedor", PDF_STYLES["h1"]),
            make_pdf_table(["Elemento", "Detalhe"], FLOW_INSPECAO, [1.55, 4.9]),
            Paragraph("Registro da ocorrencia, direcionamento para Compras/Suprimentos, Qualidade ou Fiscal, validacao pelo gestor e encerramento com evidencias quando aplicavel.", PDF_STYLES["body"]),
            Paragraph("6. Flow de homologacao de fornecedor", PDF_STYLES["h1"]),
            make_pdf_table(["Elemento", "Detalhe"], FLOW_HOMOLOGACAO, [1.55, 4.9]),
            Paragraph("Abertura de homologacao, anexo de documentos, informacao de certificacao/referencia/visita, validacao, aprovacao, reprovacao ou solicitacao de ajustes.", PDF_STYLES["body"]),
            Paragraph("7. Regras de uso e boas praticas", PDF_STYLES["h1"]),
            make_pdf_table(
                ["Boa pratica", "Aplicacao"],
                [
                    ("Periodo", "Conferir o periodo antes de interpretar score ou historico."),
                    ("Busca", "Reduzir a tabela antes de investigar fornecedores com muitos registros."),
                    ("Tratativa", "Nao encerrar ocorrencia sem tratativa registrada quando houver divergencia, atraso ou problema fiscal."),
                    ("Evidencia", "Guardar anexos/documentos quando o caso impactar homologacao ou auditoria."),
                    ("Backlog", "Revisar status pendente, em tratativa e encerrada para evitar acumulo operacional."),
                ],
                [1.4, 5.05],
            ),
            Paragraph("8. Criterios de aceite", PDF_STYLES["h1"]),
            make_pdf_table(["Criterio", "Evidencia esperada"], ACCEPTANCE, [1.55, 4.9]),
            PageBreak(),
            Paragraph("9. Checklist de operacao assistida", PDF_STYLES["h1"]),
            make_pdf_table(
                ["Item", "Validacao"],
                [
                    ("Acesso validado", "Usuario consegue abrir a tela no menu Sankhya."),
                    ("Dados validados", "Indicadores conferidos com registros do Flow no periodo selecionado."),
                    ("Filtros testados", "Busca, periodo e limpar filtros testados com sucesso."),
                    ("Flow inspecao", "Registro criado e movimentado ate tratativa/encerramento."),
                    ("Flow homologacao", "Registro criado e avaliado com status final."),
                    ("Usuarios-chave treinados", "Compras/Suprimentos, Qualidade, Fiscal e Gestor receberam orientacao de uso."),
                ],
                [1.65, 4.8],
            ),
            Paragraph("10. Termo de entrega e aceite", PDF_STYLES["h1"]),
            Paragraph("Declaramos que o Dashboard Consolidado de Fornecedores e os flows associados foram entregues para validacao e uso operacional, contemplando o escopo descrito neste documento. A assinatura abaixo registra o recebimento e o aceite funcional da solucao.", PDF_STYLES["body"]),
            make_pdf_table(
                ["Parte", "Nome", "Assinatura", "Data"],
                [
                    ("Cliente - Responsavel", "&nbsp;", "&nbsp;", "&nbsp;"),
                    ("Cliente - Usuario chave", "&nbsp;", "&nbsp;", "&nbsp;"),
                    ("Zaya IT - Responsavel tecnico", "&nbsp;", "&nbsp;", "&nbsp;"),
                ],
                [1.55, 1.55, 2.1, 1.25],
            ),
            Paragraph("11. Controle de versao", PDF_STYLES["h1"]),
            make_pdf_table(
                ["Versao", "Data", "Autor", "Alteracoes"],
                [("v1.0", "30/06/2026", "Zaya IT - Gestao de Negocios", "Criacao do manual do usuario, documento de entrega, criterios de aceite e termo de assinatura.")],
                [0.75, 0.95, 1.85, 2.9],
            ),
        ]
    )

    pdf = SimpleDocTemplate(
        str(PDF_PATH),
        pagesize=letter,
        rightMargin=0.75 * inch,
        leftMargin=0.75 * inch,
        topMargin=0.72 * inch,
        bottomMargin=0.68 * inch,
        title="Manual de Entrega - Dashboard Consolidado de Fornecedores",
        author="Zaya IT - Gestao de Negocios",
    )
    pdf.build(story, onFirstPage=header_footer, onLaterPages=header_footer)


PDF_STYLES = pdf_styles()


if __name__ == "__main__":
    build_docx()
    build_pdf()
    print(DOCX_PATH)
    print(PDF_PATH)
