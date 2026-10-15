package br.com.crmbonus.sales.service;

import br.com.crmbonus.sales.dto.request.SalesTotalRequestDTO;
import br.com.crmbonus.sales.dto.request.SalesCancelRequestDTO;
import br.com.crmbonus.sales.dto.response.SalesTotalResponseDTO;
import br.com.crmbonus.sales.dto.response.SalesCancelResponseDTO;

public interface SalesService {

    /**
     * Passo 8: sales-total
     * Enviar dados finais após emissão da NF e salvar o sale_id
     */
    SalesTotalResponseDTO submitSalesTotal(SalesTotalRequestDTO request, String requestHash) throws Exception;

    /**
     * Passo 9: sales-cancel
     * Cancelar a operação na CRM caso a nota seja cancelada no Sankhya
     */
    SalesCancelResponseDTO cancelSales(SalesCancelRequestDTO request, String requestHash) throws Exception;
}

