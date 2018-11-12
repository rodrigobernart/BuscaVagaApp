package com.example.root.buscavagaapp.modelo;

public class DadosEmpresas {
    private String nome_empresa;
    private String telefone_fixo;
    private String telefone_cel;
    private Double latitude;
    private Double longitude;


    public String getNome_empresa() {
        return nome_empresa;
    }

    public void setNome_empresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "DadosEmpresas{" +
                "nome_empresa='" + nome_empresa + '\'' +
                ", telefone_fixo='" + telefone_fixo + '\'' +
                ", telefone_cel='" + telefone_cel + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
