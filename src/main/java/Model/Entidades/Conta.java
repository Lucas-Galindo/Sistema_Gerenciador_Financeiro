import java.util.Collection;

public class Conta {

	private int CONTA_ID;

	private String CONTA_NOME;

	private int CONTA_SALDOINICIAL;

	private int TIPOCONTA_ID;

	private int BANDO_ID;

	private int USU_ID;

	private TipoConta tipoConta;

	private BANCOS bANCOS;

	private Collection<Transacao> transacao_Contas;

}
