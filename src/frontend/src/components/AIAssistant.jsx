import React, { useState, useRef, useEffect } from 'react';

const fmt = (v) =>
  new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v);

// Respostas simuladas do assistente
const botReplies = {
  default: "Analisei suas finanças recentes. Posso ajudar com orçamentos, análise de gastos, metas ou qualquer dúvida financeira.",
  gasto: `Os principais fatores foram três visitas ao **The Marble Arch Bistro** e um aumento nas taxas de serviços de delivery. Para manter o controle, sugiro:\n\n• Limitar apps de delivery a uma vez por semana (economia estimada: ${fmt(45)})\n• Transferir o "excedente de alimentação" de ${fmt(120)} diretamente para o seu Cofre de Metas hoje.`,
  meta: `Sua meta **Fundo de Emergência** está 87% completa — faltam apenas ${fmt(3200)}. Com sua taxa de poupança atual, você chegará lá em **6 semanas**. Deseja ativar depósitos automáticos semanais?`,
  transacao: `No último mês você teve **14 transações de saída** e **6 entradas**. O maior gasto foi em Tecnologia (${fmt(1299)}). Sua categoria com maior crescimento foi Alimentação, +15% vs. trimestre anterior.`,
  previsao: `Baseado no seu histórico, o próximo mês deve ter um fluxo líquido de aproximadamente **${fmt(11200)}** — ligeiramente abaixo deste mês por conta do fechamento da fatura do cartão de crédito no dia 10.`,
};

function getBotReply(msg) {
  const lower = msg.toLowerCase();
  if (lower.includes('gasto') || lower.includes('alimenta') || lower.includes('delivery') || lower.includes('volta')) return botReplies.gasto;
  if (lower.includes('meta') || lower.includes('emerg') || lower.includes('poupan')) return botReplies.meta;
  if (lower.includes('transac') || lower.includes('históri') || lower.includes('histórico')) return botReplies.transacao;
  if (lower.includes('previsao') || lower.includes('previsão') || lower.includes('próximo') || lower.includes('prox')) return botReplies.previsao;
  return botReplies.default;
}

const initialMessages = [
  {
    id: 1,
    role: 'assistant',
    text: 'Bom dia! Analisei suas transações dos últimos 7 dias. Percebi que você gastou **15% a mais em alimentação** esta semana do que sua média no último trimestre.',
    hasCard: true,
    card: { cat: 'Categoria Alimentação', value: 482.50, delta: '+15%', savings: 120 },
    time: 'HOJE, 24 OUT',
  },
];

const quickActions = [
  { icon: '🎯', label: 'Revisar progresso da meta' },
  { icon: '📊', label: 'Mostrar grandes transações' },
  { icon: '📈', label: 'Prever fluxo do próximo mês' },
];

// Formata markdown simples (**bold**)
function renderText(text) {
  return text.split('**').map((part, i) =>
    i % 2 === 1 ? <strong key={i}>{part}</strong> : part
  );
}

export default function AIAssistant() {
  const [messages, setMessages] = useState(initialMessages);
  const [input, setInput] = useState('');
  const [typing, setTyping] = useState(false);
  const endRef = useRef(null);

  useEffect(() => {
    endRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, typing]);

  function sendMessage(text) {
    if (!text.trim()) return;
    const userMsg = { id: Date.now(), role: 'user', text };
    setMessages((prev) => [...prev, userMsg]);
    setInput('');
    setTyping(true);

    setTimeout(() => {
      const reply = getBotReply(text);
      setMessages((prev) => [
        ...prev,
        { id: Date.now() + 1, role: 'assistant', text: reply },
      ]);
      setTyping(false);
    }, 1200);
  }

  return (
    <div className="page-enter" style={{ display: 'flex', flexDirection: 'column', height: 'calc(100vh - 64px)' }}>
      {/* Header */}
      <div className="page-header" style={{ marginBottom: 16 }}>
        <div>
          <div className="page-title">AI Insights</div>
        </div>
        <div className="search-box">
          <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="var(--bg-300)" strokeWidth="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
          <input className="search-input" placeholder="Pesquisar insights..." />
        </div>
      </div>

      {/* Chat area */}
      <div style={{ flex: 1, overflowY: 'auto', paddingBottom: 8 }}>
        {messages.map((msg, idx) => {
          const isUser = msg.role === 'user';
          return (
            <React.Fragment key={msg.id}>
              {msg.time && (
                <div className="chat-date-label" style={{ margin: '12px 0 16px' }}>{msg.time}</div>
              )}
              <div className={`chat-msg${isUser ? ' user' : ''}`} style={{ marginBottom: 14 }}>
                <div className={`chat-avatar${isUser ? ' user-av' : ''}`}>
                  {isUser ? 'AM' : '✦'}
                </div>
                <div>
                  <div className="chat-bubble">
                    <p style={{ margin: 0, lineHeight: 1.6 }}>{renderText(msg.text)}</p>

                    {/* Insight card */}
                    {msg.hasCard && (
                      <div className="chat-insight-card">
                        <div className="insight-metric">
                          <div className="insight-metric-label">{msg.card.cat}</div>
                          <div className="insight-metric-value">
                            {fmt(msg.card.value)}{' '}
                            <span className="delta">{msg.card.delta}</span>
                          </div>
                        </div>
                        <div className="insight-metric">
                          <div className="insight-metric-label">Economia Potencial</div>
                          <div className="insight-metric-value">
                            {fmt(msg.card.savings)}
                            <span className="delta pos"> /semana</span>
                          </div>
                        </div>
                      </div>
                    )}

                    {/* Action button for first message */}
                    {idx === 0 && (
                      <div style={{ marginTop: 12 }}>
                        <button
                          className="btn btn-primary"
                          style={{ fontSize: '.82rem', padding: '8px 16px' }}
                          onClick={() => sendMessage('Como posso voltar ao controle com minha meta de Imóvel?')}
                        >
                          Transferir {fmt(120)} para Cofre
                        </button>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </React.Fragment>
          );
        })}

        {/* Typing indicator */}
        {typing && (
          <div className="chat-msg" style={{ marginBottom: 14 }}>
            <div className="chat-avatar">✦</div>
            <div className="chat-bubble" style={{ padding: '14px 18px' }}>
              <div style={{ display: 'flex', gap: 5, alignItems: 'center' }}>
                {[0, 1, 2].map((i) => (
                  <div
                    key={i}
                    style={{
                      width: 7, height: 7, borderRadius: '50%',
                      background: 'var(--bg-300)',
                      animation: `bounce 1s infinite ${i * 0.18}s`,
                    }}
                  />
                ))}
                <style>{`
                  @keyframes bounce {
                    0%, 80%, 100% { transform: translateY(0); }
                    40% { transform: translateY(-6px); }
                  }
                `}</style>
              </div>
            </div>
          </div>
        )}
        <div ref={endRef} />
      </div>

      {/* Input area */}
      <div className="chat-input-area">
        {/* Quick action chips */}
        <div className="chat-actions-row">
          {quickActions.map((a) => (
            <button
              key={a.label}
              className="chat-action-chip"
              onClick={() => sendMessage(a.label)}
            >
              <span>{a.icon}</span>
              {a.label}
            </button>
          ))}
        </div>

        {/* Input row */}
        <div className="chat-input-row">
          <button style={{ background: 'none', border: 'none', color: 'var(--bg-300)', display: 'flex', padding: 0 }}>
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z"/></svg>
          </button>
          <input
            className="chat-input"
            placeholder="Pergunte qualquer coisa ao seu assistente Ledger..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && sendMessage(input)}
          />
          <button style={{ background: 'none', border: 'none', color: 'var(--bg-300)', display: 'flex', marginRight: 4 }}>
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M12 1a3 3 0 00-3 3v8a3 3 0 006 0V4a3 3 0 00-3-3z"/><path d="M19 10v2a7 7 0 01-14 0v-2M12 19v4M8 23h8"/></svg>
          </button>
          <button
            className="chat-send"
            onClick={() => sendMessage(input)}
            disabled={!input.trim() || typing}
          >
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5"><path d="M18 15l-6-6-6 6" transform="rotate(90 12 12)"/></svg>
          </button>
        </div>
      </div>
    </div>
  );
}
