let motoIdParaExcluir = null;

function abrirModal(id) {
    motoIdParaExcluir = id;
    document.getElementById('modalExcluir').style.display = 'flex';
}

function fecharModal() {
    motoIdParaExcluir = null;
    document.getElementById('modalExcluir').style.display = 'none';
}

document.getElementById('confirmarExcluir').addEventListener('click', function() {
    if(motoIdParaExcluir) {
      window.location.href = '/motos/deletar/' + motoIdParaExcluir;
    }
});