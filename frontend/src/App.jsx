import heroImg from './assets/BuilderBankLogo.png'
import './App.css'
import React, { useState } from 'react';
import Barcode from 'react-barcode';

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
  const [resultado, setResultado] = useState(null);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  /*
  const enviarParaBackEnd = async (e) => {
    e.preventDefault();
    setResultado(null); // Limpa resultados anteriores

    const hoje = new Date();
    hoje.setHours(0,0,0,0);
    if (new Date(formData.dueDate) <= hoje) {
      return alert("A data de vencimento deve ser uma data futura.");
    }

    setLoading(true);

    try {
      const response = await fetch('http://localhost:8080/api/payments/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error("Erro no processamento do boleto.");
      }

      const data = await response.json();
      console.log("Boleto gerado pelo Builder:", data);

      setResultado(data);

    } catch (error) {
      console.error("Erro na comunicação:", error);
      alert("Erro ao gerar boleto. Verifique se o Back-End está ligado");
    } finally {
      setLoading(false);
    }
  };


   */

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

      // Abre o PDF em uma nova aba ou faz o download
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

            {resultado && (
                <div className="boleto-container">
                  <div className="boleto-header">
                    <img src={resultado.logo} alt="Banco" className="banco-logo-img" />
                    <div className="linha-nomeBanco">{resultado.banco}</div>
                    <div className="banco-codigo">{resultado.codigoBanco}</div>
                    <div className="linha-digitavel">{resultado.linhaDigitavel}</div>
                  </div>

                  <div className="boleto-grid">
                    <div className="cell col-4">
                      <label>Local de Pagamento</label>
                      <div className="value">PAGÁVEL EM QUALQUER BANCO ATÉ O VENCIMENTO</div>
                    </div>
                    <div className="cell col-1">
                      <label>Vencimento</label>
                      <div className="value bold">{resultado.vencimento}</div>
                    </div>

                    <div className="cell2 col-4">
                      <div>
                        <label>Cedente / Beneficiário</label>
                        <div className="value">{resultado.beneficiario}</div>
                      </div>

                      <div className="col-7">
                        <label>CPF/CNPJ:</label>
                        <div className="value">{resultado.documentoBeneficiario}</div>
                      </div>

                    </div>




                    <div className="cell col-1">
                      <label>Agência / Código Cedente</label>
                      <div className="value">{resultado.agenciaCodigoBeneficiario}</div>
                    </div>

                    <div className="cell">
                      <label>Data do Documento</label>
                      <div className="value">{new Date().toLocaleDateString('pt-BR')}</div>
                    </div>
                    <div className="cell">
                      <label>Nº do Documento</label>
                      <div className="value">{resultado.numeroDocumento}</div>
                    </div>
                    <div className="cell">
                      <label>Espécie Doc.</label>
                      <div className="value">{resultado.especieDoc}</div>
                    </div>
                    <div className="cell">
                      <label>Aceite</label>
                      <div className="value">N</div>
                    </div>
                    <div className="cell">
                      <label>Nosso Número</label>
                      <div className="value">{resultado.nossoNumero}</div>
                    </div>

                    <div className="cell col-4">
                      <label>Instruções</label>
                      <div className="value instrucoes">
                        - Sr. Caixa, cobrar multa de 2% após o vencimento<br/>
                        - Receber até 10 dias após o vencimento<br/>
                        - Builder Pattern (K19) - BuilderBank
                      </div>
                    </div>
                    <div className="cell col-1">
                      <label>(=) Valor do Documento</label>
                      <div className="value bold">R$ {resultado.valor ? resultado.valor.toFixed(2) : "0,00"}</div>
                    </div>

                    <div className="cell col-5 pagador-cell">
                      <label>Sacado</label>
                      <div className="value">
                        <strong>{resultado.pagador}</strong><br/>
                        <div className="restInformations">
                          <div>CPF/CNPJ: {resultado.documentoPagador}</div>
                          <div>Endereço: {resultado.endePagador}</div>
                          <div>Cidade: {resultado.cidaPagador}</div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div className="barcode-area">
                    <div className="barcode-real">
                      <Barcode
                          value={resultado.codigoBarras}
                          format="ITF"
                          width={1.2}
                          height={70}
                          displayValue={false}
                          background="#ffffff"
                          lineColor="#000000"
                          margin={0}
                      />
                    </div>
                    <div className="barcode-text">{resultado.codigoBarras}</div>
                  </div>

                  <button onClick={() => window.print()} className="formBTN print-btn">
                    Salva Boleto
                  </button>
                </div>
            )}
          </div>
        </section>
      </>
  )
}

export default App;