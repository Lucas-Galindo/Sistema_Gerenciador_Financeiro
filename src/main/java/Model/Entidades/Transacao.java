import java.util.Collection;

public class Transacao {

	private int TRANSACAO_ID;

	private Decimal TRANSACAO_VALOR_TRANSCAO;

	private int TRANSACAO_DESCRICAO;

	private Date TRANSACAO_DATAMOVIMENTO;

	private int CAT_ID;

	private int USU_ID;

	private int CONTA_ID;

	private int TIPOTRANS_ID;

	private Categoria categoria;

	private TipoTransacao tipoTransacao;

	private Usuario usuario;

	private Collection<Conta> transacao_Contas;

}
