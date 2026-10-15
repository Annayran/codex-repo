package br.com.crmbonus.sales.dto.request;

public class SalesTotalRequestDTO {

    private String internal_order_id;
    private String invoice;
    private CustomerDTO customer;
    private TotalsDTO totals;

    public SalesTotalRequestDTO() {
    }

    public SalesTotalRequestDTO(String internal_order_id, String invoice, CustomerDTO customer, TotalsDTO totals) {
        this.internal_order_id = internal_order_id;
        this.invoice = invoice;
        this.customer = customer;
        this.totals = totals;
    }

    public String getInternal_order_id() {
        return internal_order_id;
    }

    public void setInternal_order_id(String internal_order_id) {
        this.internal_order_id = internal_order_id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public TotalsDTO getTotals() {
        return totals;
    }

    public void setTotals(TotalsDTO totals) {
        this.totals = totals;
    }

    public static class CustomerDTO {
        private String document_number;

        public CustomerDTO() {
        }

        public CustomerDTO(String document_number) {
            this.document_number = document_number;
        }

        public String getDocument_number() {
            return document_number;
        }

        public void setDocument_number(String document_number) {
            this.document_number = document_number;
        }
    }

    public static class TotalsDTO {
        private double total;

        public TotalsDTO() {
        }

        public TotalsDTO(double total) {
            this.total = total;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    }
}

