package Model.Entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Contas")
public class Contas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTAS_ID")
    private Long id;

    @Column(name = "CONTAS_NOME", length = 45)
    private String nome;

    @Column(name = "CONTAS_SALDO_INICIAL", precision = 15, scale = 2)
    private BigDecimal saldoInicial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_USU_ID", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANCOS_BANCOS_ID", nullable = false)
    private Bancos banco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tipo_Contas_TIPOCONTAS_ID", nullable = false)
    private TipoContas tipoContas;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Bancos getBanco() { return banco; }
    public void setBanco(Bancos banco) { this.banco = banco; }
    public TipoContas getTipoContas() { return tipoContas; }
    public void setTipoContas(TipoContas tipoContas) { this.tipoContas = tipoContas; }
    public List<Transacao> getTransacoes() { return transacoes; }
    public void setTransacoes(List<Transacao> transacoes) { this.transacoes = transacoes; }
}
