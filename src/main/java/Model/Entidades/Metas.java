package Model.Entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Metas")
public class Metas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METAS_ID")
    private Long id;

    @Column(name = "METAS_NOME", length = 45)
    private String nome;

    @Column(name = "METAS_VALOR_OBJETIVO", precision = 15, scale = 2)
    private BigDecimal valorObjetivo;

    @Column(name = "METAS_VALOR_ATUAL", length = 45)
    private String valorAtual;

    @Column(name = "METAS_DATA_LIMITE", length = 45)
    private String dataLimite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_USU_ID", nullable = false)
    private Usuario usuario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getValorObjetivo() { return valorObjetivo; }
    public void setValorObjetivo(BigDecimal valorObjetivo) { this.valorObjetivo = valorObjetivo; }
    public String getValorAtual() { return valorAtual; }
    public void setValorAtual(String valorAtual) { this.valorAtual = valorAtual; }
    public String getDataLimite() { return dataLimite; }
    public void setDataLimite(String dataLimite) { this.dataLimite = dataLimite; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
