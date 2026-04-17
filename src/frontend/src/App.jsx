import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Dashboard from './components/Dashboard';
import Transactions from './components/Transactions';
import Goals from './components/Goals';
import AIAssistant from './components/AIAssistant';
import NewTransactionModal from './components/NewTransactionModal';

export default function App() {
  const [showModal, setShowModal] = useState(false);

  return (
    <BrowserRouter>
      <div className="app-layout">
        {/* Sidebar — fixada à esquerda, sempre visível */}
        <Sidebar onNewTransaction={() => setShowModal(true)} />

        {/* Conteúdo principal */}
        <main className="main-content">
          <Routes>
            <Route path="/"           element={<Dashboard />} />
            <Route path="/transacoes" element={<Transactions />} />
            <Route path="/metas"      element={<Goals />} />
            <Route path="/assistente" element={<AIAssistant />} />
          </Routes>
        </main>

        {/* Modal de nova transação — sobreposta à página */}
        {showModal && (
          <NewTransactionModal
            onClose={() => setShowModal(false)}
            onSaved={() => {
              // Aqui você pode acionar um refresh global de dados
              // por exemplo via Context ou passando callback props
            }}
          />
        )}
      </div>
    </BrowserRouter>
  );
}
