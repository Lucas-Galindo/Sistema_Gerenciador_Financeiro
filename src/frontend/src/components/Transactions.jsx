import React, { useEffect, useState } from 'react';
import { transacaoApi, mockData } from '../services/api';

const fmt = (v) =>
  new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v);

const categoryIcons = {
  'Tecnologia': '💻', 'Alimentação': '🍽️', 'Investimentos': '📈',
  'Utilidades': '⚡', 'Aluguel': '🏠', 'Salário': '💼',
  'Transporte': '🚗', 'Saúde': '💊', 'Lazer': '🎮', 'Compras': '🛍️',
};

const timeFilters = ['Mensal', 'Trimestral', 'Anual'];
const catFilters = ['Todas', 'Alimentação', 'Aluguel', 'Lazer', 'Transporte'];

function groupByDate(txs) {
  const groups = {};
  txs.forEach((tx) => {
    const key = tx.dataMovimentacao || 'Sem data';
    if (!groups[key]) groups[key] = [];
    groups[key].push(tx);
  });
  return Object.entries(groups).sort((a, b) => new Date(b[0]) - new Date(a[0]));
}

function formatDateLabel(dateStr) {
  if (!dateStr || dateStr === 'Sem data') return 'Sem data';
  const d = new Date(dateStr + 'T00:00:00');
  const today = new Date();
  const yesterday = new Date(today);
  yesterday.setDate(today.getDate() - 1);
  if (d.toDateString() === today.toDateString()) return 'HOJE';
  if (d.toDateString() === yesterday.toDateString()) return 'ONTEM';
  return d.toLocaleDateString('pt-BR', { day: '2-digit', month: 'long', year: 'numeric' }).toUpperCase();
}

export default function Transactions() {
  const [transacoes, setTransacoes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [timeFilter, setTimeFilter] = useState('Mensal');
  const [catFilter, setCatFilter] = useState('Todas');
  const [search, setSearch] = useState('');

  useEffect(() => {
    transacaoApi.getAll().then((data) => {
      setTransacoes(data || mockData.transacoes);
      setLoading(false);
    });
  }, []);

  const filtered = transacoes.filter((tx) => {
    const matchCat = catFilter === 'Todas' || tx.categoria?.nome === catFilter;
    const matchSearch = !search || tx.descricao?.toLowerCase().includes(search.toLowerCase());
    return matchCat && matchSearch;
  });

  const netFlow = filtered.reduce((acc, tx) => acc + Number(tx.valor), 0);
  const grouped = groupByDate(filtered);

  return (
    <div className="page-enter">
      {/* Top Bar */}
      <div className="top-bar">
        <div>
          <div className="page-title">Transações</div>
        </div>
        <div className="top-bar-right">
          <div className="search-box">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="var(--bg-300)" strokeWidth="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
            <input
              className="search-input"
              placeholder="Pesquisar atividades..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
          </div>
        </div>
      </div>

      <div className="grid-2" style={{ alignItems: 'start' }}>
        {/* Left column: filters + list */}
        <div style={{ gridColumn: '1 / 2' }}>
          <div className="card card-lg" style={{ marginBottom: 16 }}>
            {/* Time filter header */}
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
              <div>
                <div style={{ fontSize: '.72rem', textTransform: 'uppercase', letterSpacing: '1.5px', color: 'var(--text-200)', fontWeight: 600, marginBottom: 4 }}>
                  PERÍODO
                </div>
                <div style={{ fontFamily: 'var(--font-display)', fontWeight: 700, fontSize: '1.1rem' }}>
                  Outubro 2023 – Presente
                </div>
              </div>
              <div className="filter-tabs">
                {timeFilters.map((f) => (
                  <button
                    key={f}
                    className={`filter-tab${timeFilter === f ? ' active' : ''}`}
                    onClick={() => setTimeFilter(f)}
                  >
                    {f}
                  </button>
                ))}
              </div>
            </div>

            {/* Category tabs */}
            <div className="cat-tabs" style={{ marginBottom: 20 }}>
              {catFilters.map((c) => (
                <button
                  key={c}
                  className={`cat-tab${catFilter === c ? ' active' : ''}`}
                  onClick={() => setCatFilter(c)}
                >
                  {c !== 'Todas' && <span>{categoryIcons[c] || '•'}</span>}
                  {c}
                </button>
              ))}
              <button className="cat-tab">··· Mais</button>
            </div>

            {/* Transaction list */}
            <div style={{ fontSize: '.72rem', textTransform: 'uppercase', letterSpacing: '1.5px', color: 'var(--text-200)', fontWeight: 600, marginBottom: 6, display: 'flex', justifyContent: 'space-between' }}>
              <span>MASTER LEDGER</span>
              <button className="btn-ghost" style={{ display: 'flex', alignItems: 'center', gap: 5, padding: '3px 8px', fontSize: '.72rem' }}>
                <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M21 15v4a2 2 0 01-2 2H5a2 2 0 01-2-2v-4M7 10l5 5 5-5M12 15V3"/></svg>
                Exportar PDF
              </button>
            </div>

            {loading ? (
              Array.from({ length: 5 }).map((_, i) => (
                <div key={i} className="skeleton" style={{ height: 64, marginBottom: 8, borderRadius: 8 }} />
              ))
            ) : grouped.length === 0 ? (
              <div style={{ textAlign: 'center', padding: '40px 0', color: 'var(--text-200)', fontSize: '.88rem' }}>
                Nenhuma transação encontrada.
              </div>
            ) : (
              grouped.map(([date, txs]) => (
                <div key={date}>
                  <div className="tx-group-label">{formatDateLabel(date)}</div>
                  {txs.map((tx) => {
                    const isPos = Number(tx.valor) > 0;
                    return (
                      <div key={tx.id} className="tx-item">
                        <div className="tx-icon">
                          {categoryIcons[tx.categoria?.nome] || '💸'}
                        </div>
                        <div className="tx-info">
                          <div className="tx-name">{tx.descricao}</div>
                          <div className="tx-sub">
                            {tx.categoria?.nome || '—'} • {tx.tipoTransacao?.nome || '—'}
                          </div>
                        </div>
                        <div className="tx-right">
                          <div className={`tx-amount ${isPos ? 'amount-pos' : 'amount-neg'}`}>
                            {isPos ? '+' : ''}{fmt(tx.valor)}
                          </div>
                          <div className="tx-status">{tx.status || 'Processado'}</div>
                        </div>
                      </div>
                    );
                  })}
                </div>
              ))
            )}
          </div>
        </div>

        {/* Right column: net flow */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: 16 }}>
          <div className="net-flow-card">
            <div className="net-flow-label">FLUXO LÍQUIDO</div>
            <div className="net-flow-amount" style={{ color: netFlow >= 0 ? '#86efac' : '#fca5a5' }}>
              {netFlow >= 0 ? '+' : ''}{fmt(netFlow)}
            </div>
            <div className="net-flow-change">
              <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M18 15l-6-6-6 6"/></svg>
              14% de aumento no último trimestre
            </div>
          </div>

          {/* Summary */}
          <div className="card">
            <div className="section-title" style={{ marginBottom: 12 }}>Resumo do Período</div>
            {[
              { label: 'Total de Receitas', value: filtered.filter(t => Number(t.valor) > 0).reduce((a, t) => a + Number(t.valor), 0), pos: true },
              { label: 'Total de Despesas', value: filtered.filter(t => Number(t.valor) < 0).reduce((a, t) => a + Math.abs(Number(t.valor)), 0), pos: false },
              { label: 'Transações', value: null, count: filtered.length },
            ].map((item, i) => (
              <div key={i} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px 0', borderBottom: i < 2 ? '1px solid var(--bg-200)' : 'none' }}>
                <span style={{ fontSize: '.82rem', color: 'var(--text-200)' }}>{item.label}</span>
                {item.count !== undefined ? (
                  <span style={{ fontFamily: 'var(--font-display)', fontWeight: 700, fontSize: '1rem' }}>{item.count}</span>
                ) : (
                  <span style={{ fontFamily: 'var(--font-display)', fontWeight: 700, fontSize: '1rem', color: item.pos ? '#16a34a' : '#dc2626' }}>
                    {item.pos ? '+' : '-'}{fmt(item.value)}
                  </span>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
