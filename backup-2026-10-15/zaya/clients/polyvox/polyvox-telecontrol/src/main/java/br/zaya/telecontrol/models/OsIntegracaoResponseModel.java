package br.zaya.telecontrol.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsIntegracaoResponseModel {
    
    @JsonAlias({"data", "response"})
    private List<OsIntegracaoModel> dados;
    
    @JsonAlias({"page"})
    private int pagina;
    
    @JsonAlias({"total_pages"})
    private int totalPaginas;
    
    @JsonAlias({"total_items"})
    private int totalItens;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("message")
    private String mensagem;
    
    public List<OsIntegracaoModel> getDados() { return dados; }
    public void setDados(List<OsIntegracaoModel> dados) { this.dados = dados; }
    
    public int getPagina() { return pagina; }
    public void setPagina(int pagina) { this.pagina = pagina; }
    
    public int getTotalPaginas() { return totalPaginas; }
    public void setTotalPaginas(int totalPaginas) { this.totalPaginas = totalPaginas; }
    
    public int getTotalItens() { return totalItens; }
    public void setTotalItens(int totalItens) { this.totalItens = totalItens; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    @JsonProperty("info")
    private void setInfo(InfoModel info) {
        if (info == null) {
            return;
        }
        this.pagina = info.page;
        this.totalItens = info.totalRegisters;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class InfoModel {
        @JsonProperty("page")
        private int page;

        @JsonProperty("total_registers")
        private int totalRegisters;
    }
}

