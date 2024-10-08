<?php
include 'db_connection.php'; // Incluir el archivo de conexión

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Obtener datos de la solicitud
    $phoneNumber = $_POST['phoneNumber'];
    $password = $_POST['password'];

    // Consulta para verificar el usuario
    $sql = "SELECT * FROM cuentas WHERE numero_telefono = ? AND contraseña = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $phoneNumber, $password); // Usar binding para prevenir inyecciones SQL
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        // Usuario encontrado
        $user = $result->fetch_assoc();
        // Aquí podrías establecer una sesión, enviar un token, etc.
        echo json_encode(array('success' => true, 'message' => 'Inicio de sesión exitoso', 'user' => $user));
    } else {
        // Usuario no encontrado
        echo json_encode(array('success' => false, 'message' => 'Número de teléfono o contraseña incorrectos'));
    }

    $stmt->close();
}

$conn->close();
?>
