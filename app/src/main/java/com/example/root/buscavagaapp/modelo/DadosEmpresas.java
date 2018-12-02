package com.example.root.buscavagaapp.modelo;

import java.util.Date;

public class DadosEmpresas {

    private int id_empresa;
    private String nome_empresa;
    private String endereco;
    private String telefone_fixo;
    private String telefone_cel;
    private Double latitude;
    private Double longitude;
    private boolean carro;
    private Double valor_meiahora_c;
    private Double valor_umahora_c;
    private Double valor_diaria_c;
    private Double valor_semana_c;
    private Double valor_mes_c;
    private int qtd_cobertas_c;
    private int qtd_descobertas_c;
    private boolean moto;
    private Double valor_meiahora_m;
    private Double valor_umahora_m;
    private Double valor_diaria_m;
    private Double valor_semana_m;
    private Double valor_mes_m;
    private int qtd_cobertas_m;
    private int qtd_descobertas_m;
    private Date hr_seg_sex;
    private Date hr_seg_sex_fim;
    private Date hr_sabado;
    private Date hr_sabado_fim;
    private Date hr_dom_fer;
    private Date hr_dom_fer_fim;


    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNome_empresa() {
        return nome_empresa;
    }

    public void setNome_empresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone_fixo() {
        return telefone_fixo;
    }

    public void setTelefone_fixo(String telefone_fixo) {
        this.telefone_fixo = telefone_fixo;
    }

    public String getTelefone_cel() {
        return telefone_cel;
    }

    public void setTelefone_cel(String telefone_cel) {
        this.telefone_cel = telefone_cel;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isCarro() {
        return carro;
    }

    public void setCarro(boolean carro) {
        this.carro = carro;
    }

    public Double getValor_meiahora_c() {
        return valor_meiahora_c;
    }

    public void setValor_meiahora_c(Double valor_meiahora_c) {
        this.valor_meiahora_c = valor_meiahora_c;
    }

    public Double getValor_umahora_c() {
        return valor_umahora_c;
    }

    public void setValor_umahora_c(Double valor_umahora_c) {
        this.valor_umahora_c = valor_umahora_c;
    }

    public Double getValor_diaria_c() {
        return valor_diaria_c;
    }

    public void setValor_diaria_c(Double valor_diaria_c) {
        this.valor_diaria_c = valor_diaria_c;
    }

    public Double getValor_semana_c() {
        return valor_semana_c;
    }

    public void setValor_semana_c(Double valor_semana_c) {
        this.valor_semana_c = valor_semana_c;
    }

    public Double getValor_mes_c() {
        return valor_mes_c;
    }

    public void setValor_mes_c(Double valor_mes_c) {
        this.valor_mes_c = valor_mes_c;
    }

    public int getQtd_cobertas_c() {
        return qtd_cobertas_c;
    }

    public void setQtd_cobertas_c(int qtd_cobertas_c) {
        this.qtd_cobertas_c = qtd_cobertas_c;
    }

    public int getQtd_descobertas_c() {
        return qtd_descobertas_c;
    }

    public void setQtd_descobertas_c(int qtd_descobertas_c) {
        this.qtd_descobertas_c = qtd_descobertas_c;
    }

    public boolean isMoto() {
        return moto;
    }

    public void setMoto(boolean moto) {
        this.moto = moto;
    }

    public Double getValor_meiahora_m() {
        return valor_meiahora_m;
    }

    public void setValor_meiahora_m(Double valor_meiahora_m) {
        this.valor_meiahora_m = valor_meiahora_m;
    }

    public Double getValor_umahora_m() {
        return valor_umahora_m;
    }

    public void setValor_umahora_m(Double valor_umahora_m) {
        this.valor_umahora_m = valor_umahora_m;
    }

    public Double getValor_diaria_m() {
        return valor_diaria_m;
    }

    public void setValor_diaria_m(Double valor_diaria_m) {
        this.valor_diaria_m = valor_diaria_m;
    }

    public Double getValor_semana_m() {
        return valor_semana_m;
    }

    public void setValor_semana_m(Double valor_semana_m) {
        this.valor_semana_m = valor_semana_m;
    }

    public Double getValor_mes_m() {
        return valor_mes_m;
    }

    public void setValor_mes_m(Double valor_mes_m) {
        this.valor_mes_m = valor_mes_m;
    }

    public int getQtd_cobertas_m() {
        return qtd_cobertas_m;
    }

    public void setQtd_cobertas_m(int qtd_cobertas_m) {
        this.qtd_cobertas_m = qtd_cobertas_m;
    }

    public int getQtd_descobertas_m() {
        return qtd_descobertas_m;
    }

    public void setQtd_descobertas_m(int qtd_descobertas_m) {
        this.qtd_descobertas_m = qtd_descobertas_m;
    }

    public Date getHr_seg_sex() {
        return hr_seg_sex;
    }

    public void setHr_seg_sex(Date hr_seg_sex) {
        this.hr_seg_sex = hr_seg_sex;
    }

    public Date getHr_seg_sex_fim() {
        return hr_seg_sex_fim;
    }

    public void setHr_seg_sex_fim(Date hr_seg_sex_fim) {
        this.hr_seg_sex_fim = hr_seg_sex_fim;
    }

    public Date getHr_sabado() {
        return hr_sabado;
    }

    public void setHr_sabado(Date hr_sabado) {
        this.hr_sabado = hr_sabado;
    }

    public Date getHr_sabado_fim() {
        return hr_sabado_fim;
    }

    public void setHr_sabado_fim(Date hr_sabado_fim) {
        this.hr_sabado_fim = hr_sabado_fim;
    }

    public Date getHr_dom_fer() {
        return hr_dom_fer;
    }

    public void setHr_dom_fer(Date hr_dom_fer) {
        this.hr_dom_fer = hr_dom_fer;
    }

    public Date getHr_dom_fer_fim() {
        return hr_dom_fer_fim;
    }

    public void setHr_dom_fer_fim(Date hr_dom_fer_fim) {
        this.hr_dom_fer_fim = hr_dom_fer_fim;
    }
}
