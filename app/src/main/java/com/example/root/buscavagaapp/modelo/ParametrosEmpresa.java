package com.example.root.buscavagaapp.modelo;

public class ParametrosEmpresa {

    private int id_empresa;
    private char tipo_veiculo;
    private Double valor_meiahora;
    private Double valor_umahora;
    private Double valor_diaria;
    private Double valor_semana;
    private Double valor_mes;

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public char getTipo_veiculo() {
        return tipo_veiculo;
    }

    public void setTipo_veiculo(char tipo_veiculo) {
        this.tipo_veiculo = tipo_veiculo;
    }

    public Double getValor_meiahora() {
        return valor_meiahora;
    }

    public void setValor_meiahora(Double valor_meiahora) {
        this.valor_meiahora = valor_meiahora;
    }

    public Double getValor_umahora() {
        return valor_umahora;
    }

    public void setValor_umahora(Double valor_umahora) {
        this.valor_umahora = valor_umahora;
    }

    public Double getValor_diaria() {
        return valor_diaria;
    }

    public void setValor_diaria(Double valor_diaria) {
        this.valor_diaria = valor_diaria;
    }

    public Double getValor_semana() {
        return valor_semana;
    }

    public void setValor_semana(Double valor_semana) {
        this.valor_semana = valor_semana;
    }

    public Double getValor_mes() {
        return valor_mes;
    }

    public void setValor_mes(Double valor_mes) {
        this.valor_mes = valor_mes;
    }
}
