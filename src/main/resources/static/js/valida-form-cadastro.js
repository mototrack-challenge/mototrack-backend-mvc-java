document.addEventListener("DOMContentLoaded", () => {
        const msg = document.getElementById("cadastroSucesso");
        if (msg) {
            setTimeout(() => {
                window.location.href = "/login";
            }, 2000);
        }
    });

document.getElementById("loginForm").addEventListener("submit", function(event) {
    let valido = true;
    let mensagens = [];

    // Nome
    const nome = document.getElementById("nome").value.trim();
    if (nome.length < 3 || nome.length > 150) {
        mensagens.push("O nome deve ter entre 3 e 150 caracteres.");
        valido = false;
    }

    // Email
    const email = document.getElementById("email").value.trim();
    if (email.length > 150) {
        mensagens.push("Digite um email válido com no máximo 150 caracteres.");
        valido = false;
    }

    // Senha
    const senha = document.getElementById("password").value.trim();
    if (senha.length < 6 || senha.length > 150) {
        mensagens.push("A senha deve ter entre 6 e 150 caracteres.");
        valido = false;
    }

    // Mostra mensagens na div
    const msgDiv = document.getElementById("validacaoMensagem");
    msgDiv.innerHTML = "";
    if (!valido) {
        event.preventDefault();
        msgDiv.style.display = "block";
        mensagens.forEach(msg => {
            const p = document.createElement("p");
            p.textContent = msg;
            msgDiv.appendChild(p);
        });
    } else {
        msgDiv.style.display = "none";
    }
});