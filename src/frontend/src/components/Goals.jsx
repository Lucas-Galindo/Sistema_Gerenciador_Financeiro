import React, { useEffect, useState } from 'react';
import { metasApi, mockData } from '../services/api';

const fmt = (v) =>
  new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(Number(v));

const goalVisuals = {
  emergency: { emoji: '🛡️', bg: 'linear-gradient(135deg, #374151, #1f2937)', label: 'Reserva de Emergência' },
  car:       { emoji: '🚗', bg: 'linear-gradient(135deg, #1e3a5f, #2d5986)', label: 'Fundo para Veículo' },
  house:     { emoji: '🏠', bg: 'linear-gradient(135deg, #312e81, #4338ca)', label: 'Fundo para Imóvel' },
};

function getVisual(meta) {
  const name = (meta.nome || '').toLowerCase();
  if (name.includes('emergenc') || name.includes('reserva')) return goalVisuals.emergency;
  if (name.includes('car') || name.includes('carro') || name.includes('veic') || name.includes('moto')) return goalVisuals.car;
  if (name.includes('house') || name.includes('casa') || name.includes('imov') || name.includes('aparta')) return goalVisuals.house;
  return { emoji: '🎯', bg: 'linear-gradient(135deg, var(--accent-200), #004d6b)', label: 'Meta' };
}

function getHeroMeta(metas) {
  // A meta mais próxima de 100%
  return metas.reduce((best, m) => {
    const pct = (Number(m.valorAtual) / Number(m.valorObjetivo)) * 100;
    const bestPct = (Number(best.valorAtual) / Number(best.valorObjetivo)) * 100;
    return pct > bestPct ? m : best;
  }, metas[0]);
}

export default function Goals() {
  const [metas, setMetas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [totalSaved, setTotalSaved] = useState(0);

  useEffect(() => {
    metasApi.getAll().then((data) => {
      const list = data || mockData.metas;
      setMetas(list);
      setTotalSaved(list.reduce((a, m) => a + Number(m.valorAtual || 0), 0));
      setLoading(false);
    });
  }, []);

  if (loading) {
    return (
      <div className="page-enter">
        <div className="page-title" style={{ marginBottom: 24 }}>Metas</div>
        <div className="skeleton" style={{ height: 220, marginBottom: 20, borderRadius: 20 }} />
        <div className="grid-3">
          {[1, 2, 3].map(i => <div key={i} className="skeleton" style={{ height: 300, borderRadius: 14 }} />)}
        </div>
      </div>
    );
  }

  const hero = metas.length > 0 ? getHeroMeta(metas) : null;
  const heroPct = hero ? Math.round((Number(hero.valorAtual) / Number(hero.valorObjetivo)) * 100) : 0;
  const remaining = hero ? Number(hero.valorObjetivo) - Number(hero.valorAtual) : 0;

  return (
    <div className="page-enter">
      {/* Page header with total */}
      <div className="page-header">
        <div>
          <div className="page-title">Metas de Investimento</div>
          <div className="page-subtitle">Arquitetura para suas aspirações futuras.</div>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: 8, background: 'var(--bg-100)', padding: '10px 16px', borderRadius: 'var(--radius-sm)', boxShadow: 'var(--shadow-sm)' }}>
          <span style={{ fontSize: '1.1rem' }}>💰</span>
          <div>
            <div style={{ fontFamily: 'var(--font-display)', fontWeight: 700, fontSize: '1.1rem' }}>
              {fmt(totalSaved)}
            </div>
            <div style={{ fontSize: '.7rem', color: 'var(--text-200)', textTransform: 'uppercase', letterSpacing: '1px' }}>
              Total Poupado
            </div>
          </div>
        </div>
      </div>

      {/* Hero + AI Insight */}
      {hero && (
        <div className="grid-2" style={{ marginBottom: 24 }}>
          <div className="goal-hero">
            <div className="milestone-badge">
              ✦ MARCO ATINGIDO
            </div>
            <h2>Sua {hero.nome} está<br />{heroPct}% completa.</h2>
            <p>
              Faltam apenas {fmt(remaining)} para atingir sua meta de {fmt(hero.valorObjetivo)}.
              Continue assim!
            </p>
            <div className="goal-hero-actions">
              <button className="btn btn-white">Depósito Rápido</button>
              <button className="btn btn-glass">Ver Breakdown</button>
            </div>
          </div>

          <div className="ai-insight-box">
            <div className="ai-insight-label">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M12 2a10 10 0 110 20A10 10 0 0112 2z"/><path d="M12 6v6l4 2"/></svg>
              AI INSIGHTS
            </div>
            <p className="ai-insight-text">
              Com base nos seus gastos atuais, você pode atingir sua meta de{' '}
              <strong style={{ color: 'var(--text-100)' }}>
                {metas.find(m => m.nome.toLowerCase().includes('car') || m.nome.toLowerCase().includes('carro'))?.nome || metas[1]?.nome || 'Veículo'}
              </strong>{' '}
              2 meses mais cedo arredondando suas transações para o inteiro mais próximo.
            </p>
            <button className="btn btn-primary" style={{ marginTop: 14, width: '100%', justifyContent: 'center', fontSize: '.82rem' }}>
              Ativar Smart Round-ups
            </button>
          </div>
        </div>
      )}

      {/* Goals grid */}
      <div className="grid-3">
        {metas.map((meta) => {
          const pct = Math.min(Math.round((Number(meta.valorAtual) / Number(meta.valorObjetivo)) * 100), 100);
          const vis = getVisual(meta);
          return (
            <div key={meta.id} className="goal-card">
              <div className="goal-card-image" style={{ background: vis.bg }}>
                <span style={{ fontSize: '3rem' }}>{vis.emoji}</span>
              </div>
              <div className="goal-card-body">
                <div className="goal-card-name">{meta.nome}</div>
                <div className="goal-card-type">{vis.label}</div>

                <div className="goal-amounts">
                  <div className="goal-current">{fmt(meta.valorAtual)}</div>
                  <div className="goal-target">de {fmt(meta.valorObjetivo)}</div>
                </div>

                <div className="progress-bar">
                  <div className="progress-fill" style={{ width: `${pct}%` }} />
                </div>

                <div className="goal-meta">
                  <span>
                    {meta.dataLimite
                      ? `META: ${new Date(meta.dataLimite + 'T00:00:00').toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' }).toUpperCase()}`
                      : 'SEM PRAZO'}
                  </span>
                  <span className="badge badge-blue">{pct}%</span>
                </div>

                <button className="btn btn-topup">
                  <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5"><path d="M12 5v14M5 12h14"/></svg>
                  Adicionar Valor
                </button>
              </div>
            </div>
          );
        })}

        {/* Create new goal */}
        <div className="create-goal-card">
          <div className="plus">+</div>
          <div style={{ fontWeight: 700, fontSize: '.95rem', color: 'var(--text-100)', marginBottom: 4 }}>
            Criar Nova Meta
          </div>
          <div style={{ fontSize: '.8rem', color: 'var(--text-200)', textAlign: 'center' }}>
            Defina seu próximo marco financeiro
          </div>
        </div>
      </div>
    </div>
  );
}
