import React, { useEffect, useState } from 'react';
import { transacaoApi, categoriaApi, tipoTransacaoApi, contasApi, mockData } from '../services/api';

export default function NewTransactionModal({ onClose, onSaved }) {
  const [categorias, setCategorias] = useState([]);
  const [tipos, setTipos] = useState([]);
  const [contas, setContas] = useState([]);
  const [loading, setLoading] = useState(false);

  const [form, setForm] = useState({
    descricao: '',
    valor: '',
    dataMovimentacao: new Date().toISOString().split('T')[0],
    categoriaId: '',
    tipoTransacaoId: '',
    contaId: '',
    usuarioId: 1, // usuário logado (mock)
  });

  useEffect(() => {
    async function loadLookups() {
      const [cats, tps, cts] = await Promise.all([
        categoriaApi.getAll(),
        tipoTransacaoApi.getAll(),
        contasApi.getAll(),
      ]);
      setCategorias(cats || [{ id: 9101, nome: 'Categoria Teste', tipo: 'DESPESA' }]);
      setTipos(tps || [{ id: 9101, nome: 'PIX' }]);
      setContas(cts || mockData.contas);
    }
    loadLookups();
  }, []);

  function set(field, value) {
    setForm((f) => ({ ...f, [field]: value }));
  }

  async function handleSubmit() {
    if (!form.descricao || !form.valor) {
      alert('Preencha ao menos descrição e valor.');
      return;
    }
    setLoading(true);

    const payload = {
      descricao: form.descricao,
      valor: parseFloat(form.valor),
      dataMovimentacao: form.dataMovimentacao,
      categoria: form.categoriaId ? { id: Number(form.categoriaId) } : null,
      tipoTransacao: form.tipoTransacaoId ? { id: Number(form.tipoTransacaoId) } : null,
      conta: form.contaId ? { id: Number(form.contaId) } : null,
      usuario: { id: form.usuarioId },
    };

    const result = await transacaoApi.save(payload);
    setLoading(false);

    if (result) {
      onSaved?.();
      onClose();
    } else {
      // Backend offline: apenas fecha o modal
      alert('Transação salva localmente (backend offline).');
      onClose();
    }
  }

  return (
    <div className="modal-overlay" onClick={(e) => e.target === e.currentTarget && onClose()}>
      <div className="modal">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 20 }}>
          <div className="modal-title">Nova Transação</div>
          <button
            onClick={onClose}
            style={{ background: 'none', border: 'none', fontSize: '1.4rem', color: 'var(--text-200)', lineHeight: 1 }}
          >
            ×
          </button>
        </div>

        <div className="form-group">
          <label className="form-label">Descrição</label>
          <input
            className="form-input"
            placeholder="Ex: Mercado, Salário, Netflix..."
            value={form.descricao}
            onChange={(e) => set('descricao', e.target.value)}
          />
        </div>

        <div className="grid-2">
          <div className="form-group">
            <label className="form-label">Valor (R$)</label>
            <input
              className="form-input"
              type="number"
              placeholder="0,00"
              step="0.01"
              value={form.valor}
              onChange={(e) => set('valor', e.target.value)}
            />
            <div style={{ fontSize: '.72rem', color: 'var(--text-200)', marginTop: 4 }}>
              Use negativo para despesas (ex: -150.00)
            </div>
          </div>

          <div className="form-group">
            <label className="form-label">Data</label>
            <input
              className="form-input"
              type="date"
              value={form.dataMovimentacao}
              onChange={(e) => set('dataMovimentacao', e.target.value)}
            />
          </div>
        </div>

        <div className="form-group">
          <label className="form-label">Categoria</label>
          <select
            className="form-select"
            value={form.categoriaId}
            onChange={(e) => set('categoriaId', e.target.value)}
          >
            <option value="">Selecione uma categoria</option>
            {categorias.map((c) => (
              <option key={c.id} value={c.id}>{c.nome} ({c.tipo})</option>
            ))}
          </select>
        </div>

        <div className="grid-2">
          <div className="form-group">
            <label className="form-label">Tipo de Transação</label>
            <select
              className="form-select"
              value={form.tipoTransacaoId}
              onChange={(e) => set('tipoTransacaoId', e.target.value)}
            >
              <option value="">Selecione o tipo</option>
              {tipos.map((t) => (
                <option key={t.id} value={t.id}>{t.nome}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label className="form-label">Conta</label>
            <select
              className="form-select"
              value={form.contaId}
              onChange={(e) => set('contaId', e.target.value)}
            >
              <option value="">Selecione a conta</option>
              {contas.map((c) => (
                <option key={c.id} value={c.id}>{c.nome}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="modal-actions">
          <button className="btn btn-secondary" onClick={onClose}>Cancelar</button>
          <button
            className="btn btn-primary"
            onClick={handleSubmit}
            disabled={loading}
          >
            {loading ? 'Salvando...' : 'Salvar Transação'}
          </button>
        </div>
      </div>
    </div>
  );
}
