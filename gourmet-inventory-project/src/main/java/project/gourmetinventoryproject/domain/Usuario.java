package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nome;
    private String cargo;
    @Column(name = "email", unique = true)
    private String email;
    private String celular;
    private String senha;
    @ManyToOne
    private Empresa empresa;

}
