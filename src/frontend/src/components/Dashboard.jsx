import React, { useEffect, useState } from 'react';
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer
} from 'recharts';
import { transacaoApi, contasApi, mockData } from '../services/api';

// ---- helpers ------------------------------------------------
const fmt = (v) =>
  new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v);

const categoryIcons = {
  'Tecnologia': '💻', 'Alimentação': '🍽️', 'Investimentos': '📈',
  'Utilidades': '⚡', 'Aluguel': '🏠', 'Salário': '💼',
  'Transporte': '🚗', 'Saúde': '💊', 'Lazer': '🎮',
};

const accountIcons = {
  'Corrente': '🏦', 'Crédito': '💳', 'Poupança': '💰', 'Corretora': '📊',
};

const chartData = [
  { mes: 'MAR', receita: 9200, despesa: 7100 },
  { mes: 'ABR', receita: 10500, despesa: 8200 },
  { mes: 'MAI', receita: 11000, despesa: 9500 },
  { mes: 'JUN', receita: 11800, despesa: 8900 },
  { mes: 'JUL', receita: 12400, despesa: 9100 },
];

// Tooltip customizado para o gráfico
const CustomTooltip = ({ active, payload, label }) => {
  if (!active || !payload?.length) return null;
  return (
    <div className="card card-sm" style={{ minWidth: 140 }}>
      <div style={{ fontWeight: 700, marginBottom: 6 }}>{label}</div>
      {payload.map((p) => (
        <div key={p.name} style={{ fontSize: '.8rem', color: p.color, marginBottom: 2 }}>
          {p.name === 'receita' ? 'Receita' : 'Despesa'}: {fmt(p.value)}
        </div>
      ))}
    </div>
  );
};

export default function Dashboard() {
  const [transacoes, setTransacoes] = useState([]);
  const [contas, setContas] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function load() {
      const [tx, ct] = await Promise.all([
        transacaoApi.getAll(),
        contasApi.getAll(),
      ]);
      setTransacoes(tx || mockData.transacoes);
      setContas(ct || mockData.contas);
      setLoading(false);
    }
    load();
  }, []);

  const totalSaldo = contas.reduce((acc, c) => acc + Number(c.saldoInicial || 0), 0);
  const totalReceitas = transacoes.filter(t => Number(t.valor) > 0).reduce((a, t) => a + Number(t.valor), 0);
  const totalDespesas = transacoes.filter(t => Number(t.valor) < 0).reduce((a, t) => a + Math.abs(Number(t.valor)), 0);

  const recentes = [...transacoes]
    .sort((a, b) => new Date(b.dataMovimentacao) - new Date(a.dataMovimentacao))
    .slice(0, 5);

  return (
    <div className="page-enter">
      {/* Top Bar */}
      <div className="top-bar">
        <div>
          <div className="page-title">Dashboard</div>
        </div>
        <div className="top-bar-right">
          <div className="search-box">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="var(--bg-300)" strokeWidth="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
            <input className="search-input" placeholder="Pesquisar dados..." />
          </div>
          <button className="icon-btn">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M18 8A6 6 0 006 8c0 7-3 9-3 9h18s-3-2-3-9M13.73 21a2 2 0 01-3.46 0"/></svg>
          </button>
          <div className="user-avatar" style={{ width: 36, height: 36 }}>AM</div>
        </div>
      </div>

      {/* Hero + AI widget */}
      <div className="grid-2" style={{ marginBottom: 20 }}>
        {/* Hero Card */}
        <div className="hero-card" style={{ gridColumn: '1 / 2' }}>
          <div className="hero-label">Liquidez Total</div>
          {loading ? (
            <div className="skeleton" style={{ height: 52, width: '60%', marginBottom: 8, borderRadius: 8 }} />
          ) : (
            <div className="hero-amount">{fmt(totalSaldo)}</div>
          )}
          <div className="hero-change">
            <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5"><path d="M18 15l-6-6-6 6"/></svg>
            +12,4% vs mês anterior
          </div>
          <div className="hero-stats">
            <div>
              <div className="hero-stat-label">Receita Mensal</div>
              <div className="hero-stat-value">{fmt(totalReceitas || 12400)}</div>
            </div>
            <div>
              <div className="hero-stat-label">Total Poupado</div>
              <div className="hero-stat-value">{fmt(contas.find(c => c.tipoContas?.nome === 'Poupança')?.saldoInicial || 89200)}</div>
            </div>
            <div>
              <div className="hero-stat-label">Investimentos</div>
              <div className="hero-stat-value">{fmt(contas.find(c => c.tipoContas?.nome === 'Corretora')?.saldoInicial || 41250)}</div>
            </div>
          </div>
        </div>

        {/* AI Assistant Widget */}
        <div className="card" style={{ display: 'flex', flexDirection: 'column', gap: 10 }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 4 }}>
            <div style={{
              width: 30, height: 30, borderRadius: '50%', background: 'var(--accent-200)',
              display: 'flex', alignItems: 'center', justifyContent: 'center'
            }}>
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="#fff" strokeWidth="2"><path d="M12 2a10 10 0 110 20A10 10 0 0112 2z"/><path d="M12 6v6l4 2"/></svg>
            </div>
            <span style={{ fontWeight: 700, fontSize: '.88rem', color: 'var(--accent-200)' }}>✦ Assistente IA</span>
          </div>
          <p style={{ fontSize: '.85rem', color: 'var(--text-200)', lineHeight: 1.55 }}>
            "Você gastou <strong style={{ color: 'var(--text-100)' }}>{fmt(420)} a mais</strong> em alimentação este mês comparado a julho. Deseja ajustar seu orçamento?"
          </p>
          <button className="btn btn-secondary" style={{ width: '100%', justifyContent: 'center', marginTop: 4 }}>
            Analisar Gastos
          </button>
          <button className="btn-ghost" style={{ background: 'none', border: 'none', fontSize: '.8rem', color: 'var(--accent-200)', fontWeight: 600, padding: '4px 0' }}>
            Perguntar ao Ledger IA →
          </button>
        </div>
      </div>

      {/* Chart + Recent Activity */}
      <div className="grid-2" style={{ marginBottom: 20 }}>
        {/* Chart */}
        <div className="card card-lg">
          <div className="section-title">
            Trajetória Financeira
            <span style={{ fontSize: '.72rem', color: 'var(--text-200)', fontWeight: 400, fontFamily: 'var(--font-body)' }}>
              Receitas vs Despesas mensais
            </span>
          </div>
          <ResponsiveContainer width="100%" height={220}>
            <BarChart data={chartData} barCategoryGap="30%" barGap={4}>
              <CartesianGrid vertical={false} stroke="var(--bg-200)" />
              <XAxis dataKey="mes" axisLine={false} tickLine={false} tick={{ fontSize: 11, fill: 'var(--text-200)' }} />
              <YAxis hide />
              <Tooltip content={<CustomTooltip />} cursor={{ fill: 'var(--bg-200)', radius: 6 }} />
              <Bar dataKey="receita" fill="var(--accent-200)" radius={[5, 5, 0, 0]} />
              <Bar dataKey="despesa" fill="var(--primary-100)" radius={[5, 5, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
          <div style={{ display: 'flex', gap: 16, marginTop: 8 }}>
            <span style={{ display: 'flex', alignItems: 'center', gap: 5, fontSize: '.75rem', color: 'var(--text-200)' }}>
              <span style={{ width: 10, height: 10, borderRadius: 2, background: 'var(--accent-200)', display: 'inline-block' }} />
              Receita
            </span>
            <span style={{ display: 'flex', alignItems: 'center', gap: 5, fontSize: '.75rem', color: 'var(--text-200)' }}>
              <span style={{ width: 10, height: 10, borderRadius: 2, background: 'var(--primary-100)', border: '1.5px solid var(--bg-300)', display: 'inline-block' }} />
              Despesa
            </span>
          </div>
        </div>

        {/* Recent Activity */}
        <div className="card card-lg">
          <div className="section-title">
            Atividade Recente
            <button className="btn-ghost" style={{ fontSize: '.78rem', padding: '4px 8px' }}>Ver tudo</button>
          </div>
          {loading ? (
            Array.from({ length: 4 }).map((_, i) => (
              <div key={i} className="skeleton" style={{ height: 52, marginBottom: 8 }} />
            ))
          ) : (
            recentes.map((tx) => {
              const isPos = Number(tx.valor) > 0;
              const icon = categoryIcons[tx.categoria?.nome] || '💸';
              return (
                <div key={tx.id} className="activity-item">
                  <div className="activity-icon" style={{ background: isPos ? '#dcfce7' : 'var(--bg-200)' }}>
                    {icon}
                  </div>
                  <div className="activity-meta">
                    <div className="activity-name">{tx.descricao}</div>
                    <div className="activity-sub">
                      {tx.categoria?.nome || '—'} • {tx.dataMovimentacao}
                    </div>
                  </div>
                  <div className={`activity-amount ${isPos ? 'amount-pos' : 'amount-neg'}`}>
                    {isPos ? '+' : ''}{fmt(tx.valor)}
                  </div>
                </div>
              );
            })
          )}
        </div>
      </div>

      {/* Financial Artifacts (accounts) */}
      <div className="section-title" style={{ marginBottom: 14 }}>
        Contas Financeiras
        <button className="btn btn-outline" style={{ fontSize: '.78rem', padding: '6px 14px' }}>
          + Vincular conta
        </button>
      </div>
      <div className="grid-4">
        {(loading ? mockData.contas : contas).map((c) => {
          const icon = accountIcons[c.tipoContas?.nome] || '🏦';
          const isCredit = c.tipoContas?.nome === 'Crédito';
          const pct = isCredit ? Math.min((c.saldoInicial / 15000) * 100, 100) : null;
          return (
            <div key={c.id} className="artifact-card">
              <div className="artifact-type">{c.tipoContas?.nome || 'Conta'}</div>
              <div style={{ fontSize: '1.5rem', marginBottom: 6 }}>{icon}</div>
              <div className="artifact-value">{fmt(c.saldoInicial)}</div>
              <div className="artifact-name">{c.nome}</div>
              {isCredit && (
                <>
                  <div className="artifact-bar">
                    <div className="artifact-bar-fill" style={{ width: `${pct}%` }} />
                  </div>
                  <div className="artifact-meta">
                    <span>Usado</span>
                    <span>R$ 15.000 TOTAL</span>
                  </div>
                </>
              )}
              {!isCredit && (
                <div style={{ fontSize: '.72rem', color: 'var(--text-200)', marginTop: 4 }}>
                  {c.banco?.nome || '—'}
                </div>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}
