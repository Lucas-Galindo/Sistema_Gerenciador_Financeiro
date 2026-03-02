package Model.Entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "TIPO_TRANSACAO")
public class TipoTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIPO_TRANSACAO_ID")
    private Long id;

    @Column(name = "TIPO_TRANSACAO_NOME", length = 45)
    private String nome;

    @Column(name = "TIPO_TRANSACAO_DESCRICAO", length = 45)
    private String descricao;

    @OneToMany(mappedBy = "tipoTransacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Transacao> getTransacoes() { return transacoes; }
    public void setTransacoes(List<Transacao> transacoes) { this.transacoes = transacoes; }
}
