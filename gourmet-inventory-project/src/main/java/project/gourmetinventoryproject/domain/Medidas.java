package project.gourmetinventoryproject.domain;

public enum Medidas {
    COLHER_DE_SOPA(15, "Colher de Sopa"),
    COLHER_DE_CHA(5, "Colher de Chá"),
    XICARA(240, "Xícara"),
    GRAMAS(1, "Gramas"),
    QUILOGRAMA(1000, "Quilograma"),
    MILILITROS(1, "Mililitros"),
    LITRO(1000, "Litro"),
    A_GOSTO(0, "A Gosto"),
    PITADA(0.5, "Pitada"),
    UNIDADE(0, "Unidade");

    private final double valorEmGramas;
    private final String nomeLegivel;

    Medidas(double valorEmGramas, String nomeLegivel) {
        this.valorEmGramas = valorEmGramas;
        this.nomeLegivel = nomeLegivel;
    }

    public double getValorEmGramas() {
        return valorEmGramas;
    }

    public String getNomeLegivel() {
        return nomeLegivel;
    }
}
