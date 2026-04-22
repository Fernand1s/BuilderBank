import heroImg from './assets/BuilderBankLogo.png'
import './App.css'
import React, { useState } from 'react';

function App() {
  const [formData, setFormData] = useState({
    customerName: '',
    cpfCnpj: '',
    custumerEnde: '',
    custumerCida: '',
    value: '',
    dueDate: '',
    bankSelection: 'BRADESCO',
    cedenteNome: '',
    cedenteCpfCnpj: '',
    cedenteAgencia: ''
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const enviarParaBackEnd = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await fetch('http://localhost:8080/api/payments/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (!response.ok) throw new Error("Erro ao gerar PDF.");

      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);

      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'boleto.pdf');
      document.body.appendChild(link);
      link.click();
      link.parentNode.removeChild(link);

      // eslint-disable-next-line no-unused-vars
    } catch (error) {
      alert("Erro ao gerar boleto.");
    } finally {
      setLoading(false);
    }
  };


  return (
      <>
        <section id="center">
          <div className="hero">
            <img src={heroImg} className="base" alt="Logo BuilderBank" />
          </div>
          <div className="main">
            <p className="inTextTitle"> Gere seu boleto abaixo </p>

            <form className="BankDiv" onSubmit={enviarParaBackEnd}>
              <div className="BankForm">
                <div className="l">
                  <div className="form">
                    <p className="inText"> Nome do Sacado </p>
                    <input name="customerName" className="in" type="text" onChange={handleChange} value={formData.customerName} required />
                  </div>

                  <div className="form">
                    <p className="inText"> CPF/CNPJ do Sacado </p>
                    <input name="cpfCnpj" className="in" type="text" onChange={handleChange} value={formData.cpfCnpj} required />
                  </div>

                  <div className="form">
                    <p className="inText"> Endereço do Sacado </p>
                    <input name="custumerEnde" className="in" type="text" onChange={handleChange} value={formData.custumerEnde} required />
                  </div>

                  <div className="form">
                    <p className="inText"> Cidade do Sacado </p>
                    <input name="custumerCida" className="in" type="text" onChange={handleChange} value={formData.custumerCida} required />
                  </div>

                  <div className="form">
                    <p className="inText"> Nome do Cedente </p>
                    <input name="cedenteNome" className="in" type="text" onChange={handleChange} value={formData.cedenteNome} required />
                  </div>
                </div>

                <div className="r">

                  <div className="form">
                    <p className="inText"> CPF/CNPJ do Cedente </p>
                    <input name="cedenteCpfCnpj" className="in" type="text" onChange={handleChange} value={formData.cedenteCpfCnpj} required />
                  </div>

                  <div className="form">
                    <p className="inText"> Agencia do Cedente </p>
                    <input name="cedenteAgencia" className="in" type="text" onChange={handleChange} value={formData.cedenteAgencia} required />
                  </div>

                  <div className="form">
                    <p className="inText"> Valor do Boleto </p>
                    <input name="value" className="in" type="number" step="0.01" onChange={handleChange} value={formData.value} required />
                  </div>

                  <div className="form">
                    <p className="inText"> Vencimento do Boleto </p>
                    <input name="dueDate" className="in" type="date" onChange={handleChange} value={formData.dueDate} required />
                  </div>

                  <div className="form">
                    <p className="inText"> Selecione o Banco </p>
                    <select name="bankSelection" className="inSelect" onChange={handleChange} value={formData.bankSelection}>
                      <option value="BRADESCO">Bradesco</option>
                      <option value="NUBANK">Nubank</option>
                      <option value="UNIBANCO">Unibanco</option>
                    </select>
                  </div>
                </div>
              </div>

              <div>
                <button type="submit" className="formBTN" disabled={loading}>
                  {loading ? 'Processando...' : 'Gerar Boleto'}
                </button>
              </div>
            </form>


          </div>
        </section>
      </>
  )
}

export default App;