let userIdParaExcluir = null;

function abrirModal(id) {
    userIdParaExcluir = id;
    document.getElementById('modalExcluir').style.display = 'flex';
}

function fecharModal() {
    userIdParaExcluir = null;
    document.getElementById('modalExcluir').style.display = 'none';
}

document.getElementById('confirmarExcluir').addEventListener('click', function() {
    if(userIdParaExcluir) {
      window.location.href = '/usuarios/deletar/' + userIdParaExcluir;
    }
});