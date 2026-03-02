package Model.Entidades;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Cidades")
public class Cidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CIDADES_ID")
    private Long id;

    @Column(name = "CIDADES_NOME", length = 45)
    private String nome;

    @OneToMany(mappedBy = "cidade", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Localizacao> localizacoes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Localizacao> getLocalizacoes() { return localizacoes; }
    public void setLocalizacoes(List<Localizacao> localizacoes) { this.localizacoes = localizacoes; }
}
