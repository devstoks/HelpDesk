let currentStep = 1;
const totalSteps = 2;

function updateStepDisplay() {

    // Mostrar/esconder steps
    document.getElementById('step1').classList.toggle('active', currentStep === 1);
    document.getElementById('step2').classList.toggle('active', currentStep === 2);

    // Atualizar indicadores
    document.getElementById('step1Indicator').classList.toggle('active', currentStep === 1);
    document.getElementById('step2Indicator').classList.toggle('active', currentStep === 2);

    // Marcar como completado
    document.getElementById('step1Indicator').classList.toggle('completed', currentStep > 1);

    // Atualizar botões
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    const submitBtn = document.getElementById('submitBtn');

    if (currentStep === 1) {

        prevBtn.style.display = 'none';
        nextBtn.style.display = 'inline-flex';
        submitBtn.style.display = 'none';

    } else if (currentStep === totalSteps) {

        prevBtn.style.display = 'inline-flex';
        nextBtn.style.display = 'none';
        submitBtn.style.display = 'inline-flex';

    } else {

        prevBtn.style.display = 'inline-flex';
        nextBtn.style.display = 'inline-flex';
        submitBtn.style.display = 'none';
    }

    // Atualizar barra de progresso
    const progressPercent = ((currentStep - 1) / (totalSteps - 1)) * 100;

    document.getElementById('progressFill').style.width = progressPercent + '%';
}

function validateStep(step) {

    const step1Inputs = document.querySelectorAll('#step1 input[required]');
    const step2Inputs = document.querySelectorAll('#step2 input[required]');

    if (step === 1) {

        for (let input of step1Inputs) {

            if (!input.value.trim()) {

                input.style.borderColor = '#ef4444';
                input.focus();

                showTemporaryMessage(
                    'Por favor, preencha todos os campos obrigatórios.',
                    'error'
                );

                return false;
            }

            input.style.borderColor = '';
        }

        // Validação de email
        const email = document.getElementById('email').value;

        if (
            !email.includes('@') ||
            !email.includes('.') ||
            email.startsWith('@') ||
            email.endsWith('@')
        ) {

            document.getElementById('email').style.borderColor = '#ef4444';

            showTemporaryMessage(
                'Por favor, insira um e-mail válido.',
                'error'
            );

            return false;
        }

        // Validação de senha
        const senha = document.getElementById('senha').value;

        if (senha.length < 6) {

            document.getElementById('senha').style.borderColor = '#ef4444';

            showTemporaryMessage(
                'A senha deve ter no mínimo 6 caracteres.',
                'error'
            );

            return false;
        }
    }

    if (step === 2) {

        for (let input of step2Inputs) {

            if (!input.value.trim()) {

                input.style.borderColor = '#ef4444';
                input.focus();

                showTemporaryMessage(
                    'Por favor, preencha todos os campos obrigatórios.',
                    'error'
                );

                return false;
            }

            input.style.borderColor = '';
        }
    }

    return true;
}

function showTemporaryMessage(msg, type) {

    // Remove mensagem existente
    const existingMsg = document.querySelector('.message-alert');

    if (existingMsg) {
        existingMsg.remove();
    }

    // Cria nova mensagem
    const form = document.getElementById('registerForm');

    const messageDiv = document.createElement('div');

    messageDiv.className = `message-alert ${type}`;

    messageDiv.innerHTML = `
        <i class="fas ${type === 'error'
            ? 'fa-exclamation-triangle'
            : 'fa-check-circle'}"></i>
        <span>${msg}</span>
    `;

    // Insere antes dos botões
    const buttons = document.querySelector('.buttons');

    form.insertBefore(messageDiv, buttons);

    // Remove após 3 segundos
    setTimeout(() => {

        if (messageDiv) {
            messageDiv.remove();
        }

    }, 3000);
}

function changeStep(direction) {

    if (direction === 1 && !validateStep(currentStep)) {
        return;
    }

    const newStep = currentStep + direction;

    if (newStep >= 1 && newStep <= totalSteps) {

        currentStep = newStep;

        updateStepDisplay();
    }
}

// ==========================
// BUSCAR CEP
// ==========================

document.getElementById('cep')?.addEventListener('blur', async function () {

    let cep = this.value;
    let numeros = "";

    for (let i = 0; i < cep.length; i++) {

        const char = cep[i];

        if (char >= '0' && char <= '9') {
            numeros += char;
        }
    }

});

// ==========================
// CPF
// ==========================

document.getElementById('cpf')?.addEventListener('input', function (e) {

    let valor = e.target.value;
    let numeros = "";

    // Remove tudo que não for número
    for (let i = 0; i < valor.length; i++) {

        const char = valor[i];

        if (char >= '0' && char <= '9') {
            numeros += char;
        }
    }

    // Limite
    if (numeros.length > 11) {
        numeros = numeros.substring(0, 11);
    }

    // Formata
    let formatado = "";

    for (let i = 0; i < numeros.length; i++) {

        if (i === 3 || i === 6) {
            formatado += ".";
        }

        if (i === 9) {
            formatado += "-";
        }

        formatado += numeros[i];
    }

    e.target.value = formatado;
});

// ==========================
// TELEFONE
// ==========================

document.getElementById('telefone')?.addEventListener('input', function (e) {

    let valor = e.target.value;
    let numeros = "";

    for (let i = 0; i < valor.length; i++) {

        const char = valor[i];

        if (char >= '0' && char <= '9') {
            numeros += char;
        }
    }

    // Limite
    if (numeros.length > 11) {
        numeros = numeros.substring(0, 11);
    }

    // Formatação
    let formatado = "";

    for (let i = 0; i < numeros.length; i++) {

        if (i === 0) {
            formatado += "(";
        }

        if (i === 2) {
            formatado += ") ";
        }

        if (i === 7) {
            formatado += "-";
        }

        formatado += numeros[i];
    }

    e.target.value = formatado;
});

// ==========================
// CEP
// ==========================

document.getElementById('cep')?.addEventListener('input', function (e) {

    let valor = e.target.value;
    let numeros = "";

    for (let i = 0; i < valor.length; i++) {

        const char = valor[i];

        if (char >= '0' && char <= '9') {
            numeros += char;
        }
    }

    // Limite
    if (numeros.length > 8) {
        numeros = numeros.substring(0, 8);
    }

    // Formatação
    let formatado = "";

    for (let i = 0; i < numeros.length; i++) {

        if (i === 5) {
            formatado += "-";
        }

        formatado += numeros[i];
    }

    e.target.value = formatado;
});

// ==========================
// INICIALIZAR
// ==========================

updateStepDisplay();