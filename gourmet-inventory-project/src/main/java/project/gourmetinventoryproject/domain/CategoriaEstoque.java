package project.gourmetinventoryproject.domain;

public enum CategoriaEstoque {
        VEGETAIS_FRESCOS("Vegetais Frescos", 10.0),
        FRUTAS_FRESCAS("Frutas Frescas", 15.0),
        LEGUMES("Legumes", 8.0),
        CARNES_VERMELHAS("Carnes Vermelhas", 12.0),
        AVES("Aves", 10.0),
        PEIXES_E_FRUTOS_DO_MAR("Peixes e Frutos do Mar", 25.0),
        GRAOS_E_CEREAIS("Grãos e Cereais", 5.0),
        LATICINIOS_E_DERIVADOS("Laticínios e Derivados", 20.0),
        OVOS("Ovos", 5.0),
        OLEAGINOSAS("Oleaginosas (Nozes, Castanhas, Amêndoas)", 3.0),
        LEGUMINOSAS_SECAS("Leguminosas Secas", 2.0),
        ERVAS_FRESCAS("Ervas Frescas", 20.0),
        ESPECIARIAS_SECAS("Especiarias Secas", 1.0),
        OLEOS_E_GORDURAS("Óleos e Gorduras", 2.0),
        MASSAS("Massas", 4.0),
        ENLATADOS_E_CONSERVAS("Enlatados e Conservas", 1.0),
        BEBIDAS("Bebidas", 2.0),
        RAIZES_E_TUBERCULOS("Raízes e Tubérculos", 10.0),
        CONGELADOS("Congelados", 6.0),
        DOCES_E_SOBREMESAS("Doces e Sobremesas", 3.0),
        MOLHOS_E_CONDIMENTOS("Molhos e Condimentos", 1.0),
        PRODUTOS_INDUSTRIALIZADOS("Produtos Industrializados", 2.0),
        FARINHAS_E_AMIDOS("Farinhas e Amidos", 3.0),
        PAES_E_BOLOS("Pães e Bolos", 10.0),
        QUEIJOS_E_FRIOS("Queijos e Frios", 15.0),
        ACUCARES_E_ADOCANTES("Açúcares e Adoçantes", 1.0),
        SUPLEMENTOS_E_INGREDIENTES_ESPECIAIS("Suplementos e Ingredientes Especiais", 5.0),
        PRODUTOS_DE_PADARIA_E_CONFEITARIA("Produtos de Padaria e Confeitaria", 8.0),
        FERMENTOS_E_LEVEDURAS("Fermentos e Leveduras", 2.0),
        INGREDIENTES_PARA_BEBIDAS("Ingredientes para Bebidas", 2.0),
        OUTROS("Outros", 5.0);

        private final String nomeExibicao;
        private final double porcentagemPerda;

        CategoriaEstoque(String nomeExibicao, double porcentagemPerda) {
            this.nomeExibicao = nomeExibicao;
            this.porcentagemPerda = porcentagemPerda;
        }

        public String getNomeExibicao() {
            return nomeExibicao;
        }

        public double getPorcentagemPerda() {
            return porcentagemPerda;
        }
    }

