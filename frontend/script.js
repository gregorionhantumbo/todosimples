const userId = 2;  // ID do usuário fixo por enquanto (deve ser dinâmico)
const apiUrl = `http://localhost:8080/task/user/${userId}`;

async function loadTasks() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error("Erro ao carregar tarefas");

        const tasks = await response.json();
        const taskList = document.getElementById("taskList");
        taskList.innerHTML = "";

        tasks.forEach(task => {
            const li = document.createElement("li");
            li.innerHTML = `${task.description}
                <button onclick="deleteTask(${task.id})">X</button>`;
            taskList.appendChild(li);
        });
    } catch (error) {
        console.error(error);
    }
}

async function addTask() {
    const taskInput = document.getElementById("taskInput");
    const taskDescription = taskInput.value.trim();

    if (taskDescription === "") return alert("Digite uma tarefa!");

    const newTask = { user: { id: userId }, description: taskDescription };

    try {
        const response = await fetch("http://localhost:8080/task", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newTask)
        });

        if (!response.ok) throw new Error("Erro ao adicionar tarefa");

        taskInput.value = "";
        loadTasks();
    } catch (error) {
        console.error(error);
    }
}

async function deleteTask(taskId) {
    try {
        const response = await fetch(`http://localhost:8080/task/${taskId}`, {
            method: "DELETE"
        });

        if (!response.ok) throw new Error("Erro ao excluir tarefa");

        loadTasks();
    } catch (error) {
        console.error(error);
    }
}

// Carrega as tarefas ao abrir a página
loadTasks();
