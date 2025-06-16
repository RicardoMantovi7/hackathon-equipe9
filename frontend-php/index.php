<?php
$api_base = 'http://localhost:3000/api'; // Altere para sua API Node/Java
?>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Eventos</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
        nav { background: #333; padding: 15px 0; margin-bottom: 20px; }
        nav ul { list-style: none; display: flex; justify-content: center; gap: 20px; }
        nav a { color: white; text-decoration: none; padding: 10px 20px; border-radius: 5px; }
        nav a:hover, nav a.active { background: #007bff; }
        .card { background: white; padding: 20px; margin: 10px 0; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .form-group { margin: 10px 0; }
        input, select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        button { background: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #f8f9fa; }
        .loading { text-align: center; padding: 20px; }
    </style>
</head>
<body>
    <nav>
        <ul>
            <li><a href="?page=home" class="<?= (!isset($_GET['page']) || $_GET['page'] == 'home') ? 'active' : '' ?>">Home</a></li>
            <li><a href="?page=alunos" class="<?= $_GET['page'] == 'alunos' ? 'active' : '' ?>">Alunos</a></li>
            <!--<li><a href="?page=palestrantes" class="<?= $_GET['page'] == 'palestrantes' ? 'active' : '' ?>">Palestrantes</a></li>-->
            <li><a href="?page=eventos" class="<?= $_GET['page'] == 'eventos' ? 'active' : '' ?>">Eventos</a></li>
            <!-- <li><a href="?page=inscricoes" class="<?= $_GET['page'] == 'inscricoes' ? 'active' : '' ?>">Inscrições</a></li> -->
        </ul>
    </nav>

    <div class="container">
        <?php
        $page = $_GET['page'] ?? 'home';
        switch($page) {
            case 'alunos': include 'pages/alunos.php'; break;
            case 'palestrantes': include 'pages/palestrantes.php'; break;
            case 'eventos': include 'pages/eventos.php'; break;
            case 'inscricoes': include 'pages/inscricoes.php'; break;
            default: echo '<div class="card"><h1>Sistema de Eventos</h1><p>Bem-vindo! Selecione uma opção no menu.</p></div>';
        }
        ?>
    </div>

    <script>
        const API_BASE = '<?= $api_base ?>';
        
        async function apiCall(endpoint, method = 'GET', data = null) {
            const config = {
                method,
                headers: { 'Content-Type': 'application/json' }
            };
            if (data) config.body = JSON.stringify(data);
            
            try {
                const response = await fetch(API_BASE + endpoint, config);
                return await response.json();
            } catch (error) {
                console.error('API Error:', error);
                return { error: 'Erro na API' };
            }
        }
    </script>
</body>
</html>
