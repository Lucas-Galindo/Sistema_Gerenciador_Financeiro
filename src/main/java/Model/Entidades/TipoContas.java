package Model.Entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tipo_Contas")
public class TipoContas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIPOCONTAS_ID")
    private Long id;

    @Column(name = "TIPOCONTAS_NOME", length = 45)
    private String nome;

    @OneToMany(mappedBy = "tipoContas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contas> contas;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Contas> getContas() { return contas; }
    public void setContas(List<Contas> contas) { this.contas = contas; }
}
