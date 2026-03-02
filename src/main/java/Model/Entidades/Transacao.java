package Model.Entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANS_ID")
    private Long id;

    @Column(name = "TRANS_VALOR", precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "TRANS_DESCRICAO", length = 45)
    private String descricao;

    @Column(name = "TRANS_DATAMOVIMENTACAO", length = 45)
    private String dataMovimentacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Categoria_CAT_ID", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_USU_ID", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_TRANSACAO_TIPO_TRANSACAO_ID", nullable = false)
    private TipoTransacao tipoTransacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Contas_CONTAS_ID", nullable = false)
    private Contas conta;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDataMovimentacao() { return dataMovimentacao; }
    public void setDataMovimentacao(String dataMovimentacao) { this.dataMovimentacao = dataMovimentacao; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public TipoTransacao getTipoTransacao() { return tipoTransacao; }
    public void setTipoTransacao(TipoTransacao tipoTransacao) { this.tipoTransacao = tipoTransacao; }
    public Contas getConta() { return conta; }
    public void setConta(Contas conta) { this.conta = conta; }
}
