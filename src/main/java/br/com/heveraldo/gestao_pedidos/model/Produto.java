package br.com.heveraldo.gestao_pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sku;
    private double preco;
    private int estoque;

    private double pesoKg;
    private double volumeM3;
    
    private int unidadesPorPalete;
    private double pesoPorPaleteKg;
    private double volumePorPaleteM3;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    public int getUnidadesPorPalete() { 
        return unidadesPorPalete; }

    public void setUnidadesPorPalete(int unidadesPorPalete) { 
        this.unidadesPorPalete = unidadesPorPalete; }

    public double getPesoPorPaleteKg() { 
        return pesoPorPaleteKg; }

    public void setPesoPorPaleteKg(double pesoPorPaleteKg) { 
        this.pesoPorPaleteKg = pesoPorPaleteKg; }

    public double getVolumePorPaleteM3() { 
        return volumePorPaleteM3; }

    public void setVolumePorPaleteM3(double volumePorPaleteM3) { 
        this.volumePorPaleteM3 = volumePorPaleteM3; }

    public double getPesoKg() { 
        return pesoKg; }
        
    public void setPesoKg(double pesoKg) { 
        this.pesoKg = pesoKg; }

    public double getVolumeM3() { 
        return volumeM3; }

    public void setVolumeM3(double volumeM3) { 
        this.volumeM3 = volumeM3; }
}