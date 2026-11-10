from pathlib import Path

from docx import Document
from docx.enum.section import WD_SECTION
from docx.enum.table import WD_CELL_VERTICAL_ALIGNMENT, WD_TABLE_ALIGNMENT
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn
from docx.shared import Cm, Pt, RGBColor


ROOT = Path(__file__).resolve().parent
OUTPUT = ROOT / "output"
OUTPUT.mkdir(parents=True, exist_ok=True)
DOCX = OUTPUT / "OS_7263_PORTOMAG_VALIDACAO_IMPORTADOR_PDF.docx"

NAVY = "17365D"
LIGHT_BLUE = "DCE6F1"
PALE_BLUE = "F3F7FB"
LIGHT_GRAY = "E7E6E6"
BORDER = "D9D9D9"


def shade(cell, fill):
    tc_pr = cell._tc.get_or_add_tcPr()
    shd = tc_pr.find(qn("w:shd"))
    if shd is None:
        shd = OxmlElement("w:shd")
        tc_pr.append(shd)
    shd.set(qn("w:fill"), fill)


def borders(table):
    tbl_pr = table._tbl.tblPr
    node = tbl_pr.find(qn("w:tblBorders"))
    if node is None:
        node = OxmlElement("w:tblBorders")
        tbl_pr.append(node)
    for edge in ("top", "left", "bottom", "right", "insideH", "insideV"):
        item = OxmlElement(f"w:{edge}")
        item.set(qn("w:val"), "single")
        item.set(qn("w:sz"), "4")
        item.set(qn("w:color"), BORDER)
        node.append(item)


def margins(cell, top=100, start=120, bottom=100, end=120):
    tc_pr = cell._tc.get_or_add_tcPr()
    tc_mar = tc_pr.first_child_found_in("w:tcMar")
    if tc_mar is None:
        tc_mar = OxmlElement("w:tcMar")
        tc_pr.append(tc_mar)
    for name, value in (("top", top), ("start", start), ("bottom", bottom), ("end", end)):
        element = tc_mar.find(qn(f"w:{name}"))
        if element is None:
            element = OxmlElement(f"w:{name}")
            tc_mar.append(element)
        element.set(qn("w:w"), str(value))
        element.set(qn("w:type"), "dxa")


def set_repeat_header(row):
    tr_pr = row._tr.get_or_add_trPr()
    tbl_header = OxmlElement("w:tblHeader")
    tbl_header.set(qn("w:val"), "true")
    tr_pr.append(tbl_header)


def keep_with_next(paragraph):
    paragraph.paragraph_format.keep_with_next = True


def add_heading(doc, text, level=1):
    p = doc.add_heading(text, level=level)
    keep_with_next(p)
    return p


def add_body(doc, text, bold_lead=None):
    p = doc.add_paragraph()
    if bold_lead and text.startswith(bold_lead):
        p.add_run(bold_lead).bold = True
        p.add_run(text[len(bold_lead):])
    else:
        p.add_run(text)
    return p


def add_bullet(doc, text, checked=False):
    p = doc.add_paragraph(style="List Bullet")
    if checked:
        p.add_run("☐ ")
    p.add_run(text)
    return p


def add_meta_table(doc):
    rows = [
        ("Ordem de serviço", "7263"),
        ("Cliente", "PORTOMAG"),
        ("Item", "34  Gestão de contratos com leitura automática de PDF"),
        ("Etapa", "Validação do cliente"),
        ("Data do documento", "04/09/2026"),
        ("Situação", "Entregável técnico disponível para homologação"),
    ]
    table = doc.add_table(rows=len(rows), cols=2)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    table.autofit = False
    table.columns[0].width = Cm(4.2)
    table.columns[1].width = Cm(12.2)
    borders(table)
    for index, (label, value) in enumerate(rows):
        left, right = table.rows[index].cells
        left.width, right.width = Cm(4.2), Cm(12.2)
        left.vertical_alignment = right.vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
        shade(left, LIGHT_BLUE)
        if index % 2:
            shade(right, PALE_BLUE)
        for cell in (left, right):
            margins(cell)
        left.paragraphs[0].add_run(label).bold = True
        right.paragraphs[0].add_run(value)
    doc.add_paragraph()


def add_result_table(doc):
    table = doc.add_table(rows=1, cols=3)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    table.autofit = False
    widths = [Cm(5.0), Cm(7.3), Cm(4.1)]
    headers = ["Componente", "Resultado desta etapa", "Situação"]
    for i, text in enumerate(headers):
        cell = table.rows[0].cells[i]
        cell.width = widths[i]
        shade(cell, NAVY)
        margins(cell)
        run = cell.paragraphs[0].add_run(text)
        run.bold = True
        run.font.color.rgb = RGBColor(255, 255, 255)
        cell.vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
    set_repeat_header(table.rows[0])
    rows = [
        ("Dashboard HTML5", "Importação, leitura e conferência do PDF pelo usuário", "Implementado"),
        ("Leitor do pedido", "Extração do cabeçalho, itens, impostos, anexos e páginas de evidência", "Validado localmente"),
        ("Metadados", "Estrutura mestre ZPM_PEDREC e detalhe ZPM_PEDITE", "Importada para homologação"),
        ("Rotina Java", "Validação no servidor e gravação pela ação 68", "Em homologação"),
        ("Nota de compra", "Regras funcionais e geração no portal", "Próxima etapa"),
    ]
    for row_index, values in enumerate(rows, 1):
        cells = table.add_row().cells
        for i, value in enumerate(values):
            cells[i].width = widths[i]
            cells[i].vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
            margins(cells[i])
            if row_index % 2 == 0:
                shade(cells[i], PALE_BLUE)
            p = cells[i].paragraphs[0]
            p.add_run(value)
            if i == 2:
                p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    borders(table)


def add_validation_table(doc):
    table = doc.add_table(rows=1, cols=3)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    table.autofit = False
    widths = [Cm(1.4), Cm(10.5), Cm(4.5)]
    for i, text in enumerate(("Item", "Validação solicitada", "Resultado")):
        cell = table.rows[0].cells[i]
        cell.width = widths[i]
        shade(cell, NAVY)
        margins(cell)
        run = cell.paragraphs[0].add_run(text)
        run.bold = True
        run.font.color.rgb = RGBColor(255, 255, 255)
        cell.vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
    set_repeat_header(table.rows[0])
    validations = [
        "Importar o pedido PDF 5500623466 pelo dashboard.",
        "Conferir número, fornecedor, comprador, condição de pagamento e total do pedido.",
        "Confirmar a criação de um registro mestre e dois itens de detalhe.",
        "Conferir o total de R$ 21.616,09 e os impostos impressos no documento.",
        "Validar descrições, site, município, datas e textos de faturamento e entrega sem truncamento.",
        "Confirmar o alerta do primeiro item, cuja composição não fecha apenas por quantidade vezes preço unitário.",
        "Fechar e reabrir o registro para confirmar a persistência e o vínculo entre mestre e itens.",
        "Registrar divergências encontradas e informar o aceite funcional da etapa.",
    ]
    for number, validation in enumerate(validations, 1):
        cells = table.add_row().cells
        values = (str(number), validation, "☐ Conforme   ☐ Ajustar")
        for i, value in enumerate(values):
            cells[i].width = widths[i]
            margins(cells[i], 115, 120, 115, 120)
            cells[i].vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
            if number % 2 == 0:
                shade(cells[i], PALE_BLUE)
            cells[i].paragraphs[0].add_run(value)
            if i != 1:
                cells[i].paragraphs[0].alignment = WD_ALIGN_PARAGRAPH.CENTER
    borders(table)


doc = Document()
section = doc.sections[0]
section.top_margin = Cm(1.8)
section.bottom_margin = Cm(1.7)
section.left_margin = Cm(2.1)
section.right_margin = Cm(2.1)

styles = doc.styles
normal = styles["Normal"]
normal.font.name = "Aptos"
normal.font.size = Pt(10.5)
normal.font.color.rgb = RGBColor(0, 0, 0)
normal.paragraph_format.space_after = Pt(6)
normal.paragraph_format.line_spacing = 1.08

for name, size, before, after in (("Title", 22, 0, 12), ("Heading 1", 15, 14, 6), ("Heading 2", 12, 10, 4)):
    style = styles[name]
    style.font.name = "Aptos Display"
    style.font.size = Pt(size)
    style.font.bold = True
    style.font.color.rgb = RGBColor(0, 0, 0)
    style.paragraph_format.space_before = Pt(before)
    style.paragraph_format.space_after = Pt(after)
    style.paragraph_format.keep_with_next = True

header = section.header.paragraphs[0]
header.alignment = WD_ALIGN_PARAGRAPH.RIGHT
run = header.add_run("ZAYA.IT  |  PORTOMAG")
run.bold = True
run.font.name = "Aptos"
run.font.size = Pt(9)
run.font.color.rgb = RGBColor(70, 70, 70)

footer = section.footer.paragraphs[0]
footer.alignment = WD_ALIGN_PARAGRAPH.CENTER
footer_run = footer.add_run("OS 7263  |  Item 34  |  Validação do importador de pedidos PDF")
footer_run.font.name = "Aptos"
footer_run.font.size = Pt(8)
footer_run.font.color.rgb = RGBColor(90, 90, 90)

title = doc.add_paragraph(style="Title")
title.add_run("Ordem de Serviço 7263")
subtitle = doc.add_paragraph()
subtitle.paragraph_format.space_after = Pt(14)
subrun = subtitle.add_run("Validação do importador de pedidos recebidos em PDF")
subrun.bold = True
subrun.font.size = Pt(13)
subrun.font.color.rgb = RGBColor(55, 55, 55)

add_meta_table(doc)

add_heading(doc, "Objetivo desta entrega")
add_body(doc, "Disponibilizar para validação da PORTOMAG a primeira etapa do Item 34, composta pela leitura automática de pedidos em PDF, conferência dos dados e gravação em estrutura própria no Sankhya. O cliente deverá validar a aderência das informações antes da definição da geração da nota de compra no portal.")

add_heading(doc, "Situação atual")
add_body(doc, "O dashboard, o leitor do layout de referência, os metadados mestre e detalhe e a rotina Java de recebimento foram desenvolvidos. Os componentes estão em homologação. A validação definitiva da gravação depende de alinhar no Dicionário de Dados o tamanho do campo ZPM_PEDITE.FATURAMENTO para 2.000 caracteres, além da coluna física já prevista com a mesma capacidade.")

add_result_table(doc)

doc.add_page_break()

add_heading(doc, "Atividades realizadas")
add_heading(doc, "Leitura e conferência do PDF", level=2)
add_body(doc, "Foi criado um dashboard HTML5 integrado ao Sankhya, com seleção ou arraste do arquivo PDF. A leitura acontece no navegador com biblioteca PDF.js local, sem envio do documento para serviço externo e sem dependência de CDN. O fluxo apresenta os dados extraídos antes da gravação para permitir conferência pelo usuário.")
add_bullet(doc, "Identificação do pedido, emissão, fornecedor, comprador, condição de pagamento e total.")
add_bullet(doc, "Leitura dos itens, quantidades, unidades, preços, impostos, totais e páginas de origem.")
add_bullet(doc, "Leitura dos anexos com site, município, período, faturamento e local de entrega.")
add_bullet(doc, "Normalização de datas, números, valores monetários e textos do layout conhecido.")

add_heading(doc, "Estrutura mestre e detalhe", level=2)
add_body(doc, "Foram definidos metadados próprios para separar o cabeçalho do pedido e seus itens. ZPM_PEDREC armazena o registro mestre e ZPM_PEDITE armazena os detalhes vinculados. A chave de importação evita o recebimento concorrente do mesmo pedido, sujeito à homologação das constraints no ambiente.")

add_heading(doc, "Gravação no Sankhya", level=2)
add_body(doc, "O botão Salvar pedido recebido solicita confirmação e envia um contrato XML fechado para a rotina Java código 68. O servidor valida estrutura, campos, tipos, limites, sequência dos itens, páginas de evidência e soma total antes de usar Jape para persistir mestre e detalhes dentro do contexto transacional da ação.")

add_heading(doc, "Correções aplicadas durante a homologação", level=2)
add_bullet(doc, "Adequação do pacote HTML5 ao formato JSP aceito pelo ambiente Sankhya da PORTOMAG.")
add_bullet(doc, "Inclusão do bloco exportInfo exigido pelo importador legado de metadados.")
add_bullet(doc, "Compatibilidade do parser XML com a implementação antiga disponível no servidor.")
add_bullet(doc, "Preparação do ajuste de capacidade do campo FATURAMENTO para textos superiores a 100 caracteres.")

add_heading(doc, "Evidência utilizada")
add_body(doc, "O pedido 5500623466, com 12 páginas, foi usado como referência. Foram reconhecidos dois itens e o total de R$ 21.616,09. O primeiro item apresenta total impresso de R$ 19.148,42, embora 30 unidades a R$ 534,56 resultem em R$ 16.036,80. O importador preserva o valor oficial impresso e sinaliza a composição para conferência, sem inventar uma regra tributária ou comercial.")

doc.add_page_break()

add_heading(doc, "Validação solicitada ao cliente")
add_body(doc, "Solicitamos que a PORTOMAG execute o roteiro abaixo em homologação e registre qualquer divergência observada. O aceite desta etapa deve confirmar a leitura, a apresentação e a persistência das informações recebidas; ele não aprova ainda a geração da nota de compra.")
add_validation_table(doc)

add_heading(doc, "Critério de aceite")
add_body(doc, "A etapa será considerada validada quando o pedido de referência puder ser lido, conferido, salvo e consultado novamente com um mestre, dois itens e os valores esperados, sem truncamento dos textos. Divergências de composição apresentadas pelo próprio PDF podem permanecer como alerta, desde que sejam compreendidas e aceitas pelo cliente.")

add_heading(doc, "Próxima etapa após o aceite")
add_body(doc, "Após a validação desta entrega, será realizado o levantamento funcional e técnico para definir como gerar a nota de compra no portal. Essa definição deverá confirmar empresa, parceiro, produtos e serviços, unidades, TOP e sua versão, natureza, negociação, regras tributárias, estoque, financeiro, fiscal, confirmação, cancelamento, duplicidade e rastreabilidade com o pedido recebido.")
add_body(doc, "A geração do documento deverá utilizar serviço ou API pública compatível com a versão Sankhya. Nenhuma geração de nota, inserção em TGFCAB ou TGFITE, confirmação ou movimentação fiscal faz parte desta entrega.")

add_heading(doc, "Registro da validação")
table = doc.add_table(rows=4, cols=2)
table.alignment = WD_TABLE_ALIGNMENT.CENTER
table.autofit = False
labels = ["Responsável PORTOMAG", "Data", "Resultado", "Observações"]
values = ["", "____/____/________", "☐ Aprovado   ☐ Aprovado com ajustes   ☐ Reprovado", ""]
for i, (label, value) in enumerate(zip(labels, values)):
    left, right = table.rows[i].cells
    left.width, right.width = Cm(4.2), Cm(12.2)
    shade(left, LIGHT_BLUE)
    margins(left, 130, 120, 130, 120)
    margins(right, 130, 120, 130, 120)
    left.vertical_alignment = right.vertical_alignment = WD_CELL_VERTICAL_ALIGNMENT.CENTER
    left.paragraphs[0].add_run(label).bold = True
    right.paragraphs[0].add_run(value)
    if i == 3:
        right.paragraphs[0].paragraph_format.space_after = Pt(30)
borders(table)

doc.core_properties.title = "Ordem de Serviço 7263 Validação do importador de pedidos PDF"
doc.core_properties.subject = "Item 34 PORTOMAG"
doc.core_properties.author = "ZAYA.IT"
doc.core_properties.keywords = "PORTOMAG, OS 7263, Item 34, PDF, Sankhya, homologação"
doc.save(DOCX)
print(DOCX)
