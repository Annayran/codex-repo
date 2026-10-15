from docx import Document
from docx.enum.section import WD_SECTION
from docx.enum.table import WD_CELL_VERTICAL_ALIGNMENT, WD_TABLE_ALIGNMENT
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Inches, Pt, RGBColor


OUT = r"C:\Users\annayran.ferreira\Documents\Codex\2026-05-07\files-mentioned-by-the-user-polyvoxtelecontrol\documentacao\IntPDD_Telecontrol_Composicao_Produtos_Pecas_Produtos.docx"

GREEN = RGBColor(67, 182, 73)
DARK_GREEN = RGBColor(36, 130, 63)
NAVY = RGBColor(11, 37, 69)
BLUE = RGBColor(46, 116, 181)
MUTED = RGBColor(96, 108, 122)
LIGHT_GREEN = "E8F5EA"
LIGHT_BLUE = "E8EEF5"
LIGHT_GRAY = "F2F4F7"
BORDER = "C9D2DC"
WHITE = RGBColor(255, 255, 255)


def set_cell_shading(cell, fill):
    tc_pr = cell._tc.get_or_add_tcPr()
    shd = tc_pr.find(qn("w:shd"))
    if shd is None:
        shd = OxmlElement("w:shd")
        tc_pr.append(shd)
    shd.set(qn("w:fill"), fill)


def set_cell_margins(cell, top=90, start=120, bottom=90, end=120):
    tc_pr = cell._tc.get_or_add_tcPr()
    tc_mar = tc_pr.first_child_found_in("w:tcMar")
    if tc_mar is None:
        tc_mar = OxmlElement("w:tcMar")
        tc_pr.append(tc_mar)
    for m, v in (("top", top), ("start", start), ("bottom", bottom), ("end", end)):
        node = tc_mar.find(qn(f"w:{m}"))
        if node is None:
            node = OxmlElement(f"w:{m}")
            tc_mar.append(node)
        node.set(qn("w:w"), str(v))
        node.set(qn("w:type"), "dxa")


def set_table_borders(table, color=BORDER, size="6"):
    tbl_pr = table._tbl.tblPr
    borders = tbl_pr.first_child_found_in("w:tblBorders")
    if borders is None:
        borders = OxmlElement("w:tblBorders")
        tbl_pr.append(borders)
    for edge in ("top", "left", "bottom", "right", "insideH", "insideV"):
        tag = f"w:{edge}"
        element = borders.find(qn(tag))
        if element is None:
            element = OxmlElement(tag)
            borders.append(element)
        element.set(qn("w:val"), "single")
        element.set(qn("w:sz"), size)
        element.set(qn("w:space"), "0")
        element.set(qn("w:color"), color)


def set_table_width(table, widths):
    table.allow_autofit = False
    table.autofit = False
    tbl_pr = table._tbl.tblPr
    tbl_w = tbl_pr.first_child_found_in("w:tblW")
    if tbl_w is None:
        tbl_w = OxmlElement("w:tblW")
        tbl_pr.append(tbl_w)
    total = sum(widths)
    tbl_w.set(qn("w:w"), str(total))
    tbl_w.set(qn("w:type"), "dxa")
    tbl_ind = tbl_pr.first_child_found_in("w:tblInd")
    if tbl_ind is None:
        tbl_ind = OxmlElement("w:tblInd")
        tbl_pr.append(tbl_ind)
    tbl_ind.set(qn("w:w"), "120")
    tbl_ind.set(qn("w:type"), "dxa")
    grid = table._tbl.tblGrid
    if grid is None:
        grid = OxmlElement("w:tblGrid")
        table._tbl.insert(0, grid)
    for child in list(grid):
        grid.remove(child)
    for width in widths:
        col = OxmlElement("w:gridCol")
        col.set(qn("w:w"), str(width))
        grid.append(col)
    for row in table.rows:
        for idx, width in enumerate(widths):
            if idx >= len(row.cells):
                continue
            cell = row.cells[idx]
            cell.width = Inches(width / 1440)
            tc_pr = cell._tc.get_or_add_tcPr()
            tc_w = tc_pr.first_child_found_in("w:tcW")
            if tc_w is None:
                tc_w = OxmlElement("w:tcW")
                tc_pr.append(tc_w)
            tc_w.set(qn("w:w"), str(width))
            tc_w.set(qn("w:type"), "dxa")


def set_run_font(run, size=None, color=None, bold=None, italic=None, name="Calibri"):
    run.font.name = name
    run._element.rPr.rFonts.set(qn("w:ascii"), name)
    run._element.rPr.rFonts.set(qn("w:hAnsi"), name)
    if size is not None:
        run.font.size = Pt(size)
    if color is not None:
        run.font.color.rgb = color
    if bold is not None:
        run.bold = bold
    if italic is not None:
        run.italic = italic


def style_para(p, before=0, after=6, line=1.1, align=None):
    fmt = p.paragraph_format
    fmt.space_before = Pt(before)
    fmt.space_after = Pt(after)
    fmt.line_spacing = line
    if align is not None:
        p.alignment = align


def add_para(doc, text="", size=11, color=NAVY, bold=False, italic=False, after=6, before=0, align=None):
    p = doc.add_paragraph()
    style_para(p, before=before, after=after, align=align)
    r = p.add_run(text)
    set_run_font(r, size=size, color=color, bold=bold, italic=italic)
    return p


def add_heading(doc, text, level=1):
    p = doc.add_paragraph()
    before_after = {1: (16, 8, 16, BLUE), 2: (12, 6, 13, BLUE), 3: (8, 4, 12, NAVY)}
    before, after, size, color = before_after[level]
    style_para(p, before=before, after=after)
    r = p.add_run(text)
    set_run_font(r, size=size, color=color, bold=True)
    return p


def add_bullet(doc, text):
    p = doc.add_paragraph(style="List Bullet")
    style_para(p, after=4, line=1.15)
    r = p.add_run(text)
    set_run_font(r, size=10.5, color=NAVY)
    return p


def add_number(doc, text):
    p = doc.add_paragraph(style="List Number")
    style_para(p, after=4, line=1.15)
    r = p.add_run(text)
    set_run_font(r, size=10.5, color=NAVY)
    return p


def add_callout(doc, title, body, fill=LIGHT_GREEN):
    table = doc.add_table(rows=1, cols=1)
    table.alignment = WD_TABLE_ALIGNMENT.LEFT
    set_table_width(table, [9360])
    set_table_borders(table, color="9ACAA1", size="6")
    cell = table.cell(0, 0)
    set_cell_shading(cell, fill)
    set_cell_margins(cell, top=140, bottom=140, start=180, end=180)
    p = cell.paragraphs[0]
    style_para(p, after=3, line=1.12)
    r = p.add_run(title)
    set_run_font(r, size=10.5, color=DARK_GREEN, bold=True)
    p2 = cell.add_paragraph()
    style_para(p2, after=0, line=1.12)
    r2 = p2.add_run(body)
    set_run_font(r2, size=10, color=NAVY)
    doc.add_paragraph().paragraph_format.space_after = Pt(2)
    return table


def add_kv_table(doc, rows, widths=(2500, 6860), header=None, fill=LIGHT_GRAY):
    table = doc.add_table(rows=0, cols=2)
    table.alignment = WD_TABLE_ALIGNMENT.LEFT
    set_table_borders(table)
    if header:
        row = table.add_row()
        row.cells[0].merge(row.cells[1])
        cell = row.cells[0]
        set_cell_shading(cell, fill)
        set_cell_margins(cell)
        p = cell.paragraphs[0]
        style_para(p, after=0)
        r = p.add_run(header)
        set_run_font(r, size=10.5, color=NAVY, bold=True)
    for label, value in rows:
        cells = table.add_row().cells
        for c in cells:
            set_cell_margins(c)
            c.vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
        set_cell_shading(cells[0], fill)
        p = cells[0].paragraphs[0]
        style_para(p, after=0)
        set_run_font(p.add_run(label), size=9.8, color=NAVY, bold=True)
        p = cells[1].paragraphs[0]
        style_para(p, after=0, line=1.12)
        set_run_font(p.add_run(value), size=9.8, color=NAVY)
    set_table_width(table, list(widths))
    doc.add_paragraph().paragraph_format.space_after = Pt(2)
    return table


def add_matrix(doc, headers, rows, widths, header_fill=LIGHT_BLUE):
    table = doc.add_table(rows=1, cols=len(headers))
    table.alignment = WD_TABLE_ALIGNMENT.LEFT
    set_table_borders(table)
    hdr = table.rows[0].cells
    for i, h in enumerate(headers):
        set_cell_shading(hdr[i], header_fill)
        set_cell_margins(hdr[i])
        p = hdr[i].paragraphs[0]
        style_para(p, after=0, line=1.1)
        set_run_font(p.add_run(h), size=9.2, color=NAVY, bold=True)
    for row in rows:
        cells = table.add_row().cells
        for i, value in enumerate(row):
            set_cell_margins(cells[i])
            cells[i].vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
            p = cells[i].paragraphs[0]
            style_para(p, after=0, line=1.08)
            set_run_font(p.add_run(value), size=9.1, color=NAVY)
    set_table_width(table, widths)
    doc.add_paragraph().paragraph_format.space_after = Pt(2)
    return table


def add_footer(section):
    footer = section.footer
    p = footer.paragraphs[0]
    p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    style_para(p, after=0)
    r = p.add_run("Zaya IT - Gestão de Negócios | ZY-POLYVOX-2026-TLC-INTPDD-002 | Confidencial | Página ")
    set_run_font(r, size=8.5, color=MUTED)
    fld_begin = OxmlElement("w:fldChar")
    fld_begin.set(qn("w:fldCharType"), "begin")
    instr = OxmlElement("w:instrText")
    instr.set(qn("xml:space"), "preserve")
    instr.text = "PAGE"
    fld_end = OxmlElement("w:fldChar")
    fld_end.set(qn("w:fldCharType"), "end")
    run = p.add_run()
    run._r.append(fld_begin)
    run._r.append(instr)
    run._r.append(fld_end)
    set_run_font(run, size=8.5, color=MUTED)


def add_header(section):
    header = section.header
    p = header.paragraphs[0]
    p.alignment = WD_ALIGN_PARAGRAPH.LEFT
    style_para(p, after=0)
    r = p.add_run("Polyvox Telecontrol | IntPDD - Composição, Produtos e Peças")
    set_run_font(r, size=8.5, color=MUTED, bold=True)


def setup_document():
    doc = Document()
    section = doc.sections[0]
    section.page_width = Inches(8.5)
    section.page_height = Inches(11)
    section.top_margin = Inches(1)
    section.bottom_margin = Inches(1)
    section.left_margin = Inches(1)
    section.right_margin = Inches(1)
    section.header_distance = Inches(0.49)
    section.footer_distance = Inches(0.49)
    add_header(section)
    add_footer(section)

    styles = doc.styles
    normal = styles["Normal"]
    normal.font.name = "Calibri"
    normal._element.rPr.rFonts.set(qn("w:ascii"), "Calibri")
    normal._element.rPr.rFonts.set(qn("w:hAnsi"), "Calibri")
    normal.font.size = Pt(11)
    normal.font.color.rgb = NAVY
    return doc


def build():
    doc = setup_document()

    add_para(doc, "ZAYA IT | GESTÃO DE NEGÓCIOS", size=9.5, color=DARK_GREEN, bold=True, after=2)
    add_para(doc, "INTPDD - DOCUMENTO DE INTEGRAÇÃO E DESENVOLVIMENTO", size=10.5, color=MUTED, bold=True, after=22)
    add_para(doc, "Integração Telecontrol Polyvox", size=25, color=NAVY, bold=True, after=4)
    add_para(doc, "Composição de Produtos (TPRLMP), Lista Básica, Produtos e Peças", size=15, color=DARK_GREEN, bold=True, after=18)
    add_callout(
        doc,
        "Resumo executivo",
        "Documento de apoio para homologação das melhorias implementadas no pacote Telecontrol. O foco é registrar a regra da composição de produtos para Lista Básica, a atualização dos cadastros de produtos e peças via GET e os controles criados para evitar duplicidade e perda de rastreabilidade.",
    )
    add_kv_table(
        doc,
        [
            ("Documento", "ZY-POLYVOX-2026-TLC-INTPDD-002"),
            ("Versão", "v1.0"),
            ("Data", "11 de junho de 2026"),
            ("Projeto", "Polyvox - Integração Telecontrol no Sankhya"),
            ("Responsável técnico", "Zaya IT - Gestão de Negócios"),
            ("Tecnologia", "Java 8 - Sankhya MGE / AcaoRotinaJava / ScheduledAction / Gradle"),
            ("Status", "Em homologação e testes assistidos"),
        ],
        header="Controle do documento",
    )
    doc.add_page_break()

    add_heading(doc, "Objetivo deste documento", 1)
    add_para(
        doc,
        "Este documento descreve as melhorias criadas na integração Telecontrol para sustentar a Lista Básica a partir da Composição de Produtos do Sankhya e para atualizar os cadastros de produtos e peças já existentes na base. O material foi preparado para leitura funcional e técnica, servindo como referência de homologação, rastreabilidade e continuidade do desenvolvimento.",
    )
    add_heading(doc, "Escopo entregue", 2)
    for item in [
        "Criação de rotina para ler matérias-primas da TPRLMP marcadas para envio à Lista Básica Telecontrol.",
        "Persistência controlada na tabela filha AD_TCLLTB, vinculada ao produto existente em AD_TCLPRO.",
        "Atualização de Produtos Telecontrol via GET, com busca por REFERENCIA e fallback na TGFPRO.",
        "Atualização de Peças Telecontrol via GET, com a mesma regra de referência e amarração ao CODPROD Sankhya.",
        "Padronização de transações para evitar conflito de sessão Jape em botões de ação e rotinas agendadas.",
        "Correção de tipos numéricos em campos Sankhya como GARANTIA, MAODEOBRA, MAODEOBRAADMIN, MULTIPLO e GARANTIADIFERENCIADA.",
        "Melhoria dos logs operacionais para evidenciar recebidos, atualizados, inseridos, não encontrados e registros com erro.",
    ]:
        add_bullet(doc, item)
    add_heading(doc, "Fora do escopo nesta fase", 2)
    for item in [
        "Envio final da Lista Básica à API Telecontrol com retorno preenchendo LISTABASICA e DHINTEG, quando a fila estiver homologada.",
        "Criação automática de produtos ou peças na TGFPRO; a regra atual apenas aproveita registros existentes por REFERENCIA.",
        "Correção de referências divergentes entre Telecontrol e Sankhya sem apoio de saneamento cadastral.",
    ]:
        add_bullet(doc, item)

    add_heading(doc, "Processo funcional", 1)
    add_heading(doc, "Fluxo AS-IS", 2)
    add_para(
        doc,
        "Antes das melhorias, os cadastros Telecontrol dependiam de cargas e envios pontuais, e a Lista Básica não era alimentada diretamente a partir da composição do produto. Como consequência, peças cadastradas como matéria-prima no Sankhya não eram refletidas na estrutura AD_TCLLTB, mesmo quando o produto e a peça já existiam nas telas customizadas.",
    )
    add_heading(doc, "Fluxo TO-BE", 2)
    add_matrix(
        doc,
        ["Etapa", "Origem", "Validação", "Resultado esperado"],
        [
            ("1", "TPRLMP.AD_ENVLISTTLC = 'S'", "Produto acabado e matéria-prima informados", "Registro elegível para Lista Básica"),
            ("2", "AD_TCLPRO", "CODPRODPA = AD_TCLPRO.CODPROD", "Produto Telecontrol localizado"),
            ("3", "AD_TCLPCA", "CODPRODMP = AD_TCLPCA.CODPROD", "Peça Telecontrol localizada"),
            ("4", "TGFPRO", "REFERENCIA por CODPRODPA e CODPRODMP", "REFPRODUTO e REFPECA preenchidos"),
            ("5", "AD_TCLLTB", "CODPROD + CODPECA inexistente", "Item de Lista Básica inserido sem duplicidade"),
        ],
        [900, 2400, 3100, 2960],
        header_fill=LIGHT_GREEN,
    )
    add_callout(
        doc,
        "Regra principal",
        "A composição só vira Lista Básica quando a matéria-prima está marcada com AD_ENVLISTTLC = 'S' e quando produto e peça já existem nas telas Telecontrol. Se a combinação CODPROD/CODPECA já existir em AD_TCLLTB, a rotina não insere novamente.",
    )

    add_heading(doc, "Composição de Produtos (TPRLMP)", 1)
    add_para(
        doc,
        "Foi criada a sustentação para utilizar a tela de Composição de Produtos como origem da Lista Básica Telecontrol. O campo AD_ENVLISTTLC identifica quais matérias-primas devem ser consideradas para integração, respeitando a estrutura nativa do Sankhya e evitando que itens não marcados sejam enviados indevidamente.",
    )
    add_kv_table(
        doc,
        [
            ("Produto acabado", "TPRLMP.CODPRODPA -> AD_TCLPRO.CODPROD"),
            ("Matéria-prima / peça", "TPRLMP.CODPRODMP -> AD_TCLPCA.CODPROD"),
            ("Quantidade", "TPRLMP.QTDMISTURA -> AD_TCLLTB.QTD"),
            ("Referência produto", "TGFPRO.REFERENCIA pelo CODPRODPA -> AD_TCLLTB.REFPRODUTO"),
            ("Referência peça", "TGFPRO.REFERENCIA pelo CODPRODMP -> AD_TCLLTB.REFPECA"),
            ("Controle de duplicidade", "Busca prévia em AD_TCLLTB por CODPROD + CODPECA"),
            ("Campos de integração futura", "LISTABASICA e DHINTEG permanecem para retorno/API Telecontrol"),
        ],
        header="Mapeamento TPRLMP -> AD_TCLLTB",
    )
    add_heading(doc, "Validações aplicadas", 2)
    for item in [
        "Ignorar composição sem AD_ENVLISTTLC = 'S'.",
        "Não inserir Lista Básica se o produto não existir em AD_TCLPRO.",
        "Não inserir Lista Básica se a peça não existir em AD_TCLPCA.",
        "Não duplicar quando CODPROD e CODPECA já estiverem gravados em AD_TCLLTB.",
        "Preencher referências a partir da TGFPRO para manter aderência ao cadastro Sankhya.",
    ]:
        add_bullet(doc, item)

    doc.add_page_break()
    add_heading(doc, "Atualização de Produtos Telecontrol", 1)
    add_para(
        doc,
        "A rotina de produtos consulta o endpoint GET de produtos do Telecontrol e atualiza a tabela AD_TCLPRO com base na REFERENCIA. O COD_TELECONTROL não é usado como chave de localização, porque o código interno que governa o vínculo no Sankhya é o CODPROD da TGFPRO.",
    )
    add_matrix(
        doc,
        ["Regra", "Comportamento"],
        [
            ("Busca primária", "Localiza registro existente em AD_TCLPRO pela REFERENCIA recebida da API."),
            ("Fallback", "Se não existir em AD_TCLPRO, consulta TGFPRO.REFERENCIA e usa o CODPROD local para inserir."),
            ("Não encontrado", "Se a referência também não existir na TGFPRO, registra log e não cria cadastro órfão."),
            ("Atualização", "Preenche descrição, família, linha, garantia, mão de obra, série obrigatória, voltagem, ativo e COD_TELECONTROL."),
            ("Tipos", "Campos numéricos são convertidos para BigDecimal antes de persistir no Jape."),
        ],
        [2100, 7260],
        header_fill=LIGHT_BLUE,
    )
    add_kv_table(
        doc,
        [
            ("Entrada Telecontrol", "produto, descricao, referencia, garantia, ativo, familia, linha, voltagem, mao_de_obra, mao_de_obra_admin, numero_serie_obrigatorio"),
            ("Tabela destino", "AD_TCLPRO"),
            ("Chave funcional", "REFERENCIA"),
            ("Chave Sankhya persistida", "CODPROD localizado em TGFPRO"),
            ("Ações disponíveis", "AtualizaProdutoBt e AtualizaProdutoSc"),
        ],
        header="Resumo técnico - produtos",
    )

    add_heading(doc, "Atualização de Peças Telecontrol", 1)
    add_para(
        doc,
        "A rotina de peças segue o mesmo padrão de produtos: consulta GET no Telecontrol, procura a peça pela REFERENCIA em AD_TCLPCA e, quando necessário, busca a referência na TGFPRO para gravar o CODPROD correto do Sankhya. Isso evita criar linhas duplicadas ou desvinculadas do cadastro de produtos.",
    )
    add_matrix(
        doc,
        ["Regra", "Comportamento"],
        [
            ("Busca primária", "Localiza a peça existente em AD_TCLPCA pela REFERENCIA."),
            ("Fallback", "Quando a peça não existe na tela Telecontrol, consulta TGFPRO.REFERENCIA para obter o CODPROD local."),
            ("Inserção", "Insere em AD_TCLPCA somente se a referência existir na TGFPRO."),
            ("Atualização", "Atualiza descrição, referência, IPI, múltiplo, devolução obrigatória, garantia diferenciada, estoque e COD_TELECONTROL."),
            ("Tipos", "Campos numéricos são tratados como BigDecimal, reduzindo falhas de tipo no Jape."),
        ],
        [2100, 7260],
        header_fill=LIGHT_BLUE,
    )
    add_kv_table(
        doc,
        [
            ("Entrada Telecontrol", "peca, descricao, referencia, ipi, multiplo, devolucao_obrigatoria, garantia_diferenciada, estoque"),
            ("Tabela destino", "AD_TCLPCA"),
            ("Chave funcional", "REFERENCIA"),
            ("Chave Sankhya persistida", "CODPROD localizado em TGFPRO"),
            ("Ações disponíveis", "AtualizaPecaBt e AtualizaPecaSc"),
        ],
        header="Resumo técnico - peças",
    )

    add_heading(doc, "Rastreabilidade e logs", 1)
    add_para(
        doc,
        "As rotinas foram ajustadas para exibir logs operacionais úteis durante homologação. O objetivo é permitir leitura rápida do que veio da API, o que foi encontrado no Sankhya e qual decisão a rotina tomou para cada referência.",
    )
    add_matrix(
        doc,
        ["Evento de log", "Uso na homologação"],
        [
            ("Pagina processada", "Mostra quantidade recebida da API e total acumulado."),
            ("Produto atualizado / Peça atualizada", "Confirma atualização de cadastro já existente."),
            ("Inserido a partir da TGFPRO", "Evidencia que a rotina achou a referência no cadastro Sankhya e criou a linha na tela Telecontrol."),
            ("Não encontrado na AD nem na TGFPRO", "Indica necessidade de saneamento cadastral por referência."),
            ("Erro ao sincronizar", "Mostra referência, código Telecontrol e mensagem técnica para correção rápida."),
        ],
        [2500, 6860],
        header_fill=LIGHT_GREEN,
    )
    add_callout(
        doc,
        "Evidência recebida em teste",
        "A execução de 11/06/2026 registrou produtos atualizados, produtos inseridos a partir da TGFPRO e referências não encontradas. Esse comportamento confirma que a rotina está usando a REFERENCIA como chave funcional e separando corretamente os casos que exigem saneamento cadastral.",
        fill="FFF8E8",
    )

    add_heading(doc, "Componentes desenvolvidos", 1)
    add_matrix(
        doc,
        ["Componente", "Responsabilidade", "Tipo"],
        [
            ("BuscaCadastrosTelecontrolService", "Orquestra GET de produtos e peças, paginação, parse e persistência.", "Service"),
            ("AtualizaProdutoBt / AtualizaProdutoSc", "Dispara atualização de produtos por botão ou rotina agendada.", "Ação / Scheduled"),
            ("AtualizaPecaBt / AtualizaPecaSc", "Dispara atualização de peças por botão ou rotina agendada.", "Ação / Scheduled"),
            ("SincronizaListaBasicaService", "Lê TPRLMP marcada e insere detalhes em AD_TCLLTB.", "Service"),
            ("SincronizaListaBasicaScheduler", "Executa a sincronização da Lista Básica de forma agendada.", "ScheduledAction"),
            ("LogUtil", "Registra logs respeitando a transação ativa para evitar conflito Jape.", "Utilitário"),
        ],
        [2700, 4960, 1700],
        header_fill=LIGHT_BLUE,
    )
    add_heading(doc, "Ajustes técnicos relevantes", 2)
    for item in [
        "Uso de conexão nativa Java 8, sem OkHttp e sem Okio.",
        "Organização do projeto com Gradle para geração do JAR final.",
        "Separação das responsabilidades entre actions, scheduled actions, services, DAOs/modelos e utilitários.",
        "Tratamento de transação para evitar erros como 'Já existe uma transação em andamento' e 'Operação requer uma transação ativa'.",
        "Conversão de campos NUMBER/FLOAT do Sankhya para BigDecimal antes do set no FluidCreateVO/FluidUpdateVO.",
    ]:
        add_bullet(doc, item)

    add_heading(doc, "Critérios de homologação", 1)
    add_matrix(
        doc,
        ["Cenário", "Resultado esperado"],
        [
            ("Produto existe em AD_TCLPRO", "Atualizar campos sem inserir duplicado."),
            ("Produto não existe em AD_TCLPRO, mas existe em TGFPRO", "Inserir AD_TCLPRO com CODPROD local e dados Telecontrol."),
            ("Produto não existe em TGFPRO", "Registrar não encontrado e não inserir linha órfã."),
            ("Peça existe em AD_TCLPCA", "Atualizar campos sem inserir duplicado."),
            ("Peça não existe em AD_TCLPCA, mas existe em TGFPRO", "Inserir AD_TCLPCA com CODPROD local e dados Telecontrol."),
            ("TPRLMP marcada para envio", "Inserir AD_TCLLTB apenas se produto, peça e combinação ainda não existirem."),
            ("TPRLMP sem marcação", "Ignorar sem movimentar Lista Básica."),
        ],
        [3000, 6360],
        header_fill=LIGHT_GREEN,
    )
    add_heading(doc, "Próximos passos recomendados", 1)
    for item in [
        "Executar homologação completa com amostra de referências conhecidas, incluindo produtos e peças não encontrados.",
        "Validar o envio posterior da AD_TCLLTB para o Telecontrol e o retorno de LISTABASICA/DHINTEG.",
        "Formalizar rotina de saneamento para referências divergentes entre Telecontrol e TGFPRO.",
        "Agendar execução recorrente das rotinas de atualização de produtos, peças e Lista Básica conforme janela operacional definida pela Polyvox.",
    ]:
        add_number(doc, item)

    add_para(
        doc,
        "Fim do documento.",
        size=9.5,
        color=MUTED,
        italic=True,
        before=18,
        align=WD_ALIGN_PARAGRAPH.CENTER,
    )
    doc.save(OUT)


if __name__ == "__main__":
    build()
    print(OUT)
