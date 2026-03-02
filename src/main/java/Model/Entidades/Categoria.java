package Model.Entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAT_ID")
    private Long id;

    @Column(name = "CAT_TIPO", length = 45)
    private String tipo;

    @Column(name = "CAT_NOME", length = 45)
    private String nome;

    @Column(name = "CAT_DESCRICAO", length = 45)
    private String descricao;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Transacao> getTransacoes() { return transacoes; }
    public void setTransacoes(List<Transacao> transacoes) { this.transacoes = transacoes; }
}
