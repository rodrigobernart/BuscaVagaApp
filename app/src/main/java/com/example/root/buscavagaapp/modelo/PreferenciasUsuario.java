package com.example.root.buscavagaapp.modelo;

public class PreferenciasUsuario {

    private boolean moto;
    private boolean carro;
    private boolean valor_meiahora;
    private boolean valor_umahora;
    private boolean valor_diaria;
    private boolean valor_semanal;
    private boolean valor_mensal;

    public boolean isMoto() {
        return moto;
    }

    public void setMoto(boolean moto) {
        this.moto = moto;
    }

    public boolean isCarro() {
        return carro;
    }

    public void setCarro(boolean carro) {
        this.carro = carro;
    }

    public boolean isValor_meiahora() {
        return valor_meiahora;
    }

    public void setValor_meiahora(boolean valor_meiahora) {
        this.valor_meiahora = valor_meiahora;
    }

    public boolean isValor_umahora() {
        return valor_umahora;
    }

    public void setValor_umahora(boolean valor_umahora) {
        this.valor_umahora = valor_umahora;
    }

    public boolean isValor_diaria() {
        return valor_diaria;
    }

    public void setValor_diaria(boolean valor_diaria) {
        this.valor_diaria = valor_diaria;
    }

    public boolean isValor_semanal() {
        return valor_semanal;
    }

    public void setValor_semanal(boolean valor_semanal) {
        this.valor_semanal = valor_semanal;
    }

    public boolean isValor_mensal() {
        return valor_mensal;
    }

    public void setValor_mensal(boolean valor_mensal) {
        this.valor_mensal = valor_mensal;
    }
}
