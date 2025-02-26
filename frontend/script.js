const apiUrl = "http://localhost:8080/task/user"; // A URL da API para buscar as tarefas
let currentTaskId = null; // Usado para armazenar o ID da tarefa sendo editada

// Função para carregar tarefas de um usuário
async function loadTasks() {
    const userId = document.getElementById("userId").value; // Pega o ID do usuário
    if (!userId) {
        alert("Por favor, forneça o ID do usuário.");
        return;
    }

    document.getElementById("loading").style.display = "flex"; // Exibe o loading

    try {
        const response = await fetch(`${apiUrl}/${userId}`, { method: "GET" });
        if (!response.ok) throw new Error("Erro ao carregar as tarefas");

        const tasks = await response.json(); // Recebe as tarefas no formato JSON
        displayTasks(tasks); // Exibe as tarefas na tela
    } catch (error) {
        console.error("Erro:", error);
        alert("Erro ao carregar as tarefas.");
    } finally {
        document.getElementById("loading").style.display = "none"; // Esconde o loading
    }
}

// Função para exibir as tarefas na tabela
function displayTasks(tasks) {
    const taskTable = document.getElementById("taskTable");
    taskTable.innerHTML = ""; // Limpa a tabela antes de adicionar as novas tarefas

    tasks.forEach(task => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${task.id}</td>
            <td>${task.description}</td>
            <td>${task.user.username}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editTask(${task.id}, '${task.description}')">Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteTask(${task.id})">Excluir</button>
            </td>
        `;
        taskTable.appendChild(row);
    });
}

// Função para abrir o modal de edição de tarefa
function editTask(taskId, taskDescription) {
    currentTaskId = taskId; // Armazena o ID da tarefa
    document.getElementById("taskDescription").value = taskDescription; // Preenche o campo de descrição no modal
    const modal = new bootstrap.Modal(document.getElementById("editTaskModal"));
    modal.show();
}

// Função para atualizar a tarefa
async function updateTask() {
    const description = document.getElementById("taskDescription").value;

    if (!description) {
        alert("Por favor, forneça uma descrição válida.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/task/${currentTaskId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                id: currentTaskId,
                description: description,
                user: { id: 1 } // Presume-se que o usuário está com ID 1
            })
        });

        if (!response.ok) throw new Error("Erro ao atualizar a tarefa");

        const updatedTask = await response.json(); // Tarefa atualizada
        alert("Tarefa atualizada com sucesso.");
        loadTasks(); // Recarrega as tarefas
    } catch (error) {
        console.error("Erro:", error);
        alert("Erro ao atualizar a tarefa.");
    }
}

// Função para excluir uma tarefa
async function deleteTask(taskId) {
    try {
        const response = await fetch(`http://localhost:8080/task/${taskId}`, { method: "DELETE" });
        if (!response.ok) throw new Error("Erro ao excluir a tarefa");

        alert("Tarefa excluída com sucesso.");
        loadTasks(); // Recarrega as tarefas
    } catch (error) {
        console.error("Erro:", error);
        alert("Erro ao excluir a tarefa.");
    }
}
