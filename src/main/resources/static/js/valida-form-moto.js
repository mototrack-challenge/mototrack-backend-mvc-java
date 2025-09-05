const formulario = document.getElementById("form_moto");
const mensagensErroContainer = document.getElementById("mensagensErro");

// Elementos de erro individuais
const erroModelo = document.getElementById("erroModelo");
const erroPlaca = document.getElementById("erroPlaca");
const erroChassi = document.getElementById("erroChassi");
const erroStatus = document.getElementById("erroStatus");

// Campos do formulário
const campoModelo = document.getElementById("modelo");
const campoPlaca = document.getElementById("placa");
const campoChassi = document.getElementById("chassi");
const campoStatus = document.getElementById("status");

// Função para limpar erros
function limparErros() {
  erroModelo.classList.remove("visivel");
  erroPlaca.classList.remove("visivel");
  erroChassi.classList.remove("visivel");
  erroStatus.classList.remove("visivel");
  mensagensErroContainer.classList.remove("visivel");
  mensagensErroContainer.innerHTML = "";

  // Remover classe de erro dos campos
  campoModelo.classList.remove("input-erro");
  campoPlaca.classList.remove("input-erro");
  campoChassi.classList.remove("input-erro");
  campoStatus.classList.remove("input-erro");
}

// Função para mostrar erro em um campo específico
function mostrarErro(campo, elementoErro, mensagem) {
  campo.classList.add("input-erro");
  elementoErro.textContent = mensagem;
  elementoErro.classList.add("visivel");
}

// Validar formato da placa (exemplo: ABC1D23 ou ABC1234)
function validarPlaca(placa) {
  const regexPlaca = /^[A-Z]{3}\d{1}[A-Z]{1}\d{2}$|^[A-Z]{3}\d{4}$/;
  return regexPlaca.test(placa.toUpperCase().replace(/[^A-Z0-9]/g, ""));
}

// Validar formato do chassi (17 caracteres alfanuméricos)
function validarChassi(chassi) {
  const regexChassi = /^[A-HJ-NPR-Z0-9]{17}$/;
  return regexChassi.test(chassi.toUpperCase().replace(/[^A-Z0-9]/g, ""));
}

// Validar formulário
function validarFormulario() {
  let valido = true;
  const erros = [];

  limparErros();

  // Validar modelo
  if (!campoModelo.value) {
    mostrarErro(campoModelo, erroModelo, "Por favor, selecione um modelo");
    valido = false;
    erros.push("Selecione um modelo");
  }

  // Validar placa
  if (!campoPlaca.value) {
    mostrarErro(campoPlaca, erroPlaca, "Por favor, insira a placa");
    valido = false;
    erros.push("Informe a placa");
  } else if (!validarPlaca(campoPlaca.value)) {
    mostrarErro(
      campoPlaca,
      erroPlaca,
      "Formato de placa inválido. Use: ABC1D23 ou ABC1234"
    );
    valido = false;
    erros.push("Placa com formato inválido");
  }

  // Validar chassi
  if (!campoChassi.value) {
    mostrarErro(campoChassi, erroChassi, "Por favor, insira o chassi");
    valido = false;
    erros.push("Informe o chassi");
  } else if (!validarChassi(campoChassi.value)) {
    mostrarErro(
      campoChassi,
      erroChassi,
      "Chassi deve ter 17 caracteres alfanuméricos"
    );
    valido = false;
    erros.push("Chassi com formato inválido");
  }

  // Validar status
  if (!campoStatus.value) {
    mostrarErro(campoStatus, erroStatus, "Por favor, selecione um status");
    valido = false;
    erros.push("Selecione um status");
  }

  // Mostrar mensagens de erro gerais se houver
  if (!valido) {
    mensagensErroContainer.innerHTML = `
                        <strong>Por favor, corrija os seguintes erros:</strong>
                        <ul>
                            ${erros.map((erro) => `<li>${erro}</li>`).join("")}
                        </ul>
                    `;
    mensagensErroContainer.classList.add("visivel");

    // Rolar para o topo do formulário para mostrar os erros
    mensagensErroContainer.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  }

  return valido;
}

// Event listeners para validação em tempo real
campoModelo.addEventListener("change", function () {
  if (this.value) {
    this.classList.remove("input-erro");
    erroModelo.classList.remove("visivel");
  }
});

campoPlaca.addEventListener("input", function () {
  if (this.value && validarPlaca(this.value)) {
    this.classList.remove("input-erro");
    erroPlaca.classList.remove("visivel");
  }
});

campoChassi.addEventListener("input", function () {
  if (this.value && validarChassi(this.value)) {
    this.classList.remove("input-erro");
    erroChassi.classList.remove("visivel");
  }
});

campoStatus.addEventListener("change", function () {
  if (this.value) {
    this.classList.remove("input-erro");
    erroStatus.classList.remove("visivel");
  }
});

// Submit do formulário
formulario.addEventListener("submit", function (e) {
  // Antes de validar e enviar, esconder a mensagem de erro de backend
  const mensagensExistencia = document.querySelectorAll(".mensagem-erro-placa-chassi-existe");
  mensagensExistencia.forEach((msg) => {
    msg.style.display = "none";
  });

  // Validar formulário no frontend
  if (!validarFormulario()) {
    e.preventDefault(); // impede envio se houver erro no JS
  }
});

// Botão cancelar
document.querySelector(".btn_cancelar").addEventListener("click", function () {
  if (
    confirm(
      "Tem certeza que deseja cancelar? Todas as informações preenchidas serão perdidas."
    )
  ) {
    window.location.href = "/motos";
  }
});
