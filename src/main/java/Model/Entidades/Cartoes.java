package Model.Entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Cartoes")
public class Cartoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARTOES_ID")
    private Long id;

    @Column(name = "CARTOES_NOME", length = 45)
    private String nome;

    @Column(name = "CARTOES_LIMITE", precision = 15, scale = 2)
    private BigDecimal limite;

    @Column(name = "CARTOES_FECHAMENTO")
    private LocalDate fechamento;

    @Column(name = "CARTOES_VENCIMENTO")
    private LocalDate vencimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_USU_ID", nullable = false)
    private Usuario usuario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getLimite() { return limite; }
    public void setLimite(BigDecimal limite) { this.limite = limite; }
    public LocalDate getFechamento() { return fechamento; }
    public void setFechamento(LocalDate fechamento) { this.fechamento = fechamento; }
    public LocalDate getVencimento() { return vencimento; }
    public void setVencimento(LocalDate vencimento) { this.vencimento = vencimento; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
