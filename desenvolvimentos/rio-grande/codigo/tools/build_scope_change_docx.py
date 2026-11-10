from pathlib import Path

from docx import Document
from docx.enum.section import WD_SECTION
from docx.enum.table import WD_CELL_VERTICAL_ALIGNMENT, WD_TABLE_ALIGNMENT
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Cm, Inches, Pt, RGBColor


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "output" / "docx" / "Rio_Grande_Alteracao_Escopo_Relatorios_Comissao.docx"
LOGO = ROOT / "assets" / "zaya_logo_cropped.png"

GREEN = "20B968"
DARK_GREEN = "118B4E"
LIGHT_GREEN = "DDF4E7"
PALE_GREEN = "F1FAF5"
DARK = "263442"
GRAY = "66727F"
LIGHT_GRAY = "F3F5F6"
MID_GRAY = "D8DEE3"
WHITE = "FFFFFF"
RED = "C0392B"

FONT = "Arial"
TABLE_WIDTH_DXA = 9360
TABLE_INDENT_DXA = 120


def set_cell_shading(cell, fill):
    tc_pr = cell._tc.get_or_add_tcPr()
    shd = tc_pr.find(qn("w:shd"))
    if shd is None:
        shd = OxmlElement("w:shd")
        tc_pr.append(shd)
    shd.set(qn("w:fill"), fill)


def set_cell_margins(cell, top=90, start=120, bottom=90, end=120):
    tc = cell._tc
    tc_pr = tc.get_or_add_tcPr()
    tc_mar = tc_pr.first_child_found_in("w:tcMar")
    if tc_mar is None:
        tc_mar = OxmlElement("w:tcMar")
        tc_pr.append(tc_mar)
    for tag, value in (("top", top), ("start", start), ("bottom", bottom), ("end", end)):
        node = tc_mar.find(qn(f"w:{tag}"))
        if node is None:
            node = OxmlElement(f"w:{tag}")
            tc_mar.append(node)
        node.set(qn("w:w"), str(value))
        node.set(qn("w:type"), "dxa")


def set_cell_border(cell, color=MID_GRAY, size="6"):
    tc_pr = cell._tc.get_or_add_tcPr()
    borders = tc_pr.first_child_found_in("w:tcBorders")
    if borders is None:
        borders = OxmlElement("w:tcBorders")
        tc_pr.append(borders)
    for edge in ("top", "left", "bottom", "right", "insideH", "insideV"):
        element = borders.find(qn(f"w:{edge}"))
        if element is None:
            element = OxmlElement(f"w:{edge}")
            borders.append(element)
        element.set(qn("w:val"), "single")
        element.set(qn("w:sz"), size)
        element.set(qn("w:color"), color)


def set_table_geometry(table, widths_dxa):
    table.autofit = False
    table.alignment = WD_TABLE_ALIGNMENT.LEFT
    tbl_pr = table._tbl.tblPr
    tbl_w = tbl_pr.first_child_found_in("w:tblW")
    if tbl_w is None:
        tbl_w = OxmlElement("w:tblW")
        tbl_pr.append(tbl_w)
    tbl_w.set(qn("w:w"), str(sum(widths_dxa)))
    tbl_w.set(qn("w:type"), "dxa")
    tbl_ind = tbl_pr.first_child_found_in("w:tblInd")
    if tbl_ind is None:
        tbl_ind = OxmlElement("w:tblInd")
        tbl_pr.append(tbl_ind)
    tbl_ind.set(qn("w:w"), str(TABLE_INDENT_DXA))
    tbl_ind.set(qn("w:type"), "dxa")

    grid = table._tbl.tblGrid
    for child in list(grid):
        grid.remove(child)
    for width in widths_dxa:
        col = OxmlElement("w:gridCol")
        col.set(qn("w:w"), str(width))
        grid.append(col)

    for row in table.rows:
        for idx, cell in enumerate(row.cells):
            width = widths_dxa[min(idx, len(widths_dxa) - 1)]
            tc_pr = cell._tc.get_or_add_tcPr()
            tc_w = tc_pr.first_child_found_in("w:tcW")
            if tc_w is None:
                tc_w = OxmlElement("w:tcW")
                tc_pr.append(tc_w)
            tc_w.set(qn("w:w"), str(width))
            tc_w.set(qn("w:type"), "dxa")
            cell.width = Inches(width / 1440)
            set_cell_margins(cell)
            set_cell_border(cell)
            cell.vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER


def set_run(run, size=10.5, color=DARK, bold=False, italic=False):
    run.font.name = FONT
    run._element.get_or_add_rPr().rFonts.set(qn("w:ascii"), FONT)
    run._element.get_or_add_rPr().rFonts.set(qn("w:hAnsi"), FONT)
    run.font.size = Pt(size)
    run.font.color.rgb = RGBColor.from_string(color)
    run.bold = bold
    run.italic = italic


def add_logo(paragraph, width):
    shape = paragraph.add_run().add_picture(str(LOGO), width=width)
    doc_pr = shape._inline.docPr
    doc_pr.set("name", "Logomarca Zaya.IT")
    doc_pr.set("descr", "Logomarca institucional da Zaya.IT")
    doc_pr.set("title", "Zaya.IT")
    return shape


def set_paragraph(p, before=0, after=6, line=1.12, alignment=None, keep_next=False):
    p.paragraph_format.space_before = Pt(before)
    p.paragraph_format.space_after = Pt(after)
    p.paragraph_format.line_spacing = line
    p.paragraph_format.keep_with_next = keep_next
    if alignment is not None:
        p.alignment = alignment


def add_body(doc, text, bold_lead=None, after=6, italic=False):
    p = doc.add_paragraph()
    set_paragraph(p, after=after)
    if bold_lead and text.startswith(bold_lead):
        r1 = p.add_run(bold_lead)
        set_run(r1, bold=True)
        r2 = p.add_run(text[len(bold_lead):])
        set_run(r2, italic=italic)
    else:
        r = p.add_run(text)
        set_run(r, italic=italic)
    return p


def add_heading(doc, text, level=1):
    p = doc.add_paragraph(style=f"Heading {level}")
    p.add_run(text)
    return p


def add_callout(doc, label, text, fill=PALE_GREEN):
    table = doc.add_table(rows=1, cols=1)
    set_table_geometry(table, [TABLE_WIDTH_DXA])
    cell = table.cell(0, 0)
    set_cell_shading(cell, fill)
    p = cell.paragraphs[0]
    set_paragraph(p, after=0)
    r = p.add_run(f"{label}: ")
    set_run(r, bold=True, color=DARK_GREEN)
    r = p.add_run(text)
    set_run(r)
    doc.add_paragraph().paragraph_format.space_after = Pt(1)


def add_table(doc, headers, rows, widths, font_size=9.2):
    table = doc.add_table(rows=1, cols=len(headers))
    table.style = "Table Grid"
    tr_pr = table.rows[0]._tr.get_or_add_trPr()
    tbl_header = OxmlElement("w:tblHeader")
    tbl_header.set(qn("w:val"), "true")
    tr_pr.append(tbl_header)
    hdr = table.rows[0].cells
    for i, header in enumerate(headers):
        set_cell_shading(hdr[i], GREEN)
        p = hdr[i].paragraphs[0]
        set_paragraph(p, after=0)
        r = p.add_run(header)
        set_run(r, size=font_size, color=WHITE, bold=True)
    for ridx, row in enumerate(rows):
        cells = table.add_row().cells
        for i, value in enumerate(row):
            if ridx % 2 == 1:
                set_cell_shading(cells[i], LIGHT_GRAY)
            p = cells[i].paragraphs[0]
            set_paragraph(p, after=0, line=1.05)
            r = p.add_run(str(value))
            set_run(r, size=font_size)
    set_table_geometry(table, widths)
    return table


def add_page_number(paragraph):
    paragraph.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    r = paragraph.add_run("Página ")
    set_run(r, size=8.5, color=GRAY)
    fld_char1 = OxmlElement("w:fldChar")
    fld_char1.set(qn("w:fldCharType"), "begin")
    instr_text = OxmlElement("w:instrText")
    instr_text.set(qn("xml:space"), "preserve")
    instr_text.text = "PAGE"
    fld_char2 = OxmlElement("w:fldChar")
    fld_char2.set(qn("w:fldCharType"), "end")
    r._r.extend([fld_char1, instr_text, fld_char2])


def configure_styles(doc):
    normal = doc.styles["Normal"]
    normal.font.name = FONT
    normal._element.rPr.rFonts.set(qn("w:ascii"), FONT)
    normal._element.rPr.rFonts.set(qn("w:hAnsi"), FONT)
    normal.font.size = Pt(10.5)
    normal.font.color.rgb = RGBColor.from_string(DARK)
    normal.paragraph_format.space_after = Pt(6)
    normal.paragraph_format.line_spacing = 1.12

    for name, size, before, after, color in (
        ("Heading 1", 17, 14, 8, DARK),
        ("Heading 2", 13, 10, 5, DARK_GREEN),
        ("Heading 3", 11, 7, 3, DARK),
    ):
        style = doc.styles[name]
        style.font.name = FONT
        style._element.rPr.rFonts.set(qn("w:ascii"), FONT)
        style._element.rPr.rFonts.set(qn("w:hAnsi"), FONT)
        style.font.size = Pt(size)
        style.font.bold = True
        style.font.color.rgb = RGBColor.from_string(color)
        style.paragraph_format.space_before = Pt(before)
        style.paragraph_format.space_after = Pt(after)
        style.paragraph_format.keep_with_next = True


def configure_page(doc):
    section = doc.sections[0]
    section.page_width = Cm(21.0)
    section.page_height = Cm(29.7)
    section.top_margin = Cm(2.1)
    section.bottom_margin = Cm(1.8)
    section.left_margin = Cm(2.2)
    section.right_margin = Cm(2.2)
    section.header_distance = Cm(0.8)
    section.footer_distance = Cm(0.7)

    header = section.header
    p = header.paragraphs[0]
    set_paragraph(p, after=0)
    p.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    add_logo(p, width=Inches(1.72))

    footer = section.footer
    table = footer.add_table(rows=1, cols=2, width=Inches(6.5))
    set_table_geometry(table, [7000, 2360])
    for cell in table.rows[0].cells:
        set_cell_border(cell, color=WHITE, size="0")
    p1 = table.cell(0, 0).paragraphs[0]
    set_paragraph(p1, after=0)
    r = p1.add_run("Alteração de Escopo - Relatórios de Comissão")
    set_run(r, size=8.5, color=GRAY)
    add_page_number(table.cell(0, 1).paragraphs[0])


def add_cover(doc):
    for _ in range(5):
        p = doc.add_paragraph()
        set_paragraph(p, after=8)

    p = doc.add_paragraph()
    set_paragraph(p, after=20, alignment=WD_ALIGN_PARAGRAPH.CENTER)
    add_logo(p, width=Inches(3.85))

    p = doc.add_paragraph()
    set_paragraph(p, after=12, alignment=WD_ALIGN_PARAGRAPH.CENTER)
    r = p.add_run("PROPOSTA DE ALTERAÇÃO DE ESCOPO")
    set_run(r, size=27, color=DARK, bold=True)

    p = doc.add_paragraph()
    set_paragraph(p, after=4, alignment=WD_ALIGN_PARAGRAPH.CENTER)
    r = p.add_run("Relatórios de Comissão")
    set_run(r, size=18, color=DARK_GREEN, bold=True)

    p = doc.add_paragraph()
    set_paragraph(p, after=48, alignment=WD_ALIGN_PARAGRAPH.CENTER)
    r = p.add_run("Rio Grande")
    set_run(r, size=15, color=GRAY)

    add_callout(
        doc,
        "Finalidade",
        "Formalizar as melhorias complementares solicitadas para o processo de Conferência e Relatórios de Comissão e justificar a necessidade de 4 horas adicionais para sua implementação e validação.",
        fill=LIGHT_GREEN,
    )

    for _ in range(5):
        doc.add_paragraph()

    p = doc.add_paragraph()
    set_paragraph(p, after=3, alignment=WD_ALIGN_PARAGRAPH.CENTER)
    r = p.add_run("Elaboração")
    set_run(r, size=9, color=GRAY, bold=True)
    p = doc.add_paragraph()
    set_paragraph(p, after=3, alignment=WD_ALIGN_PARAGRAPH.CENTER)
    r = p.add_run("Zaya.IT")
    set_run(r, size=11, color=DARK, bold=True)
    p = doc.add_paragraph()
    set_paragraph(p, after=0, alignment=WD_ALIGN_PARAGRAPH.CENTER)
    r = p.add_run("02/09/2026  |  Versão 1.0")
    set_run(r, size=9.5, color=GRAY)
    doc.add_page_break()


def build_document():
    doc = Document()
    configure_styles(doc)
    configure_page(doc)
    core = doc.core_properties
    core.title = "Proposta de Alteração de Escopo - Relatórios de Comissão - Rio Grande"
    core.subject = "Justificativa de 4 horas adicionais e PDD complementar"
    core.author = "Zaya.IT"
    core.keywords = "Rio Grande; Comissão; Sankhya; Dashboard; JRXML; Alteração de Escopo"

    add_cover(doc)

    add_heading(doc, "Resumo do documento", 1)
    add_table(
        doc,
        ["Identificação", "Informação"],
        [
            ["Cliente", "Rio Grande"],
            ["Projeto", "Conferência e Relatórios de Comissão"],
            ["Referência", "Escopo original de 16 horas e OS 8206"],
            ["Natureza", "Proposta de alteração de escopo e PDD complementar"],
            ["Esforço adicional", "4 horas adicionais estimadas"],
            ["Situação", "Proposta técnica sujeita à aprovação e posterior homologação"],
        ],
        [2700, 6660],
    )

    add_heading(doc, "Objetivo", 2)
    add_body(
        doc,
        "Este documento apresenta a evolução proposta para o escopo dos Relatórios de Comissão, descreve as melhorias que deverão ser realizadas no Dashboard e na integração com o relatório formatado e justifica a necessidade de 4 horas adicionais.",
    )
    add_body(
        doc,
        "O conteúdo complementará o escopo original e funcionará também como PDD resumido, permitindo identificar a necessidade, a solução prevista, os componentes afetados, os critérios de validação e o procedimento de retorno.",
    )

    add_heading(doc, "Controle de versão", 2)
    add_table(
        doc,
        ["Versão", "Data", "Responsável", "Descrição"],
        [["1.0", "02/09/2026", "Zaya.IT", "Proposta de alteração de escopo e justificativa das 4 horas adicionais."]],
        [1000, 1500, 2300, 4560],
        font_size=8.8,
    )

    add_heading(doc, "Conclusão executiva", 2)
    add_callout(
        doc,
        "Conclusão",
        "As 4 horas adicionais serão necessárias para atender melhorias solicitadas após a definição inicial. O esforço não corresponderá apenas a ajustes visuais: incluirá nova totalização, reorganização estrutural do Dashboard, adequação do JRXML e testes de compatibilidade com o agendamento existente.",
    )
    doc.add_page_break()

    add_heading(doc, "1. Contexto e justificativa da mudança", 1)
    add_body(
        doc,
        "O escopo original estimou 16 horas para o projeto completo, distribuídas entre levantamento, parametrização/desenvolvimento, homologação, operação assistida e documentação. A OS 8206 registrou 15 horas, embora parte desse período também tenha abrangido análise e validação técnica.",
    )
    add_body(
        doc,
        "Como evolução do processo, foram solicitados refinamentos de apresentação e novas funcionalidades para tornar a conferência mais clara e permitir que o relatório utilize diretamente o vendedor e o período escolhidos no Dashboard.",
    )

    add_heading(doc, "1.1 Comparação entre o escopo original e a evolução", 2)
    add_table(
        doc,
        ["Tema", "Previsão original", "Evolução solicitada"],
        [
            ["Totalização", "Soma total devida por parceiro.", "Resumo separado entre comissões revisadas e pendentes, com quantidade e valor."],
            ["Dashboard", "Tela de conferência com informações do fechamento.", "Duas grades: resumo de revisão na parte superior e detalhamento preservado na parte inferior."],
            ["Apresentação", "Relatório simplificado com os campos obrigatórios.", "Padronização de nomenclaturas, organização visual, faróis e refinamento dos campos."],
            ["Parâmetros", "Vendedor e período informados para execução.", "Reaproveitamento automático do contexto do Dashboard, sem nova solicitação manual no JRXML."],
            ["Compatibilidade", "Envio mensal das comissões revisadas.", "Manutenção simultânea do acionamento pela tela e do agendamento existente."],
        ],
        [1800, 3300, 4260],
        font_size=8.5,
    )

    add_heading(doc, "1.2 Por que houve necessidade das 4 horas", 2)
    add_body(doc, "Refinamento posterior ao escopo: o totalizador por situação e a divisão em duas grades não estavam detalhados no documento original.", bold_lead="Refinamento posterior ao escopo:")
    add_body(doc, "Integração entre componentes: será necessário adequar o JRXML ao formato utilizado pelo Sankhya para encaminhar o contexto da linha selecionada ao Visualizador de Relatórios.", bold_lead="Integração entre componentes:")
    add_body(doc, "Proteção contra regressão: a solução precisou preservar a consulta inferior, a ação de revisão, os aliases consumidos e o agendamento de relatórios.", bold_lead="Proteção contra regressão:")
    add_body(doc, "Validação técnica: o retorno CORE_E02500 exigiu inspeção da requisição, identificação da ausência do período e correção dos parâmetros encaminhados ao relatório.", bold_lead="Validação técnica:")
    doc.add_page_break()

    add_heading(doc, "2. Melhorias propostas", 1)

    add_heading(doc, "2.1 Padronização visual e nomenclaturas", 2)
    add_body(
        doc,
        "Serão revisados os nomes apresentados nas grades, a ordem dos campos e a organização visual do Dashboard. O objetivo será aproximar a experiência da tela de Fechamento de Comissões e facilitar a leitura operacional.",
    )

    add_heading(doc, "2.2 Totalizador de revisão", 2)
    add_body(
        doc,
        "Será incluída uma grade superior com as situações Revisadas e Pendentes. Para cada situação deverão ser apresentados o farol visual, a quantidade de comissões e o respectivo valor. A apuração utilizará a identificação da comissão na TGFCOM para reduzir o risco de duplicidade decorrente dos desdobramentos da view de fechamento.",
    )

    add_heading(doc, "2.3 Estrutura em duas grades", 2)
    add_body(
        doc,
        "O Dashboard deverá possuir uma área resumida e uma área detalhada. A grade inferior deverá manter o mesmo identificador, entidade, consulta, aliases e metadados necessários para a ação Marcar Comissão como Revisada.",
    )

    add_heading(doc, "2.4 Integração do contexto com o JRXML", 2)
    add_body(
        doc,
        "O Dashboard deverá disponibilizar o início e o fim do período como campos técnicos ocultos. No acionamento do relatório, o Sankhya encaminhará os campos da linha no conjunto parametrosPK, utilizando o prefixo PK_.",
    )
    add_body(
        doc,
        "O JRXML deverá ser referenciado e ajustado para receber PK_CODVEND, PK_PERIODO_INI e PK_PERIODO_FIN. Os parâmetros originais deverão ser preservados, mantendo também o funcionamento do agendamento que envia CODVEND, PERIODO.INI e PERIODO.FIN.",
    )

    add_heading(doc, "2.5 Regra funcional preservada", 2)
    add_callout(
        doc,
        "Regra",
        "O detalhamento do relatório continuará considerando somente comissões marcadas como revisadas. As melhorias não deverão criar movimentação financeira nem executar alteração direta de dados durante a geração do relatório.",
    )

    add_heading(doc, "2.6 Limite desta entrega", 2)
    add_body(
        doc,
        "O totalizador deverá ser incorporado ao Dashboard e refletido no relatório conforme a definição funcional. A apresentação final no JRXML deverá ser validada no ambiente Sankhya junto ao usuário-chave.",
        italic=True,
    )
    doc.add_page_break()

    add_heading(doc, "3. PDD complementar", 1)

    add_heading(doc, "3.1 Componentes afetados", 2)
    add_table(
        doc,
        ["Componente", "Responsabilidade", "Alteração"],
        [
            ["Dashboard_Conferencia_Comissoes.xml", "Estrutura visual do gadget.", "Deverá receber a grade totalizadora, a divisão do layout e os campos ocultos de período."],
            ["Dashboard_Conferencia_Comissoes.sql", "Consulta de referência do Dashboard.", "Deverá expor tecnicamente PERIODO_INI e PERIODO_FIN."],
            ["Comissao_Conferencia.jrxml", "Relatório formatado de conferência.", "Deverá ler o contexto PK_* sem perder a compatibilidade com o agendamento."],
            ["Marcar_Comissao_Revisada.js", "Ação de revisão em lote.", "Deverá permanecer sem alteração funcional nesta etapa."],
        ],
        [2700, 2600, 4060],
        font_size=8.3,
    )

    add_heading(doc, "3.2 Fluxo de parâmetros", 2)
    add_table(
        doc,
        ["Etapa", "Entrada", "Saída"],
        [
            ["1. Filtro do Dashboard", "Vendedor e período", "CODVEND, PERIODO.INI e PERIODO.FIN"],
            ["2. Grade de detalhe", "Parâmetros do filtro", "Campos técnicos CODVEND, PERIODO_INI e PERIODO_FIN"],
            ["3. Visualizador", "Linha selecionada", "PK_CODVEND, PK_PERIODO_INI e PK_PERIODO_FIN"],
            ["4. JRXML", "Parâmetros PK_*", "Valores efetivos utilizados pela consulta"],
            ["5. Agendamento", "Parâmetros originais", "Execução mensal preservada"],
        ],
        [2300, 3300, 3760],
        font_size=8.4,
    )

    add_heading(doc, "3.3 Controles técnicos", 2)
    add_body(doc, "Consultas parametrizadas: os valores continuam sendo fornecidos como parâmetros, sem concatenação de entrada do usuário.", bold_lead="Consultas parametrizadas:")
    add_body(doc, "Integridade: a solução não deverá executar DML nas tabelas padrão para produzir o totalizador ou gerar o relatório.", bold_lead="Integridade:")
    add_body(doc, "Compatibilidade: deverão ser preservados o agendamento, os parâmetros originais e o vínculo da ação com a grade detalhada.", bold_lead="Compatibilidade:")
    add_body(doc, "Estados vazios: o resumo deverá apresentar Revisadas e Pendentes mesmo quando uma das situações possuir quantidade zero.", bold_lead="Estados vazios:")

    add_heading(doc, "3.4 Procedimento de rollback", 2)
    add_body(
        doc,
        "Caso a publicação apresente incompatibilidade, o retorno consistirá em restaurar as versões anteriores do XML do Dashboard e do JRXML. Como a alteração prevista não criará objetos de banco nem executará DML, não haverá rollback de dados associado.",
    )
    doc.add_page_break()

    add_heading(doc, "4. Esforço adicional e validação", 1)

    add_heading(doc, "4.1 Previsão de distribuição das 4 horas", 2)
    add_table(
        doc,
        ["Etapa", "Duração", "Atividade prevista", "Resultado esperado"],
        [
            ["Bloco 1", "2h", "Nomenclaturas, organização dos campos e ajustes estéticos do Dashboard.", "Interface padronizada e alinhada à operação."],
            ["Bloco 2", "2h", "Totalizador, segunda grade, contexto de parâmetros, referência do JRXML e testes de geração.", "Integração disponível para publicação e validação."],
            ["Total previsto", "4h", "Implementação da alteração de escopo.", "Entregáveis técnicos disponíveis para homologação."],
        ],
        [1700, 900, 3500, 3260],
        font_size=8.3,
    )

    add_heading(doc, "4.2 Critérios de aceite", 2)
    add_table(
        doc,
        ["Critério", "Situação"],
        [
            ["Dashboard deverá apresentar resumo de Revisadas e Pendentes.", "Previsto"],
            ["Grade detalhada e ação de revisão deverão permanecer preservadas.", "Previsto"],
            ["Período e vendedor deverão ser disponibilizados ao relatório pelo contexto.", "Previsto"],
            ["Agendamento deverá continuar aceitando os parâmetros originais.", "Previsto"],
            ["Somente comissões revisadas deverão compor o detalhamento do relatório.", "Previsto"],
            ["Homologação funcional pelo usuário-chave.", "Pendente"],
        ],
        [6200, 3160],
        font_size=8.7,
    )

    add_heading(doc, "4.3 Validação e pendências", 2)
    add_body(
        doc,
        "A implementação deverá ser submetida à validação técnica, contemplando XML bem-formado, presença das duas grades, parâmetros de contexto, manutenção dos parâmetros do agendamento e preservação do filtro de comissões revisadas.",
    )
    add_body(
        doc,
        "Após o desenvolvimento, deverão ocorrer a publicação no ambiente Sankhya, a compilação do JRXML pelo mecanismo do cliente, a validação visual do totalizador no relatório e a homologação funcional pelo usuário-chave.",
    )

    add_callout(
        doc,
        "Encerramento",
        "As 4 horas adicionais representarão uma evolução funcional e de integração em relação ao escopo inicial. Recomenda-se aprovar e registrar este esforço como complemento formal do projeto antes da execução e da homologação.",
    )

    OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    doc.save(OUTPUT)
    print(OUTPUT)


if __name__ == "__main__":
    build_document()
