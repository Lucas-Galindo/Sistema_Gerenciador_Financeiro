package Model.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Localizacao")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCAL_ID")
    private Long id;

    @Column(name = "LOCAL_CEP", length = 45)
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Estados_ESTADOS_ID")
    private Estados estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cidades_CIDADES_ID")
    private Cidades cidade;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_USU_ID")
    private Usuario usuario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public Estados getEstado() { return estado; }
    public void setEstado(Estados estado) { this.estado = estado; }
    public Cidades getCidade() { return cidade; }
    public void setCidade(Cidades cidade) { this.cidade = cidade; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
