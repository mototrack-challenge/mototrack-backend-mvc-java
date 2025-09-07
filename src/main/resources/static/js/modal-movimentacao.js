// ==================== MODAL CADASTRO ====================
const modalCadastrar = document.getElementById("modalCadastrarMovimentacao");
const btnAbrirCadastro = document.getElementById("btnNovaMovimentacao");
const btnFecharCadastro = document.querySelector("#modalCadastrarMovimentacao .fechar_modal");
const btnCancelarCadastro = document.getElementById("btnCancelar");
const form = document.getElementById("formMovimentacao");

// Abrir modal de cadastro
btnAbrirCadastro.addEventListener("click", () => {
    modalCadastrar.style.display = "flex";
});

// Fechar modal de cadastro
function fecharModalCadastro() {
    modalCadastrar.style.display = "none";
}

btnFecharCadastro.addEventListener("click", fecharModalCadastro);
btnCancelarCadastro.addEventListener("click", fecharModalCadastro);

// Fechar clicando fora do modal de cadastro
window.addEventListener("click", (event) => {
    if (event.target === modalCadastrar) {
        fecharModalCadastro();
    }
});

// ==================== MODAL EXCLUSÃO ====================
let idParaExcluir = null;
const modalExcluir = document.getElementById("modalExcluir");
const btnConfirmarExcluir = document.getElementById("confirmarExcluir");

// Abrir modal de exclusão
function abrirModalExcluir(id) {
    idParaExcluir = id;
    modalExcluir.style.display = "flex";
}

// Fechar modal de exclusão
function fecharModalExcluir() {
    idParaExcluir = null;
    modalExcluir.style.display = "none";
}

// Confirmar exclusão
btnConfirmarExcluir.addEventListener("click", () => {
    if (idParaExcluir) {
        // Redireciona para a rota do backend passando o ID
        window.location.href = "/movimentacoes/deletar/" + idParaExcluir;
    }
});

// Fechar clicando fora do modal de exclusão
window.addEventListener("click", (event) => {
    if (event.target === modalExcluir) {
        fecharModalExcluir();
    }
});
